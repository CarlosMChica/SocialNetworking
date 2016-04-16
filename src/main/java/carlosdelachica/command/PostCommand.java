package carlosdelachica.command;

import carlosdelachica.Clock;
import carlosdelachica.model.Post;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.User;
import java.util.Arrays;

public class PostCommand implements Command {

  private final Clock clock;
  private final PostRepository repository;
  private final String[] arguments;

  public PostCommand(Clock clock, PostRepository repository, String[] arguments) {
    this.clock = clock;
    this.repository = repository;
    this.arguments = arguments;
  }

  public void execute() {
    User user = new User(arguments[0]);
    String message = arguments[1];
    Post post = new Post(user, message, clock.currentTimeInMillis());
    repository.store(post);
  }

  @Override public String toString() {
    return "PostCommand{" +
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

    PostCommand that = (PostCommand) o;

    return Arrays.equals(arguments, that.arguments);
  }

  @Override public int hashCode() {
    return Arrays.hashCode(arguments);
  }
}
