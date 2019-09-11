package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.swing.text.StyledEditorKit;

/**
 * @author MK
 * @create 2019-09-10 21:22
 */
public class JMSConsumer02 {
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
        MessageConsumer consumer = session.createConsumer(topic);
        //6.创建信息
      while (true){
         TextMessage message= (TextMessage) consumer.receive();
         if(null!=message) {
             System.out.println("收到的消息是：" + message.getText());
         }else {
             break;
         }
      }

      consumer.close();
      session.close();
      connection.close();


    }
}
