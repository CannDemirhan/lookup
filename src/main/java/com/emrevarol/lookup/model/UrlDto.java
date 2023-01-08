package com.emrevarol.lookup.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UrlDto {

    @NotEmpty @Size(max = 100) List<String> urls;
}
