package com.github.letsrokk.adaptavist.testng.container;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomSuiteContainer {

    private String projectKey;
    private String name;

    private Long version;

    private String testRunKey;

    @Builder.Default
    private List<CustomTestContainer> tests = new ArrayList<>();

}
