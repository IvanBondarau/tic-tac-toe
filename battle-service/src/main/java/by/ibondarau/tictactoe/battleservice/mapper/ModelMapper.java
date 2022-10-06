package by.ibondarau.tictactoe.battleservice.mapper;

import by.ibondarau.tictactoe.battleservice.dto.BattleResponseDto;
import by.ibondarau.tictactoe.battleservice.dto.MoveDto;
import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.Move;
import by.ibondarau.tictactoe.battleservice.util.BattleUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring"
)
public abstract class ModelMapper {
    @Mapping(source = "battleId", target = "id")
    @Mapping(source = ".", target = "nextMove", qualifiedByName = "toNextMove")
    public abstract BattleResponseDto battleToBattleResponse(Battle battle);

    @Mapping(source = "firstCoordinate", target = "x")
    @Mapping(source = "secondCoordinate", target = "y")
    public abstract MoveDto moveToMoveDto(Move move);


    @Mapping(target = "firstCoordinate", source = "x")
    @Mapping(target = "secondCoordinate", source = "y")
    public abstract Move moveDtoToMove(MoveDto moveDto);

    @Autowired
    protected BattleUtils battleUtils;

    @Named("toNextMove")
    protected Integer toNextMove(Battle battle) {
        return battleUtils.getNextMovePlayerId(battle);
    }
}
