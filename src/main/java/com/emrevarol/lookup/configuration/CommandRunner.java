package com.emrevarol.lookup.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommandRunner implements CommandLineRunner {
    private final RedisTemplate<String, String> redisRepository;

    @Override
    public void run(String... args) {
        ValueOperations<String, String> operations = redisRepository.opsForValue();
        Map<String, String> keysMap = new HashMap<>();
        keysMap.put("/products", "/Fashion/");
        keysMap.put("/products?gender=female", "/Women/");
        keysMap.put("/products?tag=5678", "/Boat--Shoes/");
        keysMap.put("/products?gender=female&tag=123&tag=1234", "/Women/Shoes/");
        keysMap.put("/products?gender=female&tag=123&tag=1234&tag=444", "/Women/Boots");
        keysMap.put("/products?brand=123", "/Adidas/");
        keysMap.putAll(invertMap(keysMap));
        keysMap.forEach(operations::set);
    }

    // https://stackoverflow.com/a/7147052/5685457
    private <K, V> Map<V, K> invertMap(Map<K, V> toInvert) {
        Map<V, K> result = new HashMap<>();
        for (K k : toInvert.keySet()) {
            result.put(toInvert.get(k), k);
        }
        return result;
    }
}