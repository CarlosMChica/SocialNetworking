package carlosdelachica.command;

import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.Post;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.User;
import carlosdelachica.model.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class PostCommandShould {

  private static final String ANY_USER_NAME = "userName";
  private static final String ANY_MESSAGE = "Message";

  private static final long TIMESTAMP = 1L;

  private static final User ANY_USER = new User(ANY_USER_NAME);
  private static final Post POST = new Post(ANY_USER, ANY_MESSAGE, TIMESTAMP);

  @Mock PostRepository postRepository;
  @Mock UserRepository userRepository;
  @Mock Clock clock;

  @Before public void setUp() {
    given(clock.currentTimeInMillis()).willReturn(TIMESTAMP);
    given(userRepository.getByName(ANY_USER_NAME)).willReturn(ANY_USER);
  }

  @Test public void store_user_post_if_user_is_registered() {
    givenRegisteredUser();

    String[] arguments = givenArguments();
    PostCommand postCommand = new PostCommand(clock, postRepository, userRepository, arguments);

    postCommand.execute();

    verify(postRepository).store(POST);
    verify(userRepository, never()).register(anyString());
  }

  @Test public void register_user_if_post_user_is_not_registered() {
    givenNotRegisteredUser();

    String[] arguments = givenArguments();
    PostCommand postCommand = new PostCommand(clock, postRepository, userRepository, arguments);

    postCommand.execute();

    InOrder inOrder = inOrder(userRepository, postRepository);
    inOrder.verify(userRepository).register(ANY_USER_NAME);
    inOrder.verify(postRepository).store(POST);
  }

  private void givenRegisteredUser() {
    given(userRepository.isRegistered(ANY_USER_NAME)).willReturn(true);
  }

  private void givenNotRegisteredUser() {
    given(userRepository.isRegistered(ANY_USER_NAME)).willReturn(false);
  }

  private String[] givenArguments() {
    return new String[] {ANY_USER_NAME, ANY_MESSAGE};
  }
}
