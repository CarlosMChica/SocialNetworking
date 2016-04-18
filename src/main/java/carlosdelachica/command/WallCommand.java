package carlosdelachica.command;

import carlosdelachica.model.PostRepository;
import java.util.Arrays;

public class WallCommand implements Command {

  private final PostRepository repository;
  private final String[] arguments;

  public WallCommand(PostRepository repository, String[] arguments) {
    this.repository = repository;
    this.arguments = arguments;
  }

  @Override public void execute() {
    throw new UnsupportedOperationException();
  }

  @Override public String toString() {
    return "WallCommand{" +
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

    WallCommand that = (WallCommand) o;

    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    return Arrays.equals(arguments, that.arguments);
  }

  @Override public int hashCode() {
    return Arrays.hashCode(arguments);
  }
}
