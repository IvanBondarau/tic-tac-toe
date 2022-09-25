package by.ibondarau.tictactoe.battleservice.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "move")
@SequenceGenerator(name = "move_sequence", sequenceName = "move_sequence")
@Data
@Accessors(chain = true)
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "move_sequence")
    private Integer moveId;
    @Column(name = "player_id")
    private Integer playerId;
    @Column(name = "time")
    private Instant time;
    @Column(name = "first_coordinate")
    private Integer firstCoordinate;
    @Column(name = "second_coordinate")
    private Integer secondCoordinate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_id", nullable = false)
    private Battle battle;
}
