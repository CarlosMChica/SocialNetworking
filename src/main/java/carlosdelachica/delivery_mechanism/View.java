package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.Post;
import java.util.List;

public class View {

  private static final String PROMPT = "> ";

  private final ConsoleWrapper console;
  private final PostFormatter postFormatter;

  public View(ConsoleWrapper console, PostFormatter postFormatter) {
    this.console = console;
    this.postFormatter = postFormatter;
  }

  public String getUserInput() {
    console.print(PROMPT);
    return console.readLine();
  }

  public void printTimeline(List<Post> posts) {
    console.printLines(postFormatter.formatTimeline(posts));
  }
}
