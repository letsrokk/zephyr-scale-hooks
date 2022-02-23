package com.github.letsrokk.tm4j.client.model;

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
public class IssueLink {

    @JsonProperty("self")
    private String self;
    @JsonProperty("issueId")
    private Integer issueId;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("target")
    private String target;
    @JsonProperty("type")
    private String type;

}
