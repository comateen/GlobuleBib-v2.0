package db;


import model.book.Theme;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by jof on 05/04/2016.
 */
public class ThemeDAO extends  DAO<Theme>{
    public static final String Col_ID = "Pk_Theme";
    public static final String COL_THEME_NAME = "Nom_Theme";

    @Override
    public void ajouter(Theme t){
        String sql = "INSERT INTO t_themes(Nom_Theme) VALUES (?)";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1, t.getNomTheme());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void modifier(Theme t){
        String sql = "UPDATE t_themes set Nom_Themes = ? WHERE Pk_Theme = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setString(1, t.getNomTheme());
            st.setInt(2, t.getIdTheme());
            logger.log(Level.INFO, "query is " + sql);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void supprimer(Theme t) {
        String sql = "DELETE FROM t_themes WHERE Pk_Theme = ?";
        try (PreparedStatement st = db.getConnection().prepareStatement(sql)){
            st.setInt(1, t.getIdTheme());
            logger.log(Level.INFO, "query is " + st.toString());
            st.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public List<Theme> charger(){
        String sql = "SELECT * FROM t_themes";
        try (ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery()){
            List<Theme> themes = new ArrayList<>();
            logger.log(Level.INFO, "query is " + sql);
            while(rs.next()){
                themes.add(new Theme(rs));
            }
            Collections.sort(themes);
            return themes;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    @Override
    public List<Theme> chargerPar(int chx, String condition) {
        return null;
    }

}
