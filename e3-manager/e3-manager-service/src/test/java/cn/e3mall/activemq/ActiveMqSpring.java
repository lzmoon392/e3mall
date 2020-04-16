package cn.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author: jerry
 * @create: 2020-04-16 22:53
 */
public class ActiveMqSpring {
    @Test
    public void sendMessage() throws Exception {
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //从容器中获得JMSTemplate对象
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        //从容器中获得一个Destination对象
        Destination destination = (Destination) applicationContext.getBean("queueDestination");
        //发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("hello spring");
            }
        });
    }
}
