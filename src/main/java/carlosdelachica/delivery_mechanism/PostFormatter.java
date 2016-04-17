package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.Post;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PostFormatter {

  private final TimeAgoFormatter timeAgoFormatter;

  public PostFormatter(TimeAgoFormatter timeAgoFormatter) {
    this.timeAgoFormatter = timeAgoFormatter;
  }

  public List<String> format(List<Post> posts) {
    return posts.
        stream().
        map(this::format).
        collect(toList());
  }

  private String format(Post post) {
    return String.format("%s (%s)", post.getMessage(), formatTimeAgo(post));
  }

  private String formatTimeAgo(Post post) {
    return timeAgoFormatter.format(post.getTimestamp());
  }
}
