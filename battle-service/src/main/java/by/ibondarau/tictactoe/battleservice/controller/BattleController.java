package by.ibondarau.tictactoe.battleservice.controller;

import by.ibondarau.tictactoe.battleservice.dto.BattleResponseDto;
import by.ibondarau.tictactoe.battleservice.dto.CreateBattleDto;
import by.ibondarau.tictactoe.battleservice.dto.JoinBattleDto;
import by.ibondarau.tictactoe.battleservice.dto.MoveDto;
import by.ibondarau.tictactoe.battleservice.mapper.ModelMapper;
import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.service.BattleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/battle")
@RequiredArgsConstructor
@Validated
public class BattleController {

    private final BattleService battleService;
    private final ModelMapper modelMapper;

    @GetMapping("/{battleId}")
    public ResponseEntity<BattleResponseDto> get(@PathVariable UUID battleId) {
        return ResponseEntity.ok(modelMapper.battleToBattleResponse(battleService.getBattle(battleId)));
    }

    @PostMapping
    public ResponseEntity<BattleResponseDto> createBattle(@RequestBody CreateBattleDto createBattleDto) {
        Battle battle = battleService.createBattle(
                createBattleDto.getUserId(),
                createBattleDto.getSize(),
                createBattleDto.getFirstMoveRule()
        );

        return ResponseEntity.ok(modelMapper.battleToBattleResponse(battle));
    }

    @PostMapping("/{battleId}/join")
    public ResponseEntity<BattleResponseDto> joinBattle(@PathVariable UUID battleId, @RequestBody JoinBattleDto joinBattleDto) {
        BattleResponseDto battleResponseDto = modelMapper.battleToBattleResponse(
                battleService.joinBattle(battleId, joinBattleDto.getSecondPlayerId())
        );
        return ResponseEntity.ok(battleResponseDto);
    }

    @PostMapping("/{battleId}/move")
    public ResponseEntity<BattleResponseDto> makeMove(@PathVariable UUID battleId, @RequestBody MoveDto nextMove) {
        Battle battle = battleService.makeMove(battleId, modelMapper.moveDtoToMove(nextMove));
        return ResponseEntity.ok(modelMapper.battleToBattleResponse(battle));
    }

    @GetMapping
    public ResponseEntity<List<BattleResponseDto>> findBattles(
            @RequestParam("statuses") @Size(min = 1, max = 3) Set<BattleStatus> statuses,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok(
                battleService.findBattles(statuses, pageNum, pageSize)
                        .stream().map(modelMapper::battleToBattleResponse)
                        .collect(Collectors.toList())
        );
    }
}
