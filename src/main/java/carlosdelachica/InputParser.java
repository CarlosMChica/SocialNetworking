package carlosdelachica;

import static carlosdelachica.Input.Type.POST;

public class InputParser {
  public Input parse(String input) {
    String[] arguments = input.split(" -> ");
    return new Input(POST, arguments);
  }
}
