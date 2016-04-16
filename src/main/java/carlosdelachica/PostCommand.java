package carlosdelachica;

import java.util.Arrays;

public class PostCommand implements Command {

  private final PostRepository repository;
  private final String[] arguments;

  public PostCommand(PostRepository repository, String[] arguments) {
    this.repository = repository;
    this.arguments = arguments;
  }

  public void execute() {

  }

  @Override public String toString() {
    return "PostCommand{" +
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

    PostCommand that = (PostCommand) o;

    return Arrays.equals(arguments, that.arguments);
  }

  @Override public int hashCode() {
    return Arrays.hashCode(arguments);
  }
}
