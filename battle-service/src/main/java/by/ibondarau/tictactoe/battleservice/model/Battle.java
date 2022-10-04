package by.ibondarau.tictactoe.battleservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "battle")
@SequenceGenerator(name = "battle_sequence", sequenceName = "battle_sequence")
@Data
@Accessors(chain = true)
public class Battle {

    @Id
    @Column(name = "battle_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "battle_sequence")
    private Integer battleId;
    @Column(name = "size", nullable = false)
    private Integer size;
    @Column(name = "created", nullable = false)
    private Instant created;
    @Column(name = "started")
    private Instant started;
    @Column(name = "finished")
    private Instant finished;
    @Column(name = "status", nullable = false)
    private BattleStatus status;
    @Column(name = "result")
    private BattleResult result;
    @Column(name = "first_player_id")
    private Integer firstPlayerId;
    @Column(name = "second_player_id")
    private Integer secondPlayerId;
    @Column(name = "first_move_rule", nullable = false)
    private FirstMoveRule firstMoveRule;
    @Column(name = "first_moving_player_id")
    private Integer firstMovingPlayerId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "battle", cascade = CascadeType.ALL)
    private List<Move> moves;


}
