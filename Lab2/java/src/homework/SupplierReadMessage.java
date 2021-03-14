package homework;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

class SupplierReadMessage extends Thread {
    final Supplier supplier;
    final String supplierName;
    final ConnectionFactory factory;
    final Connection connection;
    final Channel channel;
    final ArrayList<String> queueNames = new ArrayList<>();
    final String[] exchangeNames = {"team_exchange", "team_exchange3"};

    SupplierReadMessage(Supplier supplier, String supplierName) throws IOException, TimeoutException {
        this.supplier = supplier;
        this.supplierName = supplierName;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeNames[0], BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(exchangeNames[1], BuiltinExchangeType.TOPIC);
        channel.basicQos(supplier.products.size() + 1);

        queueNames.add(supplierName + "_admin_queue");
        channel.queueDeclare(queueNames.get(0), false, false, false, null);
        channel.queueBind(queueNames.get(0), exchangeNames[1], "#.Supplier");
        for (Product product : supplier.products) {
            String queueName = product.toString();
            queueNames.add(queueName);
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, exchangeNames[0], product.toString());
        }
    }

    @Override
    public void run() {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String[] message = new String(body, StandardCharsets.UTF_8).split(" ");

                if (message[0].equals("[ADMIN]")) { // admin
                    System.out.println(String.join(" ", message));
                } else {                     // team
                    System.out.println("Team [" + message[0] + "] has requested: [" + message[1] + "]");
                    supplier.order = Product.getProduct(message[1]);
                    supplier.teamName = message[0];
                    supplier.readyToOrder = false;
                    while (!supplier.readyToOrder) {
                        Thread.onSpinWait();
                    }
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
            for (String queueName : queueNames)
                channel.queueDelete(queueName);
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
