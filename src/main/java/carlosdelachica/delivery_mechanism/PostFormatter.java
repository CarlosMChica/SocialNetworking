package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.Post;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PostFormatter {

  private final TimeAgoFormatter timeAgoFormatter;

  public PostFormatter(TimeAgoFormatter timeAgoFormatter) {
    this.timeAgoFormatter = timeAgoFormatter;
  }

  public List<String> formatTimeline(List<Post> posts) {
    return posts.
        stream().
        map(this::formatTimeline).
        collect(toList());
  }

  public List<String> formatWall(List<Post> posts) {
    throw new UnsupportedOperationException();
  }

  private String formatTimeline(Post post) {
    return String.format("%s (%s)", post.getMessage(), formatTimeAgo(post));
  }

  private String formatTimeAgo(Post post) {
    return timeAgoFormatter.format(post.getTimestamp());
  }
}
