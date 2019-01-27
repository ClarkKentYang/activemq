package com.milan.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ConsumerTopicTest {

    public static void main(String[] args) throws JMSException{
        //1.创建一个工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        //3.启动连接
        connection.start();
        //4.创建会话工厂
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        //5.创建主题
        Destination destination = session.createTopic("milan_topic");
        //6.得到消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //7.接收消息
        while(true){
            Message receive = consumer.receive();
            if(receive!=null){
                System.out.println(receive.toString());
            }
        }
    }

}
