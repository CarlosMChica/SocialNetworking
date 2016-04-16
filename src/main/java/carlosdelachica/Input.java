package carlosdelachica;

import java.util.Arrays;

public class Input {

  private String[] arguments;
  private Type type;

  public Input(Type type, String[] arguments) {
    this.arguments = arguments;
    this.type = type;
  }

  public String[] getArguments() {
    return arguments;
  }

  @Override public String toString() {
    return "Input{" +
        "arguments=" + Arrays.toString(arguments) +
        ", type=" + type +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Input input = (Input) o;

    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    if (!Arrays.equals(arguments, input.arguments)) {
      return false;
    }
    return type == input.type;
  }

  @Override public int hashCode() {
    int result = Arrays.hashCode(arguments);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  public enum Type {
    POST
  }
}
