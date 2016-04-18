package carlosdelachica.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class) public class UserRepositoryShould {

  private static final String USER_NAME = "userName";
  private static final User NEW_USER = new User(USER_NAME);

  private UserRepository repository;

  @Before public void setUp() {
    repository = new UserRepository();
  }

  @Test public void return_new_user_if_user_for_given_name_does_not_exist() {
    User user = repository.getByName(USER_NAME);

    assertThat(user, is(NEW_USER));
  }

  @Test public void return_existing_user_if_user_for_given_name_exist() {
    User existingUser = givenExistingUser();
    repository.add(existingUser);

    User user = repository.getByName(USER_NAME);

    assertThat(user, is(existingUser));
  }

  private User givenExistingUser() {
    User friend = new User("friend");
    User user = new User(USER_NAME);
    user.follow(friend);
    return user;
  }
}