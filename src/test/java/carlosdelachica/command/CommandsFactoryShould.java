package carlosdelachica.command;

import carlosdelachica.Clock;
import carlosdelachica.console.Input;
import carlosdelachica.model.PostRepository;
import org.junit.Before;
import org.junit.Test;

import static carlosdelachica.console.Input.Type.POST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CommandsFactoryShould {

  private static final String[] ANY_ARGUMENTS = new String[] {"arg1"};

  private CommandsFactory commandsFactory;
  private PostRepository repository;
  private Clock clock;

  @Before public void setUp() {
    clock = new Clock();
    repository = new PostRepository();
    commandsFactory = new CommandsFactory(clock, repository);
  }

  @Test public void make_post_command_with_arguments_for_a_given_post_input() {
    Input postInput = givenPostInputWith(ANY_ARGUMENTS);

    Command command = commandsFactory.make(postInput);

    Command expectedCommand = new PostCommand(clock, repository, ANY_ARGUMENTS);
    assertThat(command, is(expectedCommand));
  }

  private Input givenPostInputWith(String[] arguments) {
    return new Input(POST, arguments);
  }
}
