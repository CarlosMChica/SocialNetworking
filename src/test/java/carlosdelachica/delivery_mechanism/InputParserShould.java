package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.Input;
import org.junit.Before;
import org.junit.Test;

import static carlosdelachica.model.Input.Type.POST;
import static carlosdelachica.model.Input.Type.READ;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InputParserShould {

  private static final String READ_ARGUMENT = "readArgument";
  private static final String POST_FIRST_ARGUMENT = "arg1";
  private static final String POST_SECOND_ARGUMENT = "arg2";
  private static final String[] ARGUMENTS =
      new String[] {POST_FIRST_ARGUMENT, POST_SECOND_ARGUMENT};

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

  @Test public void parse_input_with_read_action() {
    String input = givenInputWithReadAction();

    Input parsedInput = parser.parse(input);

    Input expectedInput = new Input(READ, READ_ARGUMENT);
    assertThat(parsedInput, is(expectedInput));
  }

  private String givenInputWithReadAction() {
    return READ_ARGUMENT;
  }

  private String givenInputWithPostAction() {
    return POST_FIRST_ARGUMENT + " -> " + POST_SECOND_ARGUMENT;
  }
}
