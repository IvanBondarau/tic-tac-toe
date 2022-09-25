package by.ibondarau.tictactoe.battleservice.service.impl;

import by.ibondarau.tictactoe.battleservice.dao.BattleDao;
import by.ibondarau.tictactoe.battleservice.exception.NotFoundException;
import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.model.FirstMoveRule;
import by.ibondarau.tictactoe.battleservice.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class BattleServiceImpl implements BattleService {

    private final BattleDao battleDao;

    @Autowired
    public BattleServiceImpl(BattleDao battleDao) {
        this.battleDao = battleDao;
    }

    @Override
    public Battle createBattle(Integer playerId, Integer size, FirstMoveRule firstMoveRule) {
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
    public Battle getBattle(Integer battleId) {
        return battleDao
                .findBattleByBattleId(battleId)
                .orElseThrow(() -> new NotFoundException("Battle with id " + battleId + " not found"));
    }
}
