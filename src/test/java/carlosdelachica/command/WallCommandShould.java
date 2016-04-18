package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.model.Post;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.User;
import carlosdelachica.model.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class WallCommandShould {

  private static final long OLDEST_TIMESTAMP = 0L;
  private static final long BETWEEN_TIMESTAMP = 1L;
  private static final long NEWEST_TIMESTAMP = 2L;

  private static final User FRIEND_1 = new User("friend1");
  private static final Post FRIEND_1_POST = new Post(FRIEND_1, "friend1Message", OLDEST_TIMESTAMP);
  private static final List<Post> FRIEND_1_POSTS = new ArrayList<>(singletonList(FRIEND_1_POST));

  private static final User FRIEND_2 = new User("friend2");
  private static final Post FRIEND_2_POST = new Post(FRIEND_2, "friend2Message", BETWEEN_TIMESTAMP);
  private static final List<Post> FRIEND_2_POSTS = new ArrayList<>(singletonList(FRIEND_2_POST));

  private static final String USER_NAME = "userName";
  private static final User USER = givenUserWithFriends(FRIEND_1, FRIEND_2);
  private static final Post USER_POST = new Post(USER, "userMessage", NEWEST_TIMESTAMP);
  private static final List<Post> USER_POSTS = new ArrayList<>(singletonList(USER_POST));

  private static final List<Post> USER_WALL = asList(USER_POST, FRIEND_2_POST, FRIEND_1_POST);

  @Mock View view;

  @Mock PostRepository postRepository;
  @Mock UserRepository userRepository;

  @Before public void setUp() {
    given(userRepository.getByName(USER_NAME)).willReturn(USER);
    given(postRepository.postsOf(USER)).willReturn(USER_POSTS);
    given(postRepository.postsOf(FRIEND_1)).willReturn(FRIEND_1_POSTS);
    given(postRepository.postsOf(FRIEND_2)).willReturn(FRIEND_2_POSTS);
  }

  @Test public void print_user_wall_in_reverse_chronological_order() {
    String[] arguments = givenArguments();
    WallCommand command = new WallCommand(view, postRepository, userRepository, arguments);

    command.execute();

    verify(view).printWall(USER_WALL);
  }

  private String[] givenArguments() {
    return new String[] {USER_NAME};
  }

  private static User givenUserWithFriends(User... friends) {
    User user = new User(USER_NAME);
    Stream.of(friends).forEach(user::follow);
    return user;
  }
}