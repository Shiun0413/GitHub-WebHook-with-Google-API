package me.shiunchiu.githubwebhookwithgoogleapi.controller;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.shiunchiu.githubwebhookwithgoogleapi.dto.GitHubIssuePayload;
import me.shiunchiu.githubwebhookwithgoogleapi.util.GoogleSheetsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GoogleController {

  @Value("${google.spreadsheet.id}")
  private String SPREADSHEET_ID;

  private final GoogleSheetsUtil googleSheetsUtil;

  @PostMapping("/google-sheets")
  public ResponseEntity<String> createIssueInformationToGoogleSheet(
      @RequestBody GitHubIssuePayload gitHubIssuePayload) {

    // Return if the issue is not closed
    if (!gitHubIssuePayload.getAction().equals("closed")) {
      return ResponseEntity.status(HttpStatus.OK).build();
    }

    // If assignee is null, return 404
    if (gitHubIssuePayload.getIssue().getAssignee() == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignee not found");
    }

    // Prepare issue information
    String closedAt = gitHubIssuePayload.getIssue().getClosed_at();
    String assignee = gitHubIssuePayload.getIssue().getAssignee().getLogin();
    String closedBy = gitHubIssuePayload.getSender().getLogin();
    String issueUrl = gitHubIssuePayload.getIssue().getUrl();
    // TODO: get issue tags and estimate hours

    try {
      Sheets service = googleSheetsUtil.getSheetsService();

      List<List<Object>> values = Arrays.asList(
          Arrays.asList(
              // Cell values ...
              closedAt, assignee, issueUrl, closedBy
              // TODO: add issue tags and estimate hours
          )
      );

      ValueRange body = new ValueRange()
          .setValues(values);
      String spreadsheetId = SPREADSHEET_ID;
      String range = assignee + "!A1:E1"; // Update to your sheet name and range

      service.spreadsheets().values().append(spreadsheetId, range, body)
          .setValueInputOption("USER_ENTERED")
          .execute();
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to write to Google Sheets");
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(
        "assignee: " + assignee + ", issue information create into Google Sheets successfully");
  }
}
