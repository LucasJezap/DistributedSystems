package task2;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Z2_Consumer {

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Z2 CONSUMER");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // routing key
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please provide routing keys (separated by space): ");
        String[] routingKeys = br.readLine().split(" ");

        // exchange
        String EXCHANGE_NAME = "exchange2";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // queue & bind
        String queueName = channel.queueDeclare().getQueue();
        for (String s: routingKeys)
            channel.queueBind(queueName, EXCHANGE_NAME, s);
        System.out.println("created queue: " + queueName);

        // consumer (message handling)
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received: " + message);
            }
        };

        // start listening
        System.out.println("Waiting for messages...");
        channel.basicConsume(queueName, true, consumer);
    }
}
