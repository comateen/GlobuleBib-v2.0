package model.reader;

import db.LecteurDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by jof on 29/03/2016.
 */
public class Lecteur implements Comparable<Lecteur>{
    private String NomLecteur, PrenomLecteur, AdresseLecteur, MailLecteur, CategorieLecteur, TelLecteur;
    private LocalDate DNLecteur, DILecteur, DRLecteur, DELecteur;
    private int IdLecteur;
    private int Loc;

    //Constructeur

    public Lecteur(){
    }

    public Lecteur(String Nom, String Prenom, LocalDate DN, String Adresse, int Localite, String Tel, String Mail, String Cat, LocalDate DI, LocalDate DR, LocalDate DE) {
        NomLecteur = Nom;
        PrenomLecteur = Prenom;
        DNLecteur = DN;
        AdresseLecteur = Adresse;
        Loc=Localite;
        TelLecteur = Tel;
        MailLecteur = Mail;
        CategorieLecteur = Cat;
        DILecteur = DI;
        DRLecteur = DR;
        DELecteur = DE;
    }

    public Lecteur(int ID, String Nom, String Prenom, LocalDate DN, String Adresse, int Localite, String Tel, String Mail, String Cat, LocalDate DI, LocalDate DR, LocalDate DE) {
        IdLecteur=ID;
        NomLecteur = Nom;
        PrenomLecteur = Prenom;
        DNLecteur = DN;
        AdresseLecteur = Adresse;
        Loc=Localite;
        TelLecteur = Tel;
        MailLecteur = Mail;
        CategorieLecteur = Cat;
        DILecteur = DI;
        DRLecteur = DR;
        DELecteur = DE;
    }

    public Lecteur(ResultSet set) throws SQLException {
        IdLecteur = set.getInt(LecteurDAO.Col_ID);
        NomLecteur = set.getString(LecteurDAO.COL_FIRST_NAME);
        PrenomLecteur = set.getString(LecteurDAO.COL_LAST_NAME);
        DNLecteur = set.getDate(LecteurDAO.COL_BIRTH_DATE).toLocalDate();
        AdresseLecteur = set.getString(LecteurDAO.COL_ADDRESS);
        Loc = set.getInt(LecteurDAO.COL_xID_Loc);
        TelLecteur = set.getString(LecteurDAO.COL_NumTel);
        MailLecteur = set.getString(LecteurDAO.COL_Mail);
        CategorieLecteur = set.getString(LecteurDAO.COL_Cat);
        DILecteur = set.getDate(LecteurDAO.COL_I_DATE).toLocalDate();
        DRLecteur = set.getDate(LecteurDAO.COL_R_DATE).toLocalDate();
        DELecteur = set.getDate(LecteurDAO.COL_E_DATE).toLocalDate();
    }

    //Accesseurs

    public int getIdLecteur(){
        return IdLecteur;
    }

    public String getNomLecteur(){
        return NomLecteur;
    }

    public String getPrenomLecteur(){
        return PrenomLecteur;
    }

    public LocalDate getDNLecteur(){
        return DNLecteur;
    }

    public String getAdresseLecteur(){
        return AdresseLecteur;
    }

    public int getLoc() {
        return Loc;
    }

    public String getTelLecteur(){
        return TelLecteur;
    }

    public String getMailLecteur(){
        return MailLecteur;
    }

    public String getCategorieLecteur() {
        return CategorieLecteur;
    }

    public LocalDate getDELecteur() {
        return DELecteur;
    }

    public LocalDate getDILecteur() {
        return DILecteur;
    }

    public LocalDate getDRLecteur() {
        return DRLecteur;
    }

    //Mutateurs

    public void setIdLecteur(int ID) {
        IdLecteur = ID;
    }

    public void setNomLecteur(String Nom) {
        NomLecteur = Nom;
    }

    public void setPrenomLecteur(String Prenom ) {
        PrenomLecteur = Prenom;
    }

    public void setAdresseLecteur(String Adresse) {
        AdresseLecteur = Adresse;
    }

    public void setLoc(int Localite) {
        Loc = Localite;
    }

    public void setMailLecteur(String Mail) {
        MailLecteur = Mail;
    }

    public void setCategorieLecteur(String Cat) {
        CategorieLecteur = Cat;
    }

    public void setTelLecteur(String Tel) {
        TelLecteur = Tel;
    }

    public void setDNLecteur(LocalDate DN) {
        DNLecteur = DN;
    }

    public void setDILecteur(LocalDate DI) {
        DILecteur = DI;
    }

    public void setDRLecteur(LocalDate DR) {
        DRLecteur = DR;
    }

    public void setDELecteur(LocalDate DE) {
        DELecteur = DE;
    }

    @Override
    public int compareTo(Lecteur lecteur2) {
        int CompNom = this.getNomLecteur().compareTo(lecteur2.getNomLecteur());
        if (CompNom!=0){
            return CompNom;
        }
        int CompPrenom = this.getPrenomLecteur().compareTo(lecteur2.getPrenomLecteur());
        if (CompPrenom!=0){
            return CompPrenom;
        }
        return 0;
    }
}
