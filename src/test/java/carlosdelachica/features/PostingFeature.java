package carlosdelachica.features;

import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.Input;
import carlosdelachica.delivery_mechanism.InputParser;
import carlosdelachica.command.Command;
import carlosdelachica.command.CommandsFactory;
import carlosdelachica.model.Post;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.User;
import java.util.List;
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

  private static final String ANY_USER_NAME = "Bob";
  private static final User BOB = new User(ANY_USER_NAME);
  private static final long FIRST_POST_TIMESTAMP = 1L;
  private static final long SECOND_POST_TIMESTAMP = 2L;
  private static final String FIRST_POST_MESSAGE = "Damn! We lost!";
  private static final String SECOND_POST_MESSAGE = "Good game though.";
  private static final Post FIRST_POST = new Post(BOB, FIRST_POST_MESSAGE, FIRST_POST_TIMESTAMP);
  private static final Post SECOND_POST = new Post(BOB, SECOND_POST_MESSAGE, SECOND_POST_TIMESTAMP);
  private static final List<Post> POSTS = asList(FIRST_POST, SECOND_POST);

  @Mock Clock clock;

  private PostRepository repository;
  private CommandsFactory commandsFactory;

  @Before public void setUp() {
    when(clock.currentTimeInMillis()).thenReturn(FIRST_POST_TIMESTAMP, SECOND_POST_TIMESTAMP);
    repository = new PostRepository();
    commandsFactory = new CommandsFactory(clock, repository);
  }

  @Test public void user_can_publish_messages_to_timeline() {
    Input[] inputs = givenInputs();

    for (Input input : inputs) {
      Command command = commandsFactory.make(input);
      command.execute();
    }

    assertThat(repository.postsOf(BOB), is(POSTS));
  }

  private Input[] givenInputs() {
    InputParser inputParser = new InputParser();
    Input firstInput = inputParser.parse(ANY_USER_NAME + " -> " + FIRST_POST_MESSAGE);
    Input secondInput = inputParser.parse(ANY_USER_NAME + " -> " + SECOND_POST_MESSAGE);
    return new Input[] {firstInput, secondInput};
  }
}
