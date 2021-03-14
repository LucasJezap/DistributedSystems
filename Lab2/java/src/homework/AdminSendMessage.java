package homework;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

class AdminSendMessage extends Thread {
    final Admin admin;
    final String adminName;
    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final ConnectionFactory factory;
    final Connection connection;
    final Channel channel;
    final String exchangeName = "team_exchange3";

    AdminSendMessage(Admin admin, String adminName) throws IOException, TimeoutException {
        this.admin = admin;
        this.adminName = adminName;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String message = br.readLine();
                String key = "";
                if (message.startsWith("-t ")) {
                    key = "Team";
                } else if (message.startsWith("-s ")) {
                    key = "Supplier";
                } else if (message.startsWith("-a ")) {
                    key = "Team.Supplier";
                } else
                    System.out.println("Incorrect message!");
                message = "[ADMIN] " + message.substring(3);
                channel.basicPublish(exchangeName, key,
                        null, message.getBytes(StandardCharsets.UTF_8));

            } catch (Exception e) {
                break;
            }
        }

        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
