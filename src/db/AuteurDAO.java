package db;

import model.book.Auteur;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by jof on 05/04/2016.
 */
public class AuteurDAO extends DAO<Auteur>{
    public static final String Col_ID = "Pk_Auteur";
    public static final String COL_FIRST_NAME = "Nom_Auteur";
    public static final String COL_LAST_NAME = "Prenom_Auteur";
    public static final String COL_BIRTH_DATE = "DN_Auteur";
    public static final String COL_DD_DATE = "DD_Auteur";

    @Override
    public void ajouter(Auteur a){
        String sql = "INSERT INTO t_auteurs(Nom_Auteur, Prenom_Auteur, DN_Auteur) VALUES (?, ?, ?)";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){ //je reste sur le multi try, j'y vois plus clair comme ça
            st.setString(1, a.getNomAuteur());
            st.setString(2, a.getPrenomAuteur());
            st.setDate(3, Date.valueOf(a.getDnAuteur()));
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void modifier(Auteur a){
        String sql = "UPDATE t_auteurs set Nom_Auteur = ?, Prenom_Auteur = ?, DN_Auteur = ? WHERE Pk_Auteur = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setString(1, a.getNomAuteur());
            st.setString(2, a.getPrenomAuteur());
            st.setDate(3, Date.valueOf(a.getDnAuteur()));
            st.setInt(4, a.getIdAuteur());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void supprimer(Auteur a) {
        String sql = "DELETE FROM t_auteurs WHERE Pk_Auteur = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setInt(1, a.getIdAuteur());
            logger.log(Level.INFO, "query is " + st.toString());
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

    }

    @Override
    public List<Auteur> charger(){
        String sql = "SELECT * FROM t_auteurs";
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Auteur> auteurs = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                auteurs.add(new Auteur(rs));
            }
            Collections.sort(auteurs);
            return auteurs;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    @Override
    public List<Auteur> chargerPar(int chx, String condition) {
        String sql = String.format("SELECT * FROM ((t_auteurs INNER JOIN t_livreauteur ON t_auteurs.Pk_Auteur=t_livreauteur.Fk_Auteur) INNER JOIN t_livres ON t_livreAuteur.Fk_Livre=t_livres.Pk_Livre) WHERE t_livres.Pk_Livre=%s", condition);
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Auteur> auteurs = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                auteurs.add(new Auteur(rs));
            }
            Collections.sort(auteurs);
            return auteurs;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

}
