package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.input.Input;
import carlosdelachica.model.post.PostRepository;
import carlosdelachica.model.user.UserRepository;

public class CommandsFactory {

  private final Clock clock;
  private final View view;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public CommandsFactory(Clock clock, View view, PostRepository postRepository,
      UserRepository userRepository) {
    this.clock = clock;
    this.postRepository = postRepository;
    this.view = view;
    this.userRepository = userRepository;
  }

  public Command make(Input input) {
    String[] arguments = input.getArguments();
    switch (input.getType()) {
      case POST:
        return new PostCommand(clock, postRepository, userRepository, arguments);
      case READ:
        return new ReadCommand(view, postRepository, userRepository, arguments);
      case FOLLOW:
        return new FollowCommand(userRepository, arguments);
      case WALL:
        return new WallCommand(view, postRepository, userRepository, arguments);
    }
    return null;
  }
}
