package carlosdelachica.command;

import carlosdelachica.infrastructure.Clock;
import carlosdelachica.console.Input;
import carlosdelachica.model.PostRepository;

public class CommandsFactory {

  private final Clock clock;
  private final PostRepository repository;

  public CommandsFactory(Clock clock, PostRepository repository) {
    this.clock = clock;
    this.repository = repository;
  }

  public Command make(Input input) {
    return new PostCommand(clock, repository, input.getArguments());
  }
}
