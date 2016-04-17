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

@RunWith(MockitoJUnitRunner.class) public class ReadingFeature {

  private static final long NOW = 10000000L;
  private static final long ONE_SEC_IN_MILLIS = 1000;
  private static final long ONE_MIN = 60 * ONE_SEC_IN_MILLIS;
  private static final long ONE_MIN_AGO = NOW - ONE_MIN;
  private static final long TWO_MINS_AGO = NOW - 2 * ONE_MIN;

  private static final User OTHER_USER = new User("other_user");
  private static final String USER_NAME = "userName";
  private static final User USER = new User(USER_NAME);

  private static final Post POST_1 = new Post(USER, "Damn! We lost!", TWO_MINS_AGO);
  private static final Post POST_2 = new Post(USER, "Good game though.", ONE_MIN_AGO);
  private static final Post OTHER_USER_POST = new Post(OTHER_USER, "I love the weather today", 3L);
  private static final List<Post> ALL_POSTS = asList(POST_1, POST_2, OTHER_USER_POST);

  private static final String POST_1_LINE = "Damn! We lost! (2 minutes ago)";
  private static final String POST_2_LINE = "Good game though. (1 minute ago)";
  private static final List<String> POSTS_LINES = asList(POST_2_LINE, POST_1_LINE);

  @Mock Clock clock;
  @Mock ConsoleWrapper console;

  private CommandsFactory commandsFactory;
  private PostRepository postRepository;

  @Before public void setUp() {
    postRepository = new PostRepository();
    View view = new View(console, new PostFormatter(new TimeAgoFormatter(clock)));
    commandsFactory = new CommandsFactory(clock, view, postRepository, new UserRepository());

    given(clock.currentTimeInMillis()).willReturn(NOW);
    populatePostsRepository();
  }

  @Test public void can_read_user_timeline() {
    Input readInput = givenReadInput();

    Command command = commandsFactory.make(readInput);
    command.execute();

    verify(console).printLines(POSTS_LINES);
  }

  private void populatePostsRepository() {
    for (Post post : ALL_POSTS) {
      postRepository.store(post);
    }
  }

  private Input givenReadInput() {
    InputParser inputParser = new InputParser();
    return inputParser.parse(USER_NAME);
  }
}
