package by.ibondarau.tictactoe.battleservice.service;


import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.model.FirstMoveRule;
import by.ibondarau.tictactoe.battleservice.model.Move;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BattleService {
    Battle createBattle(UUID playerId, int size, FirstMoveRule firstMoveRule);

    Battle getBattle(UUID battleId);

    Battle joinBattle(UUID battleId, UUID secondPlayerId);

    Battle makeMove(UUID battleId, Move move);

    List<Battle> findBattles(Set<BattleStatus> battleStatuses, int pageNum, int pageSize);
}
