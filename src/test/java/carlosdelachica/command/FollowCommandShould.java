package carlosdelachica.command;

import carlosdelachica.model.user.User;
import carlosdelachica.model.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class FollowCommandShould {

  private static final String USER_NAME = "userName";
  private static final String FRIEND_NAME = "friendName";
  private static final User FRIEND = new User(FRIEND_NAME);

  @Mock UserRepository userRepository;
  @Mock User user;

  @Before public void setUp() {
    given(userRepository.getByName(USER_NAME)).willReturn(user);
    given(userRepository.getByName(FRIEND_NAME)).willReturn(FRIEND);
  }

  @Test public void user_can_follow_other_user_timeline() {
    String[] arguments = givenArguments();
    FollowCommand command = new FollowCommand(userRepository, arguments);

    command.execute();

    verify(user).follow(FRIEND);
  }

  private String[] givenArguments() {
    return new String[] {USER_NAME, FRIEND_NAME};
  }
}