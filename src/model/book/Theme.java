package model.book;

import db.ThemeDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jof on 29/03/2016.
 */
public class Theme implements Comparable<Theme>{
    private String nomTheme;
    private int idTheme;

    //Constructeur

    public Theme(){
        nomTheme ="";
    }

    public Theme(String Nom){
        nomTheme =Nom;
    }

    public Theme(int ID, String Nom) {
        idTheme =ID;
        nomTheme =Nom;
    }

    public Theme(ResultSet set) throws SQLException {
        idTheme = set.getInt(ThemeDAO.Col_ID);
        nomTheme = set.getString(ThemeDAO.COL_THEME_NAME);
    }

    //Accesseurs

    public int getIdTheme() {
        return idTheme;
    }

    public String getNomTheme() {
        return nomTheme;
    }

    //Mutateurs

    public void setIdTheme(int ID){
        idTheme =ID;
    }

    public void setNomTheme(String Nom) {
        nomTheme = Nom;
    }

    @Override
    public int compareTo(Theme theme2) {
        int CompTheme = this.getNomTheme().compareTo(theme2.getNomTheme());
        if (CompTheme!=0){
            return CompTheme;
        }
        return 0;
    }
}
