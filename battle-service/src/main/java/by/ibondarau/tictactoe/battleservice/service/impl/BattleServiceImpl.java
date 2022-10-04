package by.ibondarau.tictactoe.battleservice.service.impl;

import by.ibondarau.tictactoe.battleservice.dao.BattleDao;
import by.ibondarau.tictactoe.battleservice.exception.NotFoundException;
import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleResult;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.model.FirstMoveRule;
import by.ibondarau.tictactoe.battleservice.model.Move;
import by.ibondarau.tictactoe.battleservice.random.RandomUtils;
import by.ibondarau.tictactoe.battleservice.service.BattleService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BattleServiceImpl implements BattleService {

    private final BattleDao battleDao;
    private final RandomUtils randomUtils;

    @Autowired
    public BattleServiceImpl(BattleDao battleDao, RandomUtils randomUtils) {
        this.battleDao = battleDao;
        this.randomUtils = randomUtils;
    }

    @Override
    public Battle createBattle(int playerId, int size, @NotNull FirstMoveRule firstMoveRule) {
        Battle battle = new Battle()
                .setFirstPlayerId(playerId)
                .setSize(size)
                .setFirstMoveRule(firstMoveRule)
                .setCreated(ZonedDateTime.now().toInstant())
                .setStatus(BattleStatus.CREATED);
        battleDao.save(battle);
        return battle;
    }

    @Override
    public Battle getBattle(int battleId) {
        return battleDao
                .findBattleByBattleId(battleId)
                .orElseThrow(() -> new NotFoundException("Battle with id " + battleId + " not found"));
    }

    @Override
    public Battle joinBattle(int battleId, int secondPlayerId) {
        Battle battle = battleDao.findBattleByBattleId(battleId).orElseThrow(() -> new NotFoundException("Battle with id " + battleId + " not found"));
        battle.setSecondPlayerId(secondPlayerId);
        battle.setStatus(BattleStatus.STARTED);
        battle.setStarted(ZonedDateTime.now().toInstant());
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
    public Battle makeMove(int battleId, Move move) {
        Battle battle = battleDao.findBattleByBattleId(battleId).orElseThrow(() -> new NotFoundException("Battle with id " + battleId + " not found"));
        battle.getMoves().add(move);
        move.setBattle(battle);
        move.setTime(ZonedDateTime.now().toInstant());
        updateGameState(battle);
        battle = battleDao.save(battle);
        return battle;
    }

    @Override
    public List<Battle> findBattles(boolean active, int pageNum, int pageSize) {
        List<BattleStatus> statuses = new LinkedList<>();
        if (active) {
            statuses.add(BattleStatus.STARTED);
        } else {
            statuses.add(BattleStatus.CREATED);
            statuses.add(BattleStatus.STARTED);
            statuses.add(BattleStatus.FINISHED);
        }

        Pageable pageable = PageRequest.of(pageNum, pageSize).withSort(Sort.by("created"));

        return battleDao.findBattles(statuses, pageable);

    }

    public void updateGameState(Battle battle) {
        int lastMovingPlayerId = battle.getMoves().get(battle.getMoves().size() - 1).getPlayerId();
        boolean result = checkWin(lastMovingPlayerId, battle.getSize(), battle.getMoves());

        if (result) {
            setGameWin(battle, lastMovingPlayerId);
        } else {
            if (battle.getMoves().size() == battle.getSize() * battle.getSize()) {
                setDraw(battle);
            }
        }
    }

    public void setGameWin(Battle battle, int winningPlayerId) {
        battle.setStatus(BattleStatus.FINISHED);
        battle.setFinished(ZonedDateTime.now().toInstant());
        if (battle.getFirstPlayerId() == winningPlayerId) {
            battle.setResult(BattleResult.FIRST_WINS);
        } else {
            battle.setResult(BattleResult.SECOND_WINS);
        }
    }

    public void setDraw(Battle battle) {
        battle.setStatus(BattleStatus.FINISHED);
        battle.setFinished(ZonedDateTime.now().toInstant());
        battle.setResult(BattleResult.DRAW);
    }

    private boolean checkWin(int playerId, int fieldSize, List<Move> moves) {
        int[] rows = new int[fieldSize];
        int[] columns = new int[fieldSize];
        AtomicInteger mainDiagonal = new AtomicInteger();
        AtomicInteger antiDiagonal = new AtomicInteger();

        return moves.stream().filter(
                move -> move.getPlayerId().equals(playerId)
        ).anyMatch(move -> {
            rows[move.getFirstCoordinate()]++;
            columns[move.getSecondCoordinate()]++;
            if (move.getFirstCoordinate().equals(move.getSecondCoordinate())) {
                mainDiagonal.getAndIncrement();
            } else if (move.getFirstCoordinate() + move.getSecondCoordinate() == fieldSize + 1) {
                antiDiagonal.getAndIncrement();
            }

            return rows[move.getFirstCoordinate()] == fieldSize
                    || columns[move.getSecondCoordinate()] == fieldSize
                    || mainDiagonal.get() == fieldSize
                    || antiDiagonal.get() == fieldSize;
        });
    }


}
