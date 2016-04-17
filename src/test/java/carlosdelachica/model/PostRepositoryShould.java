package carlosdelachica.model;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class) public class PostRepositoryShould {

  private static final String ANY_USER_NAME = "userName";
  private static final User USER = new User(ANY_USER_NAME);

  private static final boolean IS_USER_POST = true;
  private static final boolean IS_NOT_USER_POST = false;

  @Mock Post userPost;
  @Mock Post otherUserPost;

  private PostRepository repository;

  @Before public void setUp() {
    repository = new PostRepository();

    given(userPost.isFrom(USER)).willReturn(IS_USER_POST);
    given(otherUserPost.isFrom(USER)).willReturn(IS_NOT_USER_POST);
  }

  @Test public void store_post_by_user() {
    repository.store(userPost);
    repository.store(otherUserPost);

    List<Post> posts = repository.postsOf(USER);

    assertThat(posts.size(), is(1));
    assertThat(posts.get(0), is(userPost));
  }
}