package com.telstra.codechallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// check if we have NON_NULL
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class GitRepositoryDetails {
    @JsonProperty("name")
    private String name;
    @JsonProperty("html_url")
    private String html_url;
    @JsonProperty("watchers_count")
    private String watchers_count;
    @JsonProperty("language")
    private String language;
    @JsonProperty("description")
    private String description;

}
