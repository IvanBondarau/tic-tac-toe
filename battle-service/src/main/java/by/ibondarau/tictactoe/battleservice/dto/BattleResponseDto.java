package by.ibondarau.tictactoe.battleservice.dto;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

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

    public BattleResponseDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFirstPlayerId() {
        return firstPlayerId;
    }

    public void setFirstPlayerId(Integer firstPlayerId) {
        this.firstPlayerId = firstPlayerId;
    }

    public Integer getSecondPlayerId() {
        return secondPlayerId;
    }

    public void setSecondPlayerId(Integer secondPlayerId) {
        this.secondPlayerId = secondPlayerId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MoveDto> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveDto> moves) {
        this.moves = moves;
    }

    public Integer getNextMove() {
        return nextMove;
    }

    public void setNextMove(Integer nextMove) {
        this.nextMove = nextMove;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getStarted() {
        return started;
    }

    public void setStarted(Instant started) {
        this.started = started;
    }

    public Instant getFinished() {
        return finished;
    }

    public void setFinished(Instant finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BattleResponseDto) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.firstPlayerId, that.firstPlayerId) &&
                Objects.equals(this.secondPlayerId, that.secondPlayerId) &&
                Objects.equals(this.size, that.size) &&
                Objects.equals(this.status, that.status) &&
                Objects.equals(this.moves, that.moves) &&
                Objects.equals(this.nextMove, that.nextMove) &&
                Objects.equals(this.result, that.result) &&
                Objects.equals(this.created, that.created) &&
                Objects.equals(this.started, that.started) &&
                Objects.equals(this.finished, that.finished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstPlayerId, secondPlayerId, size, status, moves, nextMove, result, created, started, finished);
    }

    @Override
    public String toString() {
        return "BattleResponseDto[" +
                "id=" + id + ", " +
                "firstPlayerId=" + firstPlayerId + ", " +
                "secondPlayerId=" + secondPlayerId + ", " +
                "size=" + size + ", " +
                "status=" + status + ", " +
                "moves=" + moves + ", " +
                "nextMove=" + nextMove + ", " +
                "result=" + result + ", " +
                "created=" + created + ", " +
                "started=" + started + ", " +
                "finished=" + finished + ']';
    }

}
