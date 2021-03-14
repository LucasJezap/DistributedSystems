package homework;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

class TeamReadMessage extends Thread {
    final Team team;
    final String teamName;
    final ConnectionFactory factory;
    final Connection connection;
    final Channel channel;
    final ArrayList<String> queueNames = new ArrayList<>();
    final String[] exchangeNames = {"team_exchange2", "team_exchange3"};

    TeamReadMessage(Team team, String teamName) throws IOException, TimeoutException {
        this.team = team;
        this.teamName = teamName;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeNames[0], BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(exchangeNames[1], BuiltinExchangeType.TOPIC);
        channel.basicQos(2);

        queueNames.add(teamName + "_supplier_queue");
        queueNames.add(teamName + "_admin_queue");
        channel.queueDeclare(queueNames.get(0), false, false, false, null);
        channel.queueDeclare(queueNames.get(1), false, false, false, null);
        channel.queueBind(queueNames.get(0), exchangeNames[0], teamName);
        channel.queueBind(queueNames.get(1), exchangeNames[1], "Team.#");
    }

    @Override
    public void run() {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String[] message = new String(body, StandardCharsets.UTF_8).split(" ");

                if (message[0].equals("[ADMIN]")) { // admin
                    System.out.print("\r" + String.join(" ", message) + "\nYour order: ");
                } else {                     // supplier
                    System.out.println("Supplier [" + message[1] + "] has send you: [" + message[2] + "]");
                    team.numberOfProducts -= 1;
                    if (team.numberOfProducts == 0)
                        team.readDone = true;
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        try {
            for (String queueName : queueNames)
                channel.basicConsume(queueName, false, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!Thread.currentThread().isInterrupted())
            Thread.onSpinWait();

        try {
            channel.queueDelete(queueNames.get(0));
            channel.queueDelete(queueNames.get(1));
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
