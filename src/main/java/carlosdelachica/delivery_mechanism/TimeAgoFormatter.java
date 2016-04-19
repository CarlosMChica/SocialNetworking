package carlosdelachica.delivery_mechanism;

import carlosdelachica.infrastructure.Clock;

import static carlosdelachica.infrastructure.Clock.ONE_DAY;
import static carlosdelachica.infrastructure.Clock.ONE_HOUR;
import static carlosdelachica.infrastructure.Clock.ONE_MIN;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeAgoFormatter {

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
    long timeAgo = calculateTimeAgo(previousTimestamp);
    if (timeAgo == 0) {
      return formatNow();
    }
    if (isLessThanOneMin(timeAgo)) {
      return formatSeconds(timeAgo);
    }
    if (isLessThanOneHour(timeAgo)) {
      return formatMinutes(timeAgo);
    }
    if (isLessThanADay(timeAgo)) {
      return formatHours(timeAgo);
    }
    return formatDays(timeAgo);
  }

  private String formatNow() {
    return JUST_NOW;
  }

  private String formatDays(long millis) {
    return format(MILLISECONDS.toDays(millis), DAY);
  }

  private String formatHours(long millis) {
    return format(MILLISECONDS.toHours(millis), HOUR);
  }

  private String formatMinutes(long millis) {
    return format(MILLISECONDS.toMinutes(millis), MINUTE);
  }

  private String formatSeconds(long millis) {
    return format(MILLISECONDS.toSeconds(millis), SECOND);
  }

  private boolean isLessThanADay(long millis) {
    return millis < ONE_DAY;
  }

  private boolean isLessThanOneHour(long millis) {
    return millis < ONE_HOUR;
  }

  private boolean isLessThanOneMin(long millis) {
    return millis < ONE_MIN;
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
