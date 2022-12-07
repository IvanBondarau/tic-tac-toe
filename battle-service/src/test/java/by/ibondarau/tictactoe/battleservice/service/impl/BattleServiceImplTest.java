package by.ibondarau.tictactoe.battleservice.service.impl;

import by.ibondarau.tictactoe.battleservice.checker.GameChecker;
import by.ibondarau.tictactoe.battleservice.dao.BattleDao;
import by.ibondarau.tictactoe.battleservice.exception.BusinessException;
import by.ibondarau.tictactoe.battleservice.exception.NotFoundException;
import by.ibondarau.tictactoe.battleservice.helper.BattleTestHelper;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class BattleServiceImplTest {

    private final UUID firstPlayerId = UUID.randomUUID();
    private final UUID secondPlayerId = UUID.randomUUID();
    private final UUID battleId = UUID.randomUUID();
    @Mock
    private BattleDao battleDao;
    @Mock
    private RandomUtils randomUtils;
    @Mock
    private BattleUtils battleUtils;
    @Mock
    private GameChecker gameChecker;
    @InjectMocks
    private BattleServiceImpl battleService;

    @Test
    void testCreateBattle() {
        Mockito.when(battleDao.save(Mockito.any())).then((obj) -> {
                    Battle battle = obj.getArgument(0);
                    battle.setBattleId(battleId);
                    return battle;
                }
        );
        Battle battle = battleService.createBattle(firstPlayerId, 5, FirstMoveRule.SECOND_GOES_FIRST);
        Mockito.verify(battleDao).save(Mockito.any());
        Mockito.verifyNoMoreInteractions(battleDao);
        Assertions.assertEquals(battleId, battle.getBattleId());
        Assertions.assertEquals(BattleStatus.CREATED, battle.getStatus());
        Assertions.assertEquals(5, battle.getSize());
        Assertions.assertEquals(FirstMoveRule.SECOND_GOES_FIRST, battle.getFirstMoveRule());
    }

    @Test
    void testGetBattle() {
        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(
                Optional.of(new Battle().setBattleId(battleId))
        );
        Battle battle = battleService.getBattle(battleId);
        Mockito.verify(battleDao).findBattleByBattleId(Mockito.any());
        Mockito.verifyNoMoreInteractions(battleDao);
        Assertions.assertEquals(battleId, battle.getBattleId());
    }

    @Test
    void testGetBattleEmpty() {
        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(
                Optional.empty()
        );
        Assertions.assertThrows(NotFoundException.class, () -> battleService.getBattle(battleId));
    }

    @Test
    void testJoinBattleFirstPlayerStarts() {

        Battle battle = BattleTestHelper.generateCreatedBattle(firstPlayerId, FirstMoveRule.FIRST_GOES_FIRST);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);

        Battle result = battleService.joinBattle(battleId, secondPlayerId);

        Mockito.verify(battleDao).findBattleByBattleId(Mockito.any());
        Mockito.verify(battleDao).save(Mockito.any());
        Mockito.verifyNoMoreInteractions(battleDao);

        Assertions.assertEquals(BattleStatus.STARTED, result.getStatus());
        Assertions.assertEquals(firstPlayerId, result.getFirstMovingPlayerId());
        Assertions.assertEquals(secondPlayerId, result.getSecondPlayerId());
        Assertions.assertNotNull(result.getStarted());

    }


    @Test
    void testJoinBattleSecondPlayerStarts() {

        Battle battle = BattleTestHelper.generateCreatedBattle(secondPlayerId, FirstMoveRule.SECOND_GOES_FIRST);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);

        Battle result = battleService.joinBattle(battleId, firstPlayerId);

        Mockito.verify(battleDao).findBattleByBattleId(Mockito.any());
        Mockito.verify(battleDao).save(Mockito.any());
        Mockito.verifyNoMoreInteractions(battleDao);

        Assertions.assertEquals(BattleStatus.STARTED, result.getStatus());
        Assertions.assertEquals(firstPlayerId, result.getFirstMovingPlayerId());
        Assertions.assertEquals(firstPlayerId, result.getSecondPlayerId());
        Assertions.assertNotNull(result.getStarted());

    }

    @Test
    void testJoinBattleRandom() {

        Battle battle = BattleTestHelper.generateCreatedBattle(secondPlayerId, FirstMoveRule.RANDOM);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);
        Mockito.when(randomUtils.getRandomOf(Mockito.any(), Mockito.any())).thenReturn(firstPlayerId);

        Battle result = battleService.joinBattle(battleId, firstPlayerId);

        Mockito.verify(battleDao).findBattleByBattleId(Mockito.any());
        Mockito.verify(battleDao).save(Mockito.any());
        Mockito.verifyNoMoreInteractions(battleDao);

        Assertions.assertEquals(BattleStatus.STARTED, result.getStatus());
        Assertions.assertEquals(firstPlayerId, result.getFirstMovingPlayerId());
        Assertions.assertEquals(firstPlayerId, result.getSecondPlayerId());
        Assertions.assertNotNull(result.getStarted());

    }

    @Test
    void testJoinBattleNotFound() {
        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> battleService.joinBattle(battleId, firstPlayerId));

        Mockito.verify(battleDao).findBattleByBattleId(Mockito.any());
        Mockito.verifyNoMoreInteractions(battleDao);
    }

    @Test
    void testJoinBattleAlreadyStarted() {

        Battle battle = BattleTestHelper.generateStartedBattle(firstPlayerId, secondPlayerId, new LinkedList<>());

        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(Optional.of(battle));

        Assertions.assertThrows(BusinessException.class, () -> battleService.joinBattle(battleId, UUID.randomUUID()));

        Mockito.verify(battleDao).findBattleByBattleId(Mockito.any());
        Mockito.verifyNoMoreInteractions(battleDao);

    }

    @Test
    void testAddMove() {
        Battle battle = BattleTestHelper.generateStartedBattle(firstPlayerId, secondPlayerId, new LinkedList<>());

        Mockito.when(gameChecker.checkPlayerWon(Mockito.any(), Mockito.anyInt(), Mockito.any())).thenReturn(false);
        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(firstPlayerId);

        Move nextMove = BattleTestHelper.createMove(1, 1, firstPlayerId);

        Battle result = battleService.makeMove(battleId, nextMove);
        Assertions.assertEquals(BattleStatus.STARTED, result.getStatus());
        Assertions.assertTrue(result.getMoves().contains(nextMove));
    }


    @Test
    void testAddMoveDraw() {
        List<Move> moves = BattleTestHelper.createMoveListFromMatrix(
                new char[][]{
                        {'X', 'X', 'O'},
                        {'O', 'O', 'X'},
                        {'X', 'O', '.'}
                }, firstPlayerId, secondPlayerId
        );

        Battle battle = BattleTestHelper.generateStartedBattle(secondPlayerId, firstPlayerId, moves);

        Mockito.when(gameChecker.checkPlayerWon(Mockito.any(), Mockito.anyInt(), Mockito.any())).thenReturn(false);
        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(firstPlayerId);

        Move nextMove = BattleTestHelper.createMove(2, 2, firstPlayerId);

        Battle result = battleService.makeMove(battleId, nextMove);
        Assertions.assertEquals(BattleStatus.FINISHED, result.getStatus());
        Assertions.assertTrue(result.getMoves().contains(nextMove));
        Assertions.assertNotNull(result.getFinished());
        Assertions.assertEquals(BattleResult.DRAW, result.getResult());
    }

    @Test
    void testAddMoveWin() {
        Battle battle = BattleTestHelper.generateStartedBattle(secondPlayerId, firstPlayerId, new LinkedList<>());

        Mockito.when(gameChecker.checkPlayerWon(Mockito.any(), Mockito.anyInt(), Mockito.any())).thenReturn(true);
        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(Optional.of(battle));
        Mockito.when(battleDao.save(Mockito.any())).thenReturn(battle);
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(firstPlayerId);

        Move nextMove = BattleTestHelper.createMove(2, 2, firstPlayerId);

        Battle result = battleService.makeMove(battleId, nextMove);
        Assertions.assertEquals(BattleStatus.FINISHED, result.getStatus());
        Assertions.assertTrue(result.getMoves().contains(nextMove));
        Assertions.assertNotNull(result.getFinished());
        Assertions.assertEquals(BattleResult.SECOND_WINS, result.getResult());
    }


    @Test
    void testAddMoveBadTurn() {
        List<Move> moves = new LinkedList<>();
        moves.add(BattleTestHelper.createMove(0, 1, firstPlayerId));

        Battle battle = BattleTestHelper.generateStartedBattle(secondPlayerId, firstPlayerId, moves);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(Optional.of(battle));
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(secondPlayerId);

        Move nextMove = BattleTestHelper.createMove(1, 0, firstPlayerId);

        Assertions.assertThrows(BusinessException.class, () -> battleService.makeMove(battleId, nextMove));
    }

    @Test
    void testAddMoveDuplicatedMove() {
        List<Move> moves = new LinkedList<>();
        moves.add(BattleTestHelper.createMove(1, 0, firstPlayerId));
        moves.add(BattleTestHelper.createMove(0, 1, secondPlayerId));

        Battle battle = BattleTestHelper.generateStartedBattle(secondPlayerId, firstPlayerId, moves);

        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(Optional.of(battle));
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(firstPlayerId);

        Move nextMove = BattleTestHelper.createMove(0, 1, firstPlayerId);

        Assertions.assertThrows(BusinessException.class, () -> battleService.makeMove(battleId, nextMove));
    }

    @Test
    void testAddMoveOutOfBounds() {
        Battle battle = BattleTestHelper.generateStartedBattle(secondPlayerId, firstPlayerId, new LinkedList<>());

        Mockito.when(battleDao.findBattleByBattleId(Mockito.any())).thenReturn(Optional.of(battle));
        Mockito.when(battleUtils.getNextMovePlayerId(Mockito.any())).thenReturn(secondPlayerId);

        Move nextMove = BattleTestHelper.createMove(-1, 1, secondPlayerId);

        Assertions.assertThrows(BusinessException.class, () -> battleService.makeMove(battleId, nextMove));
    }

    @Test
    void testFilter() {
        List<Battle> results = new LinkedList<>();
        Set<BattleStatus> statuses = Set.of(BattleStatus.STARTED);
        Mockito.when(battleDao.findBattlesByStatusIn(Mockito.eq(statuses), Mockito.any())).thenReturn(results);
        List<Battle> searchResult = battleService.findBattles(statuses, 1, 1);
        Assertions.assertEquals(results, searchResult);
    }

}