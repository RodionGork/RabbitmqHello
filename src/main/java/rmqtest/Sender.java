package rmqtest;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;

public class Sender {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare(Settings.QUEUE_NAME, false, false, false, Collections.emptyMap());

        String msg;
        do {
            System.out.println("Enter your message:");
            msg = in.nextLine();
            channel.basicPublish("", Settings.QUEUE_NAME, new AMQP.BasicProperties(), msg.getBytes());
            System.out.println("        - your message was sent!");
        } while (!msg.equalsIgnoreCase("bye"));

        channel.close();
        conn.close();
    }
}
