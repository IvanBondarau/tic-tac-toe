package by.ibondarau.tictactoe.statisticservice.controller;

import by.ibondarau.tictactoe.statisticservice.dto.ShortBattleInfoDto;
import by.ibondarau.tictactoe.statisticservice.dto.UserStatisticsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return ResponseEntity.ok(new UserStatisticsDto(
                userId,
                10,
                8,
                53,
                Collections.singletonList(new ShortBattleInfoDto(1, userId, userId+1, userId, 9, ZonedDateTime.now().toInstant()))
        ));
    }
}
