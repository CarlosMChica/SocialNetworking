package carlosdelachica;

import org.junit.Before;
import org.junit.Test;

import static carlosdelachica.Input.POST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InputParserShould {

  private InputParser parser;

  @Before public void setUp() {
    parser = new InputParser();
  }

  @Test public void parse_input_with_post_action() {
    String input = givenInputWithPostAction();

    Input parsedInput = parser.parse(input);

    assertThat(parsedInput, is(POST.withArguments("User", "User Message")));
  }

  private String givenInputWithPostAction() {
    return "User -> User Message";
  }
}
