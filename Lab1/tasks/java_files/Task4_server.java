package java_files;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

@SuppressWarnings("all")
public class Task4_server {

    public static void main(String[] args) {
        System.out.println("JAVA UDP SERVER");
        DatagramSocket socket = null;
        int portNumber = 9011;
        int javaClientPort = 9012;
        int pythonClientPort = 9013;

        try {
            socket = new DatagramSocket(portNumber);
            byte[] sendBufferJava = "Java Server says: Hello Java Client!".getBytes();
            byte[] sendBufferPython = "Java Server says: Hello Python Client!".getBytes();
            byte[] receiveBuffer = new byte[1024];

            while (true) {
                // receive
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData(), "UTF-8");
                System.out.println("received msg: " + msg);
                // send
                DatagramPacket sendPacket = null;
                if (receivePacket.getPort() == javaClientPort)
                    sendPacket = new DatagramPacket(sendBufferJava, sendBufferJava.length,
                            receivePacket.getAddress(), receivePacket.getPort());
                else
                    sendPacket = new DatagramPacket(sendBufferPython, sendBufferPython.length,
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
