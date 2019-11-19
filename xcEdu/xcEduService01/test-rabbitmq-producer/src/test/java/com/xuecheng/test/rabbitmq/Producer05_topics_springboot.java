package com.xuecheng.test.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05_topics_springboot {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testSendEmail(){
        String message = "send email message to user";

        rabbitTemplate.convertAndSend("ex_routing_cms_postpage","5a751fab6abb5044e0d19ea1",message);
    }

}
