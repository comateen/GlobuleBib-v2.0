import view.Fdepart;

import javax.swing.*;

/**
 * Created by jof on 30/03/2016.
 */
class Main {
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            Fdepart start = new Fdepart();
            start.setVisible(true);
        });
    }
}
