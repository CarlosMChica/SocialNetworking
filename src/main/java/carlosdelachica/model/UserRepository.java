package carlosdelachica.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

  private final List<User> users = new ArrayList<>();

  public User getByName(String userName) {
    return getUser(userName).orElseGet(() -> store(userName));
  }

  int count() {
    return users.size();
  }

  private Optional<User> getUser(String userName) {
    return users.stream().
        filter(user -> user.hasName(userName)).
        findFirst();
  }

  private User store(String userName) {
    User user = new User(userName);
    users.add(user);
    return user;
  }
}
