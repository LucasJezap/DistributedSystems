package chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("all")
public class ServerUdpThread extends Thread {
    private final int port;
    DatagramSocket socket = null;

    private final ArrayList<SocketAddress> clientAddressList = new ArrayList<>();

    public ServerUdpThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            socket = new DatagramSocket(port);
            byte[] receiveBuffer = new byte[1024];

            while (!Thread.currentThread().isInterrupted()) {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData()).trim();

                if (msg.substring(0, receivePacket.getLength()).equals("---STARTUDP---")) {
                    clientAddressList.add(receivePacket.getSocketAddress());
                } else {
                    System.out.println(msg);

                    for (SocketAddress csa : clientAddressList) {
                        if (!csa.equals(receivePacket.getSocketAddress())) {
                            byte[] responseBuffer = msg.getBytes();
                            DatagramPacket responsePacket = new DatagramPacket(responseBuffer,
                                    responseBuffer.length, csa);
                            socket.send(responsePacket);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
