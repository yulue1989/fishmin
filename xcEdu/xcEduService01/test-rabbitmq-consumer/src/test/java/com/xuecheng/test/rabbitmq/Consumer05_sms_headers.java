package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Consumer05_sms_headers {
    //队列名称
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_HEADER_INFORM = "exchange_header_inform";
    //routing key
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;

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
        channel.queueDeclare(EXCHANGE_HEADER_INFORM, true, false, false, null);
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
        channel.exchangeDeclare(EXCHANGE_HEADER_INFORM, BuiltinExchangeType.HEADERS);

        //HEADERS模式声明键值对
        Map<String, Object> headers_sms = new Hashtable<>();
        headers_sms.put("inform_type","sms");
        //队列和交换机进行绑定
        /**
         * 参数明细
         * 1.queue 队列名称
         * 2.exchange 交换机名称
         * 3.routingKey 路由key在发布订阅模式中为空串
         */
        channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_HEADER_INFORM,"",headers_sms);
        //定义消费方法
        DefaultConsumer consumer = new DefaultConsumer(channel){
            /*** 消费者接收消息调用此方法
             * * @param consumerTag 消费者的标签，在channel.basicConsume()去指定
             * * @param envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志 (收到消息失败后是否需要重新发送)
             * * @param properties
             * @param body
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //交换机
                String exchange = envelope.getExchange();
                //路由key
                String routingKey = envelope.getRoutingKey();
                //消息id
                long deliveryTag = envelope.getDeliveryTag();
                //消息内容
                String msg = new String(body,"utf-8");
                System.out.println("receive-sms..."+msg);

            }
        };

        /*监听队列,监听队列String queue, boolean autoAck,Consumer callback
         * * 参数明细 * 1、队列名称 *
         * 2、是否自动回复，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置 为false则需要手动回复
         *  3、消费消息的方法，消费者接收到消息后调用此方法
         */
        channel.basicConsume(QUEUE_INFORM_SMS,true,consumer);
    }
}
