package by.ibondarau.tictactoe.battleservice.dao;

import by.ibondarau.tictactoe.battleservice.model.Battle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BattleDao extends JpaRepository<Battle, Integer> {
    Optional<Battle> findBattleByBattleId(Integer battleId);
}
