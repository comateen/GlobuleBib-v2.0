package view;

import controller.Controller;
import model.book.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jof on 11/04/2016.
 */
class FGestionLivres extends AppFrame {
    private DefaultTableModel TlivreModel;
    private DefaultTableModel TLAModel;
    private DefaultTableModel TLSModel;
    private final Controller controller = new Controller();
    private DefaultListModel<Livre> modellivre;
    private DefaultListModel<Editeur> modelediteur;
    private DefaultListModel<Localisation> modellocalisation;
    private DefaultListModel<Theme> modeltheme;
    private DefaultListModel<Auteur> modelauteur;
    private DefaultListModel<Sujet> modelsujet;
    private DefaultListModel<Auteur> listlivreauteur = new DefaultListModel<>();
    private DefaultListModel<Sujet> listlivresujet = new DefaultListModel<>();
    private DefaultListModel<Livre> listlivreemprunt = new DefaultListModel<>();
    private String[] Statut = new String[3];
    private JPanel PanelInfo;
    private JLabel LAuteur;
    private JLabel LTitreLivre;
    private JLabel LSection;
    private JTextField TSectionLivre;
    private JLabel LStatut;
    private JComboBox CBEditeur;
    private JLabel LTheme;
    private JLabel LLocalisation;
    private JTextField TISBN;
    private JTextField TTitreLivre;
    private JLabel LEditeur;
    private JComboBox CBTheme;
    private JComboBox CBLocalisation;
    private JPanel PanelBGestion;
    private JButton BAjouter;
    private JButton BModifier;
    private JButton BSupprimer;
    private JPanel PanelRecherche;
    private JButton BRechercher;
    private JTextField TRechercher;
    private JTable TAuteurLivre;
    private JLabel Lisbn;
    private JLabel LSujet;
    private JTable TableLivres;
    private JLabel LTitreFrame;
    private JPanel PanelBDeplacement;
    private JButton BPremier;
    private JButton BPrecedent;
    private JButton BSuivant;
    private JButton BDernier;
    private JComboBox CBAuteur;
    private JTable TSujetLivre;
    private JComboBox CBSujet;
    private JButton BAjoutAuteur;
    private JButton BSupAuteur;
    private JButton BSupSujet;
    private JButton BAjoutSujet;
    private JComboBox CBStatut;
    private JPanel Container;
    private JTextField TidLivre;
    private JButton TRecharger;
    private JLabel LCote;
    private JTextField TCote;
    private int pos = 0;
    private boolean check=true;

    @Override
    JPanel getContainer() {
        return Container; // pour forcer la création d'un container
    }

    protected FGestionLivres(){
        super("GlobuleBib - Gestion des livres", null);
        init();
        this.setLocation(100,100);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //cette fenêtre peut être fermée à tout moment par l'utilisateur
        JMenuBar MenuBar = new JMenuBar();
        this.setJMenuBar(MenuBar);
        JMenu fichier = new JMenu("Fichier");
        MenuBar.add(fichier);
        JMenuItem nouveau = new JMenuItem("Nouveau");
        fichier.add(nouveau);
        JMenuItem Dupliquer = new JMenuItem("Dupliquer");
        fichier.add(Dupliquer);
        JMenuItem Exit = new JMenuItem("Quitter");
        fichier.add(Exit);
        modellivre = controller.getModelLivre();
        modelediteur = controller.getModelEditeur();
        modellocalisation = controller.getModelLocalisation();
        modelauteur = controller.getModelAuteur();
        modelsujet = controller.getModelSujet();
        modeltheme = controller.getModelTheme();
        Statut = new String[]{"Disponible", "En prêt", "Réservé"};

        RemplirTableLivre();
        RemplirCBs();

        BAjouter.addActionListener(actionEvent -> AjouterLivre());
        BModifier.addActionListener(actionEvent -> ModifierLivre());
        BSupprimer.addActionListener(actionEvent -> SupprimerLivre());
        BRechercher.addActionListener(actionEvent -> RechercheLivre());
        BPremier.addActionListener(actionEvent -> Premier());
        BPrecedent.addActionListener(actionEvent -> Precedent());
        BSuivant.addActionListener(actionEvent -> Suivant());
        BDernier.addActionListener(actionEvent -> Dernier());
        BAjoutAuteur.addActionListener(actionEvent -> AjoutAuteur());
        BSupAuteur.addActionListener(actionEvent -> RetirerAuteur());
        BAjoutSujet.addActionListener(actionEvent -> Ajoutsujet());
        BSupSujet.addActionListener(actionEvent -> RetirerSujet());
        TableLivres.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ClicTableLivre();
            }
        });
        TRecharger.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                modellivre = controller.getModelLivre();
                RemplirTableLivre();
            }
        });
        nouveau.addActionListener(actionEvent -> NouveauLivre());
        Dupliquer.addActionListener(actionEvent -> Duplication());
        Exit.addActionListener(actionEvent -> Quitter());
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                super.windowGainedFocus(e);
                modelediteur = controller.getModelEditeur();
                modeltheme = controller.getModelTheme();
                modellocalisation = controller.getModelLocalisation();
                modelauteur = controller.getModelAuteur();
                modelsujet = controller.getModelSujet();
                RemplirCBs();
            }
        });
    }

    private void AjouterLivre(){
        TidLivre.setText("");
        CheckChamps();
        if (check){
            JOptionPane.showMessageDialog(null, "Certains champs ne sont pas complété", "Attention", JOptionPane.WARNING_MESSAGE);
        } else {
            if (TSectionLivre.getText().toLowerCase().equals("adulte") || TSectionLivre.getText().toLowerCase().equals("jeunesse")){
                controller.doSave(getData());
                NouveauLivre();
                pos=modellivre.size();
                JOptionPane.showMessageDialog(null, "Vous avez ajouté un livre", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "La section doit être adulte ou jeunesse", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        }
        RemplirTableLivre();
    }

    private void ModifierLivre(){
        if (TSectionLivre.getText().toLowerCase().equals("adulte") || TSectionLivre.getText().toLowerCase().equals("jeunesse")){
            int option = JOptionPane.showConfirmDialog(null, "Vous allez modifier un livre", "Attention", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                controller.doUpdate(getData());
                NouveauLivre();
                JOptionPane.showMessageDialog(null, "Vous avez modifié un livre", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "La section doit être adulte ou jeunesse", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void SupprimerLivre(){
        System.out.println(TidLivre.getText());
        listlivreemprunt = controller.doFindLivre(5, TidLivre.getText());
        if (listlivreemprunt.size()>0){
            int option = JOptionPane.showConfirmDialog(null, "Vous allez supprimer un livre", "Attention", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                controller.doDelete(getData());
                NouveauLivre();
                JOptionPane.showMessageDialog(null, "Vous avez supprimé un livre", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ce livre est emprunté, il ne peut être supprimé", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void RemplirTableLivre() {
        TlivreModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TlivreModel.addColumn("Titre du livre");
        TableLivres.setModel(TlivreModel);
        for (int i = 0; i < modellivre.size(); i++){
            Object [] L={modellivre.getElementAt(i).getTitreLivre()};
                TlivreModel.addRow(L);
        }
    }

    private void RemplirTableLivreAuteur(){
        TLAModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TLAModel.addColumn("Nom");
        TLAModel.addColumn(("Prenom"));
        TAuteurLivre.setModel(TLAModel);
        for (int i = 0; i < listlivreauteur.size(); i++){
            Object [] L={listlivreauteur.getElementAt(i).getNomAuteur(), listlivreauteur.getElementAt(i).getPrenomAuteur()};
            TLAModel.addRow(L);
        }
    }

    private void RemplirTableLivreAuteur(String p){
        TLAModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        listlivreauteur = controller.doFindAuteur(p);
        TLAModel.addColumn("Nom");
        TLAModel.addColumn(("Prenom"));
        TAuteurLivre.setModel(TLAModel);
        for (int i = 0; i < listlivreauteur.size(); i++){
            Object [] L={listlivreauteur.getElementAt(i).getNomAuteur(), listlivreauteur.getElementAt(i).getPrenomAuteur()};
            TLAModel.addRow(L);
        }
    }

    private void RemplirTableLivreSujet(){
        TLSModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TLSModel.addColumn("Sujet(s)");
        TSujetLivre.setModel(TLSModel);
        for (int i = 0; i < listlivresujet.size(); i++){
            Object [] L={listlivresujet.getElementAt(i).getNomSujet()};
            TLSModel.addRow(L);
        }
    }

    private void RemplirTableLivreSujet(String p){
        TLSModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        listlivresujet = controller.doFindSujet(String.format(p));
        TLSModel.addColumn("Sujet(s)");
        TSujetLivre.setModel(TLSModel);
        for (int i = 0; i < listlivresujet.size(); i++){
            Object [] L={listlivresujet.getElementAt(i).getNomSujet()};
            TLSModel.addRow(L);
        }
    }

    private void RemplirCBs(){
        String[]ListEditeur = new String[modelediteur.size()+1];
        ListEditeur[0]="";
        for (int i = 0; i< modelediteur.size(); i++){
            ListEditeur[i+1] = modelediteur.getElementAt(i).getNomEditeur();
        }
        CBEditeur.setModel(new DefaultComboBoxModel(ListEditeur));
        String[]ListLocalisation = new String[modellocalisation.size()+1];
        ListLocalisation[0]="";
        for (int i = 0; i< modellocalisation.size(); i++){
            ListLocalisation[i+1] = modellocalisation.getElementAt(i).getNomLocalisation();
        }
        CBLocalisation.setModel(new DefaultComboBoxModel(ListLocalisation));
        String[]ListTheme = new String[modeltheme.size()+1];
        ListTheme[0]="";
        for (int i = 0; i< modeltheme.size(); i++){
            ListTheme[i+1] = modeltheme.getElementAt(i).getNomTheme();
        }
        CBTheme.setModel(new DefaultComboBoxModel(ListTheme));
        String[]ListAuteur = new String[modelauteur.size()];
        for (int i = 0; i< modelauteur.size(); i++){
            ListAuteur[i] = modelauteur.getElementAt(i).getNomAuteur();
        }
        CBAuteur.setModel(new DefaultComboBoxModel(ListAuteur));
        String[]ListSujet = new String[modelsujet.size()];
        for (int i = 0; i< modelsujet.size(); i++){
            ListSujet[i] = modelsujet.getElementAt(i).getNomSujet();
        }
        CBSujet.setModel(new DefaultComboBoxModel(ListSujet));
        CBStatut.setModel(new DefaultComboBoxModel(Statut));
    }

    //Allez au premier de la liste
    private void Premier() {
        pos=0;
        TableLivres.setRowSelectionInterval(pos, pos);
        RemplissageChamps(pos);
    }

    //Allez au précédent dans la liste
    private void Precedent(){
        if (pos==0){
            pos=TableLivres.getRowCount()-1;
        } else {
            pos=pos-1;
        }
        TableLivres.setRowSelectionInterval(pos, pos);
        RemplissageChamps(pos);
    }

    //Allez au suivant la liste
    private void Suivant () {
        if (pos==TableLivres.getRowCount()-1){
            pos=0;
        } else {
            pos=pos+1;
        }
        TableLivres.setRowSelectionInterval(pos, pos);
        RemplissageChamps(pos);
    }

    //Allez au dernier de la liste
    private void Dernier(){
        pos=TableLivres.getRowCount()-1;
        TableLivres.setRowSelectionInterval(pos, pos);
        RemplissageChamps(pos);
    }

    //Selectionné dans la liste par clic
    private void ClicTableLivre(){
        if (TableLivres.getRowCount()>=1){
            pos=TableLivres.getSelectedRow();
            RemplissageChamps(pos);
        }
    }

    private void CheckChamps() {
        if (!TISBN.getText().isEmpty() && !TTitreLivre.getText().isEmpty() && !TSectionLivre.getText().isEmpty() && listlivreauteur.size()>0 && listlivresujet.size()>0) {
            check = false;
        } else {
            check = true;
        }
    }

    private void RemplissageChamps(int p) {
        TidLivre.setText(String.valueOf((modellivre.getElementAt(p).getIdLivre())));
        TISBN.setText(modellivre.getElementAt(p).getIsbnLivre());
        TTitreLivre.setText(modellivre.getElementAt(p).getTitreLivre());
        TSectionLivre.setText(modellivre.getElementAt(p).getSectionLivre());
        TCote.setText(modellivre.getElementAt(p).getCoteLivre());
        CBStatut.setSelectedIndex(modellivre.getElementAt(p).getStatutLivre());
        for (int i = 0; i< modelediteur.size(); i++){
            if (modelediteur.getElementAt(i).getIdEditeur()==modellivre.getElementAt(p).getIdEditeur()){
                CBEditeur.setSelectedIndex(i+1);
            }
        }
        for (int i = 0; i<modeltheme.size(); i++){
            if (modeltheme.getElementAt(i).getIdTheme()==modellivre.getElementAt(p).getIdTheme()){
                CBTheme.setSelectedIndex(i+1);
            }
        }
        for (int i = 0; i<modellocalisation.size(); i++){
            if (modellocalisation.getElementAt(i).getIdLocalisation()==modellivre.getElementAt(p).getIdLocalisation()){
                CBLocalisation.setSelectedIndex(i+1);
            }
        }
        RemplirTableLivreAuteur(String.valueOf(modellivre.getElementAt(p).getIdLivre()));
        RemplirTableLivreSujet(String.valueOf(modellivre.getElementAt(p).getIdLivre()));
    }

    private void RechercheLivre(){
        NettoyerChamps();
        NettoyerTables();
        TlivreModel = new DefaultTableModel();
        TlivreModel.addColumn("Titre du livre");
        TableLivres.setModel(TlivreModel);
        String[] choix = {"titre", "auteur", "Annuler"};
        int rang = JOptionPane.showOptionDialog(null,
                "Sur quel champ effectuer la recherche?",
                "Information!",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choix,
                choix[2]);
        if (choix[rang].equals("titre")){
            modellivre = controller.getModelLivre();
            for (int i = 0; i <modellivre.size(); i++){
                if (modellivre.getElementAt(i).getTitreLivre().contains(TRechercher.getText().toLowerCase())){
                    Object [] L={modellivre.getElementAt(i).getTitreLivre()};
                    TlivreModel.addRow(L);
                }
            }
            if (TlivreModel.getRowCount()<1){
                JOptionPane.showMessageDialog(null, "Il n'y a pas de livre portant ce titre", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (choix[rang].equals("auteur")) {
            String auteur = TRechercher.getText().toLowerCase();
            modellivre = controller.doFindLivre(3, auteur);
            if (modellivre.size()>0){
                RemplirTableLivre();
            } else {
                JOptionPane.showMessageDialog(null, "Il n'y a pas de livre pour cet auteur", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void NettoyerTables(){
        listlivreauteur.removeAllElements();
        listlivresujet.removeAllElements();
        modellivre = controller.getModelLivre();
        RemplirTableLivreAuteur();
        RemplirTableLivreSujet();
        RemplirTableLivre();
    }

    private void NettoyerChamps(){
        TidLivre.setText("");
        TISBN.setText("");
        TTitreLivre.setText("");
        TSectionLivre.setText("");
        TCote.setText("");
        CBStatut.setSelectedItem("");
        CBAuteur.setSelectedIndex(0);
        CBEditeur.setSelectedIndex(0);
        CBLocalisation.setSelectedIndex(0);
        CBSujet.setSelectedIndex(0);
        CBTheme.setSelectedIndex(0);
    }

    private void AjoutAuteur(){
        int exist = 0;
        for (int i = 0; i<listlivreauteur.size(); i++){
            if(listlivreauteur.getElementAt(i).getIdAuteur()==modelauteur.getElementAt(CBAuteur.getSelectedIndex()).getIdAuteur()){
                exist=1;
            }
        }
        if (exist!=1){
            listlivreauteur.addElement(modelauteur.getElementAt(CBAuteur.getSelectedIndex()));
            RemplirTableLivreAuteur();
        } else {
            JOptionPane.showMessageDialog(null, "Cet auteur est déjà inscrit comme écrivain de ce livre", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void RetirerAuteur(){
        listlivreauteur.remove(TAuteurLivre.getSelectedRow());
        for (int i =0; i < TAuteurLivre.getRowCount()-1; i++ ){
            TLAModel.removeRow(i);
        }
        RemplirTableLivreAuteur();
    }

    private void Ajoutsujet(){
        int exist=0;
        for (int i = 0; i < listlivresujet.size(); i++){
            if (listlivresujet.getElementAt(i).getIdSujet()==modelsujet.getElementAt(CBSujet.getSelectedIndex()).getIdSujet()){
                exist=1;
            }
        }
        if (exist!=1){
            listlivresujet.addElement(modelsujet.getElementAt(CBSujet.getSelectedIndex()));
            RemplirTableLivreSujet();
        } else {
            JOptionPane.showMessageDialog(null, "Ce sujet est déjà repris dans les sujets de ce livre", "Attention", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void RetirerSujet(){
        listlivresujet.remove(TSujetLivre.getSelectedRow());
        for (int i = 0; i < TSujetLivre.getRowCount()-1; i++){
            TLSModel.removeRow(i);
        }
        RemplirTableLivreSujet();
    }

    private Livre getData(){
        Livre livre = new Livre();
        List<Auteur> auteurs = new ArrayList<>();
        List<Sujet> sujets = new ArrayList<>();
        if (!TidLivre.getText().isEmpty()){
            livre.setIdLivre(Integer.valueOf(TidLivre.getText()));
        }
        livre.setIsbnLivre(TISBN.getText());
        livre.setTitreLivre(TTitreLivre.getText().toLowerCase());
        livre.setSectionLivre(TSectionLivre.getText().toLowerCase());
        livre.setCoteLivre(TCote.getText().toLowerCase());
        livre.setStatutLivre(CBStatut.getSelectedIndex());
        livre.setIdEditeur(modelediteur.getElementAt(CBEditeur.getSelectedIndex()-1).getIdEditeur());
        livre.setIdTheme(modeltheme.getElementAt(CBTheme.getSelectedIndex()-1).getIdTheme());
        livre.setIdLocalisation(modellocalisation.getElementAt(CBLocalisation.getSelectedIndex()-1).getIdLocalisation());
        for (int i = 0; i < listlivreauteur.size(); i++) {
            auteurs.add(listlivreauteur.elementAt(i));
        }
        livre.setAuteurs(auteurs);
        for (int i = 0; i < listlivresujet.size(); i++) {
            sujets.add(listlivresujet.elementAt(i));
        }
        livre.setSujets(sujets);
        return livre;
    }

    private void NouveauLivre() {
        NettoyerChamps();
        NettoyerTables();
        RemplirTableLivre();
    }

    private void Duplication (){
        Livre livre = new Livre();
        List<Auteur> auteurs = new ArrayList<>();
        List<Sujet> sujets = new ArrayList<>();
        for (int i = 0; i < modellivre.size(); i++){
            if (String.valueOf(modellivre.getElementAt(i).getIdLivre()).equals(TidLivre.getText())){
                int option = JOptionPane.showConfirmDialog(null, "Vous allez dupliquer " + modellivre.getElementAt(i).getTitreLivre(), "Attention", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    livre.setIsbnLivre(modellivre.getElementAt(i).getIsbnLivre());
                    livre.setTitreLivre(modellivre.getElementAt(i).getTitreLivre());
                    livre.setSectionLivre(modellivre.getElementAt(i).getSectionLivre());
                    livre.setCoteLivre(modellivre.getElementAt(i).getCoteLivre());
                    livre.setStatutLivre(0);
                    livre.setIdEditeur(modellivre.getElementAt(i).getIdEditeur());
                    livre.setIdTheme(modellivre.getElementAt(i).getIdTheme());
                    livre.setIdLocalisation(modellivre.getElementAt(i).getIdLocalisation());
                    for (int j = 0; j < listlivreauteur.size(); j++) {
                        auteurs.add(listlivreauteur.elementAt(j));
                    }
                    livre.setAuteurs(auteurs);
                    for (int j = 0; j < listlivresujet.size(); j++) {
                        sujets.add(listlivresujet.elementAt(j));
                    }
                    livre.setSujets(sujets);
                    controller.doSave(livre);
                    NouveauLivre();
                    pos=modellivre.size();
                    JOptionPane.showMessageDialog(null, "duplication réussie" , "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    //se déconnecter et revenir à Fdépart
    /*private void Deconnexion(){
        FGestionLivres frame = FGestionLivres.this;
        frame.close();
        Fdepart start = new Fdepart();
        start.setVisible(true);
    }*/

    //Quitter le programme
    private void Quitter (){
        System.exit(0);
    }

}
