package by.ibondarau.tictactoe.battleservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.List;

@Data
@Accessors(chain = true)
public class BattleResponseDto {
    private Integer id;
    private Integer firstPlayerId;
    private Integer secondPlayerId;
    private Integer size;
    private String status;
    private List<MoveDto> moves;
    private Integer nextMove;
    private String result;
    private Instant created;
    private Instant started;
    private Instant finished;
}
