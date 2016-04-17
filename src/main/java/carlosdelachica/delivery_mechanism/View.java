package carlosdelachica.delivery_mechanism;

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
}
