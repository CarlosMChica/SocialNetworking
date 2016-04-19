package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.model.Post;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.User;
import carlosdelachica.model.UserRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static carlosdelachica.model.Post.REVERSE_CHRONOLOGICAL;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

class WallCommand implements Command {

  private final View view;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final String[] arguments;

  WallCommand(View view, PostRepository postRepository, UserRepository userRepository,
      String[] arguments) {
    this.view = view;
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.arguments = arguments;
  }

  @Override public void execute() {
    String userName = arguments[0];
    printWall(userRepository.getByName(userName));
  }

  private void printWall(User user) {
    view.printWall(generateUserWall(user));
  }

  private List<Post> generateUserWall(User user) {
    return userAndFriendsStream(user).map(postRepository::postsOf)
        .reduce(this::allPosts)
        .orElseGet(Collections::emptyList)
        .stream()
        .sorted(REVERSE_CHRONOLOGICAL)
        .collect(toList());
  }

  private Stream<User> userAndFriendsStream(User user) {
    return concat(of(user), user.friends().stream());
  }

  private List<Post> allPosts(List<Post> posts, List<Post> posts2) {
    posts.addAll(posts2);
    return posts;
  }

  @Override public String toString() {
    return "WallCommand{" +
        "arguments=" + Arrays.toString(arguments) +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    WallCommand that = (WallCommand) o;

    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    return Arrays.equals(arguments, that.arguments);
  }

  @Override public int hashCode() {
    return Arrays.hashCode(arguments);
  }
}
