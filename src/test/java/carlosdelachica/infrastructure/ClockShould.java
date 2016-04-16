package carlosdelachica.infrastructure;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ClockShould {

  private static final long CURRENT_TIME_IN_MILLIS = 0L;

  @Test public void return_current_time_in_millis() {
    Clock clock = new TestableClock();

    long timeInMillis = clock.currentTimeInMillis();

    assertThat(timeInMillis, is(CURRENT_TIME_IN_MILLIS));
  }

  private class TestableClock extends Clock {

    @Override public long currentTimeInMillis() {
      return CURRENT_TIME_IN_MILLIS;
    }
  }
}