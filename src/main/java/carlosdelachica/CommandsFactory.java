package carlosdelachica;

public class CommandsFactory {

  private PostRepository repository;

  public CommandsFactory(PostRepository repository) {
    this.repository = repository;
  }

  public Command make(Input input) {
    return new PostCommand(repository, input.getArguments());
  }
}
