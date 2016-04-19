package carlosdelachica.model.input;

import org.junit.Before;
import org.junit.Test;

import static carlosdelachica.model.input.Input.Type.FOLLOW;
import static carlosdelachica.model.input.Input.Type.POST;
import static carlosdelachica.model.input.Input.Type.READ;
import static carlosdelachica.model.input.Input.Type.WALL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InputParserShould {

  private static final String ARGUMENT_1 = "arg1";
  private static final String ARGUMENT_2 = "arg2";
  private static final String[] ARGUMENTS = new String[] {ARGUMENT_1, ARGUMENT_2};

  private static final String POST_ACTION = " -> ";
  private static final String FOLLOW_ACTION = " follows ";
  private static final String WALL_ACTION = " wall";

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

    Input expectedInput = new Input(READ, ARGUMENT_1);
    assertThat(parsedInput, is(expectedInput));
  }

  @Test public void parse_input_with_follow_action() {
    String input = givenInputWithFollowAction();

    Input parsedInput = parser.parse(input);

    Input expectedInput = new Input(FOLLOW, ARGUMENTS);
    assertThat(parsedInput, is(expectedInput));
  }

  @Test public void parse_input_with_wall_action() {
    String input = givenInputWithWallAction();

    Input parsedInput = parser.parse(input);

    Input expectedInput = new Input(WALL, ARGUMENT_1);
    assertThat(parsedInput, is(expectedInput));
  }

  private String givenInputWithWallAction() {
    return ARGUMENT_1 + WALL_ACTION;
  }

  private String givenInputWithFollowAction() {
    return ARGUMENT_1 + FOLLOW_ACTION + ARGUMENT_2;
  }

  private String givenInputWithReadAction() {
    return ARGUMENT_1;
  }

  private String givenInputWithPostAction() {
    return ARGUMENT_1 + POST_ACTION + ARGUMENT_2;
  }
}
