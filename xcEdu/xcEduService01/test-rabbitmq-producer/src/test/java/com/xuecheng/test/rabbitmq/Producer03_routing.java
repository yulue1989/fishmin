package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer03_routing {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";
    //routing key
    private static final String ROUTINGKEY_EMAIL = "routingkey_email";
    private static final String ROUTINGKEY_SMS = "routingkey_sms";

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
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            //声明交换机
            /**
             * 参数明细
             * 1.交换机的名称
             * 2.交换机的类型,BuiltinExchangeType常量
             * fanout:对应模式就是发布订阅publish、subscribe模式
             * direct：对应Routing工作模式
             * topic：对应topics工作模式
             * headers：对应headers工作模式
             */
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            //队列和交换机进行绑定
            /**
             * 参数明细
             * 1.queue 队列名称
             * 2.exchange 交换机名称
             * 3.routingKey 路由key在发布订阅模式中为空串
             */
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_ROUTING_INFORM,ROUTINGKEY_EMAIL);
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_ROUTING_INFORM,"inform");
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_ROUTING_INFORM,ROUTINGKEY_SMS);
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_ROUTING_INFORM,"inform");

            /*//发消息,要指定routingkey
            for (int i = 0;i<5;i++){
                String message = "send email message to user"+System.currentTimeMillis();

                channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTINGKEY_EMAIL, null, message.getBytes());
                System.out.println("Send Email is:'" + message + "'");
            }
            //发消息,要指定routingkey
            for (int i = 0;i<5;i++){
                String message = "send sms message to user"+System.currentTimeMillis();

                channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTINGKEY_SMS, null, message.getBytes());
                System.out.println("Send Sms is:'" + message + "'");
            }*/
            for (int i = 0;i<5;i++){
                String message = "send inform to user"+System.currentTimeMillis();

                channel.basicPublish(EXCHANGE_ROUTING_INFORM, "inform", null, message.getBytes());
                System.out.println("Send inform is:'" + message + "'");
            }

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
