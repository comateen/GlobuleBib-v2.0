package model.book;

import db.AuteurDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by jof on 29/03/2016.
 */
public class Auteur implements Comparable<Auteur> {
    private String nomAuteur, prenomAuteur;
    private LocalDate dnAuteur;
    private int idAuteur;

    //Constructeur

    public Auteur(){
    }

    public Auteur(String Nom, String Prenom, LocalDate DN){
        nomAuteur =Nom;
        prenomAuteur =Prenom;
        dnAuteur =DN;
    }

    public Auteur(int Id, String Nom, String Prenom, LocalDate DN){
        idAuteur =Id;
        nomAuteur =Nom;
        prenomAuteur =Prenom;
        dnAuteur =DN;
    }

    public Auteur(ResultSet set) throws SQLException {
        idAuteur = set.getInt(AuteurDAO.Col_ID);
        nomAuteur = set.getString(AuteurDAO.COL_FIRST_NAME);
        prenomAuteur = set.getString(AuteurDAO.COL_LAST_NAME);
        dnAuteur = set.getDate(AuteurDAO.COL_BIRTH_DATE).toLocalDate();
    }

    //Accesseurs

    public String getNomAuteur() {
        return nomAuteur;
    }

    public String getPrenomAuteur() {
        return prenomAuteur;
    }

    public LocalDate getDnAuteur() {
        return dnAuteur;
    }

    public int getIdAuteur() {
        return idAuteur;
    }

    //Mutateurs


    public void setNomAuteur(String Nom) {
        nomAuteur = Nom;
    }

    public void setPrenomAuteur(String Prenom) {
        prenomAuteur = Prenom;
    }

    public void setDnAuteur(LocalDate DN) {
        dnAuteur = DN;
    }

    public void setIdAuteur(int ID) {
        idAuteur = ID;
    }

    @Override
    public int compareTo(Auteur auteur2) {
        int compNom = this.getNomAuteur().compareTo(auteur2.getNomAuteur());
        if (compNom!=0){
            return compNom;
        }
        int compPrenom = this.getPrenomAuteur().compareTo(auteur2.getPrenomAuteur());
        if  (compPrenom!=0){
            return compPrenom;
        }
        return 0;
    }
}
