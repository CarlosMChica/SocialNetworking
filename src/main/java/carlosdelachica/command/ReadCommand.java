package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.model.Post;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.User;
import carlosdelachica.model.UserRepository;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ReadCommand implements Command {

  private final View view;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final String[] arguments;

  public ReadCommand(View view, PostRepository postRepository, UserRepository userRepository,
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
        sorted(Post.REVERSE_CHRONOLOGICAL).
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
