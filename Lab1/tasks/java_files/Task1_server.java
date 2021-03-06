package java_files;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

@SuppressWarnings("all")
public class Task1_server {

    public static void main(String[] args) {
        System.out.println("JAVA UDP SERVER");
        DatagramSocket socket = null;
        int portNumber = 9008;

        try {
            socket = new DatagramSocket(portNumber);
            byte[] sendBuffer = "Pong Java Udp".getBytes();
            byte[] receiveBuffer = new byte[1024];

            while (true) {
                // receive
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                System.out.println("server received msg: " + msg);
                // send
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
