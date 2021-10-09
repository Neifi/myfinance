package es.neifi.myfinance.shared.Infrastructure;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GithubConfig {

  public GithubConfig() {
    System.setProperty("github-client-id",System.getenv("GITHUB_CLIENT_ID"));
    System.setProperty("github-client-secret",System.getenv("GITHUB_CLIENT_SECRET"));
  }

}
