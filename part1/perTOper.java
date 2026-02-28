package part1;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class perTOper extends JFrame {
    Thread t;
    DatagramSocket Socket;
    String SendMessage;
    private JTextPane Chat;
    private JTextField LocalIP;
    JTextField LocalPort;
    private JTextArea OnlineUser;
    private JTextField RemoteIP;
    JTextField RemotePort;
    private JButton Send;
    private JTextArea SendMessageTextField;
    private JButton StartListing;
    private JTextField Status;
    private JButton TestButton;
    private JButton jButton1;
    private JButton jButton2;
    private JComboBox<String> jComboBox1;
    private JLabel jLabel10;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JButton restore;
    private JLabel timerlabel;
    private PrintWriter logWriter;

    public perTOper() {
        this.initComponents();
        if (this.jComboBox1.getSelectedItem() == "Wi-Fi") {
            try {
                this.LocalIP.setText(InetAddress.getLocalHost().getHostAddress().toString());
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        }

        try {
            logWriter = new PrintWriter(new FileWriter("chat_log_dina.txt", true), true);
            log("Chat application started for Dina.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Client() {
        try {
            String[] ipdest = this.RemoteIP.getText().split("\\.");
            byte[] IP_other_device = new byte[]{(byte)Integer.parseInt(ipdest[0]), (byte)Integer.parseInt(ipdest[1]), (byte)Integer.parseInt(ipdest[2]), (byte)Integer.parseInt(ipdest[3])};
            InetAddress IPDest = InetAddress.getByAddress(IP_other_device);
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a"));
            this.SendMessage = " [" + timestamp + "] :  " + this.SendMessageTextField.getText();
            byte[] SendData = this.SendMessage.getBytes();
            DatagramPacket SendPacket = new DatagramPacket(SendData, SendData.length, IPDest, Integer.parseInt(this.RemotePort.getText()));
            this.Socket.send(SendPacket);
            String senmsg = "Dina " + this.SendMessage + " from " + this.Socket.getLocalPort() + "\n";
            StyledDocument doc = this.Chat.getStyledDocument();
            Style s = this.Chat.addStyle("", (Style)null);
            StyleConstants.setForeground(s, Color.YELLOW); // Dark green for Dina's messages
            StyleConstants.setBold(s, true);
            StyleConstants.setFontSize(s, 12);
            doc.insertString(doc.getLength(), senmsg, s);
            JTextField var15 = this.Status;
            String var10001 = SendPacket.getAddress().getHostAddress();
            var15.setText("Send to: " + var10001 + ", Port: " + SendPacket.getPort());
            this.log("Message sent: " + senmsg);
        } catch (NumberFormatException var13) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Please enter all fields correctly", "WARNING", 2);
        } catch (IllegalArgumentException var14) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Please enter remote port correctly", "WARNING", 2);
        } catch (ArrayIndexOutOfBoundsException var15) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Please enter IP address correctly", "WARNING", 2);
        } catch (NullPointerException var16) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Please start listening before sending a message", "WARNING", 2);
        } catch (Exception var17) {
            var17.printStackTrace();
        }
    }

    void Server() {
        try {
            while(true) {
                byte[] ReceiveData = new byte[65536];
                DatagramPacket ReceivePacket = new DatagramPacket(ReceiveData, ReceiveData.length);
                this.Socket.receive(ReceivePacket);
                String ReceiveMsg = new String(ReceivePacket.getData());
                String receivedMessage = "Zeina " + ReceiveMsg.trim() + " from " + ReceivePacket.getPort() + "\n";
                StyledDocument doc = this.Chat.getStyledDocument();
                Style style = this.Chat.addStyle("", (Style)null);
                StyleConstants.setForeground(style,Color.ORANGE); // Dark magenta for Zeina's messages
                StyleConstants.setBold(style, true);
                StyleConstants.setFontSize(style, 12);
                doc.insertString(doc.getLength(), receivedMessage, style);
                JTextField var10 = this.Status;
                String var10001 = ReceivePacket.getAddress().getHostAddress();
                var10.setText("Received from:" + var10001 + ", Port:" + ReceivePacket.getPort());
                this.log("Message received: " + receivedMessage);
            }
        } catch (NumberFormatException var10) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Please enter the correct local port", "WARNING", 2);
        } catch (BindException var11) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Port already used, please choose a different one", "WARNING", 2);
        } catch (Exception var12) {
            var12.printStackTrace();
        }
    }

    private void initComponents() {
        this.jLabel4 = new JLabel();
        this.jComboBox1 = new JComboBox();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.LocalIP = new JTextField();
        this.LocalPort = new JTextField();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.RemoteIP = new JTextField();
        this.RemotePort = new JTextField();
        this.Send = new JButton();
        this.TestButton = new JButton();
        this.jScrollPane2 = new JScrollPane();
        this.SendMessageTextField = new JTextArea();
        this.jLabel9 = new JLabel();
        this.Status = new JTextField();
        this.jScrollPane3 = new JScrollPane();
        this.OnlineUser = new JTextArea();
        this.jLabel10 = new JLabel();
        this.StartListing = new JButton();
        this.jScrollPane4 = new JScrollPane();
        this.Chat = new JTextPane();
        this.jButton1 = new JButton();
        this.jButton2 = new JButton();
        this.restore = new JButton();
        this.timerlabel = new JLabel();
        this.setDefaultCloseOperation(3);
        this.setTitle("Zeina's Chat");
        this.setSize(new Dimension(1200, 1200));

        jButton1.setBackground(new Color(255, 182, 193)); // Light pink
        jButton2.setBackground(new Color(255, 182, 193));
        restore.setBackground(new Color(255, 182, 193));
        StartListing.setBackground(new Color(255, 182, 193));
        Send.setBackground(new Color(255, 182, 193));
        TestButton.setBackground(new Color(255, 182, 193));
        getContentPane().setBackground(new Color(230, 230, 250)); // Lavender background
        this.setResizable(true);
        this.jLabel4.setText("Available Interface");
        this.jComboBox1.setModel(new DefaultComboBoxModel(new String[]{"Wi-Fi", "Ethernet", "Loopback pseudo-Interface"}));
        this.jComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	perTOper.this.jComboBox1ActionPerformed(evt);
            }
        });
        
        
        this.jLabel5.setText("Local IP:");
        this.jLabel6.setText("Local Port:");
        this.LocalIP.setFont(new Font("Tahoma", 1, 12));
        this.LocalIP.setPreferredSize(new Dimension(7, 24));
        jScrollPane4.setPreferredSize(new Dimension(450, 300));  // منطقة الدردشة
        jScrollPane2.setPreferredSize(new Dimension(450, 80));   // مربع الرسالة
        jScrollPane3.setPreferredSize(new Dimension(200, 300));  // الأرشيف
        Chat.setFont(new Font("Arial", Font.PLAIN, 14));
        SendMessageTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        OnlineUser.setFont(new Font("Arial", Font.PLAIN, 12));
      
        this.LocalIP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                perTOper.this.LocalIPActionPerformed(evt);
            }
        });
        this.LocalPort.setFont(new Font("Tahoma", 1, 12));
        this.LocalPort.setPreferredSize(new Dimension(7, 24));
        this.LocalPort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                perTOper.this.LocalPortActionPerformed(evt);
            }
        });
        this.jLabel7.setText("Remote Port:");
        this.jLabel8.setText("Remote IP:");
        this.RemoteIP.setFont(new Font("Tahoma", 1, 12));
        this.RemoteIP.setPreferredSize(new Dimension(7, 24));
        this.RemotePort.setFont(new Font("Tahoma", 1, 12));
        this.RemotePort.setPreferredSize(new Dimension(7, 24));
        this.RemotePort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	perTOper.this.RemotePortActionPerformed(evt);
            }
        });
        this.Send.setText("Send");
        this.Send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	perTOper.this.SendActionPerformed(evt);
            }
        });
        this.TestButton.setText(" Testing ");
        this.TestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	perTOper.this.TestButtonActionPerformed(evt);
            }
        });
        this.SendMessageTextField.setColumns(20);
        this.SendMessageTextField.setFont(new Font("Tahoma", 1, 12));
        this.SendMessageTextField.setRows(5);
        this.jScrollPane2.setViewportView(this.SendMessageTextField);
        this.jLabel9.setText("Status:");
        this.Status.setEditable(false);
        this.Status.setBackground(new Color(255, 255, 255));
        this.Status.setFont(new Font("Tahoma", 1, 12));
        this.Status.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	perTOper.this.StatusActionPerformed(evt);
            }
        });
        this.OnlineUser.setEditable(false);
        this.OnlineUser.setColumns(20);
        this.OnlineUser.setFont(new Font("Tahoma", 1, 12));
        this.OnlineUser.setRows(5);
        this.jScrollPane3.setViewportView(this.OnlineUser);
        this.jLabel10.setText("Archive :");
        this.StartListing.setText("Start Listening");
        this.StartListing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	perTOper.this.StartListingActionPerformed(evt);
            }
        });
        this.Chat.setEditable(false);
        this.jScrollPane4.setViewportView(this.Chat);
        this.jButton1.setText("Delete Message");
        this.jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	perTOper.this.jButton1ActionPerformed(evt);
            }
        });
        this.jButton2.setText(" Archive");
        this.jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	perTOper.this.jButton2ActionPerformed(evt);
            }
        });
        this.restore.setText("restore");
        this.restore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	perTOper.this.restoreActionPerformed(evt);
            }
        });
        this.timerlabel.setText("");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap(-1, 32767)
                        .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                                .addComponent(this.jScrollPane2, -2, 477, -2)
                                .addComponent(this.jScrollPane4, -2, 477, -2)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(this.jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(this.Status, -2, 422, -2)))
                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(this.StartListing, -2, 280, -2))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(13, 13, 13)
                                                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                                                .addComponent(this.jLabel4)
                                                                                .addComponent(this.jComboBox1, Alignment.TRAILING, -2, 273, -2))
                                                                        .addComponent(this.LocalPort, Alignment.TRAILING, -2, 150, -2)
                                                                        .addComponent(this.RemoteIP, Alignment.TRAILING, -2, 150, -2)
                                                                        .addComponent(this.RemotePort, Alignment.TRAILING, -2, 150, -2))
                                                                .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                                                                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(this.TestButton, -2, 109, -2)
                                                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                                                        .addComponent(this.jButton2, -2, 150, -2))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(this.Send, -2, 109, -2)
                                                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                                                        .addComponent(this.jButton1, -2, 150, -2)))
                                                                        .addGap(6, 6, 6))))
                                                .addGroup(Alignment.LEADING, layout.createSequentialGroup()
                                                        .addGap(40, 40, 40)
                                                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                                .addComponent(this.jLabel7)
                                                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(this.jLabel8)
                                                                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                                                .addComponent(this.jLabel6)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(this.jLabel5)
                                                                                        .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                                                                                        .addComponent(this.LocalIP, -2, 150, -2)))))))
                                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(99, 99, 99)
                                                        .addComponent(this.restore))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(37, 37, 37)
                                                        .addComponent(this.jScrollPane3, -2, 265, -2)))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(246, 246, 246)
                                .addComponent(this.jLabel10, -2, 100, -2)
                                .addGap(18, 18, 18)
                                .addComponent(this.timerlabel, -2, 101, -2))));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(49, 49, 49)
                                        .addComponent(this.jLabel4)
                                        .addGap(29, 29, 29)
                                        .addComponent(this.jComboBox1, -2, 40, -2)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(this.LocalIP, -2, -1, -2)
                                                .addComponent(this.jLabel5))
                                        .addGap(26, 26, 26)
                                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(this.LocalPort, -2, -1, -2)
                                                .addComponent(this.jLabel6))
                                        .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(this.RemoteIP, -2, -1, -2)
                                                .addComponent(this.jLabel8))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(this.RemotePort, -2, -1, -2)
                                                .addComponent(this.jLabel7, -2, 20, -2))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(this.jButton2, -2, 41, -2)
                                                .addComponent(this.TestButton, -2, 41, -2)))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addContainerGap()
                                                        .addComponent(this.jLabel10, -2, 15, -2)
                                                        .addPreferredGap(ComponentPlacement.RELATED, -1, 32767))
                                                .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addContainerGap(-1, 32767)
                                                        .addComponent(this.timerlabel)
                                                        .addPreferredGap(ComponentPlacement.RELATED)))
                                        .addComponent(this.jScrollPane3, -2, 358, -2)))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(this.restore, -2, 31, -2)
                                        .addGap(9, 9, 9))
                                .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(this.Send, -2, 36, -2)
                                                .addComponent(this.jButton1, -2, 36, -2))
                                        .addGap(8, 8, 8)))
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(this.StartListing, -2, 30, -2)
                                .addComponent(this.Status, -2, 30, -2)
                                .addComponent(this.jLabel9))
                        .addGap(63, 63, 63))
                .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(this.jScrollPane4, -2, 272, -2)
                        .addPreferredGap(ComponentPlacement.RELATED, 42, 32767)
                        .addComponent(this.jScrollPane2, -2, 96, -2)
                        .addGap(134, 134, 134)));
        this.pack();
    }

    private void LocalPortActionPerformed(ActionEvent evt) {
    }

    private void RemotePortActionPerformed(ActionEvent evt) {
    }

    private void jComboBox1ActionPerformed(ActionEvent evt) {
        if (this.jComboBox1.getSelectedItem().equals("Loopback pseudo-Interface")) {
            this.LocalIP.setText("127.0.0.1");
            this.RemoteIP.setText("127.0.0.1");
        } else if (this.jComboBox1.getSelectedItem().equals("Wi-Fi")) {
            try {
                this.RemoteIP.setText("");
                this.LocalIP.setText(InetAddress.getLocalHost().getHostAddress().toString());
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        } else {
            this.LocalIP.setText("");
            this.RemoteIP.setText("");
        }
    }
    private void setupUI() {
        // جعل النافذة غير قابلة لتغيير الحجم للحفاظ على التنسيق
        setResizable(false);
        
        // تنسيق الألوان
        getContentPane().setBackground(new Color(240, 240, 240));
        Chat.setBackground(new Color(255, 255, 255));
        
        // تحسين مظهر الأزرار
        for (JButton button : new JButton[]{Send, StartListing, TestButton, jButton1, jButton2, restore}) {
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 12));
        }
    }
    private void StatusActionPerformed(ActionEvent evt) {
    }

    private void SendActionPerformed(ActionEvent evt) {
        this.Client();
        this.SendMessageTextField.setText("");
    }

    private void TestButtonActionPerformed(ActionEvent evt) {
        this.SendMessageTextField.setText("Under Testing");
        this.Client();
        this.SendMessageTextField.setText("");
    }

    private void StartListingActionPerformed(ActionEvent evt) {
        try {
            P2PCon conn;
            if (this.Socket == null) {
                this.Socket = new DatagramSocket(Integer.parseInt(this.LocalPort.getText()));
                conn = new P2PCon();
                this.t = new Thread(conn);
                this.t.start();
            } else {
                this.Socket.close();
                this.t.interrupt();
                this.Socket = new DatagramSocket(Integer.parseInt(this.LocalPort.getText()));
                conn = new P2PCon();
                this.t = new Thread(conn);
                this.t.start();
            }
            log("Started listening on port: " + this.LocalPort.getText());
        } catch (NumberFormatException var3) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Please enter Local IP and local port correctly", "WARNING", 2);
        } catch (IllegalArgumentException var4) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Please enter the correct local port", "WARNING", 2);
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        try {
            int start = this.Chat.getSelectionStart();
            int end = this.Chat.getSelectionEnd();
            String r = this.Chat.getDocument().getText(start, end - start) + "\n";
            this.OnlineUser.setText(r);
            if (start != end) {
                this.Chat.getDocument().remove(start, end - start);
                log("Message deleted: " + r);
            }

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                        	perTOper.this.OnlineUser.setText("");
                            log("Message auto-deleted from archive: " + r);
                        }
                    });
                }
            }, 120000L);
        } catch (BadLocationException var6) {
            var6.printStackTrace();
        }
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        String Archive = this.Chat.getText();
        this.OnlineUser.setText(Archive);
        this.Chat.setText("");
        this.Status.setText("");
        log("Conversation deleted: " + Archive);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
            	perTOper.this.OnlineUser.setText("");
                log("Conversation auto-deleted from archive: " + Archive);
            }
        }, 120000L);
    }

    private void restoreActionPerformed(ActionEvent evt) {
        List<String> archiveList = new ArrayList();
        String restore = this.OnlineUser.getText();
        archiveList.add(restore);

        for (Iterator var4 = archiveList.iterator(); var4.hasNext(); this.Status.setText("message")) {
            String message = (String) var4.next();
            StyledDocument doc = this.Chat.getStyledDocument();
            Style k = this.Chat.addStyle("", (Style) null);
            Scanner scanner = new Scanner(message);
            if (scanner.hasNext()) {
                String firstWord = scanner.next();
                if (firstWord.equalsIgnoreCase("Zeina")) {
                    StyleConstants.setForeground(k,Color.YELLOW); // Dark green for Dina's messages
                } else {
                    StyleConstants.setForeground(k, Color.ORANGE); // Dark magenta for Zeina's messages
                }
            }

            StyleConstants.setBold(k, true);

            try {
                doc.insertString(doc.getLength(), message, k);
                log("Message restored: " + message);
            } catch (BadLocationException var10) {
                Logger.getLogger(perTOper.class.getName()).log(Level.SEVERE, (String) null, var10);
            }
        }

        this.OnlineUser.setText("");
    }

    private void LocalIPActionPerformed(ActionEvent evt) {
    }

    class P2PCon implements Runnable {
        P2PCon() {
        }

        public void run() {
        	perTOper.this.Server();
        }
    }

    private void log(String message) {
        if (logWriter != null) {
            logWriter.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + message);
        }
    }
}