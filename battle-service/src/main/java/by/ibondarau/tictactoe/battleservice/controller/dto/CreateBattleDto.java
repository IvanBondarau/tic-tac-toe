package by.ibondarau.tictactoe.battleservice.controller.dto;

import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
public record CreateBattleDto(
        Integer userId,
        Integer movesFirst
) {
}
