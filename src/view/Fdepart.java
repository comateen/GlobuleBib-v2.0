package view;

import controller.Controller;
import model.user.Utilisateur;

import javax.swing.*;

/**
 * Created by jof on 30/03/2016.
 */

public class Fdepart extends AppFrame{
    private JTextField TUserName;
    private JPasswordField TPassWord;
    private JButton BConnexion;
    private JPanel Container;
    private JLabel LTitre;
    private JLabel LUserName;
    private DefaultListModel<Utilisateur> modeluser;
    private boolean check = false;

    @Override
    JPanel getContainer() {
        return Container; // pour forcer la création d'un container
    }

    public Fdepart() {
        super("GlobuleBib - Accueil", null);
        Controller controller = new Controller();
        init(); //initialise la fenêtre.
        this.setLocation(350,300);
        modeluser = controller.getModelUtilisateur();

        BConnexion.addActionListener(actionEvent -> {
            String login=TUserName.getText();
            String pass=TPassWord.getText();
            DoLogin(login, pass);
        });
    }

    private void DoLogin(String login, String pass){
        boolean loginOK=true;
        String test="";
        CheckChamps(); //méthode utilisée pour vérifier que les tous les champs sont remplis
        if (check){
            JOptionPane.showMessageDialog(null,"Veuillez compléter les champs","Attention",JOptionPane.WARNING_MESSAGE);
        } else {
            for (int i =0; i<modeluser.size();i++){
                if (login.equals(modeluser.getElementAt(i).getLoginUtilisateur())){
                    loginOK=false;
                    test=modeluser.getElementAt(i).getMotDePasse();
                }
            }
            if (!loginOK){
                if (pass.equals(test)){
                    if (login.equals("admin")){
                        FGestionUtilisateurs frameUser = new FGestionUtilisateurs();
                        frameUser.setVisible(true);
                        frameUser.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        this.close();
                    } else {
                        FGestionPret framePret = new FGestionPret();
                        framePret.setVisible(true);
                        framePret.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        this.close();
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"Mauvais mot de passe","Attention", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,"Mauvais nom d'ulisateur","Attention", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void CheckChamps(){
        if (TUserName.getText().isEmpty() || TUserName.getText().isEmpty()){
            check = true;
        } else {
            check = false;
        }
    }
}
