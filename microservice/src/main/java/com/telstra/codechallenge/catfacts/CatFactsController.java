package com.telstra.codechallenge.catfacts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CatFactsController {
  private final CatFactsService catFactsService;

  public CatFactsController(
      CatFactsService catFactsService) {
    this.catFactsService = catFactsService;
  }

  @GetMapping(path = "/catFacts")
  public List<CatFact> catFacts() {
    return catFactsService.getCatFacts();
  }
}
