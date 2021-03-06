package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.model.post.Post;
import carlosdelachica.model.post.PostRepository;
import carlosdelachica.model.user.User;
import carlosdelachica.model.user.UserRepository;
import java.util.Arrays;
import java.util.List;

import static carlosdelachica.model.post.Post.REVERSE_CHRONOLOGICAL;
import static java.util.stream.Collectors.toList;

class ReadCommand implements Command {

  private final View view;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final String[] arguments;

  ReadCommand(View view, PostRepository postRepository, UserRepository userRepository,
      String[] arguments) {
    this.view = view;
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.arguments = arguments;
  }

  public void execute() {
    String userName = arguments[0];
    printTimeline(userRepository.getByName(userName));
  }

  private void printTimeline(User user) {
    view.printTimeline(generateTimelineFor(user));
  }

  private List<Post> generateTimelineFor(User user) {
    return postRepository.postsOf(user).
        stream().
        sorted(REVERSE_CHRONOLOGICAL).
        collect(toList());
  }

  @Override public String toString() {
    return "ReadCommand{" +
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

    ReadCommand that = (ReadCommand) o;

    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    return Arrays.equals(arguments, that.arguments);
  }

  @Override public int hashCode() {
    return Arrays.hashCode(arguments);
  }
}
