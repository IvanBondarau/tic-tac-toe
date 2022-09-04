package by.ibondarau.tictactoe.statisticservice.dto;

import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConstructorBinding
public record UserStatisticsDto(
        Integer userId,
        Integer wins,
        Integer losses,
        Integer draws,
        List<ShortBattleInfoDto> lastBattles
) {
}
