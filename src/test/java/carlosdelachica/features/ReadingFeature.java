package carlosdelachica.features;

import carlosdelachica.command.Command;
import carlosdelachica.command.CommandsFactory;
import carlosdelachica.delivery_mechanism.ConsoleWrapper;
import carlosdelachica.delivery_mechanism.PostFormatter;
import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.Input;
import carlosdelachica.model.Post;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.User;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static carlosdelachica.model.Input.Type.READ;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class ReadingFeature {

  private static final User OTHER_USER = new User("other_user");
  private static final User USER = new User("userName");

  private static final String POST_1_MESSAGE = "Damn! We lost!";
  private static final long POST_1_TIMESTAMP = 0L;
  private static final Post POST_1 = new Post(USER, POST_1_MESSAGE, POST_1_TIMESTAMP);

  private static final String POST_2_MESSAGE = "Good game though.";
  private static final long POST_2_TIMESTAMP = 1L;
  private static final Post POST_2 = new Post(USER, POST_2_MESSAGE, POST_2_TIMESTAMP);

  private static final long POST_3_TIMESTAMP = 3L;
  private static final String POST_3_MESSAGE = "I love the weather today";
  private static final Post OTHER_USER_POST =
      new Post(OTHER_USER, POST_3_MESSAGE, POST_3_TIMESTAMP);

  private static final List<Post> ALL_POSTS = asList(POST_1, POST_2, OTHER_USER_POST);

  @Mock Clock clock;
  @Mock ConsoleWrapper console;

  private CommandsFactory commandsFactory;
  private PostRepository postRepository;

  @Before public void setUp() {
    postRepository = new PostRepository();
    View view = new View(console, new PostFormatter());
    commandsFactory = new CommandsFactory(clock, postRepository,
        view);

    populatePostsRepository();
  }

  @Test public void user_can_read_other_user_timeline() {
    Input readInput = givenReadInput();

    Command command = commandsFactory.make(readInput);
    command.execute();

    InOrder inOrder = inOrder(console);
    inOrder.verify(console).print("Good game though. (1 minute ago)");
    inOrder.verify(console).print("Damn! We lost! (2 minutes ago)");
  }

  private void populatePostsRepository() {
    for (Post post : ALL_POSTS) {
      postRepository.store(post);
    }
  }

  private Input givenReadInput() {
    return new Input(READ, "userName");
  }
}
