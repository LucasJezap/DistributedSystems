package java_files;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

@SuppressWarnings("all")
public class Task4_client {

    public static void main(String[] args) throws Exception
    {
        System.out.println("JAVA UDP CLIENT");
        DatagramSocket socket = null;
        int portNumber = 9011;
        int clientPort = 9012;

        try {
            socket = new DatagramSocket(clientPort);
            InetAddress address = InetAddress.getByName("localhost");
            byte[] sendBuffer = "Java Client says: Hello Java Server!".getBytes();
            byte[] receiveBuffer = new byte[1024];

            while(true) {
                // send
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                socket.send(sendPacket);
                // receive
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                System.out.println("received msg: " + msg);
                Thread.sleep(1000);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
