package management.sttock.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
//    private final RedisConnectionFactory redisConnectionFactory;
//
//    @Bean
//    public RedisTemplate<?, ?> redisTemplate() {
//        RedisTemplate<?, ?> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.afterPropertiesSet();
//
//        template.execute((RedisCallback<Object>) connection -> {
//            connection.getConfig("stop-writes-on-bgsave-error");
//            connection.setConfig("stop-writes-on-bgsave-error", "no");
//            return "OK";
//        });
//        return template;
//    }
}
