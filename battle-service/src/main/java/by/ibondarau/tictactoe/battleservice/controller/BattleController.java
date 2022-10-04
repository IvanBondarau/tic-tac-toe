package by.ibondarau.tictactoe.battleservice.controller;

import by.ibondarau.tictactoe.battleservice.dto.BattleResponseDto;
import by.ibondarau.tictactoe.battleservice.dto.CreateBattleDto;
import by.ibondarau.tictactoe.battleservice.dto.JoinBattleDto;
import by.ibondarau.tictactoe.battleservice.dto.MoveDto;
import by.ibondarau.tictactoe.battleservice.exception.NotFoundException;
import by.ibondarau.tictactoe.battleservice.mapper.ModelMapper;
import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.FirstMoveRule;
import by.ibondarau.tictactoe.battleservice.service.BattleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/battle")
public class BattleController {

    private static final Logger logger = LoggerFactory.getLogger(BattleController.class);

    private final BattleService battleService;

    private final ModelMapper modelMapper;

    public BattleController(BattleService battleService, ModelMapper modelMapper) {
        this.battleService = battleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{battleId}")
    public ResponseEntity<BattleResponseDto> get(@PathVariable Integer battleId) {
        return ResponseEntity.ok(modelMapper.battleToBattleResponse(battleService.getBattle(battleId)));
    }

    @PostMapping
    public ResponseEntity<BattleResponseDto> createBattle(@RequestBody CreateBattleDto createBattleDto) {
        logger.info("BattleController.createBattle() - started");
        logger.info("BattleController.createBattle() - createBattleDto=" + createBattleDto.toString());

        Battle battle = battleService.createBattle(
                createBattleDto.getUserId(),
                createBattleDto.getSize(),
                FirstMoveRule.valueOf(createBattleDto.getFirstMoveRule())
        );

        return ResponseEntity.ok(modelMapper.battleToBattleResponse(battle));
    }

    @PostMapping("/{battleId}/join")
    public ResponseEntity<BattleResponseDto> joinBattle(@PathVariable Integer battleId, @RequestBody JoinBattleDto joinBattleDto) {
        logger.info("BattleController.createBattle() - started");
        logger.info("BattleController.createBattle() - battleId=" + battleId + ", playerId=" + joinBattleDto.getSecondPlayerId());

        BattleResponseDto battleResponseDto = modelMapper.battleToBattleResponse(
                battleService.joinBattle(battleId, joinBattleDto.getSecondPlayerId())
        );
        return ResponseEntity.ok(battleResponseDto);
    }

    @PostMapping("/{battleId}/move")
    public ResponseEntity<BattleResponseDto> makeMove(@PathVariable Integer battleId, @RequestBody MoveDto nextMove) {
        logger.info("BattleController.makeMove() - started");
        logger.info("BattleController.makeMove() - battleId="+battleId+", nextMove="+nextMove);

        Battle battle = battleService.makeMove(battleId, modelMapper.moveDtoToMove(nextMove));
        return ResponseEntity.ok(modelMapper.battleToBattleResponse(battle));
    }

    @GetMapping
    public ResponseEntity<List<BattleResponseDto>> findBattles(
            @RequestParam("active") Boolean active,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize) {
        logger.info("BattleController.findBattles - Started");
        logger.info("BattleController.findBattles - active=" + active + ", pageNum=" + pageNum + ", pageSize=" + pageSize);
        return ResponseEntity.ok(
                battleService.findBattles(active, pageNum, pageSize)
                        .stream().map(modelMapper::battleToBattleResponse)
                        .collect(Collectors.toList())
        );
    }
}
