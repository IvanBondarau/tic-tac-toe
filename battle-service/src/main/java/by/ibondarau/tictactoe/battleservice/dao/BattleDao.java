package by.ibondarau.tictactoe.battleservice.dao;

import by.ibondarau.tictactoe.battleservice.model.Battle;
import by.ibondarau.tictactoe.battleservice.model.BattleStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BattleDao extends JpaRepository<Battle, UUID> {
    Optional<Battle> findBattleByBattleId(UUID battleId);

    List<Battle> findBattlesByStatusIn(@Param("statuses") Collection<BattleStatus> statuses, Pageable pageable);
}
