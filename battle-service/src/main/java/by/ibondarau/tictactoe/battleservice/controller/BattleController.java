package by.ibondarau.tictactoe.battleservice.controller;

import by.ibondarau.tictactoe.battleservice.dto.BattleResponseDto;
import by.ibondarau.tictactoe.battleservice.dto.CreateBattleDto;
import by.ibondarau.tictactoe.battleservice.dto.JoinBattleDto;
import by.ibondarau.tictactoe.battleservice.dto.MoveDto;
import by.ibondarau.tictactoe.battleservice.exception.BadInputFormat;
import by.ibondarau.tictactoe.battleservice.mapper.ModelMapper;
import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.service.BattleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/battle")
@RequiredArgsConstructor
@Slf4j
public class BattleController {

    private final BattleService battleService;
    private final ModelMapper modelMapper;

    @GetMapping("/{battleId}")
    public ResponseEntity<BattleResponseDto> get(@PathVariable UUID battleId) {
        return ResponseEntity.ok(modelMapper.battleToBattleResponse(battleService.getBattle(battleId)));
    }

    @PostMapping
    public ResponseEntity<BattleResponseDto> createBattle(@RequestBody CreateBattleDto createBattleDto) {
        log.debug("""
                        BattleController.createBattle() - started
                        Input parameters: createBattleDto={}""",
                createBattleDto.toString());

        Battle battle = battleService.createBattle(
                createBattleDto.getUserId(),
                createBattleDto.getSize(),
                createBattleDto.getFirstMoveRule()
        );

        return ResponseEntity.ok(modelMapper.battleToBattleResponse(battle));
    }

    @PostMapping("/{battleId}/join")
    public ResponseEntity<BattleResponseDto> joinBattle(@PathVariable UUID battleId, @RequestBody JoinBattleDto joinBattleDto) {
        log.debug("""
                        BattleController.joinBattle() - started
                        Input parameters: battleId={}, playerId={}""",
                battleId, joinBattleDto.toString());

        BattleResponseDto battleResponseDto = modelMapper.battleToBattleResponse(
                battleService.joinBattle(battleId, joinBattleDto.getSecondPlayerId())
        );
        return ResponseEntity.ok(battleResponseDto);
    }

    @PostMapping("/{battleId}/move")
    public ResponseEntity<BattleResponseDto> makeMove(@PathVariable UUID battleId, @RequestBody MoveDto nextMove) {
        log.debug("""
                BattleController.makeMove() - started
                Input parameters: battleId={}, nextMove={}""", battleId, nextMove);

        Battle battle = battleService.makeMove(battleId, modelMapper.moveDtoToMove(nextMove));
        return ResponseEntity.ok(modelMapper.battleToBattleResponse(battle));
    }

    @GetMapping
    public ResponseEntity<List<BattleResponseDto>> findBattles(
            @RequestParam("status") List<BattleStatus> statuses,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize) {
        log.debug("""
                BattleController.findBattles - Started
                Input parameters: statuses={}, pageNum={}, pageSize={}""", statuses, pageNum, pageSize);
        validateStatuses(statuses);
        return ResponseEntity.ok(
                battleService.findBattles(statuses, pageNum, pageSize)
                        .stream().map(modelMapper::battleToBattleResponse)
                        .collect(Collectors.toList())
        );
    }

    private void validateStatuses(List<BattleStatus> statuses) {

        if (statuses.size() > 3) {
            throw new BadInputFormat("No more than 3 statuses are allowed");
        }
        EnumSet<BattleStatus> battleStatuses = EnumSet.noneOf(BattleStatus.class);

        statuses.forEach(status -> {
            try {
                if (battleStatuses.contains(status)) {
                    throw new BadInputFormat("Duplicated status");
                } else {
                    battleStatuses.add(status);
                }
            } catch (Exception e) {
                throw new BadInputFormat("Wrong status name");
            }
        });
    }
}
