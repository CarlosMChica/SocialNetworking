package carlosdelachica;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PostingFeature {

  private static final String ANY_USER_NAME = "Bob";
  private static final User BOB = new User(ANY_USER_NAME);
  private static final long FIRST_POST_TIMESTAMP = 1L;
  private static final long SECOND_POST_TIMESTAMP = 2L;
  private static final String FIRST_POST_MESSAGE = "Damn! We lost!";
  private static final String SECOND_POST_MESSAGE = "Good game though.";
  private static final Post FIRST_POST = new Post(BOB, FIRST_POST_MESSAGE, FIRST_POST_TIMESTAMP);
  private static final Post SECOND_POST = new Post(BOB, SECOND_POST_MESSAGE, SECOND_POST_TIMESTAMP);
  private static final List<Post> POSTS = asList(FIRST_POST, SECOND_POST);

  private PostRepository repository;
  private Invoker invoker;

  @Before public void setUp() {
    repository = new PostRepository();
    invoker = new Invoker(new Command[] {new PostCommand(repository)});
  }

  @Test public void user_can_publish_messages_to_timeline() {
    Input[] inputs = givenInputs();

    for (Input input : inputs) {
      invoker.execute(input);
    }

    assertThat(repository.postsOf(BOB), is(POSTS));
  }

  private Input[] givenInputs() {
    InputParser inputParser = new InputParser();
    Input firstInput = inputParser.parse(ANY_USER_NAME + " -> " + FIRST_POST_MESSAGE);
    Input secondInput = inputParser.parse(ANY_USER_NAME + " -> " + SECOND_POST_MESSAGE);
    return new Input[] {firstInput, secondInput};
  }
}
