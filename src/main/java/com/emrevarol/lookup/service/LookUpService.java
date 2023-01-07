package com.emrevarol.lookup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class LookUpService {

    private final Map<String, String> keyValueMap = new HashMap<>();

    @PostConstruct
    private void init() {
        keyValueMap.put("/products", "/Fashion");
        keyValueMap.put("/products?gender=female", "Women");
        keyValueMap.put("/products?tag=5678", "/Boat--Shoes");
        keyValueMap.put("/products?gender=female&tag=123&tag=1234", "/Women/Shoes");
        keyValueMap.put("/products?gender=female&tag=123&tag=1234&tag=3535", "/Women/Bags");
        keyValueMap.put("/products?brand=123", "/Adidas/");
        keyValueMap.put("/products?brand=123&tag=333", "/Adidas/Hats");
    }

    public List<MutablePair<String, String>> getPrettyUrls(Set<String> keys) {
        return keys.stream().map(s -> new MutablePair<>(s, findPrettyUrls(s))).toList();
    }

    public List<MutablePair<String, String>> getParameterizedUrls(Set<String> keys) {
        return keys.stream().map(s -> new MutablePair<>(s, findParameterizedUrls(s))).toList();
    }

    private String findParameterizedUrls(String value) {
        for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }


    private String findPrettyUrls(String key) {
        if (keyValueMap.containsKey(key)) {
            return keyValueMap.get(key);
        }

        String keyCopy = key;

        do {
            if (keyCopy.contains("&") || keyCopy.contains("?")) {
                keyCopy = removeLastParameter(keyCopy);
            } else {
                break;
            }
        } while (!keyValueMap.containsKey(keyCopy));

        String difference = StringUtils.difference(keyCopy, key);
        String value = keyValueMap.get(keyCopy);
        if (difference.charAt(0) == '&') {
            value += "?".concat(difference.substring(1, difference.length() - 1));
        } else {
            value += difference;
        }
        return value;
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
