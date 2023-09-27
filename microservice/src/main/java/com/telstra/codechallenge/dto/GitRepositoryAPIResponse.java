package com.telstra.codechallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitRepositoryAPIResponse {

    private List<GitRepositoryDetails> items = new ArrayList<>();
}
