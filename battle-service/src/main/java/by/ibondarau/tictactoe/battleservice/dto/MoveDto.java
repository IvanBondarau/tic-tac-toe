package by.ibondarau.tictactoe.battleservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class MoveDto {
    private UUID playerId;
    private Integer x;
    private Integer y;
}
