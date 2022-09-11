package by.ibondarau.tictactoe.battleservice.dto;

import java.util.Objects;

public class MoveDto{
    private Integer playerId;
    private Integer x;
    private Integer y;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveDto moveDto)) return false;
        return Objects.equals(playerId, moveDto.playerId) && Objects.equals(x, moveDto.x) && Objects.equals(y, moveDto.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, x, y);
    }

    @Override
    public String toString() {
        return "MoveDto{" +
                "playerId=" + playerId +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
