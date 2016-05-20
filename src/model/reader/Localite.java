package model.reader;

import db.LocaliteDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jof on 29/03/2016.
 */
public class Localite implements Comparable<Localite>{
    private String NomVille, CodePostal;
    private int IDLocalite;



    //Constructeur

    public Localite (){
        NomVille="";
        CodePostal="";
    }

    public Localite(String Nom, String CP){
        NomVille=Nom;
        CodePostal=CP;
    }

    public Localite(int ID, String Nom, String CP){
        IDLocalite=ID;
        NomVille=Nom;
        CodePostal=CP;
    }

    public Localite(ResultSet set) throws SQLException {
        IDLocalite = set.getInt(LocaliteDAO.Col_ID);
        NomVille = set.getString(LocaliteDAO.COL_LOC_NAME);
        CodePostal = set.getString((LocaliteDAO.COL_LOC_CP));
    }

    //Accesseurs

    public int getIDLocalite(){
        return IDLocalite;
    }

    public String getNomVille(){
        return NomVille;
    }

    public String getCodePostal(){
        return CodePostal;
    }

    //Mutateurs

    public void setIDLocalite(int ID){
        IDLocalite=ID;
    }

    public void setNomVille(String Nom){
        NomVille=Nom;
    }

    public void setCodePostal(String CP){
        CodePostal=CP;
    }

    @Override
    public int compareTo(Localite loc2) {
        int CompLoc = this.getNomVille().compareTo(loc2.getNomVille());
        if (CompLoc!=0){
            return CompLoc;
        }
        int CompCP = this.getCodePostal().compareTo(loc2.getCodePostal());
        if (CompCP!=0){
            return CompCP;
        }
        return 0;
    }
}
