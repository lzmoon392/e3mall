package cn.e3mall.message;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: jerry
 * @create: 2020-04-16 23:27
 */
public class MessageConsumer {
    @Test
    public void msgConsumer() throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //等待
        System.in.read();
    }
}
