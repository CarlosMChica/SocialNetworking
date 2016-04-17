package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.model.PostRepository;
import java.util.Arrays;

public class ReadCommand implements Command {

  private final View view;
  private final PostRepository repository;
  private final String[] arguments;

  public ReadCommand(View view, PostRepository repository, String[] arguments) {
    this.view = view;
    this.repository = repository;
    this.arguments = arguments;
  }

  public void execute() {

  }

  @Override public String toString() {
    return "ReadCommand{" +
        "arguments=" + Arrays.toString(arguments) +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ReadCommand that = (ReadCommand) o;

    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    return Arrays.equals(arguments, that.arguments);
  }

  @Override public int hashCode() {
    return Arrays.hashCode(arguments);
  }
}
