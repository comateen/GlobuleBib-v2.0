package db;

import model.book.Localisation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by jof on 05/04/2016.
 */
public class LocalisationDAO extends DAO<Localisation>{
    private static final String Table_Name = "t_localisations";
    public static final String Col_ID = "Pk_Localisation";
    public static final String COL_LOCALISATION_NAME = "Nom_Localisation";

    @Override
    public void ajouter(Localisation loca){
        String sql = "INSERT INTO t_localisations(Nom_Localisation) VALUES (?)";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1, loca.getNomLocalisation());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void modifier(Localisation loca){
        String sql = "UPDATE t_localisations SET Nom_Localisation = ? Where Pk_Localisation = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setString(1, loca.getNomLocalisation());
            st.setInt(2, loca.getIdLocalisation());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void supprimer(Localisation loca) {
        String sql = "DELETE FROM t_localisations WHERE Pk_Localisation = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setInt(1, loca.getIdLocalisation());
            logger.log(Level.INFO, "query is " + st.toString());
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public List<Localisation> charger(){
        String sql = String.format("SELECT * FROM %s", Table_Name);
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Localisation> localisations = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                localisations.add(new Localisation(rs));
            }
            Collections.sort(localisations);
            return localisations;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    @Override
    public List<Localisation> chargerPar(int chx, String condition) {
        return null;
    }

}
