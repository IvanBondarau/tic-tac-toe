package by.ibondarau.tictactoe.battleservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class CreateBattleDto {
    private Integer userId;
    private Integer movesFirst;
}
