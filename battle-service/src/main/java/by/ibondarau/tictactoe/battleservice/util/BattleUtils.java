package by.ibondarau.tictactoe.battleservice.util;

import by.ibondarau.tictactoe.battleservice.model.Battle;

import java.util.UUID;

public interface BattleUtils {
    UUID getNextMovePlayerId(Battle battle);
}
