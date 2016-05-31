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
        this.setContentPane(getContainer());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
    }

    public boolean checkIfLetters(String phrase) {
        int i = 0;
        while(i < phrase.length()) {
            if(!Character.isDigit(phrase.charAt(i))){
                i++;
            } else {
                return false;
            }
        }
        return true;
    }
}
