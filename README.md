# p2p-chat-file-transfer💬📁
Java-based P2P chat application with real-time messaging and file transfer using UDP &amp; TCP

## Overview
This project implements a **peer-to-peer (P2P) chat application with file transfer** capabilities in Java. It combines **UDP for real-time messaging** and **TCP for reliable file transfer**, supported by a central server for user coordination and client management. The system includes an intuitive GUI and real-time logging of user actions.

## Key Features
- **Real-time Chat:** Peer-to-peer messaging using UDP with color-coded sent/received messages  
- **File Transfer:** Send and receive files reliably via TCP  
- **User Management:** Registration, login, and active user tracking through a TCP server  
- **Graphical User Interface:** Interactive GUI for chat, file transfer, status updates, and user lists  
- **Message Archiving:** Archive and restore messages with auto-deletion  
- **Activity Logging:** Logs all operations with timestamps and metadata for audit and analysis  
- **Cross-Device Testing:** Fully tested between multiple machines for reliable communication  

## Technical Highlights
- **Backend:** Java with TCP & UDP socket programming  
- **Frontend/GUI:** Swing-based interface for clients and server  
- **Server:** Handles registration, login verification, and client management  
- **Protocols:** UDP for low-latency messaging, TCP for reliable file transfer  
- **Performance Metrics:** End-to-end delay, jitter, file size, and packet stats during file transfer  

## Tools & Technologies
- **Eclipse IDE** for Java development  
- **Java Swing** for GUI  
- **TCP/UDP Sockets** for networking  

## Learning Outcomes
- Understand P2P networking and client-server architectures  
- Implement dual-protocol communication (UDP + TCP)  
- Design intuitive GUIs for real-time applications  
- Handle file transfer, logging, and cross-device communication  

## Conclusion
This P2P chat and file transfer system provides a **robust, user-friendly communication tool**, balancing efficiency and reliability. It serves as a foundation for future enhancements like encryption, group chat, or multi-file transfers.
