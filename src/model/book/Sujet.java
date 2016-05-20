package model.book;

import db.SujetDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jof on 29/03/2016.
 */
public class Sujet implements Comparable<Sujet> {
    private String nomSujet;
    private int idSujet;

    //Constructeur

    public Sujet(){
        nomSujet ="";
    }

    public Sujet(String Nom){
        nomSujet =Nom;
    }

    public Sujet(int ID, String Nom) {
        idSujet =ID;
        nomSujet =Nom;
    }

    public Sujet(ResultSet set) throws SQLException {
        idSujet = set.getInt(SujetDAO.Col_ID);
        nomSujet = set.getString(SujetDAO.COL_SUJ_NAME);
    }

    //Accesseurs

    public int getIdSujet() {
        return idSujet;
    }

    public String getNomSujet() {
        return nomSujet;
    }

    //Mutateurs

    public void setIdSujet(int ID){
        idSujet =ID;
    }

    public void setNomSujet(String Nom) {
        nomSujet = Nom;
    }

    @Override
    public int compareTo(Sujet sujet2) {
        int CompSujet = this.getNomSujet().compareTo(sujet2.getNomSujet());
        if (CompSujet!=0){
            return CompSujet;
        }
        return 0;
    }
}
