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
import carlosdelachica.model.Post;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.User;
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

@RunWith(MockitoJUnitRunner.class) public class FollowingFeature {

  private static final long NOW = 10000000L;
  private static final long ONE_SEC = 1000;
  private static final long ONE_MIN = 60 * ONE_SEC;

  private static final long FIFTEEN_SECS_AGO = NOW - 15 * ONE_SEC;
  private static final long ONE_MIN_AGO = NOW - ONE_MIN;
  private static final long TWO_MINS_AGO = NOW - 2 * ONE_MIN;
  private static final long FIVE_MINS_AGO = NOW - 5 * ONE_MIN;

  private static final String FOLLOW_ACTION = " follows ";
  private static final String WALL_ACTION = " wall";

  private static final String USER_NAME = "Charlie";
  private static final String BOB_USER_NAME = "Bob";
  private static final String ALICE_USER_NAME = "Alice";
  private static final User USER = new User(USER_NAME);
  private static final User BOB_USER = new User(BOB_USER_NAME);
  private static final User ALICE_USER = new User(ALICE_USER_NAME);

  private static final String ALICE_POST_MESSAGE = "I love the weather today";
  private static final String BOB_POST_1_MESSAGE = "Good game though.";
  private static final String BOB_POST_2_MESSAGE = "Damn! We lost!";
  private static final String USER_POST_MESSAGE =
      "I'm in New York today! Anyone want to have a coffee?";

  private static final Post USER_POST = new Post(USER, USER_POST_MESSAGE, FIFTEEN_SECS_AGO);
  private static final Post ALICE_POST = new Post(ALICE_USER, ALICE_POST_MESSAGE, FIVE_MINS_AGO);
  private static final Post BOB_POST_1 = new Post(BOB_USER, BOB_POST_1_MESSAGE, TWO_MINS_AGO);
  private static final Post BOB_POST_2 = new Post(BOB_USER, BOB_POST_2_MESSAGE, ONE_MIN_AGO);

  private static final List<Post> ALL_POSTS = asList(USER_POST, ALICE_POST, BOB_POST_1, BOB_POST_2);

  private static final String USER_POST_LINE =
      "Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago)";
  private static final String BOB_POST_1_LINE = "Bob - Good game though. (1 minute ago)";
  private static final String BOB_POST_2_LINE = "Bob - Damn! We lost! (2 minutes ago)";
  private static final String ALICE_POST_LINE = "Alice - I love the weather today (5 minutes ago)";

  private static final List<String> USER_WALL_LINES =
      asList(USER_POST_LINE, BOB_POST_1_LINE, BOB_POST_2_LINE, ALICE_POST_LINE);

  @Mock Clock clock;
  @Mock ConsoleWrapper console;

  private CommandsFactory commandsFactory;
  private PostRepository postRepository;
  private InputParser inputParser;

  @Before public void setUp() {
    postRepository = new PostRepository();
    View view = new View(console, new PostFormatter(new TimeAgoFormatter(clock)));
    commandsFactory = new CommandsFactory(clock, view, postRepository, new UserRepository());

    given(clock.currentTimeInMillis()).willReturn(NOW);
    populatePostRepository();
    inputParser = new InputParser();
  }

  @Test public void user_can_subscribe_to_other_user_timeline() {
    executeFollowCommand(givenFollowInputs());
    executeWallCommand(givenWallInput());

    verify(console).printLines(USER_WALL_LINES);
  }

  private void executeFollowCommand(Input[] followInputs) {
    for (Input followInput : followInputs) {
      Command followCommand = commandsFactory.make(followInput);
      followCommand.execute();
    }
  }

  private void executeWallCommand(Input wallInput) {
    Command wallCommand = commandsFactory.make(wallInput);
    wallCommand.execute();
  }

  private Input givenWallInput() {
    return inputParser.parse(USER_NAME + WALL_ACTION);
  }

  private Input[] givenFollowInputs() {
    Input followInput1 = inputParser.parse(USER_NAME + FOLLOW_ACTION + BOB_USER_NAME);
    Input followInput2 = inputParser.parse(USER_NAME + FOLLOW_ACTION + ALICE_USER_NAME);
    return new Input[] {followInput1, followInput2};
  }

  private void populatePostRepository() {
    ALL_POSTS.stream().
        forEach(post -> postRepository.store(post));
  }
}
