package carlosdelachica;

import static carlosdelachica.Input.POST;

public class InputParser {
  public Input parse(String input) {
    String[] parts = input.split(" -> ");
    return POST.withArguments(parts);
  }
}
