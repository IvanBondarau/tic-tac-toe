package by.ibondarau.tictactoe.battleservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateBattleDto {
    private Integer userId;
    private String firstMoveRule;
    private Integer size;
}
