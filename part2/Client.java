

package part2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Client extends javax.swing.JFrame {

    DatagramSocket socket;
    String userName;
    String localIp;
    int localPort;
    String remotIp;
    int remotPort;
    InetAddress remot_IPAddress;
    byte[] S_buffer;
    DatagramPacket sendpacket;
    byte[] R_buffer;
    DatagramPacket receive_packet;
    boolean conn = false;
    boolean logedin = false;
    DefaultListModel dlm;
    DataInputStream dataFromServer;
    DataInputStream DataInputStream;
    DataOutputStream dataToServer;
    Socket serverSocket;
    Read r;
    receive channel;
    boolean t = false;
    boolean j = false;
    private HashMap<String, Color> userColorMap = new HashMap<>();

    public Client() {
        initComponents();
        Remot_IP1.setVisible(false);
        Remot_Port1.setVisible(false);
        textPaneArea.setEditable(false);
        Remot_IP.setEditable(false);
        Remot_Port.setEditable(false);
        inArea.setForeground(Color.GRAY);
        inArea.setText("enter text here");
        userName = "";
        localIp = "";
        localPort = 0;
        remotIp = "";
        remotPort = 0;
        R_buffer = new byte[50];
        receive_packet = new DatagramPacket(R_buffer, R_buffer.length);

        addIdleStatusListener();
    }

    private void addIdleStatusListener() {
        ActionListener idleStatusActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status.setText("Away");
            }
        };

        Timer idleTimer = new Timer(30000, idleStatusActionListener); // 30 seconds
        idleTimer.setRepeats(false);

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                resetIdleTimer();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                resetIdleTimer();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                resetIdleTimer();
            }

            private void resetIdleTimer() {
                status.setText("Active");
                idleTimer.restart();
            }
        };

        inArea.addKeyListener(keyListener);
        textPaneArea.addKeyListener(keyListener);
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        login = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        inArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        serIp = new javax.swing.JTextField();
        serPort = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Local_IP = new javax.swing.JTextField();
        Local_Port = new javax.swing.JTextField();
        Remot_IP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Remot_Port = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        online_user = new javax.swing.JList<>();
        jLabel9 = new javax.swing.JLabel();
        send = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        status = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        textPaneArea = new javax.swing.JTextPane();
        sendall = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        pass = new javax.swing.JTextField();
        Remot_IP1 = new javax.swing.JTextField();
        Remot_Port1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        // New buttons
        sendFileBtn = new javax.swing.JButton();
        deleteMsgBtn = new javax.swing.JButton();
        deleteConvBtn = new javax.swing.JButton();
        receiveFileBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1015, 523));
        setMinimumSize(new java.awt.Dimension(1015, 523));
        setPreferredSize(new java.awt.Dimension(1015, 523));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 153));
        jPanel1.setMaximumSize(new java.awt.Dimension(992, 479));
        jPanel1.setMinimumSize(new java.awt.Dimension(992, 479));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Username :");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(6, 7, 90, 27);

        username.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        username.setToolTipText("");
        username.setPreferredSize(new java.awt.Dimension(7, 28));
        jPanel1.add(username);
        username.setBounds(102, 8, 190, 28);

        login.setBackground(new java.awt.Color(153, 206, 235));
        login.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        login.setText("Login");
        login.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(204, 204, 204)));
        login.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });
        jPanel1.add(login);
        login.setBounds(100, 90, 77, 31);

        logout.setBackground(new java.awt.Color(153, 206, 235));
        logout.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        logout.setText("Logout");
        logout.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(204, 204, 204)));
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        jPanel1.add(logout);
        logout.setBounds(220, 90, 77, 31);

        inArea.setColumns(20);
        inArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        inArea.setLineWrap(true);
        inArea.setRows(5);
        inArea.setWrapStyleWord(true);
        inArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inAreaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inAreaFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(inArea);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(6, 308, 441, 113);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TCP Server Port :");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(460, 40, 130, 27);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Available Interfaces :");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(460, 80, 140, 24);

        serIp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        serIp.setToolTipText("");
        serIp.setPreferredSize(new java.awt.Dimension(7, 28));
        serIp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serIpActionPerformed(evt);
            }
        });
        jPanel1.add(serIp);
        serIp.setBounds(610, 10, 156, 23);

        serPort.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        serPort.setText("4000");
        serPort.setToolTipText("");
        serPort.setPreferredSize(new java.awt.Dimension(7, 28));
        serPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serPortActionPerformed(evt);
            }
        });
        jPanel1.add(serPort);
        serPort.setBounds(610, 40, 156, 23);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("TCP Server IP :");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(460, 10, 110, 24);

        jComboBox1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Wifi", "Ethernet:169.254.49.56", "Loopback Pseudo-Interface 1:127.0.0.1", "Item 4" }));
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(460, 110, 303, 41);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Local Port :");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(460, 190, 80, 27);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Local IP :");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(460, 160, 80, 24);

        Local_IP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Local_IP.setToolTipText("");
        Local_IP.setPreferredSize(new java.awt.Dimension(7, 28));
        Local_IP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Local_IPActionPerformed(evt);
            }
        });
        jPanel1.add(Local_IP);
        Local_IP.setBounds(610, 160, 160, 23);

        Local_Port.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Local_Port.setToolTipText("");
        Local_Port.setPreferredSize(new java.awt.Dimension(7, 28));
        Local_Port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Local_PortActionPerformed(evt);
            }
        });
        jPanel1.add(Local_Port);
        Local_Port.setBounds(610, 190, 160, 23);

        Remot_IP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Remot_IP.setToolTipText("");
        Remot_IP.setPreferredSize(new java.awt.Dimension(7, 28));
        Remot_IP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Remot_IPActionPerformed(evt);
            }
        });
        jPanel1.add(Remot_IP);
        Remot_IP.setBounds(607, 273, 160, 23);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Remote IP :");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(465, 272, 100, 24);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Remote Port :");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(465, 308, 100, 27);

        Remot_Port.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Remot_Port.setToolTipText("");
        Remot_Port.setPreferredSize(new java.awt.Dimension(7, 28));
        Remot_Port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Remot_PortActionPerformed(evt);
            }
        });
        jPanel1.add(Remot_Port);
        Remot_Port.setBounds(607, 310, 160, 23);

        online_user.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                online_userValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(online_user);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(780, 90, 207, 370);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Online Users :");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(770, 60, 110, 24);

        send.setBackground(new java.awt.Color(153, 206, 235));
        send.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        send.setText("Send");
        send.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(204, 204, 204)));
        send.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });
        jPanel1.add(send);
        send.setBounds(460, 350, 100, 39);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("  Status :");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(0, 440, 60, 27);

        status.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        status.setToolTipText("");
        status.setPreferredSize(new java.awt.Dimension(7, 28));
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });
        jPanel1.add(status);
        status.setBounds(60, 440, 390, 28);

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        textPaneArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        textPaneArea.setFocusCycleRoot(false);
        jScrollPane4.setViewportView(textPaneArea);

        jPanel1.add(jScrollPane4);
        jScrollPane4.setBounds(6, 135, 441, 160);

        sendall.setBackground(new java.awt.Color(153, 206, 235));
        sendall.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        sendall.setText("Send all");
        sendall.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(204, 204, 204)));
        sendall.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sendall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendallActionPerformed(evt);
            }
        });
        jPanel1.add(sendall);
        sendall.setBounds(610, 350, 100, 39);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("password :");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(6, 43, 90, 27);

        pass.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pass.setToolTipText("");
        pass.setPreferredSize(new java.awt.Dimension(7, 28));
        jPanel1.add(pass);
        pass.setBounds(102, 44, 190, 28);

        Remot_IP1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Remot_IP1.setToolTipText("");
        Remot_IP1.setPreferredSize(new java.awt.Dimension(7, 28));
        Remot_IP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Remot_IP1ActionPerformed(evt);
            }
        });
        jPanel1.add(Remot_IP1);
        Remot_IP1.setBounds(715, 273, 41, 23);

        Remot_Port1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Remot_Port1.setToolTipText("");
        Remot_Port1.setPreferredSize(new java.awt.Dimension(7, 28));
        Remot_Port1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Remot_Port1ActionPerformed(evt);
            }
        });
        jPanel1.add(Remot_Port1);
        Remot_Port1.setBounds(715, 310, 41, 23);

        // Adding new buttons
        sendFileBtn = new javax.swing.JButton();
        sendFileBtn.setBackground(new java.awt.Color(153, 206, 235));
        sendFileBtn.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        sendFileBtn.setText("Send File");
        sendFileBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(204, 204, 204)));
        sendFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFileBtnActionPerformed(evt);
            }
        });
        jPanel1.add(sendFileBtn);
        sendFileBtn.setBounds(460, 400, 100, 30);

        deleteMsgBtn = new javax.swing.JButton();
        deleteMsgBtn.setBackground(new java.awt.Color(153, 206, 235));
        deleteMsgBtn.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        deleteMsgBtn.setText("Delete Msg");
        deleteMsgBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(204, 204, 204)));
        deleteMsgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMsgBtnActionPerformed(evt);
            }
        });
        
        
        
        jPanel1.add(deleteMsgBtn);
        deleteMsgBtn.setBounds(570, 400, 100, 30);

        deleteConvBtn = new javax.swing.JButton();
        deleteConvBtn.setBackground(new java.awt.Color(153, 206, 235));
        deleteConvBtn.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        deleteConvBtn.setText("Delete Conv");
        deleteConvBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(204, 204, 204)));
        deleteConvBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteConvBtnActionPerformed(evt);
            }
        });
        
        
        
        jPanel1.add(deleteConvBtn);
        deleteConvBtn.setBounds(680, 400, 100, 30);

        receiveFileBtn = new javax.swing.JButton();
        receiveFileBtn.setBackground(new java.awt.Color(153, 206, 235));
        receiveFileBtn.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        receiveFileBtn.setText("Receive File");
        receiveFileBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(204, 204, 204)));
        receiveFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiveFileBtnActionPerformed(evt);
            }
        });
        
        
        
        jPanel1.add(receiveFileBtn);
       receiveFileBtn.setBounds(350, 400, 100, 30);
       
        jLabel12.setText("jLabel12");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(0, 0, 1020, 490);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, -1, 1020, 490);

        pack();
    }

    

    
    
    
    
    
    
    
    
    
    private void loginActionPerformed(java.awt.event.ActionEvent evt) {
        if (username.getText().equals("") || serIp.getText().equals("") || Local_IP.getText().equals("")
                || Local_Port.getText().equals("") || serPort.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "You should enter the following (TCP Port&IP, local Port&IP and your name)");
        } else if (!logedin) {
            userName = username.getText().trim();
            String TcpIP = serIp.getText();
            int TcpPort = Integer.parseInt(serPort.getText().trim());
            String localIP = Local_IP.getText();
            localPort = Integer.parseInt(Local_Port.getText().trim());
            conn = true;
            localIp = Local_IP.getText();
            localPort = Integer.parseInt(Local_Port.getText());
            try {
                socket = new DatagramSocket(localPort);
            } catch (SocketException ex) {
                if (ex instanceof BindException) {
                    JOptionPane.showMessageDialog(null, "The local port is already in use. Please choose a different port.");
                    return; // Exit the method
                } else {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    return; // Exit the method
                }
            }
            try {
                File f = new File("accounts.txt");
                BufferedReader freader = new BufferedReader(new FileReader(f));
                String s1;

                while ((s1 = freader.readLine()) != null) {
                    s1 = s1.toUpperCase();
                    String[] st = s1.split(",");

                    if (st[0].equals(username.getText().toUpperCase()) && st[1].equals(pass.getText().toUpperCase())) {
                        serverSocket = new Socket(InetAddress.getByName(TcpIP), TcpPort, InetAddress.getByName(localIP), localPort);
                        dataFromServer = new DataInputStream(serverSocket.getInputStream());
                        dataToServer = new DataOutputStream(serverSocket.getOutputStream());
                        dataToServer.writeUTF(userName);
                        String s;

                        DataInputStream = new DataInputStream(serverSocket.getInputStream());
                        s = DataInputStream.readUTF();
                        if (s.equals("founded")) {
                            JOptionPane.showMessageDialog(null, "You are already login!\n", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else if (s.equals("accept")) {
                            dlm = new DefaultListModel();
                            online_user.setModel(dlm);
                            r = new Read(userName);
                            r.start();
                        }

                        j = true;

                        channel = new receive(this);
                        channel.start();
                        t = true;
                        JOptionPane.showMessageDialog(null, "You are logged in successfully");
                        logedin = true;
                        break;
                    }
                }
                if (!logedin) {
                    JOptionPane.showMessageDialog(null, "Invalid login information, either user name or password");
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "The local port is used");
            }
        } else {
            JOptionPane.showMessageDialog(null, "You are already logged in");
        }
    }


    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {
        if (logedin) {
            JOptionPane.showMessageDialog(null, "You are loged out successfully");
            logedin = false;
            t = false;
            j = false;

            try {
                String localIp1 = Local_IP.getText();
                int localPort1 = Integer.parseInt(Local_Port.getText());
                InetAddress remot_IPAddress1 = InetAddress.getByName(localIp1);
                String msg = "logout";
                S_buffer = msg.getBytes();
                sendpacket = new DatagramPacket(S_buffer, S_buffer.length, remot_IPAddress1, localPort1);
                socket.send(sendpacket);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = "logout";
            try {
                dataToServer.writeUTF(s);

            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            dlm.clear();

            try {
                socket.close();
                serverSocket.close();
                DataInputStream.close();
                dataFromServer.close();
                dataToServer.close();

            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "You are already loged out");
        }
    }

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (!conn) {
                JOptionPane.showMessageDialog(null, "You can't send, pleace Login first");
            } else if (Remot_IP.getText().equals("") || Remot_Port.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "You should select a user from the online user list");
            } else if (inArea.getText().equals("") || inArea.getText().equals("enter text here")) {
                JOptionPane.showMessageDialog(null, "You can't send empty message");
            } else {
                userName = username.getText();
                remotIp = Remot_IP.getText();
                remotPort = Integer.parseInt(Remot_Port.getText());
                remot_IPAddress = InetAddress.getByName(remotIp);
                String msg = inArea.getText();
                inArea.setText("");
                StyledDocument doc = textPaneArea.getStyledDocument();
                Style style = textPaneArea.addStyle("", null);
                StyleConstants.setForeground(style, Color.RED);
                StyleConstants.setBackground(style, Color.white);
                LocalDateTime now = LocalDateTime.now();
                String s1 = "[" + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a")) + "] ME: " + msg + " From " + localPort + "\n";
                doc.insertString(doc.getLength(), s1, style);
                msg = userName + ": " + msg;
                S_buffer = msg.getBytes();
                sendpacket = new DatagramPacket(S_buffer, S_buffer.length, remot_IPAddress, remotPort);
                socket.send(sendpacket);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | BadLocationException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendallActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (!conn) {
                JOptionPane.showMessageDialog(null, "You can't send, pleace Login first");
            } else if (inArea.getText().equals("") || inArea.getText().equals("enter text here")) {
                JOptionPane.showMessageDialog(null, "You can't send empty message");
            } else {
                int x = online_user.getModel().getSize();

                if (x != 0) {
                    for (int index = 0; index < x; index++) {
                        ListModel model = online_user.getModel();
                        String s = (model.getElementAt(index).toString());
                        String[] tokens = s.split(",");
                        Remot_IP1.setText(tokens[1]);
                        Remot_Port1.setText(tokens[2]);
                        userName = username.getText();
                        remotIp = Remot_IP1.getText();
                        if (!tokens[2].equals(Local_Port.getText())) {
                            remotPort = Integer.parseInt(Remot_Port1.getText());
                            remot_IPAddress = InetAddress.getByName(remotIp);
                            String msg = inArea.getText();
                            StyledDocument doc = textPaneArea.getStyledDocument();
                            Style style = textPaneArea.addStyle("", null);
                            StyleConstants.setForeground(style, Color.RED);
                            StyleConstants.setBackground(style, Color.white);
                            LocalDateTime now = LocalDateTime.now();
                            String s1 = "[" + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a")) + "] ME: " + msg + " From " + localPort + " to " + tokens[2] + "\n";
                            doc.insertString(doc.getLength(), s1, style);
                            msg = userName + ": " + msg;
                            S_buffer = msg.getBytes();
                            sendpacket = new DatagramPacket(S_buffer, S_buffer.length, remot_IPAddress, remotPort);
                            socket.send(sendpacket);
                        } else {
                        }
                    }
                    inArea.setText("");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void online_userValueChanged(javax.swing.event.ListSelectionEvent evt) {
        try {
            int x = online_user.getModel().getSize();
            if (!evt.getValueIsAdjusting() && x != 0) {
                String s = (online_user.getSelectedValue().toString());
                String[] tokens = s.split(",");
                
                // Get both local and public IP if available
                String ipAddress = tokens[1]; 
                if (ipAddress.startsWith("192.168.") || ipAddress.startsWith("10.")) {
                    // Try to get public IP if local IP detected
                    try {
                        String publicIp = getPublicIp();
                        if (publicIp != null) {
                            ipAddress = publicIp;
                        }
                    } catch (Exception e) {
                        // Fall back to local IP
                    }
                }
                
                Remot_IP.setText(ipAddress);
                Remot_Port.setText(tokens[2]);
            }
        } catch (Exception ex) {
            appendToChat("Error selecting user: " + ex.getMessage(), Color.RED);
        }
    }

    private String getPublicIp() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()))) {
            return in.readLine().trim();
        }
    }

    private void serIpActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void serPortActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void Local_IPActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void Local_PortActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void Remot_IPActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void Remot_PortActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void inAreaFocusGained(java.awt.event.FocusEvent evt) {
        if (inArea.getText().equals("enter text here")) {
            inArea.setText("");
            inArea.setForeground(Color.BLACK);
        }
    }

    private void inAreaFocusLost(java.awt.event.FocusEvent evt) {
        if (inArea.getText().isEmpty()) {
            inArea.setForeground(Color.GRAY);
            inArea.setText("enter text here");
        }
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
    }

    private void formWindowClosed(java.awt.event.WindowEvent evt) {
    }

    private void Remot_Port1ActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void Remot_IP1ActionPerformed(java.awt.event.ActionEvent evt) {
    }

    
    private javax.swing.JButton sendFileBtn;
    private javax.swing.JButton deleteMsgBtn;
    private javax.swing.JButton deleteConvBtn;
    private javax.swing.JButton receiveFileBtn;
    
    

    
 // For file transfer functionality
    private File currentFileToSend;
    private String fileReceiverUsername;
    private final String FILE_UPLOAD_PATH = "received_files/"; // Folder to store received files
    
    
 
    private ServerSocket fileServerSocket;
    private int fileTransferPort = 4001; // Different from your chat port
    
    
    
    private void sendFileBtnActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        appendToChat(getNetworkInfo(), Color.BLUE); 
        if (online_user.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "Please select a recipient first!");
            return;
        }
        String recipientIp = Remot_IP.getText();
        if (!isReachable(recipientIp, fileTransferPort)) {
            appendToChat("Cannot reach " + recipientIp + ":" + fileTransferPort, Color.RED);
            showPortForwardingHelp();
            return;
        }
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            currentFileToSend = fileChooser.getSelectedFile();
            fileReceiverUsername = (String) online_user.getSelectedValue();
            
            if (fileReceiverUsername != null) {
                // Display file info in chat
                appendToChat("You are sending file: " + currentFileToSend.getName() + 
                           " (" + (currentFileToSend.length()/1024) + " KB) to " + 
                           fileReceiverUsername, Color.BLUE);
                
                // In a real implementation, you would send the file here
                // sendFile(currentFileToSend, fileReceiverUsername);
                
                status.setText("Ready to send file to " + fileReceiverUsername);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a recipient first!");
            }
        }
    }
    
    
    
    
    private void sendFile(File file, String recipient) {
        String recipientIp = Remot_IP.getText();
    
        int port = fileTransferPort;
        
        SwingUtilities.invokeLater(() -> {
            status.setText("Testing connection to " + recipientIp + ":" + port);
        });
        SwingUtilities.invokeLater(() -> {
            status.setText("Connecting to " + recipient + "...");
        });

        try (Socket socket = new Socket(recipientIp, fileTransferPort);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             FileInputStream fis = new FileInputStream(file)) {
            
            // Verify connection
            if (!socket.isConnected()) {
                SwingUtilities.invokeLater(() -> {
                    appendToChat("Connection failed", Color.RED);
                });
                return;
            }

            
            
            if (!testConnection(recipientIp, port)) {
                SwingUtilities.invokeLater(() -> {
                    appendToChat("Cannot connect to " + recipientIp + ":" + port, Color.RED);
                    appendToChat("Possible issues:", Color.RED);
                    appendToChat("1. Recipient's firewall may be blocking port " + port, Color.RED);
                    appendToChat("2. Port may not be forwarded on recipient's router", Color.RED);
                    appendToChat("3. You may need to use VPN if on different networks", Color.RED);
                });
                return;
            }
            // Send metadata
            dos.writeUTF(file.getName());
            dos.writeLong(file.length());
            dos.writeLong(calculateChecksum(file));

            // Send file data
            byte[] buffer = new byte[8192];
            int read;
            long totalSent = 0;
            while ((read = fis.read(buffer)) > 0) {
                dos.write(buffer, 0, read);
                totalSent += read;
                final int progress = (int) (totalSent * 100 / file.length());
                SwingUtilities.invokeLater(() -> {
                    status.setText("Sending: " + progress + "%");
                });
            }

            SwingUtilities.invokeLater(() -> {
                appendToChat("File sent successfully", Color.BLUE);
                status.setText("Transfer complete");
            });
            
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> {
                appendToChat("Transfer failed: " + e.getMessage(), Color.RED);
                status.setText("Error: " + e.getClass().getSimpleName());
            });
        }
    }
    
    private void showPortForwardingHelp() {
        String message = "For file transfer to work between different networks:\n\n"
                + "1. On recipient's router, forward port " + fileTransferPort + " to local IP\n"
                + "2. Ensure firewall allows incoming connections on port " + fileTransferPort + "\n"
                + "3. Both sides should use recipient's public IP address\n\n"
                + "For testing, try using a VPN to put both PCs on same virtual network";
        
        JOptionPane.showMessageDialog(this, message, "Network Setup Help", JOptionPane.INFORMATION_MESSAGE);
    }
    
 // Network testing methods
    private boolean isReachable(String ip, int port) {
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress(ip, port), 5000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String getNetworkInfo() {
        try {
            String localIp = InetAddress.getLocalHost().getHostAddress();
            String publicIp = getPublicIp();
            return "Local IP: " + localIp + "\nPublic IP: " + publicIp;
        } catch (Exception e) {
            return "Cannot determine network info";
        }
    }
    
    
    private void handleIncomingFile(Socket clientSocket) {
        try (DataInputStream dis = new DataInputStream(clientSocket.getInputStream())) {
            // Read metadata
            String fileName = dis.readUTF();
            long fileSize = dis.readLong();
            long expectedChecksum = dis.readLong();

            // Prepare directory
            File directory = new File(FILE_UPLOAD_PATH);
            if (!directory.exists()) directory.mkdirs();

            // Receive file
            File file = new File(FILE_UPLOAD_PATH + fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                byte[] buffer = new byte[8192];
                int read;
                long remaining = fileSize;
                long totalReceived = 0;
                
                SwingUtilities.invokeLater(() -> {
                    appendToChat("Receiving: " + fileName, Color.BLUE);
                });

                while ((read = dis.read(buffer, 0, (int) Math.min(buffer.length, remaining))) > 0 && remaining > 0) {
                    fos.write(buffer, 0, read);
                    remaining -= read;
                    totalReceived += read;
                    final int progress = (int) (totalReceived * 100 / fileSize);
                    SwingUtilities.invokeLater(() -> {
                        status.setText("Receiving: " + progress + "%");
                    });
                }

                // Verify checksum
                long actualChecksum = calculateChecksum(file);
                if (actualChecksum != expectedChecksum) {
                    throw new IOException("Checksum mismatch - file corrupted");
                }

                SwingUtilities.invokeLater(() -> {
                    appendToChat("File received: " + fileName, Color.BLUE);
                    status.setText("Saved to: " + file.getAbsolutePath());
                });
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> {
                appendToChat("Receive error: " + e.getMessage(), Color.RED);
            });
        } finally {
            try { clientSocket.close(); } catch (IOException e) {}
        }
    }
    
    
    
    
    
    
    
    
    

    private long calculateChecksum(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            byte[] bytes = new byte[8192];
            long checksum = 0L;
            int read;
            while ((read = is.read(bytes)) != -1) {
                for (int i = 0; i < read; i++) {
                    checksum += bytes[i] & 0xff;
                }
            }
            return checksum;
        }
    }
    
    
    private boolean testConnection(String ip, int port) {
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress(ip, port), 5000); // 5 second timeout
            return true;
        } catch (IOException e) {
            return false;
        }
    }

	private void appendToChat(String message, Color color) {
        try {
            javax.swing.text.StyledDocument doc = textPaneArea.getStyledDocument();
            
            // Create a style for the text
            javax.swing.text.Style style = textPaneArea.addStyle("Color Style", null);
            StyleConstants.setForeground(style, color);
            
            // Append the message
            doc.insertString(doc.getLength(), message + "\n", style);
            
            // Auto-scroll to bottom
            textPaneArea.setCaretPosition(doc.getLength());
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

 // Delete Message Button Implementation
    private void deleteMsgBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedStart = textPaneArea.getSelectionStart();
        int selectedEnd = textPaneArea.getSelectionEnd();
        
        if (selectedStart != selectedEnd) {
            // Delete selected text
            try {
                textPaneArea.getDocument().remove(selectedStart, selectedEnd - selectedStart);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        } else {
            // If no selection, delete the last message
            String currentText = textPaneArea.getText();
            int lastNewLine = currentText.lastIndexOf("\n");
            
            if (lastNewLine != -1) {
                textPaneArea.setText(currentText.substring(0, lastNewLine));
            } else {
                textPaneArea.setText("");
            }
        }
    }
    
	
	private void deleteConvBtnActionPerformed(java.awt.event.ActionEvent evt) {
	    int confirm = JOptionPane.showConfirmDialog(
	        this, 
	        "Are you sure you want to clear the entire conversation?", 
	        "Clear Conversation", 
	        JOptionPane.YES_NO_OPTION
	    );
	    
	    if (confirm == JOptionPane.YES_OPTION) {
	        textPaneArea.setText("");
	        status.setText("Conversation cleared");
	    }
	}
 
    
    private void receiveFileBtnActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose download location");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File saveDir = fileChooser.getSelectedFile();
            
            // Create directory if it doesn't exist
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            
            // In a real implementation, you would receive the file here
            // receiveFile(saveDir);
            
            status.setText("Files will be saved to: " + saveDir.getAbsolutePath());
            appendToChat("Ready to receive files in: " + saveDir.getAbsolutePath(), Color.BLUE);
        }
    }
    
    private String getVpnIp() {
        try {
            for (NetworkInterface netInt : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (netInt.getName().contains("Hamachi") || 
                    netInt.getName().contains("Radmin") ||
                    netInt.getName().contains("ZeroTier")) {
                    for (InetAddress addr : Collections.list(netInt.getInetAddresses())) {
                        if (addr instanceof Inet4Address) {
                            return addr.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    private Color getRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }

    private Color getUserColor(String username) {
        if (!userColorMap.containsKey(username)) {
            Color randomColor = getRandomColor();
            userColorMap.put(username, randomColor);
        }
        return userColorMap.get(username);
    }

    void receive() {
        try {
            if (t) {
                StyledDocument doc = textPaneArea.getStyledDocument();
                Style style = textPaneArea.addStyle("", null);
                socket.receive(receive_packet);
                String msg = new String(R_buffer, 0, receive_packet.getLength());
                if (msg.equals("logout")) {
                    return;
                }
                InetAddress S_IPAddress = receive_packet.getAddress();
                int Sport = receive_packet.getPort();
                StyleConstants.setForeground(style, getUserColor(msg.split(":")[0]));
                StyleConstants.setBackground(style, Color.white);

                LocalDateTime now = LocalDateTime.now();
                String s1 = "[" + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a")) + "]" + msg + " From " + Sport + "\n";

                doc.insertString(doc.getLength(), s1, style);
                String s = S_IPAddress.getHostAddress();
                status.setText("Received From IP= " + s + ", port: " + Sport);
            }
        } catch (IOException | BadLocationException ex) {
        }
    }

    class Read extends Thread {
        String userName;

        public Read(String userName) {
            this.userName = userName;
        }

        public void run() {
            while (j) {
                try {
                    String inputData = dataFromServer.readUTF();
                    if (inputData.equals("logout")) {
                        break;
                    }
                    if (inputData.contains("add to list")) {
                        inputData = inputData.substring(11);
                        dlm.clear();
                        StringTokenizer st = new StringTokenizer(inputData, "&?");
                        while (st.hasMoreTokens()) {
                            String line = st.nextToken();
                            String[] tokens = line.split(",");

                            String element = new String(tokens[0] + "," + tokens[2] + "," + tokens[1]);
                            dlm.addElement(element);
                        }
                    }
                } catch (IOException ex) {
                }
            }
        }
    }

    class receive extends Thread {
        Client client;

        public receive(Client client) {
            this.client = client;
        }

        public void run() {
            while (true) {
                client.receive();
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Client().setVisible(true);
        });
    }

    private javax.swing.JTextField Local_IP;
    private javax.swing.JTextField Local_Port;
    private javax.swing.JTextField Remot_IP;
    private javax.swing.JTextField Remot_IP1;
    private javax.swing.JTextField Remot_Port;
    private javax.swing.JTextField Remot_Port1;
    private javax.swing.JTextArea inArea;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton login;
    private javax.swing.JButton logout;
    private javax.swing.JList<String> online_user;
    private javax.swing.JTextField pass;
    private javax.swing.JButton send;
    private javax.swing.JButton sendall;
    private javax.swing.JTextField serIp;
    private javax.swing.JTextField serPort;
    private javax.swing.JTextField status;
    private javax.swing.JTextPane textPaneArea;
    private javax.swing.JTextField username;
}
