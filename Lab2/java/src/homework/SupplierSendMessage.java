package homework;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

class SupplierSendMessage extends Thread {
    final Supplier supplier;
    final String supplierName;
    final ConnectionFactory factory;
    final Connection connection;
    final Channel channel;
    final String exchangeName = "team_exchange2";

    SupplierSendMessage(Supplier supplier, String supplierName) throws IOException, TimeoutException {
        this.supplier = supplier;
        this.supplierName = supplierName;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!supplier.readyToOrder) {
                try {
                    System.out.println("Sending [" + supplier.teamName + "] a product: ["  + supplier.order.toString() + "]");
                    String message = supplier.teamName + " " + supplierName + " " + supplier.order.toString();
                    channel.basicPublish(exchangeName, supplier.teamName,
                            null, message.getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                supplier.readyToOrder = true;
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
