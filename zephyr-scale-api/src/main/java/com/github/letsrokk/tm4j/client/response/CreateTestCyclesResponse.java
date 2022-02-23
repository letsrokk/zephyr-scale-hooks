package com.github.letsrokk.tm4j.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.letsrokk.tm4j.client.model.TestCycle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTestCyclesResponse {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("self")
    private String self;
    @JsonProperty("key")
    private String key;

}