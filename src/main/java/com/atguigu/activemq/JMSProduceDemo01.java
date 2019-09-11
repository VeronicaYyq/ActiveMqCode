package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author MK
 * @create 2019-09-10 19:41
 */
public class JMSProduceDemo01 {
    public static final String MQ_URL = "tcp://192.168.230.230:61616";
    public static final String MyQUEUE = "queue";
    public static void main(String[] args) throws JMSException {
        //1 通过ConnectionFactory工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        //2 获得connection对象并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3 通过connection对象获得session对象

        // 第一个参数叫mq的事务/第二个参数叫消息的签收，此时忽略用默认
        //事务的参数是false的时候
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //事务的参数是true的时候，客户端什么都没提交，要在session.close()关闭之前commit（）提交
        //Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        //将签收的参数改为手动，生产者的签收对于消费者没有影响
       // Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);



        //4 通过session获得目的地
        Queue queue = session.createQueue(MyQUEUE);
        //5 通过session获得消息的生产者
        MessageProducer producer = session.createProducer(queue);
        //6 开始生产3条消息发送到Activemq上
        for(int i=1;i<=3;i++){
            TextMessage textMessage = session.createTextMessage("现在正在发送"+i+"条消息");
            //7 用messageProducer发送消息到mq
            producer.send(textMessage);
        }

        //8 释放资源
        producer.close();
        //session.commit();
        session.close();
        connection.close();
        System.out.println("成功运行.....");
    }
}
