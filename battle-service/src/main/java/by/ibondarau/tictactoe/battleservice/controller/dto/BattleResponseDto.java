package by.ibondarau.tictactoe.battleservice.controller.dto;

import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Instant;
import java.util.List;

@ConstructorBinding
public record BattleResponseDto(
        Integer id,
        Integer firstPlayerId,
        Integer secondPlayerId,
        Integer size,
        String status,
        List<MoveDto> moves,
        Integer nextMove,
        String result,
        Instant created,
        Instant started,
        Instant finished) {
}
