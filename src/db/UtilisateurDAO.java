package db;

import model.user.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by jof on 08/04/2016.
 */
public class UtilisateurDAO extends DAO<Utilisateur> {
    public static final String Table_Name = "t_utilisateurs";
    public static final String Col_ID = "PK_Utilisateur";
    public static final String COL_USER_NAME = "Nom_Utilisateur";
    public static final String COL_USER_LASTNAME = "Prenom_Utilisateur";
    public static final String COL_USER_LOGIN = "Login_Utilisateur";
    public static final String COL_USER_PASSWORD = "MDP_Utilisateur";


    @Override
    public void ajouter(Utilisateur user) {
        String sql = "INSERT INTO t_utilisateurs(Nom_Utilisateur, Prenom_Utilisateur, Login_Utilisateur, MDP_Utilisateur) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1, user.getNomUtilisateur());
            st.setString(2, user.getPrenomUtilisateur());
            st.setString(3, user.getLoginUtilisateur());
            st.setString(4, user.getMotDePasse());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void modifier(Utilisateur user) {
        String sql = "UPDATE t_utilisateurs set Nom_Utilisateur = ?, Prenom_Utilisateur = ?, Login_Utilisateur = ?, MDP_Utilisateur = ? WHERE PK_Utilisateur = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setString(1, user.getNomUtilisateur());
            st.setString(2, user.getPrenomUtilisateur());
            st.setString(3, user.getLoginUtilisateur());
            st.setString(4, user.getMotDePasse());
            st.setInt(5, user.getIDUtilisateur());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(Utilisateur user) {
        String sql = "DELETE FROM t_utilisateurs WHERE Pk_Utilisateur = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setInt(1, user.getIDUtilisateur());
            logger.log(Level.INFO, "query is " + st.toString());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Utilisateur> charger() {
        String sql = "SELECT * FROM t_utilisateurs";
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Utilisateur> users = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                users.add(new Utilisateur(rs));
            }
            Collections.sort(users);
            return users;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    @Override
    public List<Utilisateur> chargerPar(int chx, String condition) {
        return null;
    }
}
