package by.ibondarau.tictactoe.battleservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class BattleResponseDto {
    private UUID id;
    private UUID firstPlayerId;
    private UUID secondPlayerId;
    private Integer size;
    private String status;
    private List<MoveDto> moves;
    private UUID nextMove;
    private String result;
    private Instant created;
    private Instant started;
    private Instant finished;
}
