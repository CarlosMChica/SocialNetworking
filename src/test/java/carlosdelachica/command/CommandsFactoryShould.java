package carlosdelachica.command;

import carlosdelachica.delivery_mechanism.ConsoleWrapper;
import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.Input;
import carlosdelachica.model.PostRepository;
import org.junit.Before;
import org.junit.Test;

import static carlosdelachica.model.Input.Type.POST;
import static carlosdelachica.model.Input.Type.READ;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CommandsFactoryShould {

  private static final String[] ANY_ARGUMENTS = new String[] {"arg1"};

  private CommandsFactory commandsFactory;
  private PostRepository repository;
  private Clock clock;
  private View view;

  @Before public void setUp() {
    clock = new Clock();
    repository = new PostRepository();
    view = new View(new ConsoleWrapper());
    commandsFactory = new CommandsFactory(clock, repository, view);
  }

  @Test public void make_post_command_with_arguments_for_a_given_post_input() {
    Input postInput = givenPostInputWith(ANY_ARGUMENTS);

    Command command = commandsFactory.make(postInput);

    Command expectedCommand = new PostCommand(clock, repository, ANY_ARGUMENTS);
    assertThat(command, is(expectedCommand));
  }

  @Test public void make_read_command_with_arguments_for_a_given_read_input() {
    Input readInput = givenReadInputWith(ANY_ARGUMENTS);

    Command command = commandsFactory.make(readInput);

    Command expectedCommand = new ReadCommand(view, repository, ANY_ARGUMENTS);
    assertThat(command, is(expectedCommand));
  }

  private Input givenPostInputWith(String[] arguments) {
    return new Input(POST, arguments);
  }

  private Input givenReadInputWith(String[] arguments) {
    return new Input(READ, arguments);
  }
}
