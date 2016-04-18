package carlosdelachica.features;

import carlosdelachica.command.Command;
import carlosdelachica.command.CommandsFactory;
import carlosdelachica.delivery_mechanism.ConsoleWrapper;
import carlosdelachica.delivery_mechanism.InputParser;
import carlosdelachica.delivery_mechanism.PostFormatter;
import carlosdelachica.delivery_mechanism.TimeAgoFormatter;
import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.Input;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.UserRepository;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class FollowingFeature {

  private static final long NOW = 10000000L;
  private static final long ONE_SEC = 1000;
  private static final long ONE_MIN = 60 * ONE_SEC;

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

  private CommandsFactory commandsFactory;
  private InputParser inputParser;

  @Before public void setUp() {
    inputParser = new InputParser();
    View view = new View(console, new PostFormatter(new TimeAgoFormatter(timeAgoFormatterClock)));
    commandsFactory =
        new CommandsFactory(commandsClock, view, new PostRepository(), new UserRepository());

    given(timeAgoFormatterClock.currentTimeInMillis()).willReturn(NOW);
    given(commandsClock.currentTimeInMillis()).willReturn(FIVE_MINS_AGO, TWO_MINS_AGO, ONE_MIN_AGO,
        FIFTEEN_SECS_AGO);
  }

  @Test public void user_can_subscribe_to_other_user_timeline() {
    executePostCommands(givenPostInputs());
    executeFollowCommands(givenFollowInputs());
    executeWallCommand(givenWallInput());

    verify(console).printLines(USER_WALL_LINES);
  }

  private void executePostCommands(Input[] postInputs) {
    executeInputs(postInputs);
  }

  private void executeFollowCommands(Input[] followInputs) {
    executeInputs(followInputs);
  }

  private void executeWallCommand(Input wallInput) {
    executeInputs(wallInput);
  }

  private void executeInputs(Input... postInputs) {
    Stream.of(postInputs).forEach(input -> {
      Command command = commandsFactory.make(input);
      command.execute();
    });
  }

  private Input[] givenPostInputs() {
    Input postInput1 = inputParser.parse(ALICE_USER_NAME + POST_ACTION + ALICE_POST_MESSAGE);
    Input postInput2 = inputParser.parse(BOB_USER_NAME + POST_ACTION + BOB_POST_1_MESSAGE);
    Input postInput3 = inputParser.parse(BOB_USER_NAME + POST_ACTION + BOB_POST_2_MESSAGE);
    Input postInput4 = inputParser.parse(CHARLIE_USER_NAME + POST_ACTION + CHARLIE_POST_MESSAGE);
    return new Input[] {postInput1, postInput2, postInput3, postInput4};
  }

  private Input givenWallInput() {
    return inputParser.parse(CHARLIE_USER_NAME + WALL_ACTION);
  }

  private Input[] givenFollowInputs() {
    Input followInput1 = inputParser.parse(CHARLIE_USER_NAME + FOLLOW_ACTION + BOB_USER_NAME);
    Input followInput2 = inputParser.parse(CHARLIE_USER_NAME + FOLLOW_ACTION + ALICE_USER_NAME);
    return new Input[] {followInput1, followInput2};
  }
}
