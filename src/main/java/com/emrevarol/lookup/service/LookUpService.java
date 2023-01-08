package com.emrevarol.lookup.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.emrevarol.lookup.configuration.RedisConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LookUpService {

    private final RedisTemplate<String, String> redisRepository;

    @Cacheable(RedisConfig.URL_CACHE)
    public List<MutablePair<String, String>> getUrls(Set<String> keys) {
        return keys.stream().map(s -> new MutablePair<>(s, findUrl(s))).toList();
    }

    private String findUrl(String url) {
        String s = redisRepository.opsForValue().get(url);
        if (!StringUtils.isEmpty(s)) {
            return s;
        }

        String urlCopy = url;

        do {
            //check if url contains parameters
            if (urlCopy.contains("&") || urlCopy.contains("?")) {
                urlCopy = removeLastParameter(urlCopy);
            } else {
                return url;
            }
        } while (StringUtils.isEmpty(redisRepository.opsForValue().get(urlCopy)));

        String difference = StringUtils.difference(urlCopy, url);
        String value = redisRepository.opsForValue().get(urlCopy);

        return (!StringUtils.isEmpty(difference) && difference.charAt(0) == '&') ?
            value + "?".concat(difference.substring(1)) :
            value + difference;
    }

    private String removeLastParameter(String url) {
        if (url.contains("&")) {
            int index = url.lastIndexOf("&");
            url = url.substring(0, index);
        } else if (url.contains("?")) {
            url = url.split("\\?")[0];
        }
        return url;
    }

}
