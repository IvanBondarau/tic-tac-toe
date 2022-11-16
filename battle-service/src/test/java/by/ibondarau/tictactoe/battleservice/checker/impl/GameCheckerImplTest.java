package by.ibondarau.tictactoe.battleservice.checker.impl;

import by.ibondarau.tictactoe.battleservice.helper.BattleTestHelper;
import by.ibondarau.tictactoe.battleservice.model.Move;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class GameCheckerImplTest {

    private final UUID firstPlayerId = UUID.randomUUID();
    private final UUID secondPlayerId = UUID.randomUUID();
    private final GameCheckerImpl gameChecker = new GameCheckerImpl();

    @Test
    void testPlayerWonDiagonal() {

        List<Move> moves = BattleTestHelper.createMoveListFromMatrix(
                new char[][]{
                        {'X', 'O', '.'},
                        {'O', 'X', '.'},
                        {'.', '.', 'X'}
                }, firstPlayerId, secondPlayerId
        );
        Assertions.assertTrue(gameChecker.checkPlayerWon(firstPlayerId, 3, moves));
    }


    @Test
    void testPlayerWonRow() {
        List<Move> moves = BattleTestHelper.createMoveListFromMatrix(
                new char[][]{
                        {'X', '.', 'X'},
                        {'O', 'O', 'O'},
                        {'X', '.', '.'}
                }, firstPlayerId, secondPlayerId
        );

        Assertions.assertTrue(gameChecker.checkPlayerWon(secondPlayerId, 3, moves));
    }


    @Test
    void testPlayerWonColumn() {
        List<Move> moves = BattleTestHelper.createMoveListFromMatrix(
                new char[][]{
                        {'X', 'O', 'X'},
                        {'.', 'O', '.'},
                        {'X', 'O', '.'}
                }, firstPlayerId, secondPlayerId
        );

        Assertions.assertTrue(gameChecker.checkPlayerWon(secondPlayerId, 3, moves));

    }

    @Test
    void testPlayerWonAntiDiagonal() {
        List<Move> moves = BattleTestHelper.createMoveListFromMatrix(
                new char[][]{
                        {'O', '.', 'X'},
                        {'X', 'O', '.'},
                        {'X', '.', 'O'}
                }, firstPlayerId, secondPlayerId
        );

        Assertions.assertTrue(gameChecker.checkPlayerWon(secondPlayerId, 3, moves));
    }

    @Test
    void testDraw() {

        List<Move> moves = BattleTestHelper.createMoveListFromMatrix(
                new char[][]{
                        {'X', 'X', 'O'},
                        {'O', 'O', 'X'},
                        {'X', 'O', 'X'}
                }, firstPlayerId, secondPlayerId
        );

        Assertions.assertFalse(gameChecker.checkPlayerWon(secondPlayerId, 3, moves));

    }

}