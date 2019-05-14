package database.gamer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Class representing the data of a player.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Gamer {

    @GeneratedValue
    @Id
    private int id;

    /**
     * The name of the player.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The amount of scores the player has.
     */
    @Column(nullable = false)
    private int score=0;

}