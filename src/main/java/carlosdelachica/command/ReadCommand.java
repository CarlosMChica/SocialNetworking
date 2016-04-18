package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.model.Post;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.User;
import carlosdelachica.model.UserRepository;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ReadCommand implements Command {

  private static final Comparator<Post> REVERSE_CHRONOLOGICAL =
      (post1, post2) -> (int) (post2.getTimestamp() - post1.getTimestamp());

  private final View view;
  private final PostRepository repository;
  private final UserRepository userRepository;
  private final String[] arguments;

  public ReadCommand(View view, PostRepository repository, UserRepository userRepository,
      String[] arguments) {
    this.view = view;
    this.repository = repository;
    this.userRepository = userRepository;
    this.arguments = arguments;
  }

  public void execute() {
    String userName = arguments[0];
    printTimeline(userRepository.getByName(userName));
  }

  private void printTimeline(User user) {
    List<Post> timeline = generateTimelineFor(user);
    view.print(timeline);
  }

  private List<Post> generateTimelineFor(User user) {
    return repository.postsOf(user).
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
