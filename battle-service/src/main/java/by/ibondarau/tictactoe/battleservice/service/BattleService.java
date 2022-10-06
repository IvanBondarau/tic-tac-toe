package by.ibondarau.tictactoe.battleservice.service;


import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.model.FirstMoveRule;
import by.ibondarau.tictactoe.battleservice.model.Move;
import com.sun.istack.NotNull;

import java.util.List;

public interface BattleService {
    Battle createBattle(int playerId, int size, @NotNull FirstMoveRule firstMoveRule);
    Battle getBattle(int battleId);

    Battle joinBattle(int battleId, int secondPlayerId);

    Battle makeMove(int battleId, Move move);

    List<Battle> findBattles(List<BattleStatus> battleStatuses, int pageNum, int pageSize);
}
