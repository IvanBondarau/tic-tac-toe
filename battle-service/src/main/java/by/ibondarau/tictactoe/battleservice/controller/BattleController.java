package by.ibondarau.tictactoe.battleservice.controller;

import by.ibondarau.tictactoe.battleservice.controller.dto.BattleResponseDto;
import by.ibondarau.tictactoe.battleservice.controller.dto.CreateBattleDto;
import by.ibondarau.tictactoe.battleservice.controller.dto.JoinBattleDto;
import by.ibondarau.tictactoe.battleservice.controller.dto.MoveDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/battle")
public class BattleController {

    @GetMapping("/{battleId}")
    public ResponseEntity<BattleResponseDto> get(@PathVariable Integer battleId) {
        return ResponseEntity.ok(
                new BattleResponseDto(
                        battleId,
                        1,
                        2,
                        3,
                        "Finished",
                        Arrays.asList(new MoveDto(1, 0, 0), new MoveDto(1, 1, 1), new MoveDto(1, 2, 2)),
                        -1,
                        "First wins",
                        ZonedDateTime.now().toInstant(),
                        ZonedDateTime.now().toInstant(),
                        ZonedDateTime.now().toInstant()
                )
        );
    }

    @PostMapping
    public ResponseEntity<BattleResponseDto> createBattle(@RequestBody CreateBattleDto createBattleDto) {
        return ResponseEntity.ok(
                new BattleResponseDto(
                        1,
                        createBattleDto.userId(),
                        null,
                        3,
                        "Not started",
                        Collections.emptyList(),
                        Arrays.asList(0, 1).contains(createBattleDto.movesFirst()) ? 1 : 2,
                        null,
                        ZonedDateTime.now().toInstant(),
                        null,
                        null
                )
        );
    }

    @PostMapping("/{battleId}/join")
    public ResponseEntity<BattleResponseDto> joinBattle(@PathVariable Integer battleId, @RequestBody JoinBattleDto joinBattleDto) {
        return ResponseEntity.ok(
                new BattleResponseDto(
                        battleId,
                        1,
                        joinBattleDto.secondPlayerId(),
                        3,
                        "Started",
                        Collections.emptyList(),
                        1,
                        null,
                        ZonedDateTime.now().toInstant(),
                        ZonedDateTime.now().toInstant(),
                        null
                )
        );
    }

    @PostMapping("/{battleId}/move")
    public ResponseEntity<BattleResponseDto> joinBattle(@PathVariable Integer battleId, @RequestBody MoveDto nextMove) {
        return ResponseEntity.ok(
                new BattleResponseDto(
                        battleId,
                        1,
                        2,
                        3,
                        "Started",
                        Collections.singletonList(nextMove),
                        2,
                        null,
                        ZonedDateTime.now().toInstant(),
                        ZonedDateTime.now().toInstant(),
                        null
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<BattleResponseDto>> searchBattles(
            @RequestParam("active") Boolean active,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNum") Integer pageNum) {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
