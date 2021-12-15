package chat.server;

import network.TCPConnection;
import network.TCPConnectionListiner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListiner {

    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<TCPConnection> tcpConnections = new ArrayList<>();

    private ChatServer() {
        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println(InetAddress.getLocalHost());
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        tcpConnections.add(tcpConnection);
        sendToAllClients("Client " + tcpConnection + " connected");
    }

    @Override
    public synchronized void onReceiveMessage(TCPConnection tcpConnection, String value) {
        sendToAllClients(value);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        tcpConnections.remove(tcpConnection);
        sendToAllClients("Client " + tcpConnection + " disconnected");
    }

    private void sendToAllClients(String value) {
        for (TCPConnection connection : tcpConnections)
            if (value != null)
                connection.sendString(value);
    }
}
