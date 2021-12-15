# Chat for locale network

### Description

This project is created manage a simplified version of an online chat.

The project was realised using 3 separated modules:

* ClientWindow
* Network
* Server

We can use client window and server modules separately. This two modules depends only on the network module.

***

### Network

This module connects server and client using sockets.

Server and Client have different comportment in case of receiving strings and etc

The interface was used to solve this situation, so we don't have to duplicate code.

```Java
public interface TCPConnectionListener {

    void onConnectionReady(TCPConnection tcpConnection);

    void onReceiveString(TCPConnection tcpConnection, String value);

    void onDisconnect(TCPConnection tcpConnection);

    void onException(TCPConnection tcpConnection, Exception e);
}
```

Method sendString using socket's output stream and sending string to client or server socket depending on situation.

Method disconnect is used to stop the thread and close the socket.
***

### Server

Simplified version of a server. Waiting for connection using serverSocket.

All the connections are stored in arrayList.

This class implements TCPConnectionListener.

#### onConnectionReady

When we get a new connection, we add it to the arrayList and sending message to all connections, using sendToAllClients
method

#### onReceiveString

When a connection get a string from Client, we're sending this string to all connections, using sendToAllClients method

#### onDisconnect

If client disconnect, we remove him from arrayList and notify other clients

#### onException

If we get an exception, we printing it in terminal

#### sendToAllClients

We iterate arrayList and send message to all clients using socket's output stream

***

### ClientWindow

This class implement simple user interface using swing. 

Up we have a text field where user can write his nickname
In the middle is placed text area where will be placed all messages
Down we have text field where user can write his message

Text field for messages is listened by ActionListener. When we press enter, we're sending
message to server's socket to which user is connected.

This class implements TCPConnectionListener.

#### OnConnectionReady

When user is successfully connected, we're printing message in his window, using printMessage method.

#### onReceiveString

When user's socket get a string from server, connection associated with user call this method, after 
what we print message in user's window using printMessage method

#### onDisconnect

When user disconnect from server, we print message in his window using printMessage method.

#### onException

If user get an exception, we print detail in his window

#### printMessage

We append received message to the text area and set caret to the end to scroll text area automatically
for user


