package model.loan;

import db.EmpruntDAO;
import model.book.Livre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by jof on 29/03/2016.
 */
public class Emprunt {
    private LocalDate dateEmprunt, dateRetour;
    private int idEmprunt, lecteur;
    private List<Livre> livres;

    //Consttructeur
    public Emprunt(){
    }

    public Emprunt(int lector, LocalDate DE, LocalDate DR, List<Livre> books){
        lecteur=lector;
        livres=books;
        dateEmprunt =DE;
        dateRetour =DR;
    }

    public Emprunt(int id, int lector, LocalDate DE, LocalDate DR, List<Livre> books){
        idEmprunt = id;
        lecteur=lector;
        livres=books;
        dateEmprunt =DE;
        dateRetour =DR;
    }

    public Emprunt(ResultSet set) throws SQLException {
        idEmprunt = set.getInt(EmpruntDAO.Col_ID);
        lecteur = set.getInt(EmpruntDAO.Col_xID_Lecteur);
        dateEmprunt = set.getDate(EmpruntDAO.Col_DE).toLocalDate();
        dateRetour = set.getDate(EmpruntDAO.Col_DR).toLocalDate();
    }

    //Accesseurs

    public int getIdEmprunt() {
        return idEmprunt;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public int getLecteur() {
        return lecteur;
    }

    public List<Livre> getLivres() {
        return livres;
    }

    //Mutateurs


    public void setIdEmprunt(int idE) {
        idEmprunt = idE;
    }

    public void setDateEmprunt(LocalDate DE) {
        dateEmprunt = DE;
    }

    public void setDateRetour(LocalDate DR) {
        dateRetour = DR;
    }

    public void setLecteur(int lector) {
        lecteur = lector;
    }

    public void setLivres(List<Livre> books) {
        livres = books;
    }
}
