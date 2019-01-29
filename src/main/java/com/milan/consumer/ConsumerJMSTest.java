package com.milan.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ConsumerJMSTest {

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
        //6.得到消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //7.接收消息
        while(true){
            TextMessage textMessage = (TextMessage) consumer.receive();
            if(textMessage!=null){
                System.out.println(textMessage.getText());
                //textMessage.acknowledge();//表示手动签收
                session.commit();//提交事务
            }
        }
    }

}
