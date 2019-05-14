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
 * Játékos adatai.
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
     * Játékos neve.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Játékos pontszáma.
     */
    @Column(nullable = false)
    private int score=0;

}