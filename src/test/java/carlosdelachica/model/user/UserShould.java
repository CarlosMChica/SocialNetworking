package carlosdelachica.model.user;

import java.util.List;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserShould {

  private static final String ANY_USER_NAME = "userName";
  private static final User FRIEND = new User("friendName");

  @Test public void store_user_friends_without_duplicates() {
    User user = new User(ANY_USER_NAME);

    user.follow(FRIEND);
    user.follow(FRIEND);

    List<User> friends = user.friends();
    assertThat(friends.size(), is(1));
    assertThat(friends.get(0), is(FRIEND));
  }
}