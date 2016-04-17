package carlosdelachica.delivery_mechanism;

import carlosdelachica.infrastructure.Clock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static carlosdelachica.delivery_mechanism.TimeAgoFormatter.MILLIS_IN_A_DAY;
import static carlosdelachica.delivery_mechanism.TimeAgoFormatter.MILLIS_IN_A_HOUR;
import static carlosdelachica.delivery_mechanism.TimeAgoFormatter.MILLIS_IN_A_MIN;
import static carlosdelachica.delivery_mechanism.TimeAgoFormatter.MILLIS_IN_A_SEC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class) public class TimeAgoFormatterShould {

  private static final long NOW = MILLIS_IN_A_DAY;
  private static final long FIFTY_NINE_SECONDS = 59 * MILLIS_IN_A_SEC;
  private static final long FIFTY_NINE_SECS_AGO = NOW - FIFTY_NINE_SECONDS;
  private static final long ONE_SEC_AGO = NOW - MILLIS_IN_A_SEC;
  private static final long ONE_MIN_AGO = NOW - MILLIS_IN_A_MIN;
  private static final long FIFTY_NINE_MINS_AGO = NOW - 59 * MILLIS_IN_A_MIN;
  private static final long ONE_HOUR_AGO = NOW - MILLIS_IN_A_HOUR;
  private static final long TWENTY_THREE_HOURS_AGO = NOW - 23 * MILLIS_IN_A_HOUR;
  private static final long ONE_DAY_AGO = NOW - MILLIS_IN_A_DAY;
  private static final long HUNDRED_DAYS_AGO = NOW - 100 * MILLIS_IN_A_DAY;

  @Mock Clock clock;

  private TimeAgoFormatter formatter;

  @Before public void setUp() {
    given(clock.currentTimeInMillis()).willReturn(NOW);
    formatter = new TimeAgoFormatter(clock);
  }

  @Test public void formatTimeAgo() {
    assertTimeAgoFormat("Just now", NOW);
    assertTimeAgoFormat("1 second ago", ONE_SEC_AGO);
    assertTimeAgoFormat("59 seconds ago", FIFTY_NINE_SECS_AGO);
    assertTimeAgoFormat("1 minute ago", ONE_MIN_AGO);
    assertTimeAgoFormat("59 minutes ago", FIFTY_NINE_MINS_AGO);
    assertTimeAgoFormat("1 hour ago", ONE_HOUR_AGO);
    assertTimeAgoFormat("23 hours ago", TWENTY_THREE_HOURS_AGO);
    assertTimeAgoFormat("1 day ago", ONE_DAY_AGO);
    assertTimeAgoFormat("100 days ago", HUNDRED_DAYS_AGO);
  }

  private void assertTimeAgoFormat(String format, long timeAgo) {
    assertThat(formatter.format(timeAgo), is(format));
  }
}
