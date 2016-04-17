package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.Input;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.UserRepository;

public class CommandsFactory {

  private final Clock clock;
  private final View view;
  private final PostRepository repository;
  private final UserRepository userRepository;

  public CommandsFactory(Clock clock, View view, PostRepository postRepository,
      UserRepository userRepository) {
    this.clock = clock;
    this.repository = postRepository;
    this.view = view;
    this.userRepository = userRepository;
  }

  public Command make(Input input) {
    String[] arguments = input.getArguments();
    switch (input.getType()) {
      case POST:
        return new PostCommand(clock, repository, arguments);
      case READ:
        return new ReadCommand(view, repository, arguments);
      case FOLLOW:
        return new FollowCommand(userRepository, arguments);
      case WALL:
        return new WallCommand(repository, arguments);
    }
    return null;
  }
}
