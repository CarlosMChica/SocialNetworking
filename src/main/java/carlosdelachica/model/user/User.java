package carlosdelachica.model.user;

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

  public boolean hasName(String name) {
    return this.name.equals(name);
  }

  public void follow(User friend) {
    friends.add(friend);
  }

  public String getUserName() {
    return name;
  }

  public List<User> friends() {
    return unmodifiableList(friends);
  }

  @Override public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        ", friends=" + friends +
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

    if (name != null ? !name.equals(user.name) : user.name != null) {
      return false;
    }
    return friends != null ? friends.equals(user.friends) : user.friends == null;
  }

  @Override public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (friends != null ? friends.hashCode() : 0);
    return result;
  }
}
