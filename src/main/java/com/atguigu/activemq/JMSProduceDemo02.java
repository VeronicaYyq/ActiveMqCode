package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author MK
 * @create 2019-09-10 21:18
 */
public class JMSProduceDemo02 {//订阅模式的时候要先订阅再生产
    public static final String MYURL="tcp://192.168.230.230:61616";
    public static final String TOPIC_NAME="topic";
    public static void main(String[] args) throws JMSException {
        //1.创建connectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MYURL);
        //2.创建connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地
        Topic topic = session.createTopic(TOPIC_NAME);
        //5.创建producer
        MessageProducer producer = session.createProducer(topic);
        //6.创建信息
        for (int i = 1; i <=3 ; i++) {
            TextMessage textMessage = session.createTextMessage("topicMessage:"+i);
            producer.send(textMessage);

        }
        producer.close();
        session.close();
        connection.close();

        System.out.println("success.....");
    }
}


