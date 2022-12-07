package by.ibondarau.tictactoe.battleservice.checker.impl;

import by.ibondarau.tictactoe.battleservice.checker.GameChecker;
import by.ibondarau.tictactoe.battleservice.model.Move;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("GameChecker")
public class GameCheckerImpl implements GameChecker {
    @Override
    public boolean checkPlayerWon(UUID playerId, int fieldSize, List<Move> moves) {
        int[] rows = new int[fieldSize];
        int[] columns = new int[fieldSize];
        int mainDiagonal = 0;
        int antiDiagonal = 0;

        List<Move> playerMoves = moves.stream().filter(move -> move.getPlayerId().equals(playerId)).toList();

        for (Move move : playerMoves) {
            int x = move.getFirstCoordinate();
            int y = move.getSecondCoordinate();

            rows[x]++;
            columns[y]++;
            if (x == y) {
                mainDiagonal++;
            }
            if (x + y + 1 == fieldSize) {
                antiDiagonal++;
            }

            if (checkWin(fieldSize, rows[x], columns[y], mainDiagonal, antiDiagonal)) {
                return true;
            }
        }

        return false;

    }

    private boolean checkWin(int fieldSize, int row, int column, int mainDiagonal, int antiDiagonal) {
        return row == fieldSize
                || column == fieldSize
                || mainDiagonal == fieldSize
                || antiDiagonal == fieldSize;
    }

}


