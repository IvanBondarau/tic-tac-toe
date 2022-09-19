package by.ibondarau.tictactoe.statisticservice.controller;

import by.ibondarau.tictactoe.statisticservice.dto.ShortBattleInfoDto;
import by.ibondarau.tictactoe.statisticservice.dto.UserStatisticsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Collections;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @GetMapping
    public ResponseEntity<UserStatisticsDto> getUserStatistics(
            @RequestParam("userId") Integer userId,
            @RequestParam("lastBattles") Integer lastBattles
    ) {
        UserStatisticsDto userStatisticsDto = new UserStatisticsDto()
                .userId(userId)
                .wins(10)
                .draws(53)
                .losses(8);

        ShortBattleInfoDto shortBattleInfoDto = new ShortBattleInfoDto()
                .battleId(1)
                .firstUserId(userId)
                .secondUserId(userId + 1)
                .turns(9)
                .battleFinished(ZonedDateTime.now().toInstant())
                .result("Draw");
        userStatisticsDto.lastBattles(Collections.singletonList(shortBattleInfoDto));
        return ResponseEntity.ok(userStatisticsDto);
    }
}
