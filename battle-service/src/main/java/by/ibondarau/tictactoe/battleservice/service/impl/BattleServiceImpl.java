package by.ibondarau.tictactoe.battleservice.service.impl;

import by.ibondarau.tictactoe.battleservice.checker.GameChecker;
import by.ibondarau.tictactoe.battleservice.dao.BattleDao;
import by.ibondarau.tictactoe.battleservice.exception.BadMoveException;
import by.ibondarau.tictactoe.battleservice.exception.BusinessException;
import by.ibondarau.tictactoe.battleservice.exception.NotFoundException;
import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleResult;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.model.FirstMoveRule;
import by.ibondarau.tictactoe.battleservice.model.Move;
import by.ibondarau.tictactoe.battleservice.service.BattleService;
import by.ibondarau.tictactoe.battleservice.util.BattleUtils;
import by.ibondarau.tictactoe.battleservice.util.RandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleDao battleDao;
    private final RandomUtils randomUtils;
    private final BattleUtils battleUtils;

    private final GameChecker gameChecker;

    @Override
    public Battle createBattle(UUID playerId, int size, FirstMoveRule firstMoveRule) {
        Battle battle = new Battle()
                .setFirstPlayerId(playerId)
                .setSize(size)
                .setFirstMoveRule(firstMoveRule)
                .setStatus(BattleStatus.CREATED);
        battleDao.save(battle);
        return battle;
    }

    @Override
    public Battle getBattle(UUID battleId) {
        return battleDao
                .findBattleByBattleId(battleId)
                .orElseThrow(() -> new NotFoundException("Battle with id " + battleId + " not found"));
    }

    @Override
    public Battle joinBattle(UUID battleId, UUID secondPlayerId) {
        Battle battle = battleDao.findBattleByBattleId(battleId).orElseThrow(() -> new NotFoundException("Battle with id " + battleId + " not found"));

        if (!BattleStatus.CREATED.equals(battle.getStatus()) || battle.getSecondPlayerId() != null) {
            throw new BusinessException("Unable to join: invalid battle status");
        }

        battle.setSecondPlayerId(secondPlayerId);
        battle.setStatus(BattleStatus.STARTED);
        battle.setStarted(Timestamp.from(ZonedDateTime.now().toInstant()));
        setFirstMovingPlayer(battle);
        battle = battleDao.save(battle);
        return battle;
    }

    private void setFirstMovingPlayer(Battle battle) {
        battle.setFirstMovingPlayerId(switch (battle.getFirstMoveRule()) {
            case FIRST_GOES_FIRST -> battle.getFirstPlayerId();
            case SECOND_GOES_FIRST -> battle.getSecondPlayerId();
            case RANDOM -> randomUtils.getRandomOf(battle.getFirstPlayerId(), battle.getSecondPlayerId());
        });
    }

    @Override
    public Battle makeMove(UUID battleId, Move move) {
        Battle battle = battleDao.findBattleByBattleId(battleId).orElseThrow(() -> new NotFoundException("Battle with id " + battleId + " not found"));
        addMove(battle, move);
        updateGameState(battle);
        battle = battleDao.save(battle);
        return battle;
    }

    private void addMove(Battle battle, Move move) {
        UUID nextMovePlayerId = battleUtils.getNextMovePlayerId(battle);
        if (nextMovePlayerId == null || !nextMovePlayerId.equals(move.getPlayerId())) {
            throw new BadMoveException("Invalid player id");
        }
        if (move.getFirstCoordinate() < 0 || move.getSecondCoordinate() < 0 ||
                move.getFirstCoordinate() >= battle.getSize() || move.getSecondCoordinate() >= battle.getSize()) {
            throw new BadMoveException("Coordinates are out of field bounds");
        }

        if (battle.getMoves().stream().anyMatch(battleMove ->
                battleMove.getFirstCoordinate().equals(move.getFirstCoordinate())
                        && battleMove.getSecondCoordinate().equals(move.getSecondCoordinate())
        )) {
            throw new BadMoveException("Cell is not empty");
        }
        battle.getMoves().add(move);
        move.setBattle(battle);
    }

    @Override
    public List<Battle> findBattles(Set<BattleStatus> statuses, int pageNum, int pageSize) {

        Pageable pageable = PageRequest.of(pageNum, pageSize).withSort(Sort.by("createdAt"));

        return battleDao.findBattlesByStatusIn(statuses, pageable);

    }

    public void updateGameState(Battle battle) {
        UUID lastMovingPlayerId = battle.getMoves().get(battle.getMoves().size() - 1).getPlayerId();
        boolean playerWonGame = gameChecker.checkPlayerWon(lastMovingPlayerId, battle.getSize(), battle.getMoves());

        if (playerWonGame) {
            setGameWin(battle, lastMovingPlayerId);
        } else {
            if (battle.getMoves().size() == battle.getSize() * battle.getSize()) {
                setDraw(battle);
            }
        }
    }

    private void setGameWin(Battle battle, UUID winningPlayerId) {
        battle.setStatus(BattleStatus.FINISHED);
        battle.setFinished(Timestamp.from(ZonedDateTime.now().toInstant()));
        if (battle.getFirstPlayerId() == winningPlayerId) {
            battle.setResult(BattleResult.FIRST_WINS);
        } else {
            battle.setResult(BattleResult.SECOND_WINS);
        }
    }

    private void setDraw(Battle battle) {
        battle.setStatus(BattleStatus.FINISHED);
        battle.setFinished(Timestamp.from(ZonedDateTime.now().toInstant()));
        battle.setResult(BattleResult.DRAW);
    }

}
