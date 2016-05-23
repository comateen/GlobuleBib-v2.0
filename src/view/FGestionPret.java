package view;

import controller.Controller;
import model.book.Livre;
import model.loan.Emprunt;
import model.reader.Lecteur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jof on 02/04/2016.
 */
public class FGestionPret extends AppFrame {
    private DefaultTableModel TPeriodeModel;
    private DefaultTableModel TELModel;
    private DefaultTableModel TLivreModel;
    private final Controller controller = new Controller();
    private DefaultListModel<Livre> modellivre;
    private DefaultListModel<Lecteur> modellecteur;
    private DefaultListModel<Emprunt> modelEmprunt;
    private DefaultListModel<Livre> listlivreemprunt = new DefaultListModel<>();
    private DefaultListModel<Lecteur> ListRechercheLecteur = new DefaultListModel<>();
    private DefaultListModel<Lecteur> ListRechercheLivre = new DefaultListModel<>();
    private DefaultListModel<Livre> listPeriode = new DefaultListModel<>();

    private JPanel Container;
    private JTabbedPane TPPret;
    private JPanel PanelInfo;
    private JLabel LPrenomLecteur;
    private JLabel LDateEmprunt;
    private JLabel LDateRetour;
    private JLabel LNomLecteur;
    private JTextField TPrenomLecteur;
    private JTextField TNomLecteur;
    private JComboBox CBLecteur;
    private JPanel PanelBGestion;
    private JButton BAjouter;
    private JButton BFullRetour;
    private JButton BProlonger;
    private JButton BRechercherLecteur;
    private JLabel LNumLecteur;
    private JComboBox CBNumLecteur;
    private JTextField TDateEmprunt;
    private JTextField TDateRetour;
    private JPanel PanelBDeplacement;
    private JButton BLPremier;
    private JButton BLPrecedent;
    private JButton BLSuivant;
    private JButton BLDernier;
    private JButton BRecharger;
    private JLabel LLivre;
    private JLabel TLEmprunt;
    private JButton BEPremier;
    private JButton BEPrecedent;
    private JButton BESuivant;
    private JButton BEDernier;
    private JButton AjoutEmprunt;
    private JButton RetirerEmprunt;
    private JTable TableLivres;
    private JTable TableEmprunts;
    private JTextField TRechercherLecteur;
    private JTextField TidEmprunt;
    private JButton BRecherhceEmprunt;
    private JTextField TREmprunt;
    private JButton BRechercheLivre;
    private JTextField TRechercheLivre;
    private JButton BRetour;
    private JPanel Global;
    private JLabel Lperiode;
    private JLabel LDébut;
    private JLabel LFin;
    private JLabel Lnbr;
    private JTextField TNbrPret;
    private JButton BRechercher;
    private JFormattedTextField TDebutPeriode;
    private JFormattedTextField TFinPeriode;
    private JLabel LListeLivre;
    private JTable TablePeriode;
    private JButton BPPremier;
    private JButton BPPrecedent;
    private JButton BPSuivant;
    private JButton BPDernier;
    private JTextField TNbrSortieLivre;
    private JLabel LTotalLivres;
    private JTextField TTotalLivre;
    private LocalDate date, dateRetour;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private int posLivre =0;
    private int posEmprunt=0;
    private int posPeriode=0;

    @Override
    JPanel getContainer() {
        return Container; // pour forcer la création d'un container
    }

    public FGestionPret(){
        super("GlobuleBib - Gestion des emprunts", null);
        init();
        this.setLocation(100,100);
        //Création de la barre de menu et de ses composants
        JMenuBar MenuBar = new JMenuBar();
        this.setJMenuBar(MenuBar);
        JMenu fichier = new JMenu("Fichier");
        MenuBar.add(fichier);
        JMenuItem NewPret = new JMenuItem("Nouveau Prêt");
        fichier.add(NewPret);
        JMenuItem Deco = new JMenuItem("Se déconnecter");
        fichier.add(Deco);
        JMenuItem Exit = new JMenuItem("Quitter");
        fichier.add(Exit);
        JMenu Gestion = new JMenu("Gestion");
        MenuBar.add(Gestion);
        JMenuItem Lecteur = new JMenuItem("Lecteurs");
        Gestion.add(Lecteur);
        JMenuItem Livre = new JMenuItem("Livres");
        Gestion.add(Livre);
        JMenuItem Donnees = new JMenuItem("Données");
        Gestion.add(Donnees);
        modellivre = controller.getModelLivre();
        modellecteur = controller.getModel();
        modelEmprunt = controller.getModelEmprunt();
        TDateEmprunt.setText(String.valueOf(date.now().format(formatter)));
        CalculDateRetour();
        try {
            MaskFormatter mf = new MaskFormatter("##-##-####");
            DefaultFormatterFactory form = new DefaultFormatterFactory(mf);
            TDebutPeriode.setFormatterFactory(form);
            TFinPeriode.setFormatterFactory(form);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RemplirTableLivre();

        //Listeners
        Deco.addActionListener(actionEvent -> Deconnexion());
        Exit.addActionListener(actionEvent -> Quitter());
        Donnees.addActionListener(actionEvent -> GDonnee());
        Lecteur.addActionListener(actionEvent -> GLecteur());
        Livre.addActionListener(actionEvent -> GLivre());
        NewPret.addActionListener(actionEvent -> NouveauPret());
        BRechercherLecteur.addActionListener(actionEvent -> RechercherLecteur());

        CBLecteur.addActionListener(actionEvent -> RemplirChamps(1));
        CBNumLecteur.addActionListener(actionEvent -> RemplirChamps(2));

        BRechercheLivre.addActionListener(actionEvent -> RechercherLivre());
        TableLivres.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ClicTableLivre();
            }
        });
        AjoutEmprunt.addActionListener(actionEvent -> AjoutLivreEmprunt());
        RetirerEmprunt.addActionListener(actionEvent -> RetirerLivreEmprunt());
        BRecharger.addActionListener(actionEvent -> Recharger());
        BLPremier.addActionListener(actionEvent -> LPremier());
        BLPrecedent.addActionListener(actionEvent -> LPrecedent());
        BLSuivant.addActionListener(actionEvent -> LSuivant());
        BLDernier.addActionListener(actionEvent -> LDernier());
        BAjouter.addActionListener(actionEvent -> AjouterEmprunt());
        BRecherhceEmprunt.addActionListener(actionEvent -> RechercherEmprunt());
        BRetour.addActionListener(actionEvent -> RetourLivre());
        BFullRetour.addActionListener(actionEvent -> RetourTotalite());
        BEPremier.addActionListener(actionEvent -> EPremier());
        BEPrecedent.addActionListener(actionEvent -> EPrecedent());
        BESuivant.addActionListener(actionEvent -> ESuivant());
        BEDernier.addActionListener(actionEvent -> EDernier());
        BProlonger.addActionListener(actionEvent -> Prolonger());
        BPPremier.addActionListener(actionEvent -> PPremier());
        BPPrecedent.addActionListener(actionEvent -> PPrecedent());
        BPSuivant.addActionListener(actionEvent -> PSuivant());
        BPDernier.addActionListener(actionEvent -> PDernier());
        TablePeriode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ClicTablePeriode();
            }
        });
        BRechercher.addActionListener(actionEvent -> RechercherPeriode());


    }

    private void RemplirChamps(int mode){
        switch (mode){
            case 1 :
                CBNumLecteur.setSelectedIndex(CBLecteur.getSelectedIndex());
                TNomLecteur.setText(ListRechercheLecteur.getElementAt(CBLecteur.getSelectedIndex()).getNomLecteur());
                TPrenomLecteur.setText(ListRechercheLecteur.getElementAt(CBLecteur.getSelectedIndex()).getPrenomLecteur());
                break;
            case 2 :
                CBLecteur.setSelectedIndex(CBNumLecteur.getSelectedIndex());
                TNomLecteur.setText(ListRechercheLecteur.getElementAt(CBNumLecteur.getSelectedIndex()).getNomLecteur());
                TPrenomLecteur.setText(ListRechercheLecteur.getElementAt(CBNumLecteur.getSelectedIndex()).getPrenomLecteur());
                break;
            case 3 :
                TNomLecteur.setText(ListRechercheLecteur.getElementAt(CBNumLecteur.getSelectedIndex()).getNomLecteur());
                TPrenomLecteur.setText(ListRechercheLecteur.getElementAt(CBNumLecteur.getSelectedIndex()).getPrenomLecteur());
                break;
        }
    }

    private void RechercherLecteur(){
        TREmprunt.setText("");
        ListRechercheLecteur = new DefaultListModel<>();
        if (TRechercherLecteur.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Le champ de recherche est vide", "Attention", JOptionPane.WARNING_MESSAGE);
        } else {
            modellecteur = controller.getModel();
            for (int i = 0; i < modellecteur.size(); i++){
                if (modellecteur.getElementAt(i).getNomLecteur().equals(TRechercherLecteur.getText())){
                    ListRechercheLecteur.addElement(modellecteur.elementAt(i));
                }
            }
            if (ListRechercheLecteur.size()>0){
                String[] lecteurs = new String[ListRechercheLecteur.size()];
                String[] num = new String[ListRechercheLecteur.size()];
                for (int i = 0; i < ListRechercheLecteur.size(); i++){
                    lecteurs[i]=ListRechercheLecteur.elementAt(i).getPrenomLecteur();
                    num[i]= String.valueOf(ListRechercheLecteur.elementAt(i).getIdLecteur());
                }
                CBLecteur.setModel(new DefaultComboBoxModel(lecteurs));
                CBNumLecteur.setModel(new DefaultComboBoxModel(num));
                Nettoyerchamps(); //méthode qui remet tous les champs du formulaire à leur valeur par défaut
            } else {
                JOptionPane.showMessageDialog(null, "Il n'y a pas de lecteur(s) portant ce nom", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void RechercherLecteur(int p){
        TREmprunt.setText("");
        String[] lecteurs = new String[ListRechercheLecteur.size()];
        CBLecteur.setModel(new DefaultComboBoxModel(lecteurs));
        modellecteur = controller.getModel();
        ListRechercheLecteur = new DefaultListModel<>();
        for (int i = 0; i < modellecteur.size(); i++){
            if (modellecteur.getElementAt(i).getIdLecteur()==p){
                ListRechercheLecteur.addElement(modellecteur.elementAt(i));
            }
        }
        String[] num = new String[ListRechercheLecteur.size()];
        for (int i = 0; i < ListRechercheLecteur.size(); i++){
            num[i]= String.valueOf(ListRechercheLecteur.elementAt(i).getIdLecteur());
        }
        CBNumLecteur.setModel(new DefaultComboBoxModel(num));
    }

    private void Nettoyerchamps(){
        TNomLecteur.setText("");
        TPrenomLecteur.setText("");
        TidEmprunt.setText("");
    }

    private void RechercherLivre(){
        TREmprunt.setText("");
        if (TRechercheLivre.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Le champ de recherche est vide", "Attention", JOptionPane.WARNING_MESSAGE);
        } else {
            NettoyerTableLivre(); //cette méthode vide la table de tous les livre de son contenu
            TLivreModel = new DefaultTableModel();
            TLivreModel.addColumn("Identifiant");
            TLivreModel.addColumn("Titre");
            TableLivres.setModel(TLivreModel);
            modellivre = controller.getModelLivre();
            String[] choix = {"titre", "numéro unique", "Annuler"};
            int rang = JOptionPane.showOptionDialog(null,
                    "Sur quel champ effectuer la recherche?",
                    "Information!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choix,
                    choix[2]);
            if (choix[rang].equals("titre")) {
                for (int i = 0; i < modellivre.size(); i++) {
                    if (modellivre.getElementAt(i).getTitreLivre().contains(TRechercheLivre.getText().toLowerCase())) {
                        Object[] L = {modellivre.getElementAt(i).getIdLivre(), modellivre.getElementAt(i).getTitreLivre()};
                        TLivreModel.addRow(L);
                    }
                }
                if (TLivreModel.getRowCount() < 1) {
                    JOptionPane.showMessageDialog(null, "Il n'y a pas de livre portant ce titre", "Attention", JOptionPane.WARNING_MESSAGE);
                }
            }
            if (choix[rang].equals("numéro unique")) {
                for (int i = 0; i < modellivre.size(); i++) {
                    if (Integer.valueOf(TRechercheLivre.getText())==modellivre.getElementAt(i).getIdLivre()) {
                        Object[] L = {modellivre.getElementAt(i).getIdLivre(), modellivre.getElementAt(i).getTitreLivre()};
                        TLivreModel.addRow(L);
                    }
                }
                if (TLivreModel.getRowCount() < 1) {
                    JOptionPane.showMessageDialog(null, "Il n'y a pas de livre portant ce numéro unique", "Attention", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    private void ClicTableLivre(){
        if (TableLivres.getRowCount()>=1){
            posLivre =TableLivres.getSelectedRow();
        }
    }

    private void ClicTablePeriode(){
        if (TablePeriode.getRowCount()>=1){
            posPeriode = TablePeriode.getSelectedRow();
            TNbrSortieLivre.setText(String.valueOf(NbrSortie(TablePeriode.getValueAt(posPeriode, 0).toString())));
        }
    }

    private int NbrSortie(String titre){
        int count=0;
        System.out.println(listPeriode.size());
        for (int i = 0; i<listPeriode.size(); i++){
            if (listPeriode.getElementAt(i).getTitreLivre().equals(titre)){
                count++;
            }
        }
        return count;
    }

    private void LPremier(){
        posLivre =0;
        TableLivres.setRowSelectionInterval(posLivre, posLivre);
    }

    private void LPrecedent() {
        if (posLivre ==0){
            posLivre =TableLivres.getRowCount()-1;
        } else {
            posLivre = posLivre -1;
        }
        TableLivres.setRowSelectionInterval(posLivre, posLivre);
    }

    private void LSuivant(){
        if (posLivre ==TableLivres.getRowCount()-1){
            posLivre =0;
        } else {
            posLivre = posLivre +1;
        }
        TableLivres.setRowSelectionInterval(posLivre, posLivre);
    }

    private void LDernier(){
        posLivre =TableLivres.getRowCount()-1;
        TableLivres.setRowSelectionInterval(posLivre, posLivre);
    }

    private void EPremier(){
        posEmprunt = 0;
        TableEmprunts.setRowSelectionInterval(posEmprunt, posEmprunt);
    }

    private void EPrecedent(){
        if (posEmprunt == 0){
            posEmprunt = TableEmprunts.getRowCount()-1;
        } else {
            posEmprunt = posEmprunt-1;
        }
        TableEmprunts.setRowSelectionInterval(posEmprunt, posEmprunt);
    }

    private void ESuivant(){
        if (posEmprunt == TableEmprunts.getRowCount()-1){
            posEmprunt = 0;
        } else {
            posEmprunt = posEmprunt+1;
        }
        TableEmprunts.setRowSelectionInterval(posEmprunt, posEmprunt);
    }

    private void EDernier(){
        posEmprunt = TableEmprunts.getRowCount()-1;
        TableEmprunts.setRowSelectionInterval(posEmprunt, posEmprunt);
    }

    private void PPremier(){
        posPeriode = 0;
        TablePeriode.setRowSelectionInterval(posPeriode, posPeriode);
    }

    private void PPrecedent(){
        if (posPeriode == 0){
            posPeriode = TablePeriode.getRowCount()-1;
        } else {
            posPeriode = posPeriode-1;
        }
        TablePeriode.setRowSelectionInterval(posPeriode, posPeriode);
    }

    private void PSuivant(){
        if (posPeriode == TablePeriode.getRowCount()-1){
            posPeriode = 0;
        } else {
            posPeriode = posPeriode+1;
        }
        TablePeriode.setRowSelectionInterval(posPeriode, posPeriode);
    }

    private void PDernier(){
        posPeriode = TablePeriode.getRowCount()-1;
        TablePeriode.setRowSelectionInterval(posPeriode, posPeriode);
    }

    private void AjoutLivreEmprunt(){
        int exist =0;
        if (modellivre.getElementAt(TableLivres.getSelectedRow()).getStatutLivre()==0){
            for (int i = 0; i<listlivreemprunt.size(); i++){
                if (modellivre.getElementAt(TableLivres.getSelectedRow()).getIdLivre()==listlivreemprunt.getElementAt(i).getIdLivre()){
                    exist = 1;
                }
            }
            if (exist != 1){
                listlivreemprunt.addElement(modellivre.getElementAt(TableLivres.getSelectedRow()));
                RemplirTableEmprunt();
            } else {
                JOptionPane.showMessageDialog(null, "Ce livre est déjà dans la liste d'emprunt", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ce livre n'est pas disponible", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void RetirerLivreEmprunt(){
        listlivreemprunt.remove(TableEmprunts.getSelectedRow());
        RemplirTableEmprunt();
    }

    private void Recharger(){
        modellivre=controller.getModelLivre();
        RemplirTableLivre();
    }

    private void RemplirTableEmprunt(){
        TELModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TELModel.addColumn("Titre");
        TableEmprunts.setModel(TELModel);
        for (int i = 0; i<listlivreemprunt.size(); i++){
            Object [] livre={listlivreemprunt.getElementAt(i).getTitreLivre()};
            TELModel.addRow(livre);
        }
    }

    private void RemplirTableLivre(){
        TLivreModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TLivreModel.addColumn("Titre");
        TLivreModel.addColumn("Identifiant");
        TableLivres.setModel(TLivreModel);
        for (int i = 0; i<modellivre.size(); i++){
            Object [] livre={modellivre.getElementAt(i).getTitreLivre(), modellivre.getElementAt(i).getIdLivre()};
            TLivreModel.addRow(livre);
        }
    }

    private void NettoyerTableLivre(){
        for (int i=0; i<TableLivres.getRowCount()-1; i++){
            TLivreModel.removeRow(i);
        }
    }

    private void CalculDateRetour(){
        if (date.now().plusDays(14).getDayOfWeek().equals("SUNDAY")) {
            TDateRetour.setText(String.valueOf(date.now().plusDays(15).format(formatter)));
        } else {
            TDateRetour.setText(String.valueOf(date.now().plusDays(14).format(formatter)));
        }
    }

    private void AjouterEmprunt(){
        if (TNomLecteur.getText().isEmpty() || listlivreemprunt.size()<1){
            JOptionPane.showMessageDialog(null, "Certains champs ne sont pas complétés", "Attention", JOptionPane.WARNING_MESSAGE);
        } else {
            if(ListRechercheLecteur.getElementAt(CBNumLecteur.getSelectedIndex()).getDELecteur().isBefore((LocalDate.parse(TDateRetour.getText(), formatter)))){
                JOptionPane.showMessageDialog(null, "L'échéance d'abonnement de ce lecteur se situe avant la date de retour du prêt", "Attention", JOptionPane.WARNING_MESSAGE);
            } else {
                controller.doSave(getData());
                Nettoyerchamps(); //méthode qui remet tous les champs du fromulaire à leur valeur par défaut
                for (int i =0; i<TableEmprunts.getRowCount()-1; i++){
                    TELModel.removeRow(i);
                }
                JOptionPane.showMessageDialog(null, "Vous avez ajouté un emprunt", "Information", JOptionPane.INFORMATION_MESSAGE);
                modelEmprunt = controller.getModelEmprunt();
            }
        }
    }

    private void RechercherEmprunt(){
        if (TREmprunt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez entrer un numéro d'emprunt", "Attention", JOptionPane.WARNING_MESSAGE);
        } else {
            Nettoyerchamps(); //méthode qui remet tous les champs du formulaire à leur valeur par défaut
            listlivreemprunt = controller.doFindLivre(4, TREmprunt.getText());
            TELModel = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            TELModel.addColumn("Titre");
            TableEmprunts.setModel(TELModel);
            if (listlivreemprunt.size()>0){
                for (int i = 0; i<modelEmprunt.size(); i++){
                    if (TREmprunt.getText().equals(String.valueOf(modelEmprunt.getElementAt(i).getIdEmprunt()))){
                        for (int j=0; j<listlivreemprunt.size(); j++){
                            int retour = controller.doFindReturn(TREmprunt.getText(), String.valueOf(listlivreemprunt.getElementAt(j).getIdLivre()));
                            if (retour==0){
                                Object [] livre={listlivreemprunt.getElementAt(j).getTitreLivre()};
                                TELModel.addRow(livre);
                            }
                        }
                        RechercherLecteur(modelEmprunt.elementAt(i).getLecteur()); //méthode utilisée pour rechercher un lecteur via son identifiant unique
                        RemplirChamps(3); //méthode qui permet d’afficher les informations du lecteurs dans les champs le concernant
                        TDateEmprunt.setText(modelEmprunt.getElementAt(i).getDateEmprunt().format(formatter));
                        TDateRetour.setText(modelEmprunt.getElementAt(i).getDateRetour().format(formatter));
                        TidEmprunt.setText(String.valueOf(modelEmprunt.getElementAt(i).getIdEmprunt()));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ce numéro d'emprunt n'existe pas", "Attention", JOptionPane.WARNING_MESSAGE);
            }
            RemplirTableLivre();
        }
    }

    private void RetourLivre(){
        controller.doReturnBook(TidEmprunt.getText(), String.valueOf(listlivreemprunt.elementAt(TableEmprunts.getSelectedRow()).getIdLivre()));
        listlivreemprunt.removeElementAt(TableEmprunts.getSelectedRow());
        modelEmprunt = controller.getModelEmprunt();
        RemplirTableEmprunt(); //méthode qui remplit la table des livre emprunté avec les éléments se trouvant dans listlivreemprunt
    }

    private void RetourTotalite(){
        for (int i = 0; i < listlivreemprunt.size(); i++){
            controller.doReturnBook(TREmprunt.getText(), String.valueOf(listlivreemprunt.getElementAt(i).getIdLivre()));
        }
        listlivreemprunt.removeAllElements();
        modelEmprunt = controller.getModelEmprunt();
        RemplirTableEmprunt(); //méthode qui remplit la table des livre emprunté avec les éléments se trouvant dans listlivreemprunt
    }

    private void NouveauPret(){
        Nettoyerchamps();
        String[] nom = new String[0];
        CBLecteur.setModel(new DefaultComboBoxModel (nom));
        String[] num = new String[0];
        CBNumLecteur.setModel(new DefaultComboBoxModel(num));
        TREmprunt.setText("");
        Recharger();
        if (TELModel.getRowCount()>0){
            for (int i = 0; i<listlivreemprunt.size(); i++){
                TELModel.removeRow(i);
            }
            listlivreemprunt.removeAllElements();
        }
        TDateEmprunt.setText(String.valueOf(date.now().format(formatter)));
        CalculDateRetour();
    }

    private void Prolonger(){
        if (LocalDate.parse(TDateRetour.getText(), formatter)
                .plusDays(14).getDayOfWeek().equals("SUNDAY")){
            TDateRetour.setText(String.valueOf(LocalDate.parse(TDateRetour.getText(), formatter).plusDays(15).format(formatter)));
        } else {
            TDateRetour.setText(String.valueOf(LocalDate.parse(TDateRetour.getText(), formatter).plusDays(14).format(formatter)));
        }
        controller.doUpdate(getData());
        modelEmprunt = controller.getModelEmprunt();
        JOptionPane.showMessageDialog(null, "L'emprunt à été prolongé",
                "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void RechercherPeriode(){
        try {
            String debut = LocalDate.parse(TDebutPeriode.getText(), formatter).toString();
            String fin = LocalDate.parse(TFinPeriode.getText(), formatter).toString();
            listPeriode = controller.doFindLivrePeriode(debut, fin);
            TPeriodeModel = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            TPeriodeModel.addColumn("Titre");
            TablePeriode.setModel(TPeriodeModel);
            for (int i = 0; i<listPeriode.size(); i++){
                if (i>0){
                    if (listPeriode.getElementAt(i).getIdLivre()!=listPeriode.getElementAt(i-1).getIdLivre()){
                        Object[] livre={listPeriode.getElementAt(i).getTitreLivre()};
                        TPeriodeModel.addRow(livre);
                    }
                } else {
                    Object[] livre={listPeriode.getElementAt(i).getTitreLivre()};
                    TPeriodeModel.addRow(livre);
                }
            }
            TNbrPret.setText(String.valueOf(controller.doCountLoan(debut, fin)));
            TTotalLivre.setText(String.valueOf(listPeriode.size()));
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Une date n'est pas valide", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }

    private Emprunt getData(){
        Emprunt emprunt = new Emprunt();
        List<Livre> livres = new ArrayList<>();
        if (!TidEmprunt.getText().isEmpty()){
            emprunt.setIdEmprunt(Integer.valueOf(TidEmprunt.getText()));
        }
        emprunt.setLecteur(Integer.parseInt(CBNumLecteur.getSelectedItem().toString()));
        emprunt.setDateEmprunt(LocalDate.parse(TDateEmprunt.getText(), formatter));
        emprunt.setDateRetour(LocalDate.parse(TDateRetour.getText(), formatter));
        for (int i = 0; i < listlivreemprunt.size(); i++){
            livres.add(listlivreemprunt.elementAt(i));
        }
        emprunt.setLivres(livres);
        return emprunt;
    }

    //se déconnecter et revenir à Fdépart
    private void Deconnexion() {
        FGestionPret frame = FGestionPret.this;
        frame.close();
        Fdepart start = new Fdepart();
        start.setVisible(true);
    }

    //Quitter le programme
    private void Quitter(){
        System.exit(0);
    }

    //ouvrir les Donnees significative
    private void GDonnee(){
        FGestionDonnees frameDonne = new FGestionDonnees();
        frameDonne.setVisible(true);
        frameDonne.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void GLecteur() {
        FGestionLecteurs frameLecteur = new FGestionLecteurs();
        frameLecteur.setVisible(true);
        frameLecteur.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void GLivre() {
        FGestionLivres frameLivre = new FGestionLivres();
        frameLivre.setVisible(true);
        frameLivre.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
