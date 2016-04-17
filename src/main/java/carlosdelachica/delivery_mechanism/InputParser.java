package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.Input;

import static carlosdelachica.model.Input.Type.POST;

public class InputParser {

  private static final String POST_ACTION = " -> ";

  public Input parse(String input) {
    String[] arguments = input.split(POST_ACTION);
    return new Input(POST, arguments);
  }
}
