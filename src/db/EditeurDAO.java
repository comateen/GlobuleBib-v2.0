package db;

import model.book.Editeur;

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

public class EditeurDAO extends DAO<Editeur>{
    public static final String Col_ID = "Pk_Editeur";
    public static final String COL_Col_NAME = "Nom_Editeur";

    @Override
    public void ajouter(Editeur editor){
        String sql = "INSERT INTO t_editeurs(Nom_Editeur) VALUES (?)";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            st.setString(1, editor.getNomEditeur());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void modifier(Editeur editor){
        String sql = "UPDATE t_editeurs set Nom_Editeur = ? WHERE Pk_Editeur = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setString(1, editor.getNomEditeur());
            st.setInt(2, editor.getIdEditeur());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void supprimer(Editeur editor) {
        String sql = "DELETE FROM t_editeurs WHERE Pk_Editeur = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setInt(1, editor.getIdEditeur());
            logger.log(Level.INFO, "query is " + st.toString());
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public List<Editeur> charger(){
        String sql = "SELECT * FROM t_editeurs";
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Editeur> editeurs = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                editeurs.add(new Editeur(rs));
            }
            Collections.sort(editeurs);
            return editeurs;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    @Override
    public List<Editeur> chargerPar(int chx, String condition) {
        return null;
    } // --> pas encore utiliser je dois la faire

}
