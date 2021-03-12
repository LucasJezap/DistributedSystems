package chat;

import java.io.IOException;
import java.net.*;

class ClientMulticastThread extends Thread {
    public MulticastSocket socket = null;
    private SocketAddress socketAddress;
    private NetworkInterface networkInterface;

    private boolean socketToClose = true;
    private final ChatClient client;

    ClientMulticastThread(ChatClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        System.out.println("Multicast channel: UP");
        try {
            socketToClose = false;


            InetAddress inetAddress = InetAddress.getByName(ChatClient.multicastAddress);
            socketAddress = new InetSocketAddress(inetAddress, ChatClient.multicastPort);

            socket = new MulticastSocket(ChatClient.multicastPort);
            networkInterface = NetworkInterface.getByInetAddress(inetAddress);
            socket.joinGroup(socketAddress, networkInterface);

            byte[] buff = new byte[1024];
            while (client.isOnline()) {
                DatagramPacket packet = new DatagramPacket(buff, buff.length);
                socket.receive(packet);

                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.print('\r' + msg + '\n' + client.getNickname() + ": ");
            }
        } catch (IOException ioe) {
            if (!socketToClose) {
                ioe.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            byte[] msgBuff = message.getBytes();
            DatagramPacket packet = new DatagramPacket(msgBuff, msgBuff.length, socketAddress);

            socket.leaveGroup(socketAddress, networkInterface);
            socket.send(packet);
            socket.joinGroup(socketAddress, networkInterface);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void closeSocket() {
        socketToClose = true;
        socket.close();
    }
}
