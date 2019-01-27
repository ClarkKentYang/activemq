package com.milan.product;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicProducerTest {

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
        //5.得到生产者
        MessageProducer producer = session.createProducer(null);
        //6、表示不持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //7.发送消息
        for (int i = 0; i < 5; i++) {
            sendMessage(session,producer,"I'm producer :"+ i);
        }
        //http://localhost:8161/ 可以查看对面信息
    }

    public static void sendMessage(Session session,MessageProducer producer,String s) throws JMSException{
        //创建文本消息
        TextMessage textMessage = session.createTextMessage("hello,activemq "+s);
        Destination destination = session.createTopic("milan_topic");
        producer.send(destination,textMessage);
    }

}
