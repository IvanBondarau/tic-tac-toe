package by.ibondarau.tictactoe.battleservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class MoveDto {
    private Integer playerId;
    private Integer x;
    private Integer y;
}
