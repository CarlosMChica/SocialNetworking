package carlosdelachica;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PostRepositoryShould {

  private static final long TIMESTAMP = 0L;
  private static final String ANY_USER_NAME = "userName";
  private static final String ANY_MESSAGE = "Message";
  private static final User ANY_USER = new User(ANY_USER_NAME);
  private static final int NUMBER_OF_POSTS = 1;

  private PostRepository repository;

  @Before public void setUp() {
    repository = new PostRepository();
  }

  @Test public void store_posts() {
    Post post = givenPostFor(ANY_USER);

    repository.store(post);

    List<Post> posts = repository.postsOf(ANY_USER);
    assertThat(posts.size(), is(NUMBER_OF_POSTS));
    assertThat(posts.get(0), is(post));
  }

  private Post givenPostFor(User user) {
    return new Post(user, ANY_MESSAGE, TIMESTAMP);
  }
}