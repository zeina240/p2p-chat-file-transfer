
package part1;


import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import NetworkHW2.perTOper;
public class Network {
    public static void main(String[] args) {
        // Create two instances - one for Zeina and one for Dina
        SwingUtilities.invokeLater(() -> {
            perTOper zeinaApp = new perTOper();
            zeinaApp.setTitle("Zeina's Chat");
            zeinaApp.LocalPort.setText("1235");  // Zeina listens on port 1234
            zeinaApp.RemotePort.setText("1234");  // Zeina sends to port 1235
            
            perTOper dinaApp = new perTOper();
            dinaApp.setTitle("Dina's Chat");
            dinaApp.LocalPort.setText("1234");   // Dina listens on port 1235
            dinaApp.RemotePort.setText("12354");  // Dina sends to port 1234
            int windowWidth = 500;
            int windowHeight = 650;
            zeinaApp.setSize(windowWidth, windowHeight);
            dinaApp.setSize(windowWidth, windowHeight);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int xZeina = 50;  // تباعد 50 بكسل من حافة الشاشة اليسرى
            int xDina = xZeina + windowWidth + 20;
            zeinaApp.setLocation(xZeina, 50);
            dinaApp.setLocation(xDina, 50);
            
            zeinaApp.setVisible(true);
            dinaApp.setVisible(true);
        });
    }
}

/*

public class Network {

    public static void main(String[] args) {
        new perTOper().setVisible(true);
    }

}
*/
/*
public class Network {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] options = {"Zeina", "Dina"};
            String selectedUser = (String) JOptionPane.showInputDialog(
                null,
                "Choose your profile:",
                "Login",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (selectedUser != null) {
                new perTOper().setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}
*/