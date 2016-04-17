package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.Input;
import carlosdelachica.model.PostRepository;

public class CommandsFactory {

  private final Clock clock;
  private final PostRepository repository;

  public CommandsFactory(Clock clock, PostRepository repository, View view) {
    this.clock = clock;
    this.repository = repository;
  }

  public Command make(Input input) {
    return new PostCommand(clock, repository, input.getArguments());
  }
}
