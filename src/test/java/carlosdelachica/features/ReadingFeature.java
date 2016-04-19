package carlosdelachica.features;

import carlosdelachica.Application.SocialNetworkingApp;
import carlosdelachica.command.CommandsFactory;
import carlosdelachica.delivery_mechanism.ConsoleWrapper;
import carlosdelachica.delivery_mechanism.InputParser;
import carlosdelachica.delivery_mechanism.PostFormatter;
import carlosdelachica.delivery_mechanism.TimeAgoFormatter;
import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.UserRepository;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class ReadingFeature {

  private static final String POST_ACTION = " -> ";

  private static final long NOW = 10000000L;
  private static final long ONE_SEC_IN_MILLIS = 1000;
  private static final long ONE_MIN = 60 * ONE_SEC_IN_MILLIS;
  private static final long ONE_MIN_AGO = NOW - ONE_MIN;
  private static final long TWO_MINS_AGO = NOW - 2 * ONE_MIN;

  private static final String OTHER_USER_NAME = "Alice";
  private static final String BOB = "Bob";

  private static final String POST_1_MESSAGE = "Damn! We lost!";
  private static final String POST_2_MESSAGE = "Good game though.";
  private static final String OTHER_USER_POST_MESSAGE = "I love the weather today";

  private static final String POST_1_LINE = "Damn! We lost! (2 minutes ago)";
  private static final String POST_2_LINE = "Good game though. (1 minute ago)";
  private static final List<String> POSTS_LINES = asList(POST_2_LINE, POST_1_LINE);

  @Mock Clock timeAgoFormatterClock;
  @Mock Clock commandsClock;
  @Mock ConsoleWrapper console;

  private SocialNetworkingApp app;

  @Before public void setUp() {
    given(timeAgoFormatterClock.currentTimeInMillis()).willReturn(NOW);
    given(commandsClock.currentTimeInMillis()).willReturn(TWO_MINS_AGO, ONE_MIN_AGO);

    View view = new View(console, new PostFormatter(new TimeAgoFormatter(timeAgoFormatterClock)));
    CommandsFactory commandsFactory =
        new CommandsFactory(commandsClock, view, new PostRepository(), new UserRepository());
    app = new SocialNetworkingApp(new InputParser(), commandsFactory, view);
  }

  @Test public void can_read_user_timeline() {
    executePostCommands();

    executeReadCommand();

    verify(console).printLines(POSTS_LINES);
  }

  private void executePostCommands() {
    app.execute(BOB + POST_ACTION + POST_1_MESSAGE);
    app.execute(BOB + POST_ACTION + POST_2_MESSAGE);
    app.execute(OTHER_USER_NAME + POST_ACTION + OTHER_USER_POST_MESSAGE);
  }

  private void executeReadCommand() {
    app.execute(BOB);
  }
}
