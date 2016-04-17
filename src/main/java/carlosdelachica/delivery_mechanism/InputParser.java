package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.Input;

import static carlosdelachica.model.Input.Type.POST;
import static carlosdelachica.model.Input.Type.READ;

public class InputParser {

  private static final String POST_ACTION = " -> ";

  public Input parse(String input) {
    if (isPostType(input)) {
      return new Input(POST, argumentsFor(POST_ACTION, input));
    } else {
      return new Input(READ, input);
    }
  }

  private boolean isPostType(String input) {
    return argumentsFor(POST_ACTION, input).length > 1;
  }

  private String[] argumentsFor(String action, String input) {
    return input.split(action);
  }
}
