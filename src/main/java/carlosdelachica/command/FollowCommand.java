package carlosdelachica.command;

import carlosdelachica.model.User;
import carlosdelachica.model.UserRepository;
import java.util.Arrays;

public class FollowCommand implements Command {

  private final UserRepository userRepository;
  private final String[] arguments;

  public FollowCommand(UserRepository userRepository, String[] arguments) {
    this.userRepository = userRepository;
    this.arguments = arguments;
  }

  @Override public void execute() {
    user().follow(friend());
  }

  private User user() {
    String userName = arguments[0];
    return userRepository.getByName(userName);
  }

  private User friend() {
    String friendName = arguments[1];
    return userRepository.getByName(friendName);
  }

  @Override public String toString() {
    return "FollowCommand{" +
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

    FollowCommand that = (FollowCommand) o;

    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    return Arrays.equals(arguments, that.arguments);
  }

  @Override public int hashCode() {
    return Arrays.hashCode(arguments);
  }
}
