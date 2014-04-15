package rmqtest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.util.Collections;

public class Receiver {

    public static void main(String... args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare(Settings.QUEUE_NAME, false, false, false, Collections.emptyMap());
        System.out.println("Waiting for messages...");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(Settings.QUEUE_NAME, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("Message: " + msg);
        }
    }

}
