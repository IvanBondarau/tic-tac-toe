package by.ibondarau.tictactoe.battleservice.util.impl;

import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.util.BattleUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@Scope("singleton")
public final class BattleUtilsImpl implements BattleUtils {

    @Override
    public UUID getNextMovePlayerId(Battle battle) {
        if (battle.getStatus() != BattleStatus.STARTED) {
            return null;
        }
        UUID firstPlayerId = Objects.equals(battle.getFirstPlayerId(), battle.getFirstMovingPlayerId()) ? battle.getFirstPlayerId() : battle.getSecondPlayerId();
        UUID secondPlayerId = Objects.equals(battle.getFirstPlayerId(), battle.getFirstMovingPlayerId()) ? battle.getSecondPlayerId() : battle.getFirstPlayerId();

        if (battle.getMoves() == null || battle.getMoves().isEmpty()) {
            return battle.getFirstMovingPlayerId();
        } else {
            return battle.getMoves().size() % 2 == 0 ? firstPlayerId : secondPlayerId;
        }
    }

}
