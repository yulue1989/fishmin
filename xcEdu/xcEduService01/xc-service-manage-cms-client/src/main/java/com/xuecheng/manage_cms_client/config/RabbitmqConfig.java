package com.xuecheng.manage_cms_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    //队列Bean的名称
    public static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    //交换机名称
    public static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";
    //队列的名称
    @Value("${xuecheng.mq.queue}")
    public String queue_cms_postpage_name;
    //routingKey即站点id
    @Value("${xuecheng.mq.routingKey}")
    public String routingKey;

    /**
     * 交换机配置使用direct类型
     * @return
     */
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange EXCHANGE_TOPICS_INFORM(){
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    /**
     * 声明队列
     * @return
     */
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue QUEUE_CMS_POSTPAGE(){
        Queue queue = new Queue(queue_cms_postpage_name);
        return queue;
    }

    /**
     * 绑定交换机和队列
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_CMS(@Qualifier(QUEUE_CMS_POSTPAGE) Queue queue,@Qualifier(EX_ROUTING_CMS_POSTPAGE) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
}
