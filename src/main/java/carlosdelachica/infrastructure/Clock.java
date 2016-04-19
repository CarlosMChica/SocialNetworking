package carlosdelachica.infrastructure;

public class Clock {

  public static final long ONE_SEC = 1000;
  public static final long ONE_MIN = 60 * ONE_SEC;
  public static final long ONE_HOUR = 60 * ONE_MIN;
  public static final long ONE_DAY = 24 * ONE_HOUR;

  public long currentTimeInMillis() {
    return System.currentTimeMillis();
  }
}
