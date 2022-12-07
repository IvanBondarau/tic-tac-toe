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
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Mapper(
        componentModel = "spring"
)
public abstract class ModelMapper {
    @Mapping(source = "battleId", target = "id")
    @Mapping(source = ".", target = "nextMove", qualifiedByName = "toNextMove")
    @Mapping(source = "createdAt", target = "created", qualifiedByName = "timestampToInstant")
    @Mapping(source = "started", target = "started", qualifiedByName = "timestampToInstant")
    @Mapping(source = "finished", target = "finished", qualifiedByName = "timestampToInstant")
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
    protected UUID toNextMove(Battle battle) {
        return battleUtils.getNextMovePlayerId(battle);
    }

    @Named("timestampToInstant")
    protected @Nullable Instant timestampToInstant(@Nullable Timestamp timestamp) {
        return timestamp != null ? timestamp.toInstant() : null;
    }
}
