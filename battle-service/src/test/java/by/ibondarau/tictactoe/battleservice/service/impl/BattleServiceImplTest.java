package by.ibondarau.tictactoe.battleservice.service.impl;

import by.ibondarau.tictactoe.battleservice.dao.BattleDao;
import by.ibondarau.tictactoe.battleservice.exception.BusinessException;
import by.ibondarau.tictactoe.battleservice.exception.NotFoundException;
import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleResult;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.model.FirstMoveRule;
import by.ibondarau.tictactoe.battleservice.model.Move;
import by.ibondarau.tictactoe.battleservice.util.BattleUtils;
import by.ibondarau.tictactoe.battleservice.util.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BattleServiceImplTest {

    @Mock
    private BattleDao battleDao;

    @Mock
    private RandomUtils randomUtils;

    @Mock
    private BattleUtils battleUtils;

    @InjectMocks
    private BattleServiceImpl battleService;

    @Test
    void testCreateBattle() {
        Mockito.when(battleDao.save(Mockito.any())).then((obj) -> {
                    Battle battle = obj.getArgument(0);
                    battle.setBattleId(999);
                    return battle;
                }
        );
        Battle battle = battleService.createBattle(1, 5, FirstMoveRule.SECOND_GOES_FIRST);
        Mockito.verify(battleDao).save(Mockito.any());
        Mockito.verifyNoMoreInteractions(battleDao);
        Assertions.assertEquals(999, battle.getBattleId());
        Assertions.assertEquals(BattleStatus.CREATED, battle.getStatus());
        Assertions.assertEquals(5, battle.getSize());
        Assertions.assertEquals(FirstMoveRule.SECOND_GOES_FIRST, battle.getFirstMoveRule());
        Assertions.assertNotNull(battle.getCreated());
    }

    @Test
    void testGetBattle() {
        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(
                Optional.of(new Battle().setBattleId(10))
        );
        Battle battle = battleService.getBattle(10);
        Mockito.verify(battleDao).findBattleByBattleId(Mockito.anyInt());
        Mockito.verifyNoMoreInteractions(battleDao);
        Assertions.assertEquals(10, battle.getBattleId());
    }

    @Test
    void testGetBattleEmpty() {
        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(
                Optional.empty()
        );
        Assertions.assertThrows(NotFoundException.class, () -> battleService.getBattle(10));
    }

    @Test
    void testJoinBattleFirstPlayerStarts() {

        Battle battle = new Battle()
                .setBattleId(100)
                .setStatus(BattleStatus.CREATED)
                .setFirstPlayerId(30)
                .setFirstMoveRule(FirstMoveRule.FIRST_GOES_FIRST);
        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);

        Battle result = battleService.joinBattle(100, 20);

        Mockito.verify(battleDao).findBattleByBattleId(Mockito.anyInt());
        Mockito.verify(battleDao).save(Mockito.any());
        Mockito.verifyNoMoreInteractions(battleDao);

        Assertions.assertEquals(BattleStatus.STARTED, result.getStatus());
        Assertions.assertEquals(30, result.getFirstMovingPlayerId());
        Assertions.assertEquals(20, result.getSecondPlayerId());
        Assertions.assertNotNull(result.getStarted());

    }


    @Test
    void testJoinBattleSecondPlayerStarts() {

        Battle battle = new Battle()
                .setBattleId(100)
                .setStatus(BattleStatus.CREATED)
                .setFirstPlayerId(30)
                .setFirstMoveRule(FirstMoveRule.SECOND_GOES_FIRST);
        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);

        Battle result = battleService.joinBattle(100, 20);

        Mockito.verify(battleDao).findBattleByBattleId(Mockito.anyInt());
        Mockito.verify(battleDao).save(Mockito.any());
        Mockito.verifyNoMoreInteractions(battleDao);

        Assertions.assertEquals(BattleStatus.STARTED, result.getStatus());
        Assertions.assertEquals(20, result.getFirstMovingPlayerId());
        Assertions.assertEquals(20, result.getSecondPlayerId());
        Assertions.assertNotNull(result.getStarted());

    }

    @Test
    void testJoinBattleRandom() {

        Battle battle = new Battle()
                .setBattleId(100)
                .setStatus(BattleStatus.CREATED)
                .setFirstPlayerId(30)
                .setFirstMoveRule(FirstMoveRule.RANDOM);
        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);
        Mockito.when(randomUtils.getRandomOf(Mockito.anyInt(), Mockito.anyInt())).thenReturn(500);

        Battle result = battleService.joinBattle(100, 20);

        Mockito.verify(battleDao).findBattleByBattleId(Mockito.anyInt());
        Mockito.verify(battleDao).save(Mockito.any());
        Mockito.verifyNoMoreInteractions(battleDao);

        Assertions.assertEquals(BattleStatus.STARTED, result.getStatus());
        Assertions.assertEquals(500, result.getFirstMovingPlayerId());
        Assertions.assertEquals(20, result.getSecondPlayerId());
        Assertions.assertNotNull(result.getStarted());

    }

    @Test
    void testJoinBattleNotFound() {
        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> battleService.joinBattle(100, 20));

        Mockito.verify(battleDao).findBattleByBattleId(Mockito.anyInt());
        Mockito.verifyNoMoreInteractions(battleDao);
    }

    @Test
    void testJoinBattleAlreadyStarted() {

        Battle battle = new Battle()
                .setBattleId(100)
                .setStatus(BattleStatus.STARTED)
                .setFirstPlayerId(30)
                .setSecondPlayerId(20)
                .setFirstMovingPlayerId(20)
                .setFirstMoveRule(FirstMoveRule.RANDOM);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));

        Assertions.assertThrows(BusinessException.class, () -> battleService.joinBattle(100, 50));

        Mockito.verify(battleDao).findBattleByBattleId(Mockito.anyInt());
        Mockito.verifyNoMoreInteractions(battleDao);

    }

    @Test
    void testAddMove() {
        Battle battle = new Battle()
                .setBattleId(100)
                .setSize(3)
                .setStatus(BattleStatus.STARTED)
                .setFirstPlayerId(30)
                .setSecondPlayerId(20)
                .setFirstMovingPlayerId(20)
                .setFirstMoveRule(FirstMoveRule.RANDOM)
                .setMoves(new LinkedList<>());

        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(20);

        Move move = new Move();
        move.setPlayerId(20);
        move.setFirstCoordinate(1);
        move.setSecondCoordinate(1);

        Battle result = battleService.makeMove(100, move);
        Assertions.assertEquals(BattleStatus.STARTED, result.getStatus());
        Assertions.assertTrue(result.getMoves().contains(move));
    }


    @Test
    void testAddMoveWinDiagonal() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(0).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(0).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(1).setSecondCoordinate(0));

        Battle battle = new Battle()
                .setBattleId(100)
                .setSize(3)
                .setStatus(BattleStatus.STARTED)
                .setFirstPlayerId(30)
                .setSecondPlayerId(20)
                .setFirstMovingPlayerId(20)
                .setFirstMoveRule(FirstMoveRule.RANDOM)
                .setMoves(moves);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(20);

        Move move = new Move();
        move.setPlayerId(20);
        move.setFirstCoordinate(2);
        move.setSecondCoordinate(2);

        Battle result = battleService.makeMove(100, move);
        Assertions.assertEquals(BattleStatus.FINISHED, result.getStatus());
        Assertions.assertTrue(result.getMoves().contains(move));
        Assertions.assertNotNull(result.getFinished());
        Assertions.assertEquals(BattleResult.SECOND_WINS, result.getResult());
    }


    @Test
    void testAddMoveWinRow() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(0).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(0).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(1).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(2).setSecondCoordinate(1));

        Battle battle = new Battle()
                .setBattleId(100)
                .setSize(3)
                .setStatus(BattleStatus.STARTED)
                .setFirstPlayerId(30)
                .setSecondPlayerId(20)
                .setFirstMovingPlayerId(20)
                .setFirstMoveRule(FirstMoveRule.RANDOM)
                .setMoves(moves);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(30);

        Move move = new Move();
        move.setPlayerId(30);
        move.setFirstCoordinate(1);
        move.setSecondCoordinate(2);

        Battle result = battleService.makeMove(100, move);
        Assertions.assertEquals(BattleStatus.FINISHED, result.getStatus());
        Assertions.assertTrue(result.getMoves().contains(move));
        Assertions.assertNotNull(result.getFinished());
        Assertions.assertEquals(BattleResult.FIRST_WINS, result.getResult());
    }


    @Test
    void testAddMoveWinColumn() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(0).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(1).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(0).setSecondCoordinate(1));

        Battle battle = new Battle()
                .setBattleId(100)
                .setSize(3)
                .setStatus(BattleStatus.STARTED)
                .setFirstPlayerId(30)
                .setSecondPlayerId(20)
                .setFirstMovingPlayerId(20)
                .setFirstMoveRule(FirstMoveRule.RANDOM)
                .setMoves(moves);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(20);

        Move move = new Move();
        move.setPlayerId(20);
        move.setFirstCoordinate(2);
        move.setSecondCoordinate(0);

        Battle result = battleService.makeMove(100, move);
        Assertions.assertEquals(BattleStatus.FINISHED, result.getStatus());
        Assertions.assertTrue(result.getMoves().contains(move));
        Assertions.assertNotNull(result.getFinished());
        Assertions.assertEquals(BattleResult.SECOND_WINS, result.getResult());
    }

    @Test
    void testAddMoveWinAntiDiagonal() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(0).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(2).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(2).setSecondCoordinate(2));

        Battle battle = new Battle()
                .setBattleId(100)
                .setSize(3)
                .setStatus(BattleStatus.STARTED)
                .setFirstPlayerId(30)
                .setSecondPlayerId(20)
                .setFirstMovingPlayerId(20)
                .setFirstMoveRule(FirstMoveRule.RANDOM)
                .setMoves(moves);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(30);

        Move move = new Move();
        move.setPlayerId(30);
        move.setFirstCoordinate(0);
        move.setSecondCoordinate(2);

        Battle result = battleService.makeMove(100, move);
        Assertions.assertEquals(BattleStatus.FINISHED, result.getStatus());
        Assertions.assertTrue(result.getMoves().contains(move));
        Assertions.assertNotNull(result.getFinished());
        Assertions.assertEquals(BattleResult.FIRST_WINS, result.getResult());
    }

    @Test
    void testAddMoveDraw() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(0).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(0).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(1).setSecondCoordinate(2));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(2).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(0).setSecondCoordinate(2));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(1).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(2).setSecondCoordinate(1));

        Battle battle = new Battle()
                .setBattleId(100)
                .setSize(3)
                .setStatus(BattleStatus.STARTED)
                .setFirstPlayerId(30)
                .setSecondPlayerId(20)
                .setFirstMovingPlayerId(30)
                .setFirstMoveRule(FirstMoveRule.RANDOM)
                .setMoves(moves);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(30);

        Move move = new Move();
        move.setPlayerId(30);
        move.setFirstCoordinate(2);
        move.setSecondCoordinate(2);

        Battle result = battleService.makeMove(100, move);
        Assertions.assertEquals(BattleStatus.FINISHED, result.getStatus());
        Assertions.assertTrue(result.getMoves().contains(move));
        Assertions.assertNotNull(result.getFinished());
        Assertions.assertEquals(BattleResult.DRAW, result.getResult());
    }


    @Test
    void testAddMoveWinBadTurn() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(0).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(2).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(2).setSecondCoordinate(2));

        Battle battle = new Battle()
                .setBattleId(100)
                .setSize(3)
                .setStatus(BattleStatus.STARTED)
                .setFirstPlayerId(30)
                .setSecondPlayerId(20)
                .setFirstMovingPlayerId(20)
                .setFirstMoveRule(FirstMoveRule.RANDOM)
                .setMoves(moves);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(30);

        Move move = new Move();
        move.setPlayerId(20);
        move.setFirstCoordinate(0);
        move.setSecondCoordinate(2);

        Assertions.assertThrows(BusinessException.class, () -> battleService.makeMove(100, move));
    }

    @Test
    void testAddMoveWinDuplicateMoe() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(0).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(2).setSecondCoordinate(0));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(30).setFirstCoordinate(1).setSecondCoordinate(1));
        moves.add(new Move().setPlayerId(20).setFirstCoordinate(2).setSecondCoordinate(2));

        Battle battle = new Battle()
                .setBattleId(100)
                .setSize(3)
                .setStatus(BattleStatus.STARTED)
                .setFirstPlayerId(30)
                .setSecondPlayerId(20)
                .setFirstMovingPlayerId(20)
                .setFirstMoveRule(FirstMoveRule.RANDOM)
                .setMoves(moves);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(20);

        Move move = new Move();
        move.setPlayerId(20);
        move.setFirstCoordinate(0);
        move.setSecondCoordinate(1);

        Assertions.assertThrows(BusinessException.class, () -> battleService.makeMove(100, move));
    }

    @Test
    void testAddMoveWinOutOfBounds() {
        List<Move> moves = new LinkedList<>();

        Battle battle = new Battle()
                .setBattleId(100)
                .setSize(3)
                .setStatus(BattleStatus.STARTED)
                .setFirstPlayerId(30)
                .setSecondPlayerId(20)
                .setFirstMovingPlayerId(20)
                .setFirstMoveRule(FirstMoveRule.RANDOM)
                .setMoves(moves);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.anyInt())).thenReturn(Optional.of(battle));
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(30);

        Move move = new Move();
        move.setPlayerId(30);
        move.setFirstCoordinate(900);
        move.setSecondCoordinate(900);

        Assertions.assertThrows(BusinessException.class, () -> battleService.makeMove(100, move));
    }

    @Test
    void testFilter() {
        List<Battle> results = new LinkedList<>();
        List<BattleStatus> statuses = Arrays.asList(BattleStatus.STARTED);
        Mockito.when(battleDao.findBattles(Mockito.eq(statuses), Mockito.any())).thenReturn(results);
        List<Battle> searchResult = battleService.findBattles(statuses, 1, 1);
        Assertions.assertEquals(results, searchResult);
    }

}