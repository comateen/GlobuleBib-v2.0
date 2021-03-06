package model.book;

import db.LivreDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jof on 29/03/2016.
 */
public class Livre implements Comparable<Livre>{
    private String isbnLivre, titreLivre, sectionLivre, coteLivre;
    private int idLivre, idEditeur, idLocalisation, idTheme, statutLivre;
    private List<Auteur> auteurs;
    private List<Sujet> sujets;

    //Constructeurs

    public Livre(){
        isbnLivre ="";
        titreLivre ="";
        sectionLivre ="";
        coteLivre="";
    }

    public Livre(String Isbn, String Titre, String Section, String Cote, int Statut, List<Auteur> Aut, int IdCol, int IdLoca, List<Sujet> suj, int IdThem){
        isbnLivre =Isbn;
        titreLivre =Titre;
        sectionLivre =Section;
        coteLivre = Cote;
        statutLivre=Statut;
        auteurs =Aut;
        idEditeur =IdCol;
        idLocalisation =IdLoca;
        sujets =suj;
        idTheme =IdThem;
    }

    public Livre(int ID, String Isbn, String Titre, String Section, String Cote, int Statut, List<Auteur> Aut, int IdCol, int IdLoca, List<Sujet> suj, int IdThem){
        idLivre =ID;
        isbnLivre =Isbn;
        titreLivre =Titre;
        sectionLivre =Section;
        coteLivre = Cote;
        statutLivre=Statut;
        auteurs =Aut;
        idEditeur =IdCol;
        idLocalisation =IdLoca;
        sujets =suj;
        idTheme =IdThem;
    }

    public Livre(ResultSet set) throws SQLException {
        idLivre = set.getInt(LivreDAO.COL_ID);
        isbnLivre = set.getString(LivreDAO.COL_ISBN);
        titreLivre = set.getString(LivreDAO.COL_Titre);
        sectionLivre = set.getString(LivreDAO.COL_SECTION);
        coteLivre = set.getString(LivreDAO.COL_COTE);
        statutLivre = set.getInt(LivreDAO.COL_STATUT);
        idEditeur = set.getInt(LivreDAO.COL_xID_Editeur);
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

    public String getCoteLivre(){
        return coteLivre;
    }

    public int getStatutLivre() {
        return statutLivre;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public int getIdEditeur() {
        return idEditeur;
    }

    public int getIdLocalisation() {
        return idLocalisation;
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

    public void setCoteLivre(String Cote) {
        coteLivre = Cote;
    }

    public void setStatutLivre(int Statut) {
        statutLivre = Statut;
    }

    public void setIdLivre(int ID) {
        idLivre = ID;
    }

    public void setIdEditeur(int IdCol) {
        idEditeur = IdCol;
    }

    public void setIdLocalisation(int IdLoca) {
        idLocalisation = IdLoca;
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
