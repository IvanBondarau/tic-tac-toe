package by.ibondarau.tictactoe.battleservice.checker.impl;

import by.ibondarau.tictactoe.battleservice.checker.GameChecker;
import by.ibondarau.tictactoe.battleservice.model.Move;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component("GameChecker")
public class GameCheckerImpl implements GameChecker {
    @Override
    public boolean checkWin(UUID playerId, int fieldSize, List<Move> moves) {
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
            }
            if (move.getFirstCoordinate() + move.getSecondCoordinate() + 1 == fieldSize) {
                antiDiagonal.getAndIncrement();
            }

            return rows[move.getFirstCoordinate()] == fieldSize
                    || columns[move.getSecondCoordinate()] == fieldSize
                    || mainDiagonal.get() == fieldSize
                    || antiDiagonal.get() == fieldSize;
        });
    }
}


