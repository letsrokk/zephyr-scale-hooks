package com.github.letsrokk.adaptavist.client.model;

/**
 * Created by JacksonGenerator on 2/26/20.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Execution {

    @JsonProperty("projectKey")
    private String projectKey;

    @JsonProperty("testCaseKey")
    private String testCaseKey;

    @JsonProperty("status")
    private String status;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("assignedTo")
    private String assignedTo;
    @JsonProperty("executedBy")
    private String executedBy;

    @JsonProperty("environment")
    private String environment;

    @JsonProperty("executionTime")
    private Long executionTime;

    @JsonProperty("plannedStartDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime plannedStartDate;
    @JsonProperty("actualStartDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime actualStartDate;

    @JsonProperty("actualEndDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime actualEndDate;
    @JsonProperty("plannedEndDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime plannedEndDate;

    @JsonProperty("customFields")
    private HashMap<String, String> customFields;

    @JsonProperty("scriptResults")
    private List<ScriptResult> scriptResults;

}