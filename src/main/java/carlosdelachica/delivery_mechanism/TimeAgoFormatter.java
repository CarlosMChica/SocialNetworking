package carlosdelachica.delivery_mechanism;

import carlosdelachica.infrastructure.Clock;

public class TimeAgoFormatter {

  private final Clock clock;

  public TimeAgoFormatter(Clock clock) {
    this.clock = clock;
  }

  public String format(long timestamp) {
    throw new UnsupportedOperationException();
  }
}
