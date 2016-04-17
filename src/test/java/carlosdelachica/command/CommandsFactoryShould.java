package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.Input;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static carlosdelachica.model.Input.Type.FOLLOW;
import static carlosdelachica.model.Input.Type.POST;
import static carlosdelachica.model.Input.Type.READ;
import static carlosdelachica.model.Input.Type.WALL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class) public class CommandsFactoryShould {

  private static final String[] ANY_ARGUMENTS = new String[] {"arg1"};

  @Mock UserRepository userRepository;
  @Mock PostRepository postRepository;
  @Mock Clock clock;
  @Mock View view;

  private CommandsFactory commandsFactory;

  @Before public void setUp() {
    commandsFactory = new CommandsFactory(clock, view, postRepository, userRepository);
  }

  @Test public void make_post_command_with_arguments_for_a_given_post_input() {
    Input postInput = givenPostInputWith(ANY_ARGUMENTS);

    Command command = commandsFactory.make(postInput);

    Command expectedCommand = new PostCommand(clock, postRepository, ANY_ARGUMENTS);
    assertThat(command, is(expectedCommand));
  }

  @Test public void make_read_command_with_arguments_for_a_given_read_input() {
    Input readInput = givenReadInputWith(ANY_ARGUMENTS);

    Command command = commandsFactory.make(readInput);

    Command expectedCommand = new ReadCommand(view, postRepository, ANY_ARGUMENTS);
    assertThat(command, is(expectedCommand));
  }

  @Test public void make_follow_command_with_arguments_for_a_given_follow_input() {
    Input readInput = givenFollowInputWith(ANY_ARGUMENTS);

    Command command = commandsFactory.make(readInput);

    Command expectedCommand = new FollowCommand(userRepository, ANY_ARGUMENTS);
    assertThat(command, is(expectedCommand));
  }

  @Test public void make_wall_command_with_arguments_for_a_given_wall_input() {
    Input readInput = givenWallInputWith(ANY_ARGUMENTS);

    Command command = commandsFactory.make(readInput);

    Command expectedCommand = new WallCommand(postRepository, ANY_ARGUMENTS);
    assertThat(command, is(expectedCommand));
  }

  private Input givenWallInputWith(String[] arguments) {
    return new Input(WALL, arguments);
  }

  private Input givenFollowInputWith(String[] arguments) {
    return new Input(FOLLOW, arguments);
  }

  private Input givenPostInputWith(String[] arguments) {
    return new Input(POST, arguments);
  }

  private Input givenReadInputWith(String[] arguments) {
    return new Input(READ, arguments);
  }
}
