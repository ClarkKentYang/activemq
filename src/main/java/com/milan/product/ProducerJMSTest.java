package com.milan.product;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * JMS消息可靠机制：
 int AUTO_ACKNOWLEDGE = 1;      自动签收
 int CLIENT_ACKNOWLEDGE = 2;    手动签收
 int DUPS_OK_ACKNOWLEDGE = 3;   不是必须签收
 int SESSION_TRANSACTED = 0;    事务
 */
public class ProducerJMSTest {

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
        //第一个boolean值的参数，表示是否开启事务
        Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
        //5.创建队列
        Destination destination = session.createQueue("milan_queue");
        //6.得到生产者
        MessageProducer producer = session.createProducer(destination);
        //7、表示不持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //8.发送消息
        for (int i = 0; i < 5; i++) {
            sendMessage(session,producer,"I'm producer :"+ i);
            session.commit();//提交事务
        }
        //http://localhost:8161/ 可以查看对面信息
    }

    public static void sendMessage(Session session,MessageProducer producer,String s) throws JMSException{
        //创建文本消息
        TextMessage textMessage = session.createTextMessage("hello,activemq "+s);
        producer.send(textMessage);
    }

}
