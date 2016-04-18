package carlosdelachica.model;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

  private final List<User> users = new ArrayList<>();

  public void add(User user) {
    users.add(user);
  }

  public User getByName(String userName) {
    return users.stream()
        .filter(user -> user.hasName(userName))
        .findFirst()
        .orElseGet(() -> new User(userName));
  }
}
