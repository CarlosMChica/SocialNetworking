package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.Post;
import java.util.List;

public class View {

  private static final String PROMPT = "> ";

  private ConsoleWrapper console;

  public View(ConsoleWrapper console) {
    this.console = console;
  }

  public String getUserInput() {
    console.print(PROMPT);
    return console.readLine();
  }

  public void print(List<Post> posts) {
    throw new UnsupportedOperationException();
  }
}
