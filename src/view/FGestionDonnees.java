package view;

import controller.Controller;
import model.book.*;
import model.reader.Lecteur;
import model.reader.Localite;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by jof on 02/04/2016.
 */
public class FGestionDonnees extends AppFrame {
    private DefaultTableModel TDonneesModel;
    private final Controller controller = new Controller();
    private DefaultListModel<Auteur> modelautor;
    private DefaultListModel<Editeur> modelediteur;
    private DefaultListModel<Localisation> modellocalisation;
    private DefaultListModel<Localite> modellocalite;
    private DefaultListModel<Sujet> modelsujet;
    private DefaultListModel<Theme> modeltheme;
    private DefaultListModel<Livre> listlivreauteur;
    private DefaultListModel<Livre> listlivresujet;
    private DefaultListModel<Livre> listlivre;
    private DefaultListModel<Lecteur> listlecteur;
    private JPanel Container;
    private JLabel LDonnee1;
    private JTextField TDonnee1;
    private JLabel LDonnee2;
    private JFormattedTextField TDonnee2;
    private JLabel LDonnee3;
    private JTable TableDonnees;
    private JFormattedTextField TDonnee3;
    private JButton BAjouter;
    private JButton BModifier;
    private JButton BSupprimer;
    private JButton BPremier;
    private JButton BPrecedent;
    private JButton BSuivant;
    private JButton BDernier;
    private JButton BRechercher;
    private JTextField TRechercher;
    private JPanel PanelInfo;
    private JPanel PanelBGestion;
    private JPanel PanelRecherche;
    private JPanel PanelBDeplacement;
    private JScrollPane Scroll;
    private JTextField Tid;
    private JPanel PannelTable;
    private Statement st = null;
    private ResultSet rs = null;
    private Connection con = null;
    private int pos = 0;
    private TypeDonnees chx;
    private boolean check = true;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private enum TypeDonnees {AUTEUR, EDITEUR, LOCALISATION, LOCALITE, SUJET, THEME}



    @Override
    JPanel getContainer() {
        return Container; // pour forcer la création d'un container
    }

    protected FGestionDonnees() {
        super("GlobuleBib - Gestion des données", null);
        init();
        this.setLocation(150,150);
        Cacher(); //pour forcer l'utilisateur à choisir un type le type de données sur lequel il veut travailler
        //Création de la barre de menu et de ses composants
        JMenuBar MenuBar = new JMenuBar();
        this.setJMenuBar(MenuBar);
        JMenu fichier = new JMenu("Fichier");
        MenuBar.add(fichier);
        /*JMenuItem Deco = new JMenuItem("Se déconnecter");
        fichier.add(Deco);*/
        JMenuItem Exit = new JMenuItem("Quitter");
        fichier.add(Exit);
        JMenu choix = new JMenu("Choix du type de données");
        MenuBar.add(choix);
        JMenuItem auteur = new JMenuItem("Auteur");
        choix.add(auteur);
        JMenuItem editeur = new JMenuItem("Editeur");
        choix.add(editeur);
        JMenuItem localisation = new JMenuItem("Localisation");
        choix.add(localisation);
        JMenuItem localite = new JMenuItem("Localité");
        choix.add(localite);
        JMenuItem sujet = new JMenuItem("Sujet");
        choix.add(sujet);
        JMenuItem themes = new JMenuItem("Thème");
        choix.add(themes);
        try {
            MaskFormatter mf = new MaskFormatter("##-##-####");
            DefaultFormatterFactory form = new DefaultFormatterFactory(mf);
            TDonnee3.setFormatterFactory(form);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Listeners
        auteur.addActionListener(actionEvent -> ChxAuteur());
        editeur.addActionListener(actionEvent -> ChxEditeur());
        localisation.addActionListener(actionEvent -> ChxLocalisation());
        localite.addActionListener(actionEvent -> ChxLocalite());
        sujet.addActionListener(actionEvent -> ChxSujet());
        themes.addActionListener(actionEvent -> ChxThemes());
        //Deco.addActionListener(actionEvent -> Deconnexion());
        Exit.addActionListener(actionEvent -> Quitter());

        BPremier.addActionListener(actionEvent -> Premier());
        BPrecedent.addActionListener(actionEvent -> Precedent());
        BSuivant.addActionListener(actionEvent -> Suivant());
        BDernier.addActionListener(actionEvent -> Dernier());
        TableDonnees.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ClicTable();
            }
        }); //pas trouver d'action listener sur les jtable
        BRechercher.addActionListener(actionEvent -> RechercherDonnees());
        BAjouter.addActionListener(actionEvent -> {
            Tid.setText("");
            CheckChamps();
            if (check) {
                JOptionPane.showMessageDialog(null, "Certains champs ne sont pas complétés", "Attention", JOptionPane.WARNING_MESSAGE);
            } else {
                if (checkIfLetters(TDonnee1.getText())){
                    switch (chx) {
                        case AUTEUR:
                            if (checkIfLetters(TDonnee2.getText())){
                                if (Exist() != 1) {
                                    try { // try catch pour récupéré l'execption si la date n'est pas valide
                                        controller.doSave(getDataAuteur());
                                        JOptionPane.showMessageDialog(null, "Vous avez ajouté un auteur", "Information", JOptionPane.INFORMATION_MESSAGE);
                                        modelautor = controller.getModelAuteur();
                                    } catch (Exception e){
                                        JOptionPane.showMessageDialog(null, "La date n'est pas valide", "Attention", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "il y a un ou des chiffres non valide dans le second champ", "Attention", JOptionPane.WARNING_MESSAGE);
                            }
                            break;
                        case EDITEUR:
                            if (Exist() != 1) {
                                controller.doSave(getDataEditeur());
                                modelediteur = controller.getModelEditeur();
                                JOptionPane.showMessageDialog(null, "Vous avez ajouté un éditeur", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        case LOCALISATION:
                            if (Exist() != 1) {
                                controller.doSave(getDataLocalisation());
                                modellocalisation = controller.getModelLocalisation();
                                JOptionPane.showMessageDialog(null, "Vous avez ajouté une localisation", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        case LOCALITE:
                            if (Exist() != 1) {
                                controller.doSave(getDataLocalite());
                                modellocalite = controller.getModelLoc();
                                JOptionPane.showMessageDialog(null, "Vous avez ajouté une localité", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        case SUJET:
                            if (Exist() != 1) {
                                controller.doSave(getDataSujet());
                                modelsujet = controller.getModelSujet();
                                JOptionPane.showMessageDialog(null, "Vous avez ajouté un sujet", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        case THEME:
                            if (Exist() != 1) {
                                controller.doSave(getDataTheme());
                                modeltheme = controller.getModelTheme();
                                JOptionPane.showMessageDialog(null, "Vous avez ajouté un thème", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "il y a un ou des chiffres non valide dans le premier champ", "Attention", JOptionPane.WARNING_MESSAGE);
                }
            }
            RemplirTableDonnee();
            Nettoyerchamps();
        });
        BModifier.addActionListener(actionEvent ->  {
            int option;
            if (checkIfLetters(TDonnee1.getText())){
                switch (chx) {
                    case AUTEUR:
                        if (checkIfLetters(TDonnee2.getText())){
                            if (Exist() != 1){
                                option = JOptionPane.showConfirmDialog(null, "Vous allez modifier un auteur", "Attention", JOptionPane.YES_NO_OPTION);
                                if (option == JOptionPane.OK_OPTION) {
                                    try {
                                        controller.doUpdate(getDataAuteur());
                                        modelautor = controller.getModelAuteur();
                                        JOptionPane.showMessageDialog(null, "Vous avez modifié un auteur", "Information", JOptionPane.INFORMATION_MESSAGE);
                                    } catch (Exception e){
                                        JOptionPane.showMessageDialog(null, "La date n'est pas valide", "Attention", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "il y a un ou des chiffres non valide dans le second champ", "Attention", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    case EDITEUR:
                        if (Exist() != 1){
                            option = JOptionPane.showConfirmDialog(null, "Vous allez modifier une collection", "Attention", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.OK_OPTION) {
                                controller.doUpdate(getDataEditeur());
                                modelediteur = controller.getModelEditeur();
                                JOptionPane.showMessageDialog(null, "Vous avez modifié un éditeur", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        break;
                    case LOCALISATION:
                        if (Exist() != 1){
                            option = JOptionPane.showConfirmDialog(null, "Vous allez modifier une localisation", "Attention", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.OK_OPTION) {
                                controller.doUpdate(getDataLocalisation());
                                modellocalisation = controller.getModelLocalisation();
                                JOptionPane.showMessageDialog(null, "Vous avez modifié une localisation", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        break;
                    case LOCALITE:
                        if (Exist() !=1){
                            option = JOptionPane.showConfirmDialog(null, "Vous allez modifier une localité", "Attention", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.OK_OPTION) {
                                controller.doUpdate(getDataLocalite());
                                modellocalite = controller.getModelLoc();
                                JOptionPane.showMessageDialog(null, "Vous avez modifié une localité", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        break;
                    case SUJET:
                        if (Exist() !=1){
                            option = JOptionPane.showConfirmDialog(null, "Vous allez modifier un sujet", "Attention", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.OK_OPTION) {
                                controller.doUpdate(getDataSujet());
                                modelsujet = controller.getModelSujet();
                                JOptionPane.showMessageDialog(null, "Vous avez modifié un sujet", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        break;
                    case THEME:
                        if (Exist() != 1){
                            option = JOptionPane.showConfirmDialog(null, "Vous allez modifier un thème", "Attention", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.OK_OPTION) {
                                controller.doUpdate(getDataTheme());
                                modeltheme = controller.getModelTheme();
                                JOptionPane.showMessageDialog(null, "Vous avez modifié un thème", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        break;
                }
                RemplirTableDonnee();
            } else {
                JOptionPane.showMessageDialog(null, "il y a un ou des chiffres non valide dans le premier champ", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        });
        BSupprimer.addActionListener(actionEvent ->  {
            boolean utiliser = true;
            int option;
            switch (chx) {
                case AUTEUR:
                    String aut = Tid.getText();
                    listlivreauteur = controller.doFindLivre(1, aut);
                    if (listlivreauteur.size() > 0) {
                        JOptionPane.showMessageDialog(null, "Cette auteur ne peut être supprimé, il y a au moins un livre à son nom", "Attention", JOptionPane.WARNING_MESSAGE);
                    } else {
                        option = JOptionPane.showConfirmDialog(null, "Vous allez supprimer un auteur", "Attention", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            try {
                                controller.doDelete(getDataAuteur());
                                modelautor = controller.getModelAuteur();
                                JOptionPane.showMessageDialog(null, "Vous avez supprimer un auteur", "Information", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception e){
                                JOptionPane.showMessageDialog(null, "La date n'est pas valide", "Attention", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                    break;
                case EDITEUR:
                    listlivre = controller.getModelLivre();
                    for (int i = 0; i < listlivre.size(); i++) {
                        if (Integer.valueOf(Tid.getText()) == listlivre.getElementAt(i).getIdEditeur()) {
                            utiliser = false;
                        }
                    }
                    if (utiliser) {
                        option = JOptionPane.showConfirmDialog(null, "Vous allez supprimer un éditeur", "Attention", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            controller.doDelete(getDataEditeur());
                            modelediteur = controller.getModelEditeur();
                            JOptionPane.showMessageDialog(null, "Vous avez supprimer un éditeur", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Cet éditeur ne peut être supprimé, il comprend au moins un livre", "Attention", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case LOCALISATION:
                    listlivre = controller.getModelLivre();
                    for (int i = 0; i < listlivre.size(); i++) {
                        if (Integer.valueOf(Tid.getText()) == listlivre.getElementAt(i).getIdLocalisation()) {
                            utiliser = false;
                        }
                    }
                    if (utiliser) {
                        option = JOptionPane.showConfirmDialog(null, "Vous allez supprimer une localisation", "Attention", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            controller.doDelete(getDataLocalisation());
                            modellocalisation = controller.getModelLocalisation();
                            JOptionPane.showMessageDialog(null, "Vous avez supprimé une localisation", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Cette localisation ne peut être supprimée, elle contient au moins un livre", "Attention", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case LOCALITE:
                    listlecteur = controller.getModel();
                    for (int i = 0; i < listlecteur.size(); i++) {
                        if (Integer.valueOf(Tid.getText()) == listlecteur.getElementAt(i).getLoc()) {
                            utiliser = false;
                        }
                    }
                    if (utiliser) {
                        option = JOptionPane.showConfirmDialog(null, "Vous allez supprimer une localité", "Attention", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            controller.doDelete(getDataLocalite());
                            modellocalite = controller.getModelLoc();
                            JOptionPane.showMessageDialog(null, "Vous avez supprimé une localité", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Cette localité ne peut être supprimée, elle contient au moins un lecteur", "Attention", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case SUJET:
                    String suj = Tid.getText();
                    listlivresujet = controller.doFindLivre(2, suj);
                    if (listlivresujet.size() > 0) {
                        JOptionPane.showMessageDialog(null, "Ce sujet ne peut être supprimé, il y a au moins un livre qui en parle", "Attention", JOptionPane.WARNING_MESSAGE);
                    } else {
                        option = JOptionPane.showConfirmDialog(null, "Vous allez supprimer un sujet", "Attention", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            controller.doDelete(getDataSujet());
                            modelsujet = controller.getModelSujet();
                            JOptionPane.showMessageDialog(null, "Vous avez supprimé un sujet", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    break;
                case THEME:
                    listlivre = controller.getModelLivre();
                    for (int i = 0; i < listlivre.size(); i++) {
                        if (Integer.valueOf(Tid.getText()) == listlivre.getElementAt(i).getIdTheme()) {
                            utiliser = false;
                        }
                    }
                    if (utiliser) {
                        option = JOptionPane.showConfirmDialog(null, "Vous allez supprimer un thème", "Attention", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            controller.doDelete(getDataTheme());
                            modeltheme = controller.getModelTheme();
                            JOptionPane.showMessageDialog(null, "Vous avez supprimé un thème", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Ce thème ne peut être supprimé, il comprend au moins un livre", "Attention", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
            }
            Nettoyerchamps();
            RemplirTableDonnee();
        });
    }

    private int Exist() {
        int exist=0;
        switch (chx){
            case AUTEUR:
                for (int i = 0; i < modelautor.size(); i++) {
                    if (TDonnee1.getText().equals(modelautor.getElementAt(i).getNomAuteur()) && TDonnee2.getText().equals(modelautor.getElementAt(i).getPrenomAuteur())) {
                        JOptionPane.showMessageDialog(null, "Cet auteur existe déjà, vous ne pouvez pas l'ajouter", "Attention", JOptionPane.WARNING_MESSAGE);
                        exist = 1;
                    }
                }
            break;
            case EDITEUR:
                for (int i = 0; i < modelediteur.size(); i++) {
                    if (TDonnee1.getText().equals(modelediteur.getElementAt(i).getNomEditeur())) {
                        JOptionPane.showMessageDialog(null, "Cet éditeur existe déjà, vous ne pouvez pas l'ajouter", "Attention", JOptionPane.WARNING_MESSAGE);
                        exist = 1;
                    }
                }
            break;
            case LOCALISATION:
                for (int i = 0; i < modellocalisation.size(); i++) {
                    if (TDonnee1.getText().equals(modellocalisation.getElementAt(i).getNomLocalisation())) {
                        JOptionPane.showMessageDialog(null, "Cette localisation existe déjà, vous ne pouvez pas l'ajouter", "Attention", JOptionPane.WARNING_MESSAGE);
                        exist = 1;
                    }
                }
            break;
            case LOCALITE:
                for (int i = 0; i < modellocalite.size(); i++) {
                    if (TDonnee1.getText().equals(modellocalite.getElementAt(i).getNomVille()) && TDonnee2.getText().equals(modellocalite.getElementAt(i).getCodePostal())) {
                        JOptionPane.showMessageDialog(null, "Cette localité avec ce code postal existe déjà, vous ne pouvez pas l'ajouter", "Attention", JOptionPane.WARNING_MESSAGE);
                        exist = 1;
                    }
                }
            break;
            case SUJET:
                for (int i = 0; i < modelsujet.size(); i++) {
                    if (TDonnee1.getText().equals(modelsujet.getElementAt(i).getNomSujet())) {
                        JOptionPane.showMessageDialog(null, "Ce sujet existe déjà, vous ne pouvez pas l'ajouter", "Attention", JOptionPane.WARNING_MESSAGE);
                        exist = 1;
                    }
                }
            break;
            case THEME:
                for (int i = 0; i < modeltheme.size(); i++) {
                    if (TDonnee1.getText().equals(modeltheme.getElementAt(i).getNomTheme())) {
                        JOptionPane.showMessageDialog(null, "Ce thème existe déjà, vous ne pouvez pas l'ajouter", "Attention", JOptionPane.WARNING_MESSAGE);
                        exist = 1;
                    }
                }
            break;
        }
        return exist;
    }

    //Cacher tout les pannel à l'ouverture
    private void Cacher() {
        Scroll.setVisible(false);
        LDonnee1.setVisible(false);
        TDonnee1.setVisible(false);
        LDonnee2.setVisible(false);
        TDonnee2.setVisible(false);
        LDonnee3.setVisible(false);
        TDonnee3.setVisible(false);
        PanelBGestion.setVisible(false);
        PanelRecherche.setVisible(false);
        PanelBDeplacement.setVisible(false);
    }

    //Montrer les pannel une fois la sélection faite
    private void Montrer() {
        Scroll.setVisible(true);
        LDonnee1.setVisible(true);
        TDonnee1.setVisible(true);
        LDonnee2.setVisible(true);
        TDonnee2.setVisible(true);
        LDonnee3.setVisible(true);
        TDonnee3.setVisible(true);
        PanelBGestion.setVisible(true);
        PanelRecherche.setVisible(true);
        PanelBDeplacement.setVisible(true);
    }

    //Remplir le jtable
    private void RemplirTableDonnee() {
        TDonneesModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        switch (chx) {
            case AUTEUR:
                TDonneesModel.addColumn("Nom");
                TDonneesModel.addColumn("Prenom");
                TableDonnees.setModel(TDonneesModel);
                for (int i = 0; i < modelautor.size(); i++) {
                    Object[] a = {modelautor.getElementAt(i).getNomAuteur(), modelautor.getElementAt(i).getPrenomAuteur()};
                    TDonneesModel.addRow(a);
                }
                break;
            case EDITEUR:
                TDonneesModel.addColumn("Editeur");
                TableDonnees.setModel(TDonneesModel);
                for (int i = 0; i < modelediteur.size(); i++) {
                    Object[] a = {modelediteur.getElementAt(i).getNomEditeur()};
                    TDonneesModel.addRow(a);
                }
                break;
            case LOCALISATION:
                TDonneesModel.addColumn("Localisation");
                TableDonnees.setModel(TDonneesModel);
                for (int i = 0; i < modellocalisation.size(); i++) {
                    Object[] a = {modellocalisation.getElementAt(i).getNomLocalisation()};
                    TDonneesModel.addRow(a);
                }
                break;
            case LOCALITE:
                TDonneesModel.addColumn("Localité");
                TDonneesModel.addColumn("Code Postal");
                TableDonnees.setModel(TDonneesModel);
                for (int i = 0; i < modellocalite.size(); i++) {
                    Object[] a = {modellocalite.getElementAt(i).getNomVille(), modellocalite.getElementAt(i).getCodePostal()};
                    TDonneesModel.addRow(a);
                }
                break;
            case SUJET:
                TDonneesModel.addColumn("Sujet");
                TableDonnees.setModel(TDonneesModel);
                for (int i = 0; i < modelsujet.size(); i++) {
                    Object[] a = {modelsujet.getElementAt(i).getNomSujet()};
                    TDonneesModel.addRow(a);
                }
                break;
            case THEME:
                TDonneesModel.addColumn("Thème");
                TableDonnees.setModel(TDonneesModel);
                for (int i = 0; i < modeltheme.size(); i++) {
                    Object[] a = {modeltheme.getElementAt(i).getNomTheme()};
                    TDonneesModel.addRow(a);
                }
                break;
        }
    }

    //Remplir les champs nom, prenom,...
    private void RemplissageChamps(int P) {
        switch (chx) {
            case AUTEUR:
                Tid.setText(String.valueOf(modelautor.getElementAt(P).getIdAuteur()));
                TDonnee1.setText(modelautor.getElementAt(P).getNomAuteur());
                TDonnee2.setText(modelautor.getElementAt(P).getPrenomAuteur());
                TDonnee3.setText(String.valueOf(modelautor.getElementAt(P).getDnAuteur().format(formatter)));
                break;
            case EDITEUR:
                Tid.setText(String.valueOf(modelediteur.getElementAt(P).getIdEditeur()));
                TDonnee1.setText(modelediteur.getElementAt(P).getNomEditeur());
                break;
            case LOCALISATION:
                Tid.setText(String.valueOf(modellocalisation.getElementAt(P).getIdLocalisation()));
                TDonnee1.setText(modellocalisation.getElementAt(P).getNomLocalisation());
                break;
            case LOCALITE:
                Tid.setText(String.valueOf(modellocalite.getElementAt(P).getIDLocalite()));
                TDonnee1.setText(modellocalite.getElementAt(P).getNomVille());
                TDonnee2.setText(modellocalite.getElementAt(P).getCodePostal());
                break;
            case SUJET:
                Tid.setText(String.valueOf(modelsujet.getElementAt(P).getIdSujet()));
                TDonnee1.setText(modelsujet.getElementAt(P).getNomSujet());
                break;
            case THEME:
                Tid.setText(String.valueOf(modeltheme.getElementAt(P).getIdTheme()));
                TDonnee1.setText(modeltheme.getElementAt(P).getNomTheme());
                break;
        }
        TableDonnees.setRowSelectionInterval(P, P);
    }

    //Rechercher des données
    private void RechercherDonnees() {
        TDonneesModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        switch (chx) {
            case AUTEUR:
                for (int i = 0; i < modelautor.size(); i++) {
                    if (TRechercher.getText().toLowerCase().equals(modelautor.getElementAt(i).getNomAuteur())) {
                        Tid.setText(String.valueOf(modelautor.getElementAt(i).getIdAuteur()));
                        TDonnee1.setText(modelautor.getElementAt(i).getNomAuteur());
                        TDonnee2.setText(modelautor.getElementAt(i).getPrenomAuteur());
                        TDonnee3.setText(String.valueOf(modelautor.getElementAt(i).getDnAuteur().format(formatter)));
                        TableDonnees.setRowSelectionInterval(i,i);
                    }
                }

                break;
            case EDITEUR:
                for (int i = 0; i < modelediteur.size(); i++) {
                    if (TRechercher.getText().toLowerCase().equals(modelediteur.getElementAt(i).getNomEditeur())) {
                        Tid.setText(String.valueOf(modelediteur.getElementAt(i).getIdEditeur()));
                        TDonnee1.setText(modelediteur.getElementAt(i).getNomEditeur());
                        TableDonnees.setRowSelectionInterval(i,i);
                    }
                }
                break;
            case LOCALISATION:
                for (int i = 0; i < modellocalisation.size(); i++) {
                    if (TRechercher.getText().toLowerCase().equals(modellocalisation.getElementAt(i).getNomLocalisation())) {
                        Tid.setText(String.valueOf(modellocalisation.getElementAt(i).getIdLocalisation()));
                        TDonnee1.setText(modellocalisation.getElementAt(i).getNomLocalisation());
                        TableDonnees.setRowSelectionInterval(i,i);
                    }
                }
                break;
            case LOCALITE:
                for (int i = 0; i < modellocalite.size(); i++) {
                    if (TRechercher.getText().toLowerCase().equals(modellocalite.getElementAt(i).getNomVille())) {
                        Tid.setText(String.valueOf(modellocalite.getElementAt(i).getIDLocalite()));
                        TDonnee1.setText(modellocalite.getElementAt(i).getNomVille());
                        TDonnee2.setText(modellocalite.getElementAt(i).getCodePostal());
                        TableDonnees.setRowSelectionInterval(i,i);
                    }
                }

                break;
            case SUJET:
                for (int i = 0; i < modelsujet.size(); i++) {
                    if (TRechercher.getText().toLowerCase().equals(modelsujet.getElementAt(i).getNomSujet())) {
                        Tid.setText(String.valueOf(modelsujet.getElementAt(i).getIdSujet()));
                        TDonnee1.setText(modelsujet.getElementAt(i).getNomSujet());
                        TableDonnees.setRowSelectionInterval(i,i);
                    }
                }
                break;
            case THEME:
                for (int i = 0; i < modeltheme.size(); i++) {
                    if (TRechercher.getText().toLowerCase().equals(modeltheme.getElementAt(i).getNomTheme())) {
                        Tid.setText(String.valueOf(modeltheme.getElementAt(i).getIdTheme()));
                        TDonnee1.setText(modeltheme.getElementAt(i).getNomTheme());
                        TableDonnees.setRowSelectionInterval(i,i);
                    }
                }
                break;
        }
        if (Tid.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "il n'y a aucune donnée pour cette recherche", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void NettoyerTable() {
        for (int i = TableDonnees.getRowCount() - 1; i >= 0; i--) {
            TDonneesModel.removeRow(i);
        }
    }

    private void Nettoyerchamps() {
        Tid.setText("");
        TDonnee1.setText("");
        TDonnee2.setText("");
        TDonnee3.setText("");
        TRechercher.setText("");
    }

    private void CheckChamps() {
        switch (chx) {
            case AUTEUR:
                if (!TDonnee1.getText().isEmpty() && !TDonnee2.getText().isEmpty() && !TDonnee3.getText().isEmpty()) {
                    check = false;
                } else {
                    check = true;
                }
                break;
            case EDITEUR:
                if (!TDonnee1.getText().isEmpty()) {
                    check = false;
                } else {
                    check = true;
                }
                break;
            case LOCALISATION:
                if (!TDonnee1.getText().isEmpty()) {
                    check = false;
                } else {
                    check = true;
                }
                break;
            case LOCALITE:
                if (!TDonnee1.getText().isEmpty() && !TDonnee2.getText().isEmpty()) {
                    check = false;
                } else {
                    check = true;
                }
                break;
            case SUJET:
                if (!TDonnee1.getText().isEmpty()) {
                    check = false;
                } else {
                    check = true;
                }
                break;
            case THEME:
                if (!TDonnee1.getText().isEmpty()) {
                    check = false;
                } else {
                    check = true;
                }
                break;
        }
    }

    private Auteur getDataAuteur() {
        Auteur A = new Auteur();
        if (!Tid.getText().isEmpty()) {
            A.setIdAuteur(Integer.valueOf(Tid.getText()));
        }
        A.setNomAuteur(TDonnee1.getText().toLowerCase());
        A.setPrenomAuteur(TDonnee2.getText().toLowerCase());
        A.setDnAuteur(LocalDate.parse(TDonnee3.getText(), formatter));
        return A;
    }

    private Editeur getDataEditeur() {
        Editeur C = new Editeur();
        if (!Tid.getText().isEmpty()) {
            C.setIdEditeur(Integer.valueOf(Tid.getText()));
        }
        C.setNomEditeur(TDonnee1.getText().toLowerCase());
        return C;
    }

    private Localisation getDataLocalisation() {
        Localisation loca = new Localisation();
        if (!Tid.getText().isEmpty()) {
            loca.setIdLocalisation(Integer.valueOf(Tid.getText()));
        }
        loca.setNomSLocalisation(TDonnee1.getText().toLowerCase());
        return loca;
    }

    private Localite getDataLocalite() {
        Localite loc = new Localite();
        if (!Tid.getText().isEmpty()) {
            loc.setIDLocalite(Integer.valueOf(Tid.getText()));
        }
        loc.setNomVille(TDonnee1.getText().toLowerCase());
        loc.setCodePostal(TDonnee2.getText());
        return loc;
    }

    private Sujet getDataSujet() {
        Sujet suj = new Sujet();
        if (!Tid.getText().isEmpty()) {
            suj.setIdSujet(Integer.valueOf(Tid.getText()));
        }
        suj.setNomSujet(TDonnee1.getText().toLowerCase());
        return suj;
    }

    private Theme getDataTheme() {
        Theme the = new Theme();
        if (!Tid.getText().isEmpty()) {
            the.setIdTheme(Integer.valueOf(Tid.getText()));
        }
        the.setNomTheme(TDonnee1.getText().toLowerCase());
        return the;
    }

    //Méthode des Listeners
    private void ChxAuteur() {
        Nettoyerchamps();
        chx = TypeDonnees.AUTEUR;
        modelautor = controller.getModelAuteur();
        Montrer();
        LDonnee1.setText("Nom");
        LDonnee2.setText("Prénom");
        TDonnee2.setFormatterFactory(null);
        RemplirTableDonnee();
    }

    private void ChxEditeur() {
        Nettoyerchamps();
        chx = TypeDonnees.EDITEUR;
        modelediteur = controller.getModelEditeur();
        Montrer();
        LDonnee1.setText("Editeur :");
        LDonnee2.setVisible(false);
        TDonnee2.setVisible(false);
        LDonnee3.setVisible(false);
        TDonnee3.setVisible(false);
        RemplirTableDonnee();
    }

    private void ChxLocalisation() {
        Nettoyerchamps();
        chx = TypeDonnees.LOCALISATION;
        modellocalisation = controller.getModelLocalisation();
        Montrer();
        LDonnee1.setText("Localisation :");
        LDonnee2.setVisible(false);
        TDonnee2.setVisible(false);
        LDonnee3.setVisible(false);
        TDonnee3.setVisible(false);
        RemplirTableDonnee();
    }

    private void ChxLocalite() {
        Nettoyerchamps();
        chx = TypeDonnees.LOCALITE;
        modellocalite = controller.getModelLoc();
        Montrer();
        LDonnee1.setText("Localite :");
        LDonnee2.setText("Code postal :");
        try {
            MaskFormatter mf2 = new MaskFormatter("####");
            DefaultFormatterFactory CP = new DefaultFormatterFactory(mf2);
            TDonnee2.setFormatterFactory(CP);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LDonnee3.setVisible(false);
        TDonnee3.setVisible(false);
        RemplirTableDonnee();
    }

    private void ChxSujet() {
        Nettoyerchamps();
        chx = TypeDonnees.SUJET;
        modelsujet = controller.getModelSujet();
        Montrer();
        LDonnee1.setText("Sujet :");
        LDonnee2.setVisible(false);
        TDonnee2.setVisible(false);
        LDonnee3.setVisible(false);
        TDonnee3.setVisible(false);
        RemplirTableDonnee();
    }

    private void ChxThemes() {
        Nettoyerchamps();
        chx = TypeDonnees.THEME;
        modeltheme = controller.getModelTheme();
        Montrer();
        LDonnee1.setText("Thème :");
        LDonnee2.setVisible(false);
        TDonnee2.setVisible(false);
        LDonnee3.setVisible(false);
        TDonnee3.setVisible(false);
        RemplirTableDonnee();
    }

    //Allez au premier de la table des données
    private void Premier() {
        pos = 0;
        RemplissageChamps(pos);
    }

    //Allez au précédent dans la table des données
    private void Precedent() {
        if (pos == 0) {
            pos = TableDonnees.getRowCount() - 1;
        } else {
            pos = pos - 1;
        }
        RemplissageChamps(pos);
    }

    //Allez au suivant la table des données
    private void Suivant() {
        if (pos == TableDonnees.getRowCount() - 1) {
            pos = 0;
        } else {
            pos = pos + 1;
        }
        RemplissageChamps(pos);
    }

    //Allez au dernier de la table des données
    private void Dernier() {
        pos = TableDonnees.getRowCount() - 1;
        RemplissageChamps(pos);
    }

    //Selectionné dans la liste par clic
    private void ClicTable() {
        if (TableDonnees.getSelectedRowCount() >= 1) {
            pos = TableDonnees.getSelectedRow();
            RemplissageChamps(pos);
        }
    }

    //se déconnecter et revenir à Fdépart
    /*private void Deconnexion() {
        FGestionDonnees frame = FGestionDonnees.this;
        frame.close();
        Fdepart start = new Fdepart();
        start.setVisible(true);
    }*/

    //Quitter le programme
    private void Quitter() {
        System.exit(0);
    }



}