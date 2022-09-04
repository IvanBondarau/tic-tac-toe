package by.ibondarau.tictactoe.battleservice.controller.dto;

import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
public record JoinBattleDto(Integer secondPlayerId) {
}
