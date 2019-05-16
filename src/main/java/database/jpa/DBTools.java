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
     * Beszúr egy sort a játékos adataival az adatbázisba, ha az még nincs jelen.
     * @param gamer egy {@link Gamer} objektum, ami a játékos adatait szolgáltatja.
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
     * Frissít egy sort az adatbázisban, ami a megadott játékos adatait tartalmazza.
     * @param gamer egy {@link Gamer} objektum, ami a játékos adatait szolgáltatja.
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
     * A függvény lekéri a játékosokat az adatbázisból, és pontszám alapján csökkenő sorrendbe rendezi.
     * @return játékosokat, játékosok adatait adja vissza
     */
    public List<Gamer> getLeaderboard() {
        return gmd.findAll().stream().sorted(Comparator.comparingInt(Gamer::getScore).reversed()).collect(Collectors.toList());
    }
}
