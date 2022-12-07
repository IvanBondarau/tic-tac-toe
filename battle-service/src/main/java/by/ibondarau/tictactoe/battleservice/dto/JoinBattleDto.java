package by.ibondarau.tictactoe.battleservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class JoinBattleDto {
    private UUID secondPlayerId;
}
