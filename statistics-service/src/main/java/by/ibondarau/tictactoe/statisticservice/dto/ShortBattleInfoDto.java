package by.ibondarau.tictactoe.statisticservice.dto;

import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Instant;

@ConstructorBinding
public record ShortBattleInfoDto(
        Integer battleId,
        Integer firstUserId,
        Integer secondUserId,
        Integer winningUserId,
        Integer turns,
        Instant battleFinished
) {
}
