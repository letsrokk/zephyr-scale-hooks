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
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestCycle {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("key")
    private String key;
    @JsonProperty("name")
    private String name;

    @JsonProperty("project")
    private Project project;
    @JsonProperty("jiraProjectVersion")
    private Version jiraProjectVersion;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("folder")
    private Folder folder;

    @JsonProperty("description")
    private String description;

    @JsonProperty("plannedStartDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime plannedStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("plannedEndDate")
    private LocalDateTime plannedEndDate;

    @JsonProperty("owner")
    private Owner owner;

    @JsonProperty("customFields")
    private HashMap<String, Object> customFields;


}
