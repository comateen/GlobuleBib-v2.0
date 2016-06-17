package db;

import model.loan.Emprunt;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by jof on 27/04/2016.
 */
public class EmpruntDAO extends DAO<Emprunt> {
    public static final String Col_ID = "Pk_Emprunt";
    public static final String Col_DE = "Date_Emprunt";
    public static final String Col_DR = "DateRetour_Emprunt";
    public static final String Col_xID_Lecteur = "Fk_Lecteur";

    @Override
    public void ajouter(Emprunt emprunt) {
        int pk = 0;
        String sql = "INSERT INTO t_emprunts(Date_Emprunt, DateRetour_Emprunt, Fk_Lecteur) VALUES (?, ?, ?)";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            st.setDate(1, Date.valueOf(emprunt.getDateEmprunt()));
            st.setDate(2, Date.valueOf(emprunt.getDateRetour()));
            st.setInt(3, emprunt.getLecteur());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        for (int i = 0; i < emprunt.getLivres().size(); i++) {
            String sql2 = "INSERT INTO t_empruntlivre(Fk_Emprunt, Fk_Livre, Retour) VALUES (?,?,?)";
            try (PreparedStatement st = db.getConnection().prepareStatement(sql2, PreparedStatement.NO_GENERATED_KEYS)) {
                st.setInt(1, pk);
                st.setInt(2, emprunt.getLivres().get(i).getIdLivre());
                st.setInt(3, 0);
                logger.log(Level.INFO, "query is " + sql2);
                st.executeUpdate();
            } catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }
        for (int i = 0; i < emprunt.getLivres().size(); i++) {
            String sql3 = "UPDATE t_livres SET Statut_Livre = 1 WHERE Pk_Livre = ?";
            try (PreparedStatement st = db.getConnection().prepareStatement(sql3)) {
                st.setInt(1, emprunt.getLivres().get(i).getIdLivre());
                logger.log(Level.INFO, "query is " + sql3);
                st.executeUpdate();
            } catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage());;
            }
        }
    }

    @Override
    public void modifier(Emprunt emprunt) {
        String sql = "UPDATE t_emprunts SET DateRetour_Emprunt = ? WHERE Pk_Emprunt = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setDate(1, Date.valueOf(emprunt.getDateRetour()));
            st.setInt(2, emprunt.getIdEmprunt());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void supprimer(Emprunt emprunt) {
    }

    @Override
    public List<Emprunt> charger() {
        String sql = "SELECT * FROM t_emprunts";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            List<Emprunt> emprunts = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while (rs.next()) {
                emprunts.add(new Emprunt(rs));
            }
            return emprunts;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    @Override
    public List<Emprunt> chargerPar(int chx, String condition) {return null;}

    public int ChercherRetour(String xIdEmprunt, String xIdLivre) {
        int retour = -1;
        String sql = String.format("SELECT Retour FROM t_empruntlivre WHERE Fk_Emprunt = %s AND Fk_Livre = %s", xIdEmprunt, xIdLivre);
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()) {
            while (rs.next()) {
                retour = rs.getInt(1);
            }
            logger.log(Level.INFO, "query is " + sql);
            return retour;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return -1;
        }
    }

    public void ChangerRetour(String xIdEmprunt, String xIdLivre) {
        String sql = String.format("UPDATE t_empruntlivre SET Retour = 1 WHERE Fk_Emprunt = %s AND Fk_Livre = %s", xIdEmprunt, xIdLivre);
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)) {
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        String sql2 = String.format("UPDATE t_livres SET Statut_Livre = 0 WHERE Pk_Livre = %s", xIdLivre);
        try (PreparedStatement st = db.getConnection().prepareStatement(sql2)) {
            logger.log(Level.INFO, "query is " + sql2);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public int compter(String debut, String fin){
        int count = -1;
        String sql = String.format("SELECT COUNT(t_emprunts.Pk_Emprunt) FROM t_emprunts WHERE t_emprunts.Date_Emprunt>='%s' AND t_emprunts.Date_Emprunt<='%s'", debut, fin);
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            logger.log(Level.INFO, "query is " + sql);
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return -1;
        }
    }

    public int Rechercher(String xIdLivre){
        int xIdEmprunt = -1;
        String sql = String.format("SELECT Fk_Emprunt FROM t_empruntlivre WHERE Retour = 0 AND Fk_Livre = %s", xIdLivre);
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()) {
            logger.log(Level.INFO, "query is " + sql);
            while (rs.next()) {
                xIdEmprunt = rs.getInt(1);
            }
            return xIdEmprunt;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return xIdEmprunt;
        }
    }
}
