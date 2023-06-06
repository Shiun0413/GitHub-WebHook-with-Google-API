package me.shiunchiu.githubwebhookwithgoogleapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GoogleController {

  @PostMapping("/google-sheets")
  public ResponseEntity<String> createIssueInformationToGoogleSheet() {
    return ResponseEntity.status(HttpStatus.OK).body("Hello");
  }
}
