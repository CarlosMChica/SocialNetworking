package carlosdelachica.features;

import carlosdelachica.command.Command;
import carlosdelachica.command.CommandsFactory;
import carlosdelachica.delivery_mechanism.InputParser;
import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.Input;
import carlosdelachica.model.Post;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.User;
import carlosdelachica.model.UserRepository;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class PostingFeature {

  private static final String POST_ACTION = " -> ";

  private static final String ANY_USER_NAME = "Bob";
  private static final User BOB = new User(ANY_USER_NAME);

  private static final long POST_1_TIMESTAMP = 1L;
  private static final long POST_2_TIMESTAMP = 2L;

  private static final String POST_1_MESSAGE = "Damn! We lost!";
  private static final String POST_2_MESSAGE = "Good game though.";

  private static final Post BOB_POST_1 = new Post(BOB, POST_1_MESSAGE, POST_1_TIMESTAMP);
  private static final Post BOB_POST_2 = new Post(BOB, POST_2_MESSAGE, POST_2_TIMESTAMP);
  private static final List<Post> BOB_POSTS = asList(BOB_POST_1, BOB_POST_2);

  @Mock Clock clock;
  @Mock View view;

  private PostRepository repository;
  private CommandsFactory commandsFactory;

  @Before public void setUp() {
    when(clock.currentTimeInMillis()).thenReturn(POST_1_TIMESTAMP, POST_2_TIMESTAMP);
    repository = new PostRepository();
    commandsFactory = new CommandsFactory(clock, view, repository, new UserRepository());
  }

  @Test public void user_can_publish_messages_to_timeline() {
    Input[] inputs = givenInputs();

    Stream.of(inputs).forEach(input -> {
      Command command = commandsFactory.make(input);
      command.execute();
    });

    assertThat(repository.postsOf(BOB), is(BOB_POSTS));
  }

  private Input[] givenInputs() {
    InputParser inputParser = new InputParser();
    Input firstInput = inputParser.parse(ANY_USER_NAME + POST_ACTION + POST_1_MESSAGE);
    Input secondInput = inputParser.parse(ANY_USER_NAME + POST_ACTION + POST_2_MESSAGE);
    return new Input[] {firstInput, secondInput};
  }
}
