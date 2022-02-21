package com.github.letsrokk.tm4j.client.model;

/**
 * Created by JacksonGenerator on 2/26/20.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class ExecutionSummary {
    @JsonProperty("Pass")
    private Integer pass;
    @JsonProperty("Fail")
    private Integer fail;
}