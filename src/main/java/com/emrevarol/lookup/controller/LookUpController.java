package com.emrevarol.lookup.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emrevarol.lookup.model.UrlDto;
import com.emrevarol.lookup.service.LookUpService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lookup")
public class LookUpController {

    private final LookUpService lookUpService;

    @PostMapping("/findPrettyUrls")
    public ResponseEntity<Object> lookUpKey(@RequestBody @Valid UrlDto urlDto) {
        try {
            log.info("Getting pretty urls request started. UrlDto: {}", urlDto);
            Set<String> keys = new HashSet<>(urlDto.getUrls());
            return ResponseEntity.status(HttpStatus.OK).body(lookUpService.getUrls(keys));
        }
        catch (Exception e) {
            log.warn("Internal server error while getting paramterized urls", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(e.getMessage());
        }
    }

    @PostMapping("/findParameterizedUrls")
    public ResponseEntity<Object> getParameterizedUrls(@RequestBody @Valid UrlDto urlDto) {
        try {
            log.info("Getting parameterized urls request started. UrlDto: {}", urlDto);
            Set<String> keys = new HashSet<>(urlDto.getUrls());
            return ResponseEntity.status(HttpStatus.OK).body(lookUpService.getUrls(keys));
        }
        catch (Exception e) {
            log.warn("Internal server error while getting paramterized urls", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(e.getMessage());
        }
    }
}