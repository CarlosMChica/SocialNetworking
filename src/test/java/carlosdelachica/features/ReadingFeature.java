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

@RunWith(MockitoJUnitRunner.class) public class ReadingFeature {

  private static final String POST_ACTION = " -> ";

  private static final long NOW = 10000000L;
  private static final long ONE_SEC_IN_MILLIS = 1000;
  private static final long ONE_MIN = 60 * ONE_SEC_IN_MILLIS;
  private static final long ONE_MIN_AGO = NOW - ONE_MIN;
  private static final long TWO_MINS_AGO = NOW - 2 * ONE_MIN;

  private static final String OTHER_USER_NAME = "other_user";
  private static final String USER_NAME = "userName";

  private static final String POST_1_MESSAGE = "Damn! We lost!";
  private static final String POST_2_MESSAGE = "Good game though.";
  private static final String OTHER_USER_POST_MESSAGE = "I love the weather today";

  private static final String POST_1_LINE = "Damn! We lost! (2 minutes ago)";
  private static final String POST_2_LINE = "Good game though. (1 minute ago)";
  private static final List<String> POSTS_LINES = asList(POST_2_LINE, POST_1_LINE);

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
    given(commandsClock.currentTimeInMillis()).willReturn(TWO_MINS_AGO, ONE_MIN_AGO);
  }

  @Test public void can_read_user_timeline() {
    executePostCommands(givenPostInputs());

    executeReadCommand(givenReadInput());

    verify(console).printLines(POSTS_LINES);
  }

  private void executePostCommands(Input[] postInputs) {
    executeInputs(postInputs);
  }

  private void executeReadCommand(Input readInput) {
    executeInputs(readInput);
  }

  private void executeInputs(Input... inputs) {
    Stream.of(inputs).forEach(input -> {
      Command command = commandsFactory.make(input);
      command.execute();
    });
  }

  private Input givenReadInput() {
    return inputParser.parse(USER_NAME);
  }

  private Input[] givenPostInputs() {
    Input postInput1 = inputParser.parse(USER_NAME + POST_ACTION + POST_1_MESSAGE);
    Input postInput2 = inputParser.parse(USER_NAME + POST_ACTION + POST_2_MESSAGE);
    Input postInput3 = inputParser.parse(OTHER_USER_NAME + POST_ACTION + OTHER_USER_POST_MESSAGE);
    return new Input[] {postInput1, postInput2, postInput3};
  }
}
