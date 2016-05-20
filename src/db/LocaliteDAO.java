package db;

import model.reader.Localite;

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
public class LocaliteDAO extends DAO<Localite>{
    public static final String Col_ID = "Pk_Localite";
    public static final String COL_LOC_NAME = "Nom_Localite";
    public static final String COL_LOC_CP = "Cp_Localite";

    @Override
    public void ajouter(Localite loc){
        String sql = "INSERT INTO t_localites(Nom_Localite, Cp_Localite) VALUES (?, ?)";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1, loc.getNomVille());
            st.setString(2, loc.getCodePostal());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void modifier(Localite loc){
        String sql = "UPDATE t_localites set Nom_Localite = ?, Cp_Localite = ? WHERE Pk_Localite =?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setString(1, loc.getNomVille());
            st.setString(2, loc.getCodePostal());
            st.setInt(3, loc.getIDLocalite());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void supprimer(Localite loc) {
        String sql = "DELETE FROM t_localites WHERE Pk_Localite = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setInt(1, loc.getIDLocalite());
            logger.log(Level.INFO, "query is " + st.toString());
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public List<Localite> charger(){
        String sql = "SELECT * FROM t_localites";
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Localite> localites = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                localites.add(new Localite(rs));
            }
            Collections.sort(localites);
            return localites;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    @Override
    public List<Localite> chargerPar(int chx, String condition) {
        return null;
    }
}
