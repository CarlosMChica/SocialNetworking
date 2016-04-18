package carlosdelachica.model;

import java.util.Comparator;

public class Post {

  public static final Comparator<Post> REVERSE_CHRONOLOGICAL =
      (post1, post2) -> (int) (post2.getTimestamp() - post1.getTimestamp());

  private final User user;
  private final String message;
  private final long timestamp;

  public Post(User user, String message, long timestamp) {
    this.user = user;
    this.message = message;
    this.timestamp = timestamp;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public boolean isFrom(User user) {
    return this.user.equals(user);
  }

  public String getMessage() {
    return message;
  }

  public String getUserName() {
    return this.user.getUserName();
  }

  @Override public String toString() {
    return "Post{" +
        "user=" + user +
        ", message='" + message + '\'' +
        ", timestamp=" + timestamp +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Post post = (Post) o;

    if (timestamp != post.timestamp) {
      return false;
    }
    if (user != null ? !user.equals(post.user) : post.user != null) {
      return false;
    }
    return message != null ? message.equals(post.message) : post.message == null;
  }

  @Override public int hashCode() {
    int result = user != null ? user.hashCode() : 0;
    result = 31 * result + (message != null ? message.hashCode() : 0);
    result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
    return result;
  }
}
