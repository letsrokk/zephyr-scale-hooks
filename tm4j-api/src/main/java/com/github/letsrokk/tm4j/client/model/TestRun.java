package com.github.letsrokk.tm4j.client.model;

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
public class TestRun {

    @JsonProperty("projectKey")
    private String projectKey;
    @JsonProperty("key")
    private String key;
    @JsonProperty("name")
    private String name;
    @JsonProperty("folder")
    private String folder;

    @JsonProperty("version")
    private String version;

    @JsonProperty("status")
    private String status;

    @JsonProperty("estimatedTime")
    private Long estimatedTime;
    @JsonProperty("executionTime")
    private Long executionTime;

    @JsonProperty("plannedStartDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime plannedStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("plannedEndDate")
    private LocalDateTime plannedEndDate;

    @JsonProperty("iteration")
    private String iteration;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("issueKey")
    private String issueKey;
    @JsonProperty("issueCount")
    private Integer issueCount;
    @JsonProperty("testCaseCount")
    private Integer testCaseCount;

    @JsonProperty("items")
    private List<Execution> items;

    @JsonProperty("executionResultsSummary")
    private ExecutionSummary executionResultsSummary;

    @JsonProperty("customFields")
    private HashMap<String, String> customFields;

}
