package by.ibondarau.tictactoe.battleservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "battle")
@GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
)
@Data
public class Battle extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(nullable = false)
    private UUID battleId;
    @Column(nullable = false)
    private Integer size;
    @Column
    private Timestamp started;
    @Column
    private Timestamp finished;
    @Column(nullable = false)
    private BattleStatus status;
    @Column
    private BattleResult result;
    @Column
    private UUID firstPlayerId;
    @Column
    private UUID secondPlayerId;
    @Column(nullable = false)
    private FirstMoveRule firstMoveRule;
    @Column
    private UUID firstMovingPlayerId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "battle", cascade = CascadeType.ALL)
    private List<Move> moves;


}
