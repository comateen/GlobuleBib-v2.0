package model.book;

import db.EditeurDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jof on 29/03/2016.
 */
public class Editeur implements Comparable<Editeur>{
    private String nomEditeur;
    private int idEditeur;

    //Constructeur

    public Editeur(){
        nomEditeur ="";
    }

    public Editeur(String Nom){
        nomEditeur =Nom;
    }

    public Editeur(int ID, String Nom) {
        idEditeur =ID;
        nomEditeur =Nom;
    }

    public Editeur(ResultSet set) throws SQLException {
        idEditeur = set.getInt(EditeurDAO.Col_ID);
        nomEditeur = set.getString(EditeurDAO.COL_Col_NAME);
    }

    //Accesseurs

    public int getIdEditeur() {
        return idEditeur;
    }

    public String getNomEditeur() {
        return nomEditeur;
    }

    //Mutateurs

    public void setIdEditeur(int ID){
        idEditeur =ID;
    }

    public void setNomEditeur(String Nom) {
        nomEditeur = Nom;
    }

    @Override
    public int compareTo(Editeur editeur2) {
        int compCol = this.getNomEditeur().compareTo(editeur2.getNomEditeur());
        if (compCol!=0){
            return compCol;
        }
        return 0;
    }
}
