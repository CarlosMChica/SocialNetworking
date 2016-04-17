package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.model.Post;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.User;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ReadCommand implements Command {

  private static final Comparator<Post> TIMELINE_COMPARATOR =
      (post1, post2) -> (int) (post2.getTimestamp() - post1.getTimestamp());

  private final View view;
  private final PostRepository repository;
  private final String[] arguments;

  public ReadCommand(View view, PostRepository repository, String[] arguments) {
    this.view = view;
    this.repository = repository;
    this.arguments = arguments;
  }

  public void execute() {
    User user = new User(arguments[0]);
    List<Post> timeline = generateTimelineFor(user);
    view.print(timeline);
  }

  private List<Post> generateTimelineFor(User user) {
    return repository.postsOf(user).
        stream().
        sorted(TIMELINE_COMPARATOR).
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
