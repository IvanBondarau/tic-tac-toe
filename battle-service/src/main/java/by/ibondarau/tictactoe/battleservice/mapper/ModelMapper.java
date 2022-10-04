package by.ibondarau.tictactoe.battleservice.mapper;

import by.ibondarau.tictactoe.battleservice.dto.BattleResponseDto;
import by.ibondarau.tictactoe.battleservice.dto.MoveDto;
import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import by.ibondarau.tictactoe.battleservice.model.Move;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Objects;

@Mapper(
        componentModel = "spring"
)
public interface ModelMapper {
    @Mapping(source = "battleId", target = "id")
    @Mapping(source = ".", target = "nextMove", qualifiedByName = "toNextMove")
    BattleResponseDto battleToBattleResponse(Battle battle);

    @Mapping(source = "firstCoordinate", target = "x")
    @Mapping(source = "secondCoordinate", target = "y")
    MoveDto moveToMoveDto(Move move);


    @Mapping(target = "firstCoordinate", source = "x")
    @Mapping(target = "secondCoordinate", source = "y")
    Move moveDtoToMove(MoveDto moveDto);

    @Named("toNextMove")
    static Integer toNextMove(Battle battle) {
        if (battle.getStatus() != BattleStatus.STARTED) {
            return null;
        }
        Integer firstPlayerId = Objects.equals(battle.getFirstPlayerId(), battle.getFirstMovingPlayerId()) ? battle.getFirstPlayerId() : battle.getSecondPlayerId();
        Integer secondPlayerId = Objects.equals(battle.getFirstPlayerId(), battle.getFirstMovingPlayerId()) ? battle.getSecondPlayerId() : battle.getFirstPlayerId();

        if (battle.getMoves() == null || battle.getMoves().isEmpty()) {
            return battle.getFirstMovingPlayerId();
        } else {
            return battle.getMoves().size() % 2 == 0 ? firstPlayerId : secondPlayerId;
        }
    }
}
