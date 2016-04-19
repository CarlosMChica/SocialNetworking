package carlosdelachica.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class) public class UserRepositoryShould {

  private static final String USER_NAME = "userName";
  private static final User USER = new User(USER_NAME);

  private UserRepository repository;

  @Before public void setUp() {
    repository = new UserRepository();
  }

  @Test public void create_new_users() {
    repository.register(USER_NAME);

    assertThat(repository.getByName(USER_NAME), is(USER));
  }

  @Test public void return_false_if_user_is_not_registered() {
    boolean registered = repository.isRegistered(USER_NAME);

    assertThat(registered, is(false));
  }

  @Test public void return_true_if_user_is_not_registered() {
    repository.register(USER_NAME);

    boolean registered = repository.isRegistered(USER_NAME);

    assertThat(registered, is(true));
  }

  @Test(expected = UserRepository.UserNotRegistered.class)
  public void throw_exception_if_get_not_registered_user() {
    repository.getByName(USER_NAME);
  }
}