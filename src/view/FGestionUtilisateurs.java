package view;

import controller.Controller;
import model.user.Utilisateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by jof on 30/03/2016.
 */
public class FGestionUtilisateurs extends AppFrame{
    private DefaultTableModel TuserModel;
    private final Controller controller = new Controller();
    private DefaultListModel<Utilisateur> modeluser;
    private JPanel Container;
    private JLabel LTitre;
    private JLabel LNomUser;
    private JTextField TNomUser;
    private JLabel LPrenom;
    private JTextField TPrenomUser;
    private JLabel LLogin;
    private JLabel LPassWord;
    private JTextField TLoginUser;
    private JTextField TMDPUser;
    private JTable jTableUser;
    private JButton BAjouter;
    private JButton BModifier;
    private JButton BSupprimer;
    private JButton BPremier;
    private JButton BPrecedent;
    private JButton BSuivant;
    private JButton BDernier;
    private JTextField TidUtilisateur;
    private int pos=0;
    private boolean check = true;

    @Override
    JPanel getContainer() {
        return Container; // pour forcer la création d'un container
    }

    public FGestionUtilisateurs(){
        super("GlobuleBib - Gestion des utilisateurs", null);
        init();
        //Création de la barre de menu et de ses composants
        JMenuBar MenuBar = new JMenuBar();
        this.setJMenuBar(MenuBar);
        JMenu fichier = new JMenu("Fichier");
        MenuBar.add(fichier);
        JMenuItem Deco = new JMenuItem("Se déconnecter");
        fichier.add(Deco);
        JMenuItem Exit = new JMenuItem("Quitter");
        fichier.add(Exit);
        modeluser = controller.getModelUtilisateur();
        RemplirTableUser(); //appel de la méthode pour remplir la talbe avec les éléments existants
        //Les différents listener nécessaire
        BAjouter.addActionListener(actionEvent -> AjouterUser());
        BModifier.addActionListener(actionEvent -> ModificationUser());
        BSupprimer.addActionListener(actionEvent -> SupprimerUser());
        BPremier.addActionListener(actionEvent -> Premier());
        BPrecedent.addActionListener(actionEvent -> Precedent());
        BSuivant.addActionListener(actionEvent -> Suivant());
        BDernier.addActionListener(actionEvent -> Dernier());
        jTableUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ClicTable();
            }
        });
        Deco.addActionListener(actionEvent -> Deconnexion());
        Exit.addActionListener(actionEvent -> Quitter());
    }

    //Remplir le jtable
    private void RemplirTableUser() {
        TuserModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TuserModel.addColumn("Nom");
        TuserModel.addColumn("Prenom");
        jTableUser.setModel(TuserModel);
        for (int i = 0; i < modeluser.size(); i++) {
            Object [] L={modeluser.getElementAt(i).getNomUtilisateur(), modeluser.getElementAt(i).getPrenomUtilisateur()};
            TuserModel.addRow(L);
        }
    }

    //Ajout d'un utilisateur dans la db
    private void AjouterUser(){
        int nom =0, login=0;
        TidUtilisateur.setText("");
        CheckChamps();
        if (check){
            JOptionPane.showMessageDialog(null, "Certains champs ne sont pas complété", "Attention", JOptionPane.WARNING_MESSAGE);
        } else {
            for (int i=0; i<modeluser.size(); i++){
                if (TNomUser.getText().equals(modeluser.getElementAt(i).getNomUtilisateur()) && TPrenomUser.getText().equals(modeluser.getElementAt(i).getPrenomUtilisateur())){
                    nom =1;
                }
            }
            if (nom != 1) {
                for (int i=0; i<modeluser.size(); i++) {
                    if (TLoginUser.getText().equals(modeluser.getElementAt(i).getLoginUtilisateur())){
                        login=1;
                    }
                }
                if (login!=1){
                    controller.doSave(getData());
                    modeluser = controller.getModelUtilisateur();
                    JOptionPane.showMessageDialog(null, "vous avez ajouté un utilisateur", "Information", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Ce login a déjà utilisé, veuillez en choisir un autre", "Attention", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cette personne a déjà un compte utilisateur", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        }
        RemplirTableUser();
        Nettoyerchamps();
    }

    //Supression d'un utilisateur
    private void SupprimerUser(){
        if (TNomUser.getText().equals("administrateur")){
            JOptionPane.showMessageDialog(null, "Cette utilisateur ne peut pas être suprimmé", "Attention", JOptionPane.WARNING_MESSAGE);
            Nettoyerchamps();
        } else {
            int option = JOptionPane.showConfirmDialog(null, "Vous allez supprimer un utilisateur", "Attention", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                controller.doDelete(getData());
                modeluser = controller.getModelUtilisateur();
                Nettoyerchamps();
                RemplirTableUser();
                pos=0;
                JOptionPane.showMessageDialog(null, "vous avez supprimé un utilisateur", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //Modification d'un utilisateur
    private void ModificationUser(){
        if (TNomUser.getText().equals("administrateur")){
            JOptionPane.showMessageDialog(null, "Cette utilisateur ne peut pas être modifié", "Attention", JOptionPane.WARNING_MESSAGE);
            Nettoyerchamps();
        } else {
            int option = JOptionPane.showConfirmDialog(null, "Vous allez modifier un utilisateur", "Attention", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.OK_OPTION) {

                controller.doUpdate(getData());
                modeluser = controller.getModelUtilisateur();
                RemplirTableUser();
                RemplissageChamps(pos);
                JOptionPane.showMessageDialog(null, "vous avez modifié un utilisateur", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //vériefie les champs
    private void CheckChamps(){
        if (!TNomUser.getText().isEmpty() ||
                !TPrenomUser.getText().isEmpty() ||
                !TLoginUser.getText().isEmpty() ||
                !TMDPUser.getText().isEmpty()) {
            check = false;
        } else {
            check = true;
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
            pos=TuserModel.getRowCount()-1;
        } else {
            pos=pos-1;
        }
        RemplissageChamps(pos);
    }

    //Allez au suivant la liste
    private void Suivant() {
        if (pos==TuserModel.getRowCount()-1){
            pos=0;
        } else {
            pos=pos+1;
        }
        RemplissageChamps(pos);
    }

    //Allez au dernier de la liste
    private void Dernier(){
        pos=TuserModel.getRowCount()-1;
        RemplissageChamps(pos);
    }

    //Selectionné dans la liste par clic
    private void ClicTable(){
        if (jTableUser.getSelectedRowCount()==1){
            pos=jTableUser.getSelectedRow();
            RemplissageChamps(pos);
        }
    }

    //Nettoyer les champs
    private void Nettoyerchamps(){
        TidUtilisateur.setText("");
        TNomUser.setText("");
        TPrenomUser.setText("");
        TLoginUser.setText("");
        TMDPUser.setText("");
    }

    //Remplir les champs nom, prenom,...
    private void RemplissageChamps(int P) {
        TidUtilisateur.setText(String.valueOf(modeluser.getElementAt(P).getIDUtilisateur()));
        TNomUser.setText(modeluser.getElementAt(P).getNomUtilisateur());
        TPrenomUser.setText((modeluser.getElementAt(P).getPrenomUtilisateur()));
        TLoginUser.setText(modeluser.getElementAt(P).getLoginUtilisateur());
        TMDPUser.setText((modeluser.getElementAt(P).getMotDePasse()));
        jTableUser.setRowSelectionInterval(pos, pos);
    }

    private Utilisateur getData(){
        Utilisateur user= new Utilisateur();
        if (!TidUtilisateur.getText().isEmpty()) {user.setIDUtilisateur(Integer.valueOf(TidUtilisateur.getText()));}
        user.setNomUtilisateur(TNomUser.getText().toLowerCase());
        user.setPrenomUtilisateur(TPrenomUser.getText().toLowerCase());
        user.setLoginUtilisateur(TLoginUser.getText().toLowerCase());
        user.setMotDePasse(TMDPUser.getText().toLowerCase());
        return user;
    }

    //se déconnecter et revenir à Fdépart
    private void Deconnexion(){
        FGestionUtilisateurs frame = FGestionUtilisateurs.this;
        frame.close();
        Fdepart start = new Fdepart();
        start.setVisible(true);
    }

    //Quitter le programme
    private void Quitter(){
        System.exit(0);
    }
}
