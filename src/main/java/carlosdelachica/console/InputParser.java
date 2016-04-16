package carlosdelachica.console;

import static carlosdelachica.console.Input.Type.POST;

public class InputParser {

  private static final String POST_ACTION = " -> ";

  public Input parse(String input) {
    String[] arguments = input.split(POST_ACTION);
    return new Input(POST, arguments);
  }
}
