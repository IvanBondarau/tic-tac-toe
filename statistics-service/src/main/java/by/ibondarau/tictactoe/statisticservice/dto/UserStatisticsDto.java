package by.ibondarau.tictactoe.statisticservice.dto;

import java.util.List;
import java.util.Objects;

public class UserStatisticsDto {
    private Integer userId;
    private Integer wins;
    private Integer losses;
    private Integer draws;
    private List<ShortBattleInfoDto> lastBattles;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getDraws() {
        return draws;
    }

    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    public List<ShortBattleInfoDto> getLastBattles() {
        return lastBattles;
    }

    public void setLastBattles(List<ShortBattleInfoDto> lastBattles) {
        this.lastBattles = lastBattles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserStatisticsDto that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(wins, that.wins) && Objects.equals(losses, that.losses) && Objects.equals(draws, that.draws) && Objects.equals(lastBattles, that.lastBattles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, wins, losses, draws, lastBattles);
    }

    @Override
    public String toString() {
        return "UserStatisticsDto{" +
                "userId=" + userId +
                ", wins=" + wins +
                ", losses=" + losses +
                ", draws=" + draws +
                ", lastBattles=" + lastBattles +
                '}';
    }
}
