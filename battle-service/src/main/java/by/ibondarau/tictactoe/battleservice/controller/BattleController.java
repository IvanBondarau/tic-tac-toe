package by.ibondarau.tictactoe.battleservice.controller;

import by.ibondarau.tictactoe.battleservice.dto.BattleResponseDto;
import by.ibondarau.tictactoe.battleservice.dto.CreateBattleDto;
import by.ibondarau.tictactoe.battleservice.dto.JoinBattleDto;
import by.ibondarau.tictactoe.battleservice.dto.MoveDto;
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
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/battle")
public class BattleController {

    @GetMapping("/{battleId}")
    public ResponseEntity<BattleResponseDto> get(@PathVariable Integer battleId) {
        BattleResponseDto dto = new BattleResponseDto()
                .id(battleId)
                .firstPlayerId(1)
                .secondPlayerId(2)
                .size(3)
                .status("Finished")
                .moves(Collections.emptyList())
                .nextMove(null)
                .result("First wins")
                .created(ZonedDateTime.now().toInstant())
                .started(ZonedDateTime.now().toInstant())
                .finished(ZonedDateTime.now().toInstant());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<BattleResponseDto> createBattle(@RequestBody CreateBattleDto createBattleDto) {
        BattleResponseDto dto = new BattleResponseDto()
                .id(1)
                .firstPlayerId(createBattleDto.userId())
                .secondPlayerId(2)
                .size(3)
                .status("Created")
                .moves(Collections.emptyList())
                .nextMove(1)
                .result(null)
                .created(ZonedDateTime.now().toInstant())
                .started(null)
                .finished(null);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{battleId}/join")
    public ResponseEntity<BattleResponseDto> joinBattle(@PathVariable Integer battleId, @RequestBody JoinBattleDto joinBattleDto) {
        BattleResponseDto dto = new BattleResponseDto()
                .id(battleId)
                .firstPlayerId(1)
                .secondPlayerId(joinBattleDto.secondPlayerId())
                .size(3)
                .status("Started")
                .moves(Collections.emptyList())
                .nextMove(1)
                .result(null)
                .created(ZonedDateTime.now().toInstant())
                .started(ZonedDateTime.now().toInstant())
                .finished(null);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{battleId}/move")
    public ResponseEntity<BattleResponseDto> makeMove(@PathVariable Integer battleId, @RequestBody MoveDto nextMove) {
        BattleResponseDto dto = new BattleResponseDto()
                .id(battleId)
                .firstPlayerId(1)
                .secondPlayerId(2)
                .size(3)
                .status("Started")
                .moves(Collections.singletonList(nextMove))
                .nextMove(2)
                .result(null)
                .created(ZonedDateTime.now().toInstant())
                .started(ZonedDateTime.now().toInstant())
                .finished(null);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<BattleResponseDto>> searchBattles(
            @RequestParam("active") Boolean active,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNum") Integer pageNum) {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
