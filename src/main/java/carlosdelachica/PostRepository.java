package carlosdelachica;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class PostRepository {

  private List<Post> posts = new ArrayList<Post>();

  public List<Post> postsOf(User user) {
    return unmodifiableList(posts);
  }

  public void store(Post post) {
    posts.add(post);
  }
}
