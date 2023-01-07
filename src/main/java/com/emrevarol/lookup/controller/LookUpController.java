package com.emrevarol.lookup.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emrevarol.lookup.service.LookUpService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lookup")
public class LookUpController {

    private final LookUpService lookUpService;

    @PostMapping("/findPrettyUrls")
    public List<MutablePair<String, String>> lookUpKey(@RequestBody String[] urlArray) {
        Set<String> keys = Arrays.stream(urlArray).collect(Collectors.toSet());
        return lookUpService.getPrettyUrls(keys);
    }

    @PostMapping("/findParameterizedUrls")
    public List<MutablePair<String, String>> getParameterizedUrls(@RequestBody String[] keyArray) {
        Set<String> keys = Arrays.stream(keyArray).collect(Collectors.toSet());
        return lookUpService.getParameterizedUrls(keys);
    }
}