package by.ibondarau.tictactoe.battleservice.service;


import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.FirstMoveRule;

public interface BattleService {
    Battle createBattle(Integer playerId, Integer size, FirstMoveRule firstMoveRule);
    Battle getBattle(Integer battleId);
}
