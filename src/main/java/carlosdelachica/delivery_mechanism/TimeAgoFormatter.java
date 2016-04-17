package carlosdelachica.delivery_mechanism;

import carlosdelachica.infrastructure.Clock;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeAgoFormatter {

  static final long MILLIS_IN_A_SEC = 1000;
  static final long MILLIS_IN_A_MIN = 60 * MILLIS_IN_A_SEC;
  static final long MILLIS_IN_A_HOUR = 60 * MILLIS_IN_A_MIN;
  static final long MILLIS_IN_A_DAY = 24 * MILLIS_IN_A_HOUR;

  private static final String SECOND = "second";
  private static final String MINUTE = "minute";
  private static final String HOUR = "hour";
  private static final String DAY = "day";
  private static final String JUST_NOW = "Just now";

  private final Clock clock;

  public TimeAgoFormatter(Clock clock) {
    this.clock = clock;
  }

  public String format(long previousTimestamp) {
    long quantity = calculateTimeAgo(previousTimestamp);
    if (quantity == 0) {
      return JUST_NOW;
    }
    if (quantity < MILLIS_IN_A_MIN) {
      return format(MILLISECONDS.toSeconds(quantity), SECOND);
    }
    if (quantity < MILLIS_IN_A_HOUR) {
      return format(MILLISECONDS.toMinutes(quantity), MINUTE);
    }
    if (quantity < MILLIS_IN_A_DAY) {
      return format(MILLISECONDS.toHours(quantity), HOUR);
    }
    return format(MILLISECONDS.toDays(quantity), DAY);
  }

  private long calculateTimeAgo(long before) {
    long now = clock.currentTimeInMillis();
    return now - before;
  }

  private String format(long quantity, String dateUnit) {
    return String.format("%d %s ago", quantity, plural(dateUnit, quantity));
  }

  private String plural(String word, long quantity) {
    if (quantity > 1) {
      return word.concat("s");
    }
    return word;
  }
}
