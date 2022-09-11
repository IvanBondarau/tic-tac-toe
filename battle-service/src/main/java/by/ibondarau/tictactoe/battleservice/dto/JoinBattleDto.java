package by.ibondarau.tictactoe.battleservice.dto;

import java.util.Objects;

public class JoinBattleDto {
    private Integer secondPlayerId;

    public Integer getSecondPlayerId() {
        return secondPlayerId;
    }

    public void setSecondPlayerId(Integer secondPlayerId) {
        this.secondPlayerId = secondPlayerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JoinBattleDto that)) return false;
        return Objects.equals(secondPlayerId, that.secondPlayerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secondPlayerId);
    }

    @Override
    public String toString() {
        return "JoinBattleDto{" +
                "secondPlayerId=" + secondPlayerId +
                '}';
    }
}
