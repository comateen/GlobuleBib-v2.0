package db;


import model.reader.Lecteur;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by jof on 05/04/2016.
 */
public class LecteurDAO extends DAO<Lecteur>{
    public static final String Col_ID = "Pk_Lecteur";
    public static final String COL_FIRST_NAME = "Nom_Lecteur";
    public static final String COL_LAST_NAME = "Prenom_Lecteur";
    public static final String COL_ADDRESS = "Adresse_Lecteur";
    public static final String COL_BIRTH_DATE = "DN_Lecteur";
    public static final String COL_xID_Loc = "Fk_Localite";
    public static final String COL_NumTel = "NumTel_Lecteur";
    public static final String COL_Mail = "Mail_Lecteur";
    public static final String COL_Cat = "Categorie_Lecteur";
    public static final String COL_I_DATE = "DI_Lecteur";
    public static final String COL_R_DATE = "DR_Lecteur";
    public static final String COL_E_DATE = "DE_Lecteur";

    @Override
    public void ajouter(Lecteur l) {
        String sql = "INSERT INTO t_Lecteurs(Nom_Lecteur, Prenom_Lecteur, Adresse_Lecteur, DN_Lecteur, Fk_Localite, Numtel_Lecteur, Mail_Lecteur, Categorie_Lecteur, DI_Lecteur, DR_Lecteur, DE_Lecteur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1, l.getNomLecteur());
            st.setString(2, l.getPrenomLecteur());
            st.setString(3, l.getAdresseLecteur());
            st.setDate(4, Date.valueOf(l.getDNLecteur()));
            st.setInt(5, l.getLoc());
            st.setString(6, l.getTelLecteur());
            st.setString(7, l.getMailLecteur());
            st.setString(8, l.getCategorieLecteur());
            st.setDate(9, Date.valueOf(l.getDILecteur()));
            st.setDate(10, Date.valueOf(l.getDRLecteur()));
            st.setDate(11, Date.valueOf(l.getDELecteur()));
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void modifier(Lecteur l) {
        String sql = "UPDATE t_lecteurs SET Nom_Lecteur = ?, Prenom_Lecteur = ?, Adresse_Lecteur = ?, DN_Lecteur = ?, Fk_Localite = ?, Numtel_Lecteur = ?, Mail_Lecteur = ?, Categorie_Lecteur = ?, DI_Lecteur = ?, DR_Lecteur = ?, DE_Lecteur = ? WHERE Pk_Lecteur = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setString(1, l.getNomLecteur());
            st.setString(2, l.getPrenomLecteur());
            st.setString(3, l.getAdresseLecteur());
            st.setDate(4, Date.valueOf(l.getDNLecteur()));
            st.setInt(5, l.getLoc());
            st.setString(6, l.getTelLecteur());
            st.setString(7, l.getMailLecteur());
            st.setString(8, l.getCategorieLecteur());
            st.setDate(9, Date.valueOf(l.getDILecteur()));
            st.setDate(10, Date.valueOf(l.getDRLecteur()));
            st.setDate(11, Date.valueOf(l.getDELecteur()));
            st.setInt(12, l.getIdLecteur());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void supprimer(Lecteur l) {
        String sql = "DELETE FROM t_lecteurs WHERE Pk_Lecteur = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setInt(1, l.getIdLecteur());
            logger.log(Level.INFO, "query is " + st.toString());
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public List<Lecteur> charger(){
        String sql = "SELECT * FROM t_lecteurs";
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Lecteur> lecteurs = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                lecteurs.add(new Lecteur(rs));
            }
            Collections.sort(lecteurs);
            return lecteurs;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    @Override
    public List<Lecteur> chargerPar(int chx, String condition) {
        return null;
    }
}
