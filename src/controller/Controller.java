package controller;


import db.*;
import model.book.*;
import model.loan.Emprunt;
import model.reader.Lecteur;
import model.reader.Localite;
import model.user.Utilisateur;

import javax.swing.*;
import java.util.List;

/**
 * Created by jof on 05/04/2016.
 */
public class Controller {
    private LecteurDAO lecteurDAO = new LecteurDAO();
    private AuteurDAO auteurDAO = new AuteurDAO();
    private EditeurDAO editeurDAO = new EditeurDAO();
    private LocalisationDAO localisationDAO = new LocalisationDAO();
    private LocaliteDAO localiteDAO = new LocaliteDAO();
    private SujetDAO sujetDAO = new SujetDAO();
    private ThemeDAO themeDAO = new ThemeDAO();
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private LivreDAO livreDAO = new LivreDAO();
    private EmpruntDAO empruntDAO = new EmpruntDAO();

    //public DefaultListModel<Auteur> doFindAuteur(String Condition){return (DefaultListModel) Adao.ChargerPar(Condition);}

    public void doSave(Lecteur reader) {
        lecteurDAO.ajouter(reader);
    }
    public void doSave(Auteur autor) {
        auteurDAO.ajouter(autor);
    }
    public void doSave(Editeur editor){ editeurDAO.ajouter(editor);}
    public void doSave(Localisation loca) {localisationDAO.ajouter(loca);}
    public void doSave(Localite loc) {localiteDAO.ajouter(loc);}
    public void doSave(Sujet suj) {sujetDAO.ajouter(suj);}
    public void doSave(Theme the) {themeDAO.ajouter(the);}
    public void doSave(Utilisateur user) {utilisateurDAO.ajouter(user);}
    public void doSave(Livre book) {livreDAO.ajouter(book);}
    public void doSave(Emprunt emprunt) {empruntDAO.ajouter(emprunt);}

    public void doUpdate(Lecteur reader) {
        lecteurDAO.modifier(reader);
    }
    public void doUpdate(Auteur autor) {
        auteurDAO.modifier(autor);
    }
    public void doUpdate(Editeur editor) { editeurDAO.modifier(editor);}
    public void doUpdate(Localisation loca) { localisationDAO.modifier(loca);}
    public void doUpdate(Localite loc) {localiteDAO.modifier(loc);}
    public void doUpdate(Sujet suj) {sujetDAO.modifier(suj);}
    public void doUpdate(Theme the) {themeDAO.modifier(the);}
    public void doUpdate(Utilisateur user) {utilisateurDAO.modifier(user);}
    public void doUpdate(Livre book) {livreDAO.modifier(book);}
    public void doUpdate(Emprunt emprunt) {empruntDAO.modifier(emprunt);}

    public void doDelete(Lecteur reader){
        lecteurDAO.supprimer(reader);
    }
    public void doDelete(Auteur autor){
        auteurDAO.supprimer(autor);
    }
    public void doDelete(Editeur editor) {editeurDAO.supprimer(editor);}
    public void doDelete(Localisation loca) {localisationDAO.supprimer(loca);}
    public void doDelete(Localite loc) {localiteDAO.supprimer(loc);}
    public void doDelete(Sujet suj) {sujetDAO.supprimer(suj);}
    public void doDelete(Theme the) {themeDAO.supprimer(the);}
    public void doDelete(Utilisateur user) {utilisateurDAO.supprimer(user);}
    public void doDelete(Livre book) {livreDAO.supprimer(book);}

    public DefaultListModel<Lecteur> getModel(){
        List<Lecteur> lecteurs = lecteurDAO.charger();
        DefaultListModel<Lecteur> model = new DefaultListModel<>(); //TODO Pas plus simple de créer ton propre model ? Je vais regarder ça dans mon exemple plus tard
        lecteurs.forEach(model::addElement); //méthode possible grâce à java8 mais ne fonctionne pas
        //for (Lecteur person:lecteurs) model.addElement(person);
        return model;
    }
    public DefaultListModel<Auteur> getModelAuteur(){
        List<Auteur> auteurs = auteurDAO.charger();
        DefaultListModel<Auteur> model = new DefaultListModel<>();
        auteurs.forEach(model::addElement);
        //for (Auteur person:auteurs) model.addElement(person);
        return model;
    }
    public DefaultListModel<Editeur> getModelEditeur(){
        List<Editeur> editeurs = editeurDAO.charger();
        DefaultListModel<Editeur> model = new DefaultListModel<>();
        editeurs.forEach(model::addElement);
        //for (Editeur collec:collections) model.addElement(collec);
        return model;
    }
    public DefaultListModel<Localisation> getModelLocalisation(){
        List<Localisation> localisations = localisationDAO.charger();
        DefaultListModel<Localisation> model = new DefaultListModel<>();
        localisations.forEach(model::addElement);
        //for (Localisation localisation:localisations) model.addElement(localisation);
        return model;
    }
    public DefaultListModel<Localite> getModelLoc(){
        List<Localite> localites = localiteDAO.charger();
        DefaultListModel<Localite> model = new DefaultListModel<>();
        localites.forEach(model::addElement);
        //for (Localite localite:localites) model.addElement(localite);
        return model;
    }
    public DefaultListModel<Sujet> getModelSujet(){
        List<Sujet> sujets = sujetDAO.charger();
        DefaultListModel<Sujet> model = new DefaultListModel<>();
        sujets.forEach(model::addElement);
        //for (Sujet sujet:sujets) model.addElement(sujet);
        return model;
    }
    public DefaultListModel<Theme> getModelTheme(){
        List<Theme> themes = themeDAO.charger();
        DefaultListModel<Theme> model = new DefaultListModel<>();
        themes.forEach(model::addElement);
        //for (Theme theme:themes) model.addElement(theme);
        return model;
    }
    public DefaultListModel<Utilisateur> getModelUtilisateur(){
        List<Utilisateur> users = utilisateurDAO.charger();
        DefaultListModel<Utilisateur> model = new DefaultListModel<>();
        users.forEach(model::addElement);
        //for (Utilisateur u:users) model.addElement(u);
        return model;
    }
    public DefaultListModel<Livre> getModelLivre(){
        List<Livre> livres = livreDAO.charger();
        DefaultListModel<Livre> model = new DefaultListModel<>();
        livres.forEach(model::addElement);
        //for (Livre book:livres) model.addElement(book);
        return  model;
    }
    public DefaultListModel<Emprunt> getModelEmprunt(){
        List<Emprunt> emprunts = empruntDAO.charger();
        DefaultListModel<Emprunt> model = new DefaultListModel<>();
        emprunts.forEach(model::addElement);
        return model;
    }

    public DefaultListModel<Auteur> doFindAuteur(String condition){
        List<Auteur> auteurs = auteurDAO.chargerPar(0, condition);
        DefaultListModel<Auteur> model = new DefaultListModel<>();
        auteurs.forEach(model::addElement);
        //for (Auteur person:auteurs) model.addElement(person);
        return model;
    }
    public DefaultListModel<Sujet> doFindSujet(String condition) {
        List<Sujet> sujets = sujetDAO.chargerPar(0, condition);
        DefaultListModel<Sujet> model = new DefaultListModel<>();
        sujets.forEach(model::addElement);
        //for (Sujet sujet:sujets) model.addElement(sujet);
        return model;
    }
    public DefaultListModel<Livre> doFindLivre(int chx, String condition) {
        List<Livre> livres = livreDAO.chargerPar(chx, condition);
        DefaultListModel<Livre> model = new DefaultListModel<>();
        livres.forEach(model::addElement);
        //for (Livre book:livres) model.addElement(book);
        return  model;
    }
    public DefaultListModel<Livre> doFindLivrePeriode(String debut, String fin){
        List<Livre> livres = livreDAO.chargerPeriode(debut, fin);
        DefaultListModel<Livre> model = new DefaultListModel<>();
        livres.forEach(model::addElement);
        return model;
    }
    public int doFindReturn(String xIdEmprunt, String xIdLivre) {
        return empruntDAO.ChercherRetour(xIdEmprunt, xIdLivre);
    }
    public void doReturnBook(String xIdEmprunt, String xIdLivre){ empruntDAO.ChangerRetour(xIdEmprunt, xIdLivre);}

}
