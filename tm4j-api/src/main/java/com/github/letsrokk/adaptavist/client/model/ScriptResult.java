package com.github.letsrokk.adaptavist.client.model;

/**
 * Created by JacksonGenerator on 2/26/20.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScriptResult {
    @JsonProperty("index")
    private Long index;
    @JsonProperty("status")
    private String status;
    @JsonProperty("comment")
    private String comment;
}