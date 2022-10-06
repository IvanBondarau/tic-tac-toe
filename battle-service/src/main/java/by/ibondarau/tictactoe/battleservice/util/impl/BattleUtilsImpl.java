package by.ibondarau.tictactoe.battleservice.util.impl;

import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.util.BattleUtils;
import com.sun.istack.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope("singleton")
public final class BattleUtilsImpl implements BattleUtils {

    @Override
    public Integer getNextMovePlayerId(@NotNull Battle battle) {
        if (battle.getStatus() != BattleStatus.STARTED) {
            return null;
        }
        Integer firstPlayerId = Objects.equals(battle.getFirstPlayerId(), battle.getFirstMovingPlayerId()) ? battle.getFirstPlayerId() : battle.getSecondPlayerId();
        Integer secondPlayerId = Objects.equals(battle.getFirstPlayerId(), battle.getFirstMovingPlayerId()) ? battle.getSecondPlayerId() : battle.getFirstPlayerId();

        if (battle.getMoves() == null || battle.getMoves().isEmpty()) {
            return battle.getFirstMovingPlayerId();
        } else {
            return battle.getMoves().size() % 2 == 0 ? firstPlayerId : secondPlayerId;
        }
    }

}
