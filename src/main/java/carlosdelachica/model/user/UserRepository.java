package carlosdelachica.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

  private final List<User> users = new ArrayList<>();

  public User getByName(String userName) {
    return getUser(userName).orElseGet(() -> {
      throw new UserNotRegistered();
    });
  }

  public void register(String userName) {
    users.add(new User(userName));
  }

  public boolean isRegistered(String userName) {
    return getUser(userName).isPresent();
  }

  private Optional<User> getUser(String userName) {
    return users.stream().
        filter(user -> user.hasName(userName)).
        findFirst();
  }

  class UserNotRegistered extends RuntimeException {
  }
}
