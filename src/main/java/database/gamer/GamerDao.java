package database.gamer;

import database.jpa.GenericJpaDao;

/**
 * DAO class for the {@link Gamer} entity.
 */
public class GamerDao extends GenericJpaDao<Gamer> {

    public GamerDao() {
        super(Gamer.class);
    }

}

