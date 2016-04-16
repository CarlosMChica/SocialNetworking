package carlosdelachica;

public enum Input {
  POST;

  private String[] arguments;

  public Input withArguments(String... arguments) {
    this.arguments = arguments;
    return this;
  }

  public String[] getArguments() {
    return arguments;
  }
}
