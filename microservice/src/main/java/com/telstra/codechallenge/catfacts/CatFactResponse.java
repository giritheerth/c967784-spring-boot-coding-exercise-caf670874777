package com.telstra.codechallenge.catfacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CatFactResponse {
  private List<CatFact> data;
}
