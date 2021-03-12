package chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class ClientUdpThread extends Thread {
    private final String host;
    private final int port;

    private final ChatClient chatClient;
    private boolean socketToClose = true;
    private DatagramSocket datagramSocket = null;

    ClientUdpThread(String host, int port, ChatClient chatClient) {
        this.host = host;
        this.port = port;
        this.chatClient = chatClient;
    }

    @Override
    public void run() {
        System.out.println("UDP channel: UP");
        try {
            socketToClose = false;
            datagramSocket = new DatagramSocket();
            byte[] buffer = new byte[1024];

            this.sendMessage("---STARTUDP---");

            while (chatClient.isOnline()) {
                Arrays.fill(buffer, (byte) 0);
                DatagramPacket serverMessage = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(serverMessage);

                String message = new String(serverMessage.getData()).trim();
                System.out.print('\r' + message + '\n' + chatClient.getNickname() + ": ");
            }
        } catch (Exception e) {
            if (!socketToClose) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            InetAddress address = InetAddress.getByName(host);
            byte[] buffer = message.getBytes(StandardCharsets.UTF_8);

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            datagramSocket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeSocket() {
        socketToClose = true;
        datagramSocket.close();
    }
}
