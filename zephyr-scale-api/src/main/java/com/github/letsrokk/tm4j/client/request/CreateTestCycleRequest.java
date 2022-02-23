package com.github.letsrokk.tm4j.client.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.letsrokk.tm4j.client.model.Folder;
import com.github.letsrokk.tm4j.client.model.Owner;
import com.github.letsrokk.tm4j.client.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTestCycleRequest {

    @JsonProperty("projectKey")
    private String projectKey;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;

    @JsonProperty("plannedStartDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime plannedStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("plannedEndDate")
    private LocalDateTime plannedEndDate;

    @JsonProperty("jiraProjectVersion")
    private Integer jiraProjectVersion;
    @JsonProperty("statusName")
    private String statusName;
    @JsonProperty("folderId")
    private Integer folderId;
    @JsonProperty("ownerId")
    private String ownerId;

    @JsonProperty("customFields")
    private HashMap<String, Object> customFields;

}
