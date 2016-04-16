package carlosdelachica;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class PostCommandShould {

  private static final String ANY_USER_NAME = "userName";
  private static final String ANY_MESSAGE = "Message";
  private static final long TIMESTAMP = 1L;
  private static final User USER = new User(ANY_USER_NAME);
  private static final Post POST = new Post(USER, ANY_MESSAGE, TIMESTAMP);

  @Mock PostRepository postRepository;
  @Mock Clock clock;

  @Before public void setUp() {
    when(clock.currentTimeInMillis()).thenReturn(TIMESTAMP);
  }

  @Test public void store_user_post() {
    String[] arguments = givenArguments();
    PostCommand postCommand = new PostCommand(clock, postRepository, arguments);

    postCommand.execute();

    verify(postRepository).store(POST);
  }

  private String[] givenArguments() {
    return new String[] {ANY_USER_NAME, ANY_MESSAGE};
  }
}
