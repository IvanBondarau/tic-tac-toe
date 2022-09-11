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
            @RequestParam("lastGames") Integer lastGames
    ) {
        UserStatisticsDto userStatisticsDto = new UserStatisticsDto();
        userStatisticsDto.setUserId(userId);
        userStatisticsDto.setWins(10);
        userStatisticsDto.setDraws(53);
        userStatisticsDto.setLosses(8);

        ShortBattleInfoDto shortBattleInfoDto = new ShortBattleInfoDto();
        shortBattleInfoDto.setBattleId(1);
        shortBattleInfoDto.setFirstUserId(userId);
        shortBattleInfoDto.setSecondUserId(userId + 1);
        shortBattleInfoDto.setTurns(9);
        shortBattleInfoDto.setBattleFinished(ZonedDateTime.now().toInstant());
        shortBattleInfoDto.setResult("Draw");

        userStatisticsDto.setLastBattles(Collections.singletonList(shortBattleInfoDto));
        return ResponseEntity.ok(userStatisticsDto);
    }
}
