package part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Server extends javax.swing.JFrame {
    private JFrame registrationFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitButton;
    public Server() {
        initComponents();
        textPaneArea.setEditable(false);
        initRegistrationForm();
    }
    private void initRegistrationForm() {
        // Create registration form components
        registrationFrame = new JFrame("Registration Form");
        registrationFrame.setLayout(new FlowLayout());
        registrationFrame.setSize(300, 150);
        registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        submitButton = new JButton("Register");

        // Add components to registration form
        registrationFrame.add(usernameLabel);
        registrationFrame.getContentPane().setBackground(getContentPane().getBackground());        submitButton.setBackground(new java.awt.Color(153, 206, 235));
        registrationFrame.add(usernameField);
        registrationFrame.add(passwordLabel);
        registrationFrame.add(passwordField);
        registrationFrame.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                writeToFile(username, password);
            }
        });

        registrationFrame.setVisible(true);
    }

    private void writeToFile(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt", true))) {
            writer.write (username +","+ password);
            writer.newLine();
            JOptionPane.showMessageDialog(registrationFrame, "Registration Successful!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(registrationFrame, "Error writing to file!");
            ex.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        start = new javax.swing.JButton();
        tcpPort = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textPaneArea = new javax.swing.JTextPane();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        userList = new javax.swing.JList<>();
        status = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TCP Server");

        jPanel1.setBackground(new java.awt.Color(255, 255, 153));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        start.setBackground(new java.awt.Color(153, 206, 235));
        start.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        start.setText("Start Listening");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });

        tcpPort.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tcpPort.setText("4000");
        tcpPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tcpPortActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Georgia", 0, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Port :");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        textPaneArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(textPaneArea);

        jComboBox1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Loopback Pseudo-Interface 1: 127.0.0.1", "Item 2", "Item 3", "Item 4"}));
        jComboBox1.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(153, 206, 235)));

        userList.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jScrollPane2.setViewportView(userList);

        status.setBackground(new java.awt.Color(255, 255, 255));
        status.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        status.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        status.setText("the State here");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Status :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(tcpPort, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(100, 100, 100)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(48, 48, 48)
                                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                        .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tcpPort, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void tcpPortActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private ServerSocket socket;
    private HashMap<String, Socket> clientsHash = new HashMap<>();
    boolean t = false;

    private void startActionPerformed(java.awt.event.ActionEvent evt) {
        int portNumber = 0;
        boolean isNumbers = false;

        try {
            if (!tcpPort.getText().isEmpty()) {
                portNumber = Integer.parseInt(tcpPort.getText());
                isNumbers = true;
                if (isNumbers) {
                    socket = new ServerSocket(portNumber);
                    StyledDocument doc = textPaneArea.getStyledDocument();
                    Style style = textPaneArea.addStyle("", null);
                    StyleConstants.setForeground(style, Color.BLUE);
                    StyleConstants.setBackground(style, Color.white);
                    String s1 = "Start Listening at port: " + portNumber + "\n";
                    doc.insertString(doc.getLength(), s1, style);
                    new ClientAccept(socket).start();
                    status.setText("Address: " + InetAddress.getByName("localhost").getHostAddress() + ", port: " + portNumber);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a port number in 'port number' field.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter only numbers in 'port number' field.");
            isNumbers = false;
        } catch (IOException | BadLocationException ex) {
            JOptionPane.showMessageDialog(null, "The port number is used");
        }
    }

    private class ClientAccept extends Thread {
        private ServerSocket socket;

        public ClientAccept(ServerSocket socket) {
            this.socket = socket;
        }

        public void run() {
            while (true) {
                try {
                    Socket clientSocket = socket.accept();
                    String username = new DataInputStream(clientSocket.getInputStream()).readUTF();
                    DataOutputStream dataOutOfClient = new DataOutputStream(clientSocket.getOutputStream());
                    if (clientsHash.containsKey(username)) {
                        dataOutOfClient.writeUTF("founded");
                    } else {
                        clientsHash.put(username, clientSocket);
                        addTextToArea(username, true);
                        dataOutOfClient.writeUTF("accept");
                        new endToEndList().start();
                        new ReadMessage(clientSocket, username).start();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ClientAccept.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    class ReadMessage extends Thread {
        Socket s;
        String ID;

        ReadMessage(Socket s, String username) {
            this.s = s;
            this.ID = username;
        }

        public void run() {
            while (!clientsHash.isEmpty() && clientsHash.containsKey(ID)) {
                try {
                    String in = new DataInputStream(s.getInputStream()).readUTF();
                    if (in.contains("logout")) {
                        new DataOutputStream(((Socket) clientsHash.get(ID)).getOutputStream()).writeUTF("logout");
                        clientsHash.remove(ID);
                        addTextToArea(ID, false);
                        new endToEndList().start();
                    }
                } catch (IOException | BadLocationException ex) {
                    clientsHash.remove(ID);
                    try {
                        addTextToArea(ID, false);
                    } catch (BadLocationException ex1) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    new endToEndList().start();
                    ex.printStackTrace();
                }
            }
        }
    }

    private class endToEndList extends Thread {
        DefaultListModel<String> dlm;

        public endToEndList() {
            dlm = new DefaultListModel<>();
            userList.setModel(dlm);
        }

        public void run() {
            try {
                String s = new String();

                Set<String> k = clientsHash.keySet();
                Iterator<String> itr = k.iterator();
                dlm.clear();
                while (itr.hasNext()) {
                    String key = itr.next();
                    s += key + "," + String.valueOf(((Socket) clientsHash.get(key)).getPort()) + ","
                            + ((Socket) clientsHash.get(key)).getInetAddress().getHostAddress() + "&?";
                    String ele = ((Socket) clientsHash.get(key)).getInetAddress().getHostAddress() + ","
                            + String.valueOf(((Socket) clientsHash.get(key)).getPort());
                    dlm.addElement(ele);
                }
                if (s.length() != 0) {
                    s = s.substring(0, s.length() - 2);
                }
                itr = k.iterator();
                while (itr.hasNext()) {
                    String key = itr.next();
                    try {
                        new DataOutputStream(((Socket) clientsHash.get(key)).getOutputStream()).writeUTF("add to list" + s);
                    } catch (IOException ex) {
                        clientsHash.remove(key);
                        addTextToArea(key, false);
                    }
                }
            } catch (BadLocationException ex) {
            }
        }
    }

    public void addTextToArea(String username, boolean color) throws BadLocationException {
        StyledDocument doc = textPaneArea.getStyledDocument();
        Style style = textPaneArea.addStyle("", null);
        if (color) {
            StyleConstants.setForeground(style, Color.BLUE);
            StyleConstants.setBackground(style, Color.white);
            String s1 = username + " login" + "\n";
            doc.insertString(doc.getLength(), s1, style);
        } else {
            StyleConstants.setForeground(style, Color.RED);
            StyleConstants.setBackground(style, Color.white);
            String s1 = username + " logout" + "\n";
            doc.insertString(doc.getLength(), s1, style);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton start;
    private javax.swing.JLabel status;
    private javax.swing.JTextField tcpPort;
    private javax.swing.JTextPane textPaneArea;
    private javax.swing.JList<String> userList;
}
