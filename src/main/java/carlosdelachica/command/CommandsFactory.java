package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.Input;
import carlosdelachica.model.PostRepository;

public class CommandsFactory {

  private final Clock clock;
  private final PostRepository repository;
  private final View view;

  public CommandsFactory(Clock clock, PostRepository repository, View view) {
    this.clock = clock;
    this.repository = repository;
    this.view = view;
  }

  public Command make(Input input) {
    String[] arguments = input.getArguments();
    switch (input.getType()) {
      case POST:
        return new PostCommand(clock, repository, arguments);
      case READ:
        return new ReadCommand(view, repository, arguments);
      case FOLLOW:
        return new FollowCommand(repository, arguments);
      case WALL:
        return new WallCommand(repository, arguments);
    }
    return null;
  }
}
