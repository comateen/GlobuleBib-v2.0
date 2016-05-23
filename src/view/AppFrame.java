package view;
import javax.swing.*;

/**
 * Created by jof on 31/03/2016.
 */
public abstract class AppFrame extends JFrame{

    abstract JPanel getContainer();

    AppFrame(String title, JFrame parent){
        this.setTitle(title);
        this.setLocationRelativeTo(parent);
    }

    public void close(){ this.dispose();}

    void init(){
        this.setContentPane(getContainer()/*new FGestionDonnees()*/);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        //this.setLocation(0,0);
    }

}
