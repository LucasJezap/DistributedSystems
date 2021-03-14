package homework;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

class AdminReadMessage extends Thread {
    final Admin admin;
    final String adminName;
    final ConnectionFactory factory;
    final Connection connection;
    final Channel channel;
    final String[] queueNames = {"admin_team_queue_read", "admin_supplier_queue_read"};
    final String[] exchangeNames = {"team_exchange", "team_exchange2"};

    AdminReadMessage(Admin admin, String adminName) throws IOException, TimeoutException {
        this.admin = admin;
        this.adminName = adminName;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeNames[0], BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(exchangeNames[1], BuiltinExchangeType.TOPIC);
        channel.basicQos(2);

        for (int i = 0; i < queueNames.length; i++) {
            channel.queueDeclare(queueNames[i], false, false, false, null);
            channel.queueBind(queueNames[i], exchangeNames[i], "*");
        }
    }

    @Override
    public void run() {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String[] message = new String(body, StandardCharsets.UTF_8).split(" ");
                if (message.length == 2) // team
                    System.out.println("Team [" + message[0] + "] has requested: [" + message[1] + "]");
                else                     // supplier
                    System.out.println("Supplier [" + message[1] + "] has send: [" + message[2] + "] to team [" + message[0] + "]");

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
            for (String queueName : queueNames)
                channel.queueDelete(queueName);
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
