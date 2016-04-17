package carlosdelachica.model;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class User {

  private final String name;
  private List<User> friends;

  public User(String name) {
    this.name = name;
    this.friends = new ArrayList<>();
  }

  public void follow(User friend) {
    friends.add(friend);
  }

  public List<User> friends() {
    return unmodifiableList(friends);
  }

  @Override public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    return name != null ? name.equals(user.name) : user.name == null;
  }

  @Override public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }
}
