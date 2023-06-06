package me.shiunchiu.githubwebhookwithgoogleapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Issue {
  private String htmlUrl;
  private User assignee;
  private String closedAt;
  private String title;

}
