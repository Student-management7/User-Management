//package com.easyWaySolution.User_Management.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
////
////@Configuration
////@EnableRedisRepositories
////public class RedisConfig  {
//////
//////    @Bean
//////    public JedisConnectionFactory jedisConnectionFactory(){
//////        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//////        redisStandaloneConfiguration.setHostName("s-m-s-redis");
//////        redisStandaloneConfiguration.setPort(6379);
//////        return new JedisConnectionFactory(redisStandaloneConfiguration);
//////    }
//////    @Bean
//////    public RedisTemplate<String , Object> redisTemplate(){
//////        RedisTemplate<String , Object> template = new RedisTemplate<>();
//////        template.setConnectionFactory(jedisConnectionFactory());
//////        template.setKeySerializer(new StringRedisSerializer());
//////        template.setHashKeySerializer(new StringRedisSerializer());
//////        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
//////        template.setValueSerializer(new JdkSerializationRedisSerializer());
//////        template.setEnableTransactionSupport(true);
//////        template.afterPropertiesSet();
//////        return template;
//////    }
////}
