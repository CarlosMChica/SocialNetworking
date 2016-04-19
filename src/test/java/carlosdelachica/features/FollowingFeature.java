package carlosdelachica.features;

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

import static carlosdelachica.Application.SocialNetworkingApp;
import static carlosdelachica.infrastructure.Clock.ONE_MIN;
import static carlosdelachica.infrastructure.Clock.ONE_SEC;
import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class FollowingFeature {

  private static final long NOW = 10000000L;

  private static final long FIFTEEN_SECS_AGO = NOW - 15 * ONE_SEC;
  private static final long ONE_MIN_AGO = NOW - ONE_MIN;
  private static final long TWO_MINS_AGO = NOW - 2 * ONE_MIN;
  private static final long FIVE_MINS_AGO = NOW - 5 * ONE_MIN;

  private static final String POST_ACTION = " -> ";
  private static final String FOLLOW_ACTION = " follows ";
  private static final String WALL_ACTION = " wall";

  private static final String CHARLIE_USER_NAME = "Charlie";
  private static final String BOB_USER_NAME = "Bob";
  private static final String ALICE_USER_NAME = "Alice";

  private static final String ALICE_POST_MESSAGE = "I love the weather today";
  private static final String BOB_POST_1_MESSAGE = "Damn! We lost!";
  private static final String BOB_POST_2_MESSAGE = "Good game though.";
  private static final String CHARLIE_POST_MESSAGE =
      "I'm in New York today! Anyone wants to have a coffee?";

  private static final String CHARLIE_POST_LINE =
      "Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago)";
  private static final String BOB_POST_1_LINE = "Bob - Good game though. (1 minute ago)";
  private static final String BOB_POST_2_LINE = "Bob - Damn! We lost! (2 minutes ago)";
  private static final String ALICE_POST_LINE = "Alice - I love the weather today (5 minutes ago)";

  private static final List<String> USER_WALL_LINES =
      asList(CHARLIE_POST_LINE, BOB_POST_1_LINE, BOB_POST_2_LINE, ALICE_POST_LINE);

  @Mock Clock timeAgoFormatterClock;
  @Mock Clock commandsClock;
  @Mock ConsoleWrapper console;

  private SocialNetworkingApp app;

  @Before public void setUp() {
    given(timeAgoFormatterClock.currentTimeInMillis()).willReturn(NOW);
    given(commandsClock.currentTimeInMillis()).willReturn(FIVE_MINS_AGO, TWO_MINS_AGO, ONE_MIN_AGO,
        FIFTEEN_SECS_AGO);

    View view = new View(console, new PostFormatter(new TimeAgoFormatter(timeAgoFormatterClock)));
    CommandsFactory commandsFactory =
        new CommandsFactory(commandsClock, view, new PostRepository(), new UserRepository());
    app = new SocialNetworkingApp(new InputParser(), commandsFactory, view);
  }

  @Test public void user_can_subscribe_to_other_user_timeline() {
    executePostCommands();

    executeFollowCommands();
    executeWallCommand();

    verify(console).printLines(USER_WALL_LINES);
  }

  private void executePostCommands() {
    app.executeUserInput(ALICE_USER_NAME + POST_ACTION + ALICE_POST_MESSAGE);
    app.executeUserInput(BOB_USER_NAME + POST_ACTION + BOB_POST_1_MESSAGE);
    app.executeUserInput(BOB_USER_NAME + POST_ACTION + BOB_POST_2_MESSAGE);
    app.executeUserInput(CHARLIE_USER_NAME + POST_ACTION + CHARLIE_POST_MESSAGE);
  }

  private void executeFollowCommands() {
    app.executeUserInput(CHARLIE_USER_NAME + FOLLOW_ACTION + BOB_USER_NAME);
    app.executeUserInput(CHARLIE_USER_NAME + FOLLOW_ACTION + ALICE_USER_NAME);
  }

  private void executeWallCommand() {
    app.executeUserInput(CHARLIE_USER_NAME + WALL_ACTION);
  }
}
