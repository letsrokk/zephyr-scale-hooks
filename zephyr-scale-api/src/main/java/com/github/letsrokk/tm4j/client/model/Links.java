package com.github.letsrokk.tm4j.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Links {

    @JsonProperty("self")
    private String self;

    @JsonProperty("issues")
    private List<IssueLink> issues;
    @JsonProperty("webLinks")
    private List<WebLink> webLinks;
    @JsonProperty("testPlans")
    private List<TestPlanLink> testPlans;
}
