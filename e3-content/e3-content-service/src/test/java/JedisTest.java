import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: jerry
 * @create: 2020-04-13 00:11
 */
public class JedisTest {

    @Test
    public void jedisTest() throws Exception{
        //创建Jedis对象,参数：host port
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //直接使用jedis操作Redis。所有redis命令都对应一个方法
        jedis.set("123", "456");
        System.err.println(jedis.get("123"));
        //关闭连接
        jedis.close();

    }

    @Test
    public void testJedisPoll() {
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("pool", "xxx");
        System.err.println(jedis.get("pool"));
        jedis.close();
        jedisPool.close();
    }

    @Test
    public void testJedisCluster() {
        Set<HostAndPort> node = new HashSet<>();
        node.add(new HostAndPort("127.0.0.1", 7001));
        node.add(new HostAndPort("127.0.0.1", 7002));
        node.add(new HostAndPort("127.0.0.1", 7003));
        node.add(new HostAndPort("127.0.0.1", 7004));
        node.add(new HostAndPort("127.0.0.1", 7005));
        node.add(new HostAndPort("127.0.0.1", 7006));
        JedisCluster jedisCluster = new JedisCluster(node);
        jedisCluster.set("test", "123");
        System.err.println(jedisCluster.get("test"));
        jedisCluster.close();

    }
}
