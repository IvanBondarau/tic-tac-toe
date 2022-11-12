package by.ibondarau.tictactoe.battleservice.dto;

import by.ibondarau.tictactoe.battleservice.model.FirstMoveRule;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateBattleDto {
    private UUID userId;
    private FirstMoveRule firstMoveRule;
    private Integer size;
}
