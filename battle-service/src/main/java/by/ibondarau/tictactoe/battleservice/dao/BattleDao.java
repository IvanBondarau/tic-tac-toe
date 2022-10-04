package by.ibondarau.tictactoe.battleservice.dao;

import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BattleDao extends JpaRepository<Battle, Integer> {
    Optional<Battle> findBattleByBattleId(int battleId);

    @Query("SELECT b from Battle b where b.status in :statuses")
    List<Battle> findBattles(@Param("statuses") Collection<BattleStatus> statuses, Pageable pageable);
}
