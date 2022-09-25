package by.ibondarau.tictactoe.battleservice.mapper;

import by.ibondarau.tictactoe.battleservice.dto.BattleResponseDto;
import by.ibondarau.tictactoe.battleservice.dto.MoveDto;
import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.Move;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface ModelMapper {
    @Mapping(source = "battleId", target = "id")
    BattleResponseDto battleToBattleResponse(Battle battle);

    @Mapping(source = "firstCoordinate", target = "x")
    @Mapping(source = "secondCoordinate", target = "y")
    MoveDto moveToMoveDto(Move move);
}
