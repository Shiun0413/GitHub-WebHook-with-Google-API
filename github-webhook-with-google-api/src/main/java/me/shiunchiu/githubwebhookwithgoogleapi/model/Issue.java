package me.shiunchiu.githubwebhookwithgoogleapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Issue {
  private String url;
  private User assignee;
  private String closed_at;
  private String title;

}
