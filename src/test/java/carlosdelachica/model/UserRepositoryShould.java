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

  @Test public void return_and_store_new_user_if_user_for_given_name_does_not_exist() {
    User user = repository.getByName(USER_NAME);

    assertThat(user, is(USER));
    assertThat(repository.count(), is(1));
  }

  @Test public void return_existing_user_if_user_for_given_name_exist() {
    repository.getByName(USER_NAME);

    User user = repository.getByName(USER_NAME);

    assertThat(user, is(USER));
    assertThat(repository.count(), is(1));
  }
}