package com.github.letsrokk.tm4j.client.request;

/**
 * Created by JacksonGenerator on 2/26/20.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.letsrokk.tm4j.client.model.TestScriptResult;
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
public class CreateTestExecutionRequest {

    @JsonProperty("projectKey")
    private String projectKey;
    @JsonProperty("testCaseKey")
    private String testCaseKey;
    @JsonProperty("testCycleKey")
    private String testCycleKey;
    @JsonProperty("statusName")
    private String statusName;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("assignedToId")
    private String assignedToId;
    @JsonProperty("executedById")
    private String executedById;

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
    private HashMap<String, Object> customFields;

    @JsonProperty("testScriptResults")
    private List<TestScriptResult> testScriptResults;

}