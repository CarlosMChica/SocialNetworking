package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
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

@RunWith(MockitoJUnitRunner.class) public class ReadCommandShould {

  private static final String ANY_USER_NAME = "userName";
  private static final User ANY_USER = new User(ANY_USER_NAME);

  private static final String POST_1_MESSAGE = "FirstMessage";
  private static final String POST_2_MESSAGE = "SecondMessage";
  private static final long POST_1_TIMESTAMP = 0L;
  private static final long POST_2_TIMESTAMP = 1L;
  private static final Post POST_1 = new Post(ANY_USER, POST_1_MESSAGE, POST_1_TIMESTAMP);
  private static final Post POST_2 = new Post(ANY_USER, POST_2_MESSAGE, POST_2_TIMESTAMP);

  private static final List<Post> USER_POSTS = asList(POST_1, POST_2);
  private static final List<Post> SORTED_USER_POSTS = asList(POST_2, POST_1);

  @Mock View view;
  @Mock PostRepository postRepository;
  @Mock UserRepository userRepository;

  @Before public void setUp() {
    given(postRepository.postsOf(ANY_USER)).willReturn(USER_POSTS);
    given(userRepository.getByName(ANY_USER_NAME)).willReturn(ANY_USER);
  }

  @Test public void print_user_timeline() {
    String[] arguments = givenArguments();
    ReadCommand readCommand = new ReadCommand(view, postRepository, userRepository, arguments);

    readCommand.execute();

    verify(view).print(SORTED_USER_POSTS);
  }

  private String[] givenArguments() {
    return new String[] {ANY_USER_NAME};
  }
}