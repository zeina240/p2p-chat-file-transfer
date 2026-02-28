package part2;

import javax.swing.JFrame;

public class allFrames {

    public static void main(String[] args) {

    	part2.Client client1 = new part2.Client();
        client1.setVisible(true);

        part2.Client client2 = new part2.Client();
        client2.setVisible(true);

        part2.Client client3 = new part2.Client();
        client3.setVisible(true);

        Server ServerFrame = new Server();
        ServerFrame.setVisible(true);
    }
}
