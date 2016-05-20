package model.book;

import db.LivreDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jof on 29/03/2016.
 */
public class Livre implements Comparable<Livre>{
    private String isbnLivre, titreLivre, sectionLivre;
    private int idLivre, idAuteur, idCollection, idLocalisation, idSujet, idTheme, statutLivre;
    private List<Auteur> auteurs;
    private List<Sujet> sujets;

    //Constructeurs

    public Livre(){
        isbnLivre ="";
        titreLivre ="";
        sectionLivre ="";
        //StatutLivre="";
    }

    public Livre(String Isbn, String Titre, String Section, int Statut, List<Auteur> Aut, int IdCol, int IdLoca, List<Sujet> suj, int IdThem){
        isbnLivre =Isbn;
        titreLivre =Titre;
        sectionLivre =Section;
        statutLivre=Statut;
        auteurs =Aut;
        idCollection =IdCol;
        idLocalisation =IdLoca;
        sujets =suj;
        idTheme =IdThem;
    }

    public Livre(int ID, String Isbn, String Titre, String Section, int Statut, List<Auteur> Aut, int IdCol, int IdLoca, List<Sujet> suj, int IdThem){
        idLivre =ID;
        isbnLivre =Isbn;
        titreLivre =Titre;
        sectionLivre =Section;
        statutLivre=Statut;
        auteurs =Aut;
        idCollection =IdCol;
        idLocalisation =IdLoca;
        sujets =suj;
        idTheme =IdThem;
    }

    public Livre(ResultSet set) throws SQLException {
        idLivre = set.getInt(LivreDAO.COL_ID);
        isbnLivre = set.getString(LivreDAO.COL_ISBN);
        titreLivre = set.getString(LivreDAO.COL_Titre);
        sectionLivre = set.getString(LivreDAO.COL_SECTION);
        statutLivre = set.getInt(LivreDAO.COL_STATUT);
        idCollection = set.getInt(LivreDAO.COL_xID_Collection);
        idLocalisation = set.getInt(LivreDAO.COL_xID_Localisation);
        idTheme = set.getInt(LivreDAO.COL_xID_Theme);
    }

    //Accesseurs

    public String getIsbnLivre() {
        return isbnLivre;
    }

    public String getTitreLivre() {
        return titreLivre;
    }

    public String getSectionLivre() {
        return sectionLivre;
    }

    public int getStatutLivre() {
        return statutLivre;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public int getIdAuteur() {
        return idAuteur;
    }

    public int getIdCollection() {
        return idCollection;
    }

    public int getIdLocalisation() {
        return idLocalisation;
    }

    public int getIdSujet() {
        return idSujet;
    }

    public int getIdTheme() {
        return idTheme;
    }

    public List<Auteur> getAuteurs() {
        return auteurs;
    }

    public List<Sujet> getSujets() {
        return sujets;
    }

    //Mutateurs


    public void setIsbnLivre(String Isbn) {
        isbnLivre = Isbn;
    }

    public void setTitreLivre(String Titre) {
        titreLivre = Titre;
    }

    public void setSectionLivre(String Section) {
        sectionLivre = Section;
    }

    public void setStatutLivre(int Statut) {
        statutLivre = Statut;
    }

    public void setIdLivre(int ID) {
        idLivre = ID;
    }

    public void setIdAuteur(int IdAut) {
        idAuteur = IdAut;
    }

    public void setIdCollection(int IdCol) {
        idCollection = IdCol;
    }

    public void setIdLocalisation(int IdLoca) {
        idLocalisation = IdLoca;
    }

    public void setIdSujet(int IdSuj) {
        idSujet = IdSuj;
    }

    public void setIdTheme(int IdThem) {
        idTheme = IdThem;
    }

    public void setAuteurs(List<Auteur> auteurs) {
        this.auteurs = auteurs;
    }

    public void setSujets(List<Sujet> sujets) {
        this.sujets = sujets;
    }

    @Override
    public int compareTo(Livre book2) {
        int CompLivre = this.getTitreLivre().compareTo(book2.getTitreLivre());
        if (CompLivre!=0){
            return CompLivre;
        }
        return 0;
    }
}
