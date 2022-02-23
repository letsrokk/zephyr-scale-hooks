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
public class GetTestCyclesResponse {

    @JsonProperty("next")
    private String next;
    @JsonProperty("startAt")
    private Integer startAt;
    @JsonProperty("maxResults")
    private Integer maxResults;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("isLast")
    private Boolean isLast;

    @JsonProperty("values")
    private List<TestCycle> values;

}