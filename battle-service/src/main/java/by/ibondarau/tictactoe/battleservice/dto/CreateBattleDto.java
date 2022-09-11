package by.ibondarau.tictactoe.battleservice.dto;

import java.util.Objects;

public class CreateBattleDto {
    private Integer userId;
    private Integer movesFirst;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMovesFirst() {
        return movesFirst;
    }

    public void setMovesFirst(Integer movesFirst) {
        this.movesFirst = movesFirst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateBattleDto that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(movesFirst, that.movesFirst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, movesFirst);
    }

    @Override
    public String toString() {
        return "CreateBattleDto{" +
                "userId=" + userId +
                ", movesFirst=" + movesFirst +
                '}';
    }
}
