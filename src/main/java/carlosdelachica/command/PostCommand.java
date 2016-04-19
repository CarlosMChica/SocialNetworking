package carlosdelachica.command;

import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.Post;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.User;
import carlosdelachica.model.UserRepository;
import java.util.Arrays;

public class PostCommand implements Command {

  private final Clock clock;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final String[] arguments;

  public PostCommand(Clock clock, PostRepository postRepository, UserRepository userRepository,
      String[] arguments) {
    this.clock = clock;
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.arguments = arguments;
  }

  public void execute() {
    String userName = arguments[0];
    String message = arguments[1];
    storePost(userRepository.getByName(userName), message);
  }

  private void storePost(User user, String message) {
    postRepository.store(new Post(user, message, clock.currentTimeInMillis()));
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
