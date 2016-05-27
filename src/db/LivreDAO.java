package db;

import model.book.Livre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by jof on 11/04/2016.
 */
public class LivreDAO extends DAO<Livre>{
    public static final String COL_ID = "Pk_Livre";
    public static final String COL_ISBN = "ISBN_Livre";
    public static final String COL_Titre = "Titre_Livre";
    public static final String COL_SECTION = "Section_Livre";
    public static final String COL_STATUT = "Statut_Livre";
    public static final String COL_xID_Editeur = "Fk_Editeur";
    public static final String COL_xID_Localisation = "Fk_Localisation";
    public static final String COL_xID_Theme = "Fk_Theme";


    @Override
    public void ajouter(Livre book) {
        String sql = "INSERT INTO t_livres(ISBN_Livre, Titre_Livre, Section_Livre, Statut_Livre, Fk_Editeur, Fk_Localisation, Fk_Theme) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int pk = 0;
        try (PreparedStatement st = db.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1, book.getIsbnLivre());
            st.setString(2, book.getTitreLivre());
            st.setString(3, book.getSectionLivre());
            st.setInt(4, book.getStatutLivre());
            st.setInt(5, book.getIdEditeur());
            st.setInt(6, book.getIdLocalisation());
            st.setInt(7, book.getIdTheme());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        //TODO essayer de le mettre dans auteurdao et sujetdao
        //for (Auteur a:book.getAuteurs())
        for (int j = 0; j < book.getAuteurs().size(); j++){
            String sql2 = "INSERT INTO t_livreauteur(Fk_Livre, Fk_Auteur) VALUES (?,?)";
            try (PreparedStatement st = db.getConnection().prepareStatement(sql2, PreparedStatement.NO_GENERATED_KEYS)){
                st.setInt(1, pk);
                st.setInt(2, book.getAuteurs().get(j).getIdAuteur());
                logger.log(Level.INFO, "query is " + sql2);
                st.executeUpdate();
            } catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }
        for (int j =0; j <book.getSujets().size(); j++){
            String sql3 = "INSERT INTO t_livresujet(Fk_Livre, Fk_Sujet) VALUES (?,?)";
            try (PreparedStatement st = db.getConnection().prepareStatement(sql3, PreparedStatement.NO_GENERATED_KEYS)){
                st.setInt(1, pk);
                st.setInt(2, book.getSujets().get(j).getIdSujet());
                logger.log(Level.INFO, "query is " + sql3);
                st.executeUpdate();
            } catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }
    }

    @Override
    public void modifier(Livre book) {
        //FIXME est-ce là aussi le bon DAO ?
        String sqldel = "DELETE FROM t_livreauteur WHERE Fk_Livre = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sqldel)){
            st.setInt(1, book.getIdLivre());
            logger.log(Level.INFO, "query is " + sqldel);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        String sql2 = "INSERT INTO t_livreauteur(Fk_Livre, Fk_Auteur) VALUES (?,?)";
        for (int j = 0; j < book.getAuteurs().size(); j++){
            try (PreparedStatement st = db.getConnection().prepareStatement(sql2, PreparedStatement.NO_GENERATED_KEYS)){
                st.setInt(1, book.getIdLivre());
                st.setInt(2, book.getAuteurs().get(j).getIdAuteur());
                logger.log(Level.INFO, "query is " + sql2);
                st.executeUpdate();
            }catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }

        String sqldelsuj = "DELETE FROM t_livresujet WHERE Fk_Livre = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sqldelsuj)){
            st.setInt(1, book.getIdLivre());
            logger.log(Level.INFO, "query is " + sqldelsuj);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        String sql3 = "INSERT INTO t_livresujet(Fk_Livre, Fk_Sujet) VALUES (?,?)";
        for (int j =0; j <book.getSujets().size(); j++){
            try (PreparedStatement st = db.getConnection().prepareStatement(sql3, PreparedStatement.NO_GENERATED_KEYS)){
                st.setInt(1, book.getIdLivre());
                st.setInt(2, book.getSujets().get(j).getIdSujet());
                logger.log(Level.INFO, "query is " + sql3);
                st.executeUpdate();
            } catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }
        String sql = "UPDATE t_livres SET ISBN_Livre = ?, Titre_Livre = ?, Section_Livre = ?, Statut_Livre = ?, Fk_Editeur = ?, Fk_Localisation = ?, Fk_Theme = ? WHERE Pk_Livre = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setString(1, book.getIsbnLivre());
            st.setString(2, book.getTitreLivre());
            st.setString(3, book.getSectionLivre());
            st.setInt(4, book.getStatutLivre());
            st.setInt(5, book.getIdEditeur());
            st.setInt(6, book.getIdLocalisation());
            st.setInt(7, book.getIdTheme());
            st.setInt(8, book.getIdLivre());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }


    }

    @Override
    public void supprimer(Livre book) {
        //FIXME est-ce le bon DAO ?
        String sql2 = "DELETE FROM t_livreauteur WHERE Fk_Livre = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql2)){
            st.setInt(1, book.getIdLivre());
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        String sql3 = "DELETE FROM t_livresujet WHERE Fk_Livre = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql3)){
            st.setInt(1, book.getIdLivre());
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        String sql = "DELETE FROM t_livres WHERE Pk_Livre = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setInt(1, book.getIdLivre());
            logger.log(Level.INFO, "query is " + st.toString());
            st.executeUpdate();
        }  catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public List<Livre> charger() {
        String sql = "SELECT * FROM t_livres";
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Livre> books = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                books.add(new Livre(rs));
            }
            Collections.sort(books);
            return books;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    @Override
    public List<Livre> chargerPar(int chx, String condition) {
        String sql="";
        switch (chx){
            case 1 :
                sql=String.format("SELECT * FROM ((t_livres INNER JOIN t_livreauteur ON t_livres.Pk_Livre=t_livreauteur.Fk_Livre) INNER JOIN t_auteurs ON t_livreAuteur.Fk_Auteur=t_auteurs.Pk_Auteur) WHERE t_auteurs.Pk_Auteur=%s", condition);
                break;
            case 2 :
                sql=String.format("SELECT * FROM ((t_livres INNER JOIN t_livresujet ON t_livres.Pk_Livre=t_livresujet.Fk_Livre) INNER JOIN t_sujets ON t_livresujet.Fk_Sujet=t_sujets.Pk_Sujet) WHERE t_sujets.Pk_Sujet=%s",condition);
                break;
            case 3 :
                sql=String.format("SELECT * FROM ((t_livres INNER JOIN t_livreauteur ON t_livres.Pk_livre=t_livreauteur.Fk_Livre) INNER JOIN t_Auteurs ON t_livreAuteur.Fk_Auteur=t_auteurs.Pk_Auteur) WHERE Nom_Auteur='%s'",condition);
                break;
            case 4 :
                sql=String.format("SELECT * FROM ((t_livres INNER JOIN t_empruntlivre ON t_livres.Pk_Livre=t_empruntlivre.Fk_Livre) INNER JOIN t_emprunts ON t_empruntlivre.Fk_Emprunt=t_emprunts.Pk_Emprunt) WHERE t_emprunts.Pk_Emprunt=%s",condition);
                break;
            case 5 :
                sql=String.format("SELECT * FROM t_livres WHERE Statut_Livre=0 AND Pk_Livre=%s", condition);
                break;
            case 6 :
                sql=String.format("SELECT * FROM ((t_livres INNER JOIN t_empruntlivre ON t_livres.Pk_Livre=t_empruntlivre.Fk_Livre) INNER JOIN t_emprunts ON t_empruntlivre.Fk_Emprunt=t_emprunts.Pk_Emprunt) WHERE t_emprunts.Fk_Lecteur=%s",condition);
                break;
        }
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Livre> books = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                books.add(new Livre(rs));
            }
            Collections.sort(books);
            return books;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    public List<Livre> chargerPeriode(String debut, String fin){
        String sql = String.format("SELECT * FROM ((t_livres INNER JOIN t_empruntlivre ON t_livres.Pk_Livre=t_empruntlivre.Fk_Livre) INNER JOIN t_emprunts ON t_empruntlivre.Fk_Emprunt=t_emprunts.Pk_Emprunt) WHERE t_emprunts.Date_Emprunt>='%s' AND t_emprunts.Date_Emprunt<='%s'", debut, fin);
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Livre> books = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                books.add(new Livre(rs));
            }
            Collections.sort(books);
            return books;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }
}
