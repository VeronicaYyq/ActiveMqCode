package com.atguigu.activemq;

import com.sun.corba.se.impl.orb.ParserTable;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author MK
 * @create 2019-09-10 20:09
 */
public class JMSConsumer {
    public static final String MQ_URL = "tcp://192.168.230.230:61616";
    public static final String QUEUE="queue";
    public static void main(String[] args) throws JMSException {
        //1.创建connectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        //2.创建connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //将消费者签收的参数改为手动的时候，会一直重复消费,所以必须使用msg.acknowledge();
       // Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
        //4.session创建目的地
        Queue queue = session.createQueue(QUEUE);
        //5.创建consumer
        MessageConsumer consumer = session.createConsumer(queue);
        //6.接收消息(接收和发送的信息类型要一致)
        //同步阻塞方式receive（），订阅者或者接收者调用messageconsumer的receive方法来接收消息
       /* while(true){
            TextMessage message= (TextMessage) consumer.receive();
            if (null!=message)
            System.out.println("接收到的消息是："+message.getText());
            else
                break;
        }

        //7.关闭资源
        consumer.close();
        session.close();
        connection.close();*/
       //通过监听器的方式
        consumer.setMessageListener((Message msg) -> {
            if(null!=msg&&msg instanceof TextMessage){
               TextMessage textMessage= (TextMessage) msg;
                try {
                    System.out.println("**********收到的消息是："+textMessage.getText());
                   // msg.acknowledge();
                } catch (JMSException e) {


                }
            }
        });

    }
}
