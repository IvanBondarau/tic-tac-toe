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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/battle")
public class BattleController {

    @GetMapping("/{battleId}")
    public ResponseEntity<BattleResponseDto> get(@PathVariable Integer battleId) {
        BattleResponseDto battleResponseDto = new BattleResponseDto();
        battleResponseDto.setId(battleId);
        battleResponseDto.setFirstPlayerId(1);
        battleResponseDto.setSecondPlayerId(2);
        battleResponseDto.setSize(3);
        battleResponseDto.setStatus("Finished");
        battleResponseDto.setMoves(
                Arrays.asList()
        );
        battleResponseDto.setNextMove(null);
        battleResponseDto.setResult("First wins");
        battleResponseDto.setCreated(ZonedDateTime.now().toInstant());
        battleResponseDto.setStarted(ZonedDateTime.now().toInstant());
        battleResponseDto.setFinished(ZonedDateTime.now().toInstant());

        return ResponseEntity.ok(battleResponseDto);
    }

    @PostMapping
    public ResponseEntity<BattleResponseDto> createBattle(@RequestBody CreateBattleDto createBattleDto) {
        BattleResponseDto battleResponseDto = new BattleResponseDto();
        battleResponseDto.setId(1);
        battleResponseDto.setFirstPlayerId(createBattleDto.getUserId());
        battleResponseDto.setSecondPlayerId(2);
        battleResponseDto.setSize(3);
        battleResponseDto.setStatus("Created");
        battleResponseDto.setMoves(
                Collections.emptyList()
        );
        battleResponseDto.setNextMove(createBattleDto.getMovesFirst());
        battleResponseDto.setResult(null);
        battleResponseDto.setCreated(ZonedDateTime.now().toInstant());
        battleResponseDto.setStarted(null);
        battleResponseDto.setFinished(null);

        return ResponseEntity.ok(battleResponseDto);
    }

    @PostMapping("/{battleId}/join")
    public ResponseEntity<BattleResponseDto> joinBattle(@PathVariable Integer battleId, @RequestBody JoinBattleDto joinBattleDto) {
        BattleResponseDto battleResponseDto = new BattleResponseDto();
        battleResponseDto.setId(battleId);
        battleResponseDto.setFirstPlayerId(1);
        battleResponseDto.setSecondPlayerId(joinBattleDto.getSecondPlayerId());
        battleResponseDto.setSize(3);
        battleResponseDto.setStatus("Started");
        battleResponseDto.setMoves(
                Collections.emptyList()
        );
        battleResponseDto.setNextMove(1);
        battleResponseDto.setResult(null);
        battleResponseDto.setCreated(ZonedDateTime.now().toInstant());
        battleResponseDto.setStarted(ZonedDateTime.now().toInstant());
        battleResponseDto.setFinished(null);

        return ResponseEntity.ok(battleResponseDto);
    }

    @PostMapping("/{battleId}/move")
    public ResponseEntity<BattleResponseDto> move(@PathVariable Integer battleId, @RequestBody MoveDto nextMove) {
        BattleResponseDto battleResponseDto = new BattleResponseDto();
        battleResponseDto.setId(battleId);
        battleResponseDto.setFirstPlayerId(1);
        battleResponseDto.setSecondPlayerId(2);
        battleResponseDto.setSize(3);
        battleResponseDto.setStatus("Started");
        battleResponseDto.setMoves(
                Collections.singletonList(nextMove)
        );
        battleResponseDto.setNextMove(1);
        battleResponseDto.setResult(null);
        battleResponseDto.setCreated(ZonedDateTime.now().toInstant());
        battleResponseDto.setStarted(ZonedDateTime.now().toInstant());
        battleResponseDto.setFinished(null);

        return ResponseEntity.ok(battleResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<BattleResponseDto>> searchBattles(
            @RequestParam("active") Boolean active,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNum") Integer pageNum) {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
