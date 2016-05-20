package db;

import model.book.Sujet;

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
public class SujetDAO extends DAO<Sujet>{
    public static final String Col_ID = "Pk_Sujet";
    public static final String COL_SUJ_NAME = "Nom_Sujet";

    @Override
    public void ajouter(Sujet s){
        String sql = "INSERT INTO t_sujets(Nom_Sujet) VALUES (?)";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1, s.getNomSujet());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void modifier(Sujet s){
        String sql = "UPDATE t_sujets set Nom_Sujet = ? WHERE Pk_Sujet = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setString(1, s.getNomSujet());
            st.setInt(2, s.getIdSujet());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void supprimer(Sujet s) {
        String sql = "DELETE FROM t_sujets WHERE Pk_Sujet = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setInt(1, s.getIdSujet());
            logger.log(Level.INFO, "query is " + st.toString());
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public List<Sujet> charger(){
        String sql = "SELECT * FROM t_sujets";
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Sujet> sujets = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                sujets.add(new Sujet(rs));
            }
            Collections.sort(sujets);
            return sujets;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    @Override
    public List<Sujet> chargerPar(int chx, String condition) {
        String sql = String.format("SELECT * FROM ((t_sujets INNER JOIN t_livresujet ON t_sujets.Pk_Sujet=t_livresujet.Fk_Sujet) INNER JOIN t_livres ON t_livresujet.Fk_Livre=t_livres.Pk_Livre) WHERE t_livres.Pk_Livre=%s", condition);
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Sujet> sujets = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                sujets.add(new Sujet(rs));
            }
            Collections.sort(sujets);
            return sujets;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }

    }
}
