package by.ibondarau.tictactoe.statisticservice.dto;

import java.time.Instant;
import java.util.Objects;

public class ShortBattleInfoDto {
    private Integer battleId;
    private Integer firstUserId;
    private Integer secondUserId;
    private Integer turns;

    private String result;

    private Instant battleFinished;

    public Integer getBattleId() {
        return battleId;
    }

    public void setBattleId(Integer battleId) {
        this.battleId = battleId;
    }

    public Integer getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(Integer firstUserId) {
        this.firstUserId = firstUserId;
    }

    public Integer getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Integer secondUserId) {
        this.secondUserId = secondUserId;
    }

    public Integer getTurns() {
        return turns;
    }

    public void setTurns(Integer turns) {
        this.turns = turns;
    }

    public Instant getBattleFinished() {
        return battleFinished;
    }

    public void setBattleFinished(Instant battleFinished) {
        this.battleFinished = battleFinished;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShortBattleInfoDto that)) return false;
        return Objects.equals(battleId, that.battleId) && Objects.equals(firstUserId, that.firstUserId) && Objects.equals(secondUserId, that.secondUserId) && Objects.equals(turns, that.turns) && Objects.equals(result, that.result) && Objects.equals(battleFinished, that.battleFinished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(battleId, firstUserId, secondUserId, turns, result, battleFinished);
    }

    @Override
    public String toString() {
        return "ShortBattleInfoDto{" +
                "battleId=" + battleId +
                ", firstUserId=" + firstUserId +
                ", secondUserId=" + secondUserId +
                ", turns=" + turns +
                ", result='" + result + '\'' +
                ", battleFinished=" + battleFinished +
                '}';
    }
}
