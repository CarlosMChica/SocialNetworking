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

  private static final long ANY_TIMESTAMP = 0L;

  private static final String USER_NAME = "userName";
  private static final User USER = new User(USER_NAME);

  private static final String POST_1_MESSAGE = "Good game though.";
  private static final String POST_2_MESSAGE = "Damn! We lost!";

  private static final String POST_1_TIME_AGO = "1 minute ago";
  private static final String POST_2_TIME_AGO = "2 minutes ago";

  private static final String WALL_FORMAT = "%s - %s (%s)";
  private static final String TIMELINE_FORMAT = "%s (%s)";

  private static final String POST_1_TIMELINE_FORMAT =
      String.format(TIMELINE_FORMAT, POST_1_MESSAGE, POST_1_TIME_AGO);
  private static final String POST_2_TIMELINE_FORMAT =
      String.format(TIMELINE_FORMAT, POST_2_MESSAGE, POST_2_TIME_AGO);

  private static final String POST_1_WALL_FORMAT =
      String.format(WALL_FORMAT, USER_NAME, POST_1_MESSAGE, POST_1_TIME_AGO);
  private static final String POST_2_WALL_FORMAT =
      String.format(WALL_FORMAT, USER_NAME, POST_2_MESSAGE, POST_2_TIME_AGO);

  @Mock TimeAgoFormatter timeAgoFormatter;

  private PostFormatter formatter;

  @Before public void setUp() {
    formatter = new PostFormatter(timeAgoFormatter);
    given(timeAgoFormatter.format(any(Long.class))).willReturn(POST_1_TIME_AGO, POST_2_TIME_AGO);
  }

  @Test public void format_timeline_posts() {
    List<Post> posts = givenPosts();

    List<String> formattedPosts = formatter.formatTimeline(posts);

    assertThat(formattedPosts.size(), is(2));
    assertThat(formattedPosts.get(0), is(POST_1_TIMELINE_FORMAT));
    assertThat(formattedPosts.get(1), is(POST_2_TIMELINE_FORMAT));
  }

  @Test public void format_wall_posts() {
    List<Post> posts = givenPosts();

    List<String> formattedPosts = formatter.formatWall(posts);

    assertThat(formattedPosts.size(), is(2));
    assertThat(formattedPosts.get(0), is(POST_1_WALL_FORMAT));
    assertThat(formattedPosts.get(1), is(POST_2_WALL_FORMAT));
  }

  private List<Post> givenPosts() {
    Post post1 = new Post(USER, POST_1_MESSAGE, ANY_TIMESTAMP);
    Post post2 = new Post(USER, POST_2_MESSAGE, ANY_TIMESTAMP);
    return asList(post1, post2);
  }
}