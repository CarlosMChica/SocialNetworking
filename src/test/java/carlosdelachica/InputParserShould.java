package carlosdelachica;

import org.junit.Before;
import org.junit.Test;

import static carlosdelachica.Input.Type.POST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InputParserShould {

  private static final String FIRST_ARGUMENT = "arg1";
  private static final String SECOND_ARGUMENT = "arg2";
  private static final String[] ARGUMENTS = new String[] {FIRST_ARGUMENT, SECOND_ARGUMENT};

  private InputParser parser;

  @Before public void setUp() {
    parser = new InputParser();
  }

  @Test public void parse_input_with_post_action() {
    String input = givenInputWithPostAction();

    Input parsedInput = parser.parse(input);

    Input expectedInput = new Input(POST, ARGUMENTS);
    assertThat(parsedInput, is(expectedInput));
  }

  private String givenInputWithPostAction() {
    return FIRST_ARGUMENT + " -> " + SECOND_ARGUMENT;
  }
}
