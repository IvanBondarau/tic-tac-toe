package by.ibondarau.tictactoe.battleservice.checker;

import by.ibondarau.tictactoe.battleservice.model.Move;

import java.util.List;
import java.util.UUID;

public interface GameChecker {
    boolean checkPlayerWon(UUID playerId, int fieldSize, List<Move> moves);
}
