package by.ibondarau.tictactoe.battleservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class JoinBattleDto {
    private Integer secondPlayerId;
}
