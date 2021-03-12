package homework;

import com.rabbitmq.client.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Team {

    public static void main(String[] argv) throws Exception {

        // getting team name
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        welcomeTeam(br);

        // creating connection and channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // routing key
        System.out.print("Please provide routing key: ");
        String routingKey = br.readLine();

        // exchange
        String EXCHANGE_NAME = "exchange1";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT   );

        while (true) {

            // read msg
            System.out.print("Enter message: ");
            String message = br.readLine();

            // break condition
            if ("exit".equals(message)) {
                break;
            }

            // publish
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Sent: " + message);
        }

        channel.close();
        connection.close();
    }

    private static void welcomeTeam(BufferedReader br) throws IOException {
        System.out.print("Provide your team name: ");
        String teamName = br.readLine();
        System.out.println("Welcome " + teamName);
    }
}
