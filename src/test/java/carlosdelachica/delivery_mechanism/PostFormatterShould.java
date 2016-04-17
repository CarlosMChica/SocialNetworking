package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.Post;
import carlosdelachica.model.User;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class) public class PostFormatterShould {

  private static final User ANY_USER = null;
  private static final long ANY_TIMESTAMP = 0L;

  private static final String POST_1_MESSAGE = "Good game though.";
  private static final String POST_2_MESSAGE = "Damn! We lost!";

  @Mock TimeAgoFormatter timeAgoFormatter;

  private PostFormatter formatter;

  @Before public void setUp() {
    formatter = new PostFormatter(timeAgoFormatter);
    given(timeAgoFormatter.format(any(Long.class))).willReturn("1 minute ago", "2 minutes ago");
  }

  @Test public void format_posts() {
    List<Post> posts = givenPosts();

    List<String> formattedPosts = formatter.format(posts);

    assertThat(formattedPosts.size(), is(2));
    assertThat(formattedPosts.get(0), is("Good game though. (1 minute ago)"));
    assertThat(formattedPosts.get(1), is("Damn! We lost! (2 minutes ago)"));
  }

  private List<Post> givenPosts() {
    Post post1 = new Post(ANY_USER, POST_1_MESSAGE, ANY_TIMESTAMP);
    Post post2 = new Post(ANY_USER, POST_2_MESSAGE, ANY_TIMESTAMP);
    return asList(post1, post2);
  }
}