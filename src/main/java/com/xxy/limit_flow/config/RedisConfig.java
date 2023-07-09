package com.xxy.limit_flow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class RedisConfig {

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
//
//        // 配置 Redis 主机和端口号
//        connectionFactory.setHostName("localhost");
//        connectionFactory.setPort(6379);
//
//        return connectionFactory;
//    }
    @Bean
    public RedisTemplate<Object, Object> jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        //设置String类型的序列化器：调用setStringSerializer方法，将StringRedisSerializer实例设置为键（key）和值（value）的序列化器。
        // 在向Redis存储数据时，会将String类型的数据转换为字节数组，以便保存到Redis中。
        template.setStringSerializer(new StringRedisSerializer());

        //配置json类型的序列化工具
        //配置默认的序列化工具：调用setDefaultSerializer方法，将Jackson2JsonRedisSerializer实例设置为默认的序列化器。
        // Jackson2JsonRedisSerializer是一个JSON格式的序列化工具，用于将非String类型的Java对象序列化为JSON字符串，
        // 并存储到Redis中。在从Redis中读取数据时，会将JSON字符串反序列化为对应的Java对象。
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer(Object.class));
        //设置连接工厂：调用setConnectionFactory方法，将传入的redisConnectionFactory作为连接工厂，
        // 用于与Redis建立连接和执行操作。
        template.setConnectionFactory(redisConnectionFactory);
        //将配置好的RedisTemplate对象返回。
        return template;
    }
}
