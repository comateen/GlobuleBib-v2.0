package model.user;

import db.UtilisateurDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jof on 29/03/2016.
 */
public class Utilisateur implements Comparable<Utilisateur>{
    private String NomUtilisateur, PrenomUtilisateur, LoginUtilisateur, MotDePasse;
    private int IDUtilisateur;

    //Constructeur

    public Utilisateur(){
        NomUtilisateur="";
        PrenomUtilisateur="";
        LoginUtilisateur="";
        MotDePasse="";
    }

    public Utilisateur(String Nom, String Prenom, String Login, String MDP){ //FIXME pitié, ne me dit pas que tu passes le mdp en clair…
        NomUtilisateur=Nom;
        PrenomUtilisateur=Prenom;
        LoginUtilisateur=Login;
        MotDePasse=MDP;
    }

    public Utilisateur(ResultSet set) throws SQLException {
        IDUtilisateur = set.getInt(UtilisateurDAO.Col_ID);
        NomUtilisateur = set.getString(UtilisateurDAO.COL_USER_NAME);
        PrenomUtilisateur = set.getString(UtilisateurDAO.COL_USER_LASTNAME);
        LoginUtilisateur = set.getString(UtilisateurDAO.COL_USER_LOGIN);
        MotDePasse = set.getString(UtilisateurDAO.COL_USER_PASSWORD);
    }



    //Accesseurs

    public int getIDUtilisateur(){
        return IDUtilisateur;
    }

    public String getNomUtilisateur(){
        return NomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return PrenomUtilisateur;
    }

    public String getLoginUtilisateur() {
        return LoginUtilisateur;
    }

    public String getMotDePasse(){
        return MotDePasse;
    }

    //Mutateurs

    public void setIDUtilisateur(int ID){
        IDUtilisateur=ID;
    }

    public void setNomUtilisateur(String Nom){
        NomUtilisateur=Nom;
    }

    public void setPrenomUtilisateur(String Prenom){
        PrenomUtilisateur=Prenom;
    }

    public void setLoginUtilisateur(String Login){
        LoginUtilisateur=Login;
    }

    public void setMotDePasse(String MDP){
        MotDePasse=MDP;
    }

    @Override
    public int compareTo(Utilisateur user2) {
        int CompNom = this.getNomUtilisateur().compareTo(user2.getNomUtilisateur());
        if (CompNom!=0){
            return CompNom;
        }
        int CompPrenom = this.getPrenomUtilisateur().compareTo(user2.getPrenomUtilisateur());
        if  (CompPrenom!=0){
            return CompPrenom;
        }
        return 0;
    }
}
