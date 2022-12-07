package by.ibondarau.tictactoe.battleservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MoveDto {
    private UUID playerId;
    private Integer x;
    private Integer y;
}
