package database.gamer;

import database.jpa.GenericJpaDao;

/**
 * DAO osztály a {@link Gamer} entitásnak.
 */
public class GamerDao extends GenericJpaDao<Gamer> {

    public GamerDao() {
        super(Gamer.class);
    }

}

