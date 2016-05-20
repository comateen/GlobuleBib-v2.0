package model.book;

import db.LocalisationDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jof on 29/03/2016.
 */
public class Localisation implements Comparable<Localisation>{
    private String nomLocalisation;
    private int idLocalisation;

    //Constructeur

    public Localisation(){
        nomLocalisation ="";
    }

    public Localisation(String Nom){
        nomLocalisation =Nom;
    }

    public Localisation(int ID, String Nom) {
        idLocalisation =ID;
        nomLocalisation =Nom;
    }

    public Localisation(ResultSet set) throws SQLException {
        idLocalisation = set.getInt(LocalisationDAO.Col_ID);
        nomLocalisation = set.getString(LocalisationDAO.COL_LOCALISATION_NAME);
    }

    //Accesseurs

    public int getIdLocalisation() {
        return idLocalisation;
    }

    public String getNomLocalisation() {
        return nomLocalisation;
    }

    //Mutateurs

    public void setIdLocalisation(int ID){
        idLocalisation =ID;
    }

    public void setNomSLocalisation(String Nom) {
        nomLocalisation = Nom;
    }

    @Override
    public int compareTo(Localisation localisation2) {
        int CompLoca = this.getNomLocalisation().compareTo(localisation2.getNomLocalisation());
        if (CompLoca!=0){
            return CompLoca;
        }
        return 0;
    }
}
