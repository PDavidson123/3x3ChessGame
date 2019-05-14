package database.jpa;

import com.google.inject.Guice;
import com.google.inject.Injector;
import database.gamer.Gamer;
import database.gamer.GamerDao;
import guice.PersistenceModule;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DBTools {
    private GamerDao gmd;

    public DBTools() {
        Injector injector = Guice.createInjector(new PersistenceModule("Gamer"));
        gmd = injector.getInstance(GamerDao.class);
    }

    /**
     * Inserts a row with the data of the player into the database, if it is not present yet.
     * @param gamer a {@link Gamer} object, which provides data of the player.
     */
    public void addGamer(Gamer gamer) {
        Gamer tmp = null;
        List<Gamer> list=gmd.findAll();

        for (Gamer g : list)
            if(g.getName().equals(gamer.getName()))
                tmp = g;

        if(tmp == null)
            gmd.persist(gamer);
    }
    /**
     * Updates the row in the database, which contains the data of player {@code gamer}.
     * @param gamer a {@link Gamer} object, which provides data of the player.
     */
    public void updateGamer(Gamer gamer) {
        Gamer tmp = null;
        List<Gamer> list=gmd.findAll();

        for (Gamer g : list)
            if (g.getName().equals(gamer.getName()) )
                tmp = g;

        tmp.setScore(tmp.getScore()+1);
        gmd.update(tmp);
    }

    /**
     *
     * @return
     */
    public List<Gamer> getLeaderboard() {
        return gmd.findAll().stream().sorted(Comparator.comparingInt(Gamer::getScore).reversed()).collect(Collectors.toList());
    }
}
