package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.post.Post;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class PostFormatter {

  private static final String TIMELINE_FORMAT = "%s (%s)";
  private static final String WALL_FORMAT = "%s - %s (%s)";

  private final TimeAgoFormatter timeAgoFormatter;

  public PostFormatter(TimeAgoFormatter timeAgoFormatter) {
    this.timeAgoFormatter = timeAgoFormatter;
  }

  public List<String> formatTimeline(List<Post> posts) {
    return format(posts, this::formatTimeline);
  }

  public List<String> formatWall(List<Post> posts) {
    return format(posts, this::formatWall);
  }

  private List<String> format(List<Post> posts, Function<Post, String> formatter) {
    return posts.
        stream().
        map(formatter).
        collect(toList());
  }

  private String formatWall(Post post) {
    return String.format(WALL_FORMAT, post.getUserName(), post.getMessage(), formatTimeAgo(post));
  }

  private String formatTimeline(Post post) {
    return String.format(TIMELINE_FORMAT, post.getMessage(), formatTimeAgo(post));
  }

  private String formatTimeAgo(Post post) {
    return timeAgoFormatter.format(post.getTimestamp());
  }
}
