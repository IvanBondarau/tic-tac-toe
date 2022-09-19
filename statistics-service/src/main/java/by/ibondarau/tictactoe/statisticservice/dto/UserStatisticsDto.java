package by.ibondarau.tictactoe.statisticservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true, chain = true)
public class UserStatisticsDto {
    private Integer userId;
    private Integer wins;
    private Integer losses;
    private Integer draws;
    private List<ShortBattleInfoDto> lastBattles;
}
