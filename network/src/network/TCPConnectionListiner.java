package network;

public interface TCPConnectionListiner {

    void onConnectionReady(TCPConnection tcpConnection);

    void onReceiveMessage(TCPConnection tcpConnection, String value);

    void onException(TCPConnection tcpConnection, Exception e);

    void onDisconnect(TCPConnection tcpConnection);
}
