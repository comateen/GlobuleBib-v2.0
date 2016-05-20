package view;

import controller.Controller;
import model.loan.Emprunt;
import model.reader.Lecteur;
import model.reader.Localite;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Created by jof on 03/04/2016.
 */
class FGestionLecteurs extends AppFrame {
    private DefaultTableModel TlecteurModel;
    private final Controller controller = new Controller();
    private DefaultListModel<Lecteur> model;
    private DefaultListModel<Localite> modellocalite;
    private DefaultListModel<Emprunt> modelemprunt = new DefaultListModel<>();
    //private JList<Lecteur> listLecteur;
    private JPanel Container;
    private JPanel PanelInfo;
    private JLabel LNomLecteur;
    private JTextField TNomLecteur;
    private JLabel LPrenomLecteur;
    private JTextField TPrenomLecteur;
    private JLabel LDNLecteur;
    private JLabel LAdresse;
    private JFormattedTextField TDNLecteur;
    private JLabel LCP;
    private JTextField TCP;
    private JComboBox CBLocalite;
    private JLabel LNumTel;
    private JTextField TNumTel;
    private JLabel LMail;
    private JTextField TMail;
    private JButton BInscription;
    private JPanel PanelBGestion;
    private JButton BAjouter;
    private JButton BModifier;
    private JButton BSupprimer;
    private JPanel PanelRecherche;
    private JButton BRechercher;
    private JTextField TRechercher;
    private JButton BPremier;
    private JButton BPrecedent;
    private JButton BSuivant;
    private JButton BDernier;
    private JLabel LCategorie;
    private JTextField TCategorie;
    private JTable TableLecteurs;
    private JFormattedTextField TDILecteur;
    private JFormattedTextField TDRLecteur;
    private JTextField TDELecteur;
    private JTextField TAdresseLecteur;
    private JPanel PanelBDeplacement;
    private JTextField TidLecteur;
    private JButton BRenouvellement;
    private JPanel PannelGeneral;
    private int pos=0;
    private boolean check = true;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private LocalDate date;

    @Override
    JPanel getContainer() {
        return Container; // pour forcer la création d'un container
    }

    protected FGestionLecteurs(){
        super("GlobuleBib - Gestion des lecteurs", null);
        init();
        this.setLocation(150,150);
        JMenuBar MenuBar = new JMenuBar();
        this.setJMenuBar(MenuBar);
        JMenu fichier = new JMenu("Fichier");
        MenuBar.add(fichier);
        JMenuItem nouveau = new JMenuItem("Nouveau");
        fichier.add(nouveau);
        JMenuItem Deco = new JMenuItem("Se déconnecter");
        fichier.add(Deco);
        JMenuItem Exit = new JMenuItem("Quitter");
        fichier.add(Exit);
        model = controller.getModel();
        RemplirTableLecteur();
        modellocalite = controller.getModelLoc();
        RemplirCBLoc();
        try {
            MaskFormatter mf = new MaskFormatter("##-##-####");
            DefaultFormatterFactory form = new DefaultFormatterFactory(mf);
            TDNLecteur.setFormatterFactory(form);
            TDILecteur.setFormatterFactory(form);
            TDRLecteur.setFormatterFactory(form);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Les différents listener nécessaire
        nouveau.addActionListener(actionEvent -> Nettoyerchamps());
        Deco.addActionListener(actionEvent -> Deconnexion());
        Exit.addActionListener(actionEvent -> Quitter());
        BInscription.addActionListener(actionEvent -> CalculDateInsription());
        BRenouvellement.addActionListener(actionEvent -> CalculDateRenouvellement());
        TableLecteurs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ClicTable();
            }
        });
        BPremier.addActionListener(actionEvent -> Premier());
        BPrecedent.addActionListener(actionEvent -> Precedent());
        BSuivant.addActionListener(actionEvent -> Suivant());
        BDernier.addActionListener(actionEvent -> Dernier());
        BRechercher.addActionListener(actionEvent -> RechercherLecteur());
        BAjouter.addActionListener(actionEvent -> AjoutLecteur());
        BModifier.addActionListener(actionevent -> ModifierLecteur());
        BSupprimer.addActionListener(actionEvent -> SupprimerLecteur());
        CBLocalite.addActionListener(e -> TCP.setText(modellocalite.getElementAt(CBLocalite.getSelectedIndex()).getCodePostal()));
        CBLocalite.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                modellocalite = controller.getModelLoc();
                RemplirCBLoc();
            }
        });
    }

    //Remplir le jtable
    private void RemplirTableLecteur() {
        TlecteurModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TlecteurModel.addColumn("Nom");
        TlecteurModel.addColumn("Prenom");
        TableLecteurs.setModel(TlecteurModel);
        for (int i =0; i < model.size(); i++) {
            Object [] L={model.getElementAt(i).getNomLecteur(), model.getElementAt(i).getPrenomLecteur()};
            //System.out.println(model.getElementAt(i).getNomLecteur());
            TlecteurModel.addRow(L);
        }
    }

    private void CalculDateInsription(){
        TDILecteur.setText(date.now().format(formatter));
        TDRLecteur.setText(date.now().format(formatter));
        TDELecteur.setText(date.now().plusYears(1).format(formatter));
    }

    private void CalculDateRenouvellement(){
        TDRLecteur.setText(date.now().format(formatter));
        TDELecteur.setText(date.now().plusYears(1).format(formatter));
    }

    private void AjoutLecteur(){
        int exist =0;
        CheckChamps();
        if (check){
            JOptionPane.showMessageDialog(null, "Certains champs ne sont pas complété", "Attention", JOptionPane.WARNING_MESSAGE);
        } else {
            if (TCategorie.getText().toLowerCase().equals("adulte") || TCategorie.getText().toLowerCase().equals("jeunesse")){
                for (int i=0; i<model.size(); i++){
                    if (TNomLecteur.getText().equals(model.getElementAt(i).getNomLecteur()) &&
                            TPrenomLecteur.getText().equals(model.getElementAt(i).getPrenomLecteur()) &&
                            TDNLecteur.getText().equals(model.getElementAt(i).getDNLecteur().toString())){
                        JOptionPane.showMessageDialog(null, "Ce lecteur existe déjà", "Attention", JOptionPane.WARNING_MESSAGE);
                        exist =1;
                    }
                }
                if (exist !=1) {
                    TidLecteur.setText("");
                    try {
                        controller.doSave(getData());
                        model = controller.getModel();
                        RemplirTableLecteur();
                        pos=model.getSize()-1;
                        JOptionPane.showMessageDialog(null, "Vous avez ajouté un lecteur", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e){
                        JOptionPane.showMessageDialog(null, "Une date n'est pas valide", "Attention", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "La catégorie doit être adulte ou jeunesse", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        }
        Nettoyerchamps();
    }

    private void ModifierLecteur(){
        if (TCategorie.getText().toLowerCase().equals("adulte") || TCategorie.getText().toLowerCase().equals("jeunesse")){
            int option = JOptionPane.showConfirmDialog(null, "Vous allez modifier un lecteur", "Attention", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    controller.doUpdate(getData());
                    model = controller.getModel();
                    RemplirTableLecteur();
                    JOptionPane.showMessageDialog(null, "Vous avez modifié un lecteur", "Information", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Une date n'est pas valide", "Attention", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La catégorie doit être adulte ou jeunesse", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void SupprimerLecteur(){
        modelemprunt = controller.getModelEmprunt();
        int exist = 0;
        for (int i = 0; i <modelemprunt.size(); i++){
            if (Integer.valueOf(TidLecteur.getText())==model.getElementAt(i).getIdLecteur()){
                exist = 1;
            }
        }
        if (exist !=1){
            try {
                controller.doDelete(getData());
                model = controller.getModel();
                Nettoyerchamps();
                RemplirTableLecteur();
                JOptionPane.showMessageDialog(null, "Vous avez supprimé un lecteur", "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Une date n'est pas valide", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ce lecteur a encore un emprunt en cours, il ne peut être supprimé", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }

    //Allez au premier de la liste
    private void Premier() {
        pos=0;
        RemplissageChamps(pos);
    }

    //Allez au précédent dans la liste
    private void Precedent(){
        if (pos==0){
            pos=TableLecteurs.getRowCount()-1;
        } else {
            pos=pos-1;
        }
        RemplissageChamps(pos);
    }

    //Allez au suivant la liste
    private void Suivant () {
        if (pos==TableLecteurs.getRowCount()-1){
            pos=0;
        } else {
            pos=pos+1;
        }
        RemplissageChamps(pos);
    }

    //Allez au dernier de la liste
    private void Dernier(){
        pos=TableLecteurs.getRowCount()-1;
        RemplissageChamps(pos);
    }

    //Selectionné dans la liste par clic
    private void ClicTable(){
        if (TableLecteurs.getSelectedRowCount()==1){
            pos=TableLecteurs.getSelectedRow();
            RemplissageChamps(pos);
        }
    }

    private void RemplissageChamps(int P){
        TidLecteur.setText(String.valueOf(model.getElementAt(P).getIdLecteur()));
        TNomLecteur.setText(model.getElementAt(P).getNomLecteur());
        TPrenomLecteur.setText(model.getElementAt(P).getPrenomLecteur());
        TDNLecteur.setText(String.valueOf(model.getElementAt(P).getDNLecteur().format(formatter)));
        TAdresseLecteur.setText(model.getElementAt(P).getAdresseLecteur());
        for (int i=0; i<modellocalite.size(); i++){
            if (modellocalite.getElementAt(i).getIDLocalite()==model.getElementAt(P).getLoc()){
                CBLocalite.setSelectedIndex(i);
                TCP.setText(modellocalite.getElementAt(i).getCodePostal());
            }
        }
        TNumTel.setText(model.getElementAt(P).getTelLecteur());
        TMail.setText(model.getElementAt(P).getMailLecteur());
        TDILecteur.setText(String.valueOf(model.getElementAt(P).getDILecteur().format(formatter)));
        TDRLecteur.setText(String.valueOf(model.getElementAt(P).getDRLecteur().format(formatter)));
        TDELecteur.setText(String.valueOf(model.getElementAt(P).getDELecteur().format(formatter)));
        TCategorie.setText(model.getElementAt(P).getCategorieLecteur());
        TableLecteurs.setRowSelectionInterval(P, P);
    }

    private void RechercherLecteur(){
        Nettoyerchamps();
        NettoyerTable();
        TlecteurModel = new DefaultTableModel();
        TlecteurModel.addColumn("Nom");
        TlecteurModel.addColumn("Prénom");
        TableLecteurs.setModel(TlecteurModel);
        for (int i = 0; i < model.size(); i++){
            if (TRechercher.getText().toLowerCase().equals(model.getElementAt(i).getNomLecteur())) {
                Object [] L={model.getElementAt(i).getNomLecteur(), model.getElementAt(i).getPrenomLecteur()};
                TlecteurModel.addRow(L);
            }
        }
        if (TlecteurModel.getRowCount()<1){
            JOptionPane.showMessageDialog(null, "Il n'y a pas de lecteur portant ce nom", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void NettoyerTable(){
        for( int i = TableLecteurs.getRowCount() - 1; i >= 0; i-- ) {
            TlecteurModel.removeRow(i);
        }
    }

    private void Nettoyerchamps(){
        TidLecteur.setText("");
        TNomLecteur.setText("");
        TPrenomLecteur.setText("");
        TDNLecteur.setText("");
        TAdresseLecteur.setText("");
        TCP.setText("");
        //CB
        TNumTel.setText("");
        TMail.setText("");
        TDILecteur.setText("");
        TDRLecteur.setText("");
        TDELecteur.setText("");
        TCategorie.setText("");
    }

    private void RemplirCBLoc(){
        String []Listloc = new String[modellocalite.size()];
        for (int i = 0; i < modellocalite.size(); i++){
            Listloc[i] = modellocalite.getElementAt(i).getNomVille();
        }
        CBLocalite.setModel(new DefaultComboBoxModel(Listloc));
    }

    private Lecteur getData(){
        Lecteur lecteur = new Lecteur();
        if (!TidLecteur.getText().isEmpty()) {lecteur.setIdLecteur(Integer.valueOf(TidLecteur.getText()));}
        lecteur.setNomLecteur(TNomLecteur.getText().toLowerCase());
        lecteur.setPrenomLecteur((TPrenomLecteur.getText().toLowerCase()));
        lecteur.setDNLecteur(LocalDate.parse(TDNLecteur.getText(), formatter));
        lecteur.setAdresseLecteur(TAdresseLecteur.getText().toLowerCase());
        lecteur.setLoc(modellocalite.getElementAt(CBLocalite.getSelectedIndex()).getIDLocalite());
        lecteur.setTelLecteur(TNumTel.getText());
        lecteur.setMailLecteur(TMail.getText().toLowerCase());
        lecteur.setDILecteur(LocalDate.parse(TDILecteur.getText(), formatter));
        lecteur.setDRLecteur(LocalDate.parse(TDRLecteur.getText(), formatter));
        lecteur.setDELecteur(LocalDate.parse(TDELecteur.getText(), formatter));
        lecteur.setCategorieLecteur(TCategorie.getText().toLowerCase());
        return lecteur;
    }

    private void CheckChamps(){
        if (!TNomLecteur.getText().isEmpty() &&
                !TPrenomLecteur.getText().isEmpty() &&
                !TDNLecteur.getText().isEmpty() &&
                !TAdresseLecteur.getText().isEmpty() &&
                !TNumTel.getText().isEmpty() &&
                !TMail.getText().isEmpty() &&
                !TDILecteur.getText().isEmpty() &&
                !TCategorie.getText().isEmpty()){
            check = false;
        } else {
            check = true;
        }
    }

    //Listeners

    //se déconnecter et revenir à Fdépart
    private void Deconnexion(){
        FGestionLecteurs frame = FGestionLecteurs.this;
        frame.close();
        Fdepart start = new Fdepart();
        start.setVisible(true);
    }

    //Quitter le programme
    private void Quitter() {
        System.exit(0);
    }
}
