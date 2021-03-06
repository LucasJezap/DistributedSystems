package examples;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class Z1_Consumer {
    private final int timeToSleep = 1;

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Z1 CONSUMER");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // queue
        String QUEUE_NAME = "queue1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // consumer (handle msg)
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received: " + message);
            }
        };

        // start listening
        System.out.println("Waiting for messages...");
        channel.basicConsume(QUEUE_NAME, true, consumer);

        // close
//        channel.close();
//        connection.close();
    }
}
