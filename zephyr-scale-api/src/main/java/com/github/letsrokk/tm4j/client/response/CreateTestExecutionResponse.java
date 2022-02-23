package com.github.letsrokk.tm4j.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTestExecutionResponse {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("self")
    private String self;
    @JsonProperty("key")
    private String key;

}