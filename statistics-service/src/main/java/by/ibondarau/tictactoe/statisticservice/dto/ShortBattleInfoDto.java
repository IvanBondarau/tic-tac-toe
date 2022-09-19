package by.ibondarau.tictactoe.statisticservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(fluent = true, chain = true)
public class ShortBattleInfoDto {
    private Integer battleId;
    private Integer firstUserId;
    private Integer secondUserId;
    private Integer turns;

    private String result;

    private Instant battleFinished;
}
