package by.ibondarau.tictactoe.battleservice.service.impl;

import by.ibondarau.tictactoe.battleservice.dao.BattleDao;
import by.ibondarau.tictactoe.battleservice.exception.NotFoundException;
import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.model.FirstMoveRule;
import by.ibondarau.tictactoe.battleservice.service.impl.BattleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BattleServiceImplTest {

    @Mock
    private BattleDao battleDao;

    @InjectMocks
    private BattleServiceImpl battleService;

    @Test
    void testCreateBattle() {
        Mockito.when(battleDao.save(Mockito.any())).then((obj) -> {
                    Battle battle = (Battle) obj.getArgument(0);
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
}