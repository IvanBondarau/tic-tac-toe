package by.ibondarau.tictactoe.battleservice.checker.impl;

import by.ibondarau.tictactoe.battleservice.model.Move;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

class GameCheckerImplTest {

    private final UUID firstPlayerId = UUID.randomUUID();
    private final UUID secondPlayerId = UUID.randomUUID();
    private final GameCheckerImpl gameChecker = new GameCheckerImpl();

    @Test
    void testAddMoveWinDiagonal() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(0).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(0).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(1).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(2).setSecondCoordinate(2));

        Assertions.assertTrue(gameChecker.checkPlayerWon(firstPlayerId, 3, moves));
    }


    @Test
    void testAddMoveWinRow() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(0).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(0).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(1).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(2).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(1).setSecondCoordinate(2));

        Assertions.assertTrue(gameChecker.checkPlayerWon(secondPlayerId, 3, moves));
    }


    @Test
    void testAddMoveWinColumn() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(0).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(1).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(0).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(2).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(2).setSecondCoordinate(1));

        Assertions.assertTrue(gameChecker.checkPlayerWon(secondPlayerId, 3, moves));

    }

    @Test
    void testAddMoveWinAntiDiagonal() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(0).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(2).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(2).setSecondCoordinate(2));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(0).setSecondCoordinate(2));

        Assertions.assertTrue(gameChecker.checkPlayerWon(secondPlayerId, 3, moves));
    }

    @Test
    void testDraw() {

        List<Move> moves = new LinkedList<>();
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(0).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(0).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(1).setSecondCoordinate(2));
        moves.add(new Move().setPlayerId(firstPlayerId).setFirstCoordinate(2).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(0).setSecondCoordinate(2));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(1).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(2).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(secondPlayerId).setFirstCoordinate(2).setSecondCoordinate(2));

        Assertions.assertFalse(gameChecker.checkPlayerWon(secondPlayerId, 3, moves));

    }

}