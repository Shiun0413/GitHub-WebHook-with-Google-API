package me.shiunchiu.githubwebhookwithgoogleapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.shiunchiu.githubwebhookwithgoogleapi.model.Issue;
import me.shiunchiu.githubwebhookwithgoogleapi.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubIssuePayload {
  private Issue issue;
  private User sender;

}
