package com.zero.selenium.runner;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * It is corresponding to TestNG group
 * 
 * @author sontt
 *
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Profile {
  SMOKE("smoke"), REGRESSION("regression"), FULL("full");

  private final String suite;

  public static Profile parse(String suite) {
    for (Profile profile : Profile.values()) {
      if (profile.getSuite().equalsIgnoreCase(suite)) {
        return profile;
      }
    }
    return Profile.SMOKE;
  }
}
