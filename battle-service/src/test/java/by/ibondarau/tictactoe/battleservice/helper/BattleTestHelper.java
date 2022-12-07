package by.ibondarau.tictactoe.battleservice.helper;

import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.model.FirstMoveRule;
import by.ibondarau.tictactoe.battleservice.model.Move;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class BattleTestHelper {
    public static Move createMove(int x, int y, UUID playerId) {
        return new Move().setFirstCoordinate(x).setSecondCoordinate(y).setPlayerId(playerId);
    }

    public static List<Move> createMoveListFromMatrix(char[][] moveMap, UUID firstPlayerId, UUID secondPlayerId) {
        List<Move> moves = new LinkedList<>();
        for (int x = 0; x < moveMap.length; x++) {
            for (int y = 0; y < moveMap.length; y++) {
                char cell = moveMap[x][y];
                if (cell == 'X') {
                    moves.add(createMove(x, y, firstPlayerId));
                } else if (cell == 'O') {
                    moves.add(createMove(x, y, secondPlayerId));
                }
            }
        }
        return moves;
    }

    public static Battle generateCreatedBattle(UUID firstPlayerId, FirstMoveRule firstGoesFirst) {
        return new Battle()
                .setStatus(BattleStatus.CREATED)
                .setFirstPlayerId(firstPlayerId)
                .setFirstMoveRule(firstGoesFirst);
    }

    public static Battle generateStartedBattle(UUID firstPlayerId, UUID secondPlayerId, List<Move> moves) {
        return new Battle()
                .setSize(3)
                .setStatus(BattleStatus.STARTED)
                .setFirstPlayerId(firstPlayerId)
                .setSecondPlayerId(secondPlayerId)
                .setFirstMovingPlayerId(secondPlayerId)
                .setFirstMoveRule(FirstMoveRule.RANDOM)
                .setMoves(moves);
    }
}
