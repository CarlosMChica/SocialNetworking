package carlosdelachica.model.post;

import carlosdelachica.model.user.User;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PostShould {

  private static final String USER_NAME = "userName";
  private static final String OTHER_USER_NAME = "otherUserName";
  private static final User USER = new User(USER_NAME);
  private static final User OTHER_USER = new User(OTHER_USER_NAME);

  private static final long ANY_TIMESTAMP = 0L;
  private static final String ANY_MESSAGE = "";

  @Test public void return_is_from_user_for_user_post() {
    Post post = givenUserPost();

    boolean userPost = post.isFrom(USER);

    assertThat(userPost, is(true));
  }

  @Test public void return_is_not_from_user_for_other_user_post() {
    Post post = givenUserPost();

    boolean userPost = post.isFrom(OTHER_USER);

    assertThat(userPost, is(false));
  }

  private Post givenUserPost() {
    return new Post(USER, ANY_MESSAGE, ANY_TIMESTAMP);
  }
}