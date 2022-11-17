package soccerfriend.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    /**
     * key를 통해 string 형태의 value를 반환합니다.
     *
     * @param key
     * @return value
     */
    public String getStringData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * 해당 key의 데이터를 삭제합니다.
     *
     * @param key
     */
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}