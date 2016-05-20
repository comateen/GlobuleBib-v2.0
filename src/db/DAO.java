package db;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by jof on 05/04/2016.
 */

// Cette classe abstraite me sert à centraliser ls appels aux différents DAO
abstract class DAO<T> {
    static Database db = Database.getInstance();
    static final Logger logger = Logger.getLogger("DBLOG");

    public abstract void ajouter(T value);

    public abstract void modifier(T value);

    public abstract void supprimer(T value);

    public abstract List<T> charger();

    public abstract List<T> chargerPar(int chx, String condition);
}
