package carlosdelachica.model.post;

import carlosdelachica.model.user.User;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PostRepository {

  private List<Post> posts = new ArrayList<>();

  public List<Post> postsOf(User user) {
    return posts.
        stream().
        filter(post -> post.isFrom(user)).
        collect(toList());
  }

  public void store(Post post) {
    posts.add(post);
  }
}
