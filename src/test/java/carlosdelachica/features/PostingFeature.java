package carlosdelachica.features;

import carlosdelachica.command.CommandsFactory;
import carlosdelachica.delivery_mechanism.ConsoleWrapper;
import carlosdelachica.delivery_mechanism.InputParser;
import carlosdelachica.delivery_mechanism.PostFormatter;
import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
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

import static carlosdelachica.Application.SocialNetworkingApp;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class) public class PostingFeature {

  private static final String POST_ACTION = " -> ";

  private static final String BOB_USER_NAME = "Bob";
  private static final User BOB = new User(BOB_USER_NAME);

  private static final long POST_1_TIMESTAMP = 1L;
  private static final long POST_2_TIMESTAMP = 2L;

  private static final String POST_1_MESSAGE = "Damn! We lost!";
  private static final String POST_2_MESSAGE = "Good game though.";

  private static final Post BOB_POST_1 = new Post(BOB, POST_1_MESSAGE, POST_1_TIMESTAMP);
  private static final Post BOB_POST_2 = new Post(BOB, POST_2_MESSAGE, POST_2_TIMESTAMP);
  private static final List<Post> BOB_POSTS = asList(BOB_POST_1, BOB_POST_2);

  @Mock Clock clock;
  @Mock ConsoleWrapper console;
  @Mock PostFormatter postFormatter;

  private PostRepository repository;
  private SocialNetworkingApp app;

  @Before public void setUp() {
    given(clock.currentTimeInMillis()).willReturn(POST_1_TIMESTAMP, POST_2_TIMESTAMP);

    repository = new PostRepository();
    View view = new View(console, postFormatter);
    CommandsFactory commandsFactory =
        new CommandsFactory(clock, view, repository, new UserRepository());
    app = new SocialNetworkingApp(new InputParser(), commandsFactory, view);
  }

  @Test public void user_can_publish_messages_to_timeline() {
    app.execute(BOB_USER_NAME + POST_ACTION + POST_1_MESSAGE);
    app.execute(BOB_USER_NAME + POST_ACTION + POST_2_MESSAGE);

    assertThat(repository.postsOf(BOB), is(BOB_POSTS));
  }
}
