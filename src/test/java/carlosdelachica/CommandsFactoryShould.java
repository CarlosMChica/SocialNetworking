package carlosdelachica;

import org.junit.Before;
import org.junit.Test;

import static carlosdelachica.Input.POST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CommandsFactoryShould {

  private static final String[] ANY_ARGUMENTS = new String[] {"arg1"};

  private CommandsFactory commandsFactory;
  private PostRepository repository;

  @Before public void setUp() {
    repository = new PostRepository();
    commandsFactory = new CommandsFactory(repository);
  }

  @Test public void make_post_command_with_arguments_for_a_given_post_input() {
    Input postInput = givenPostInputWith(ANY_ARGUMENTS);

    Command command = commandsFactory.make(postInput);

    Command expectedCommand = new PostCommand(repository, ANY_ARGUMENTS);
    assertThat(command, is(expectedCommand));
  }

  private Input givenPostInputWith(String[] arguments) {
    return POST.withArguments(arguments);
  }
}
