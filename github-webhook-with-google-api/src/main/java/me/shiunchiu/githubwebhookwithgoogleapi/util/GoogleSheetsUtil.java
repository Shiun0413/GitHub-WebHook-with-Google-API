package me.shiunchiu.githubwebhookwithgoogleapi.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleSheetsUtil {

  @Value("${google.service.account.key.file.path}")
  private String SEVICE_ACCOUNT_KEY_FILE_PATH;

  private static final String APPLICATION_NAME = "Google Sheets Example";
  private static HttpTransport httpTransport;
  private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

  static {
    try {
      httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    } catch (GeneralSecurityException | IOException e) {
      // Handle exception
    }
  }

  public Sheets getSheetsService() throws IOException {
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    GoogleCredentials credential = GoogleCredentials.fromStream(new FileInputStream(SEVICE_ACCOUNT_KEY_FILE_PATH))
        .createScoped(SCOPES);
    HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credential);

    return new Sheets.Builder(httpTransport, jsonFactory, requestInitializer)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }
}
