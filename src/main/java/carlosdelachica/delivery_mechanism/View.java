package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.post.Post;
import java.util.List;

public class View {

  private static final String PROMPT = "> ";

  private final ConsoleWrapper console;
  private final PostFormatter formatter;

  public View(ConsoleWrapper console, PostFormatter formatter) {
    this.console = console;
    this.formatter = formatter;
  }

  public String getUserInput() {
    console.print(PROMPT);
    return console.readLine();
  }

  public void printTimeline(List<Post> posts) {
    console.printLines(formatter.formatTimeline(posts));
  }

  public void printWall(List<Post> posts) {
    console.printLines(formatter.formatWall(posts));
  }
}
