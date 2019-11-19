package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbit入门程序
 */
public class Producer01 {

    private static final String QUEUE = "helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("192.168.33.128");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("yulue");
            connectionFactory.setPassword("123456");
            //设置虚拟机
            connectionFactory.setVirtualHost("/");
            //创建，链接和信道
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(QUEUE, true, false, false, null);
            String message = "helloworld小鱼"+System.currentTimeMillis();

            channel.basicPublish("", QUEUE, null, message.getBytes());
            System.out.println("Send Message is:'" + message + "'");
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (channel!=null){
                channel.close();
            }
            if(connection!=null){
                connection.close();
            }
        }


    }
}
