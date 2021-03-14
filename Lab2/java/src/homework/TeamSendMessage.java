package homework;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

class TeamSendMessage extends Thread {
    final Team team;
    final String teamName;
    final ConnectionFactory factory;
    final Connection connection;
    final Channel channel;
    final String exchangeName = "team_exchange";

    TeamSendMessage(Team team, String teamName) throws IOException, TimeoutException {
        this.team = team;
        this.teamName = teamName;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!team.sendDone) {
                for (Product product : team.orders) {
                    if (!product.equals(Product.ERROR))
                        try {
                            System.out.println("Sending an order for [" + product.toString() + "]");
                            String message = teamName + " " + product.toString();
                            channel.basicPublish(exchangeName, product.toString(),
                                    null, message.getBytes(StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
                team.sendDone = true;
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
