package carlosdelachica;

import carlosdelachica.command.Command;
import carlosdelachica.command.CommandsFactory;
import carlosdelachica.console.Input;
import carlosdelachica.console.InputParser;
import carlosdelachica.console.View;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static carlosdelachica.Application.SocialNetworkingApp;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class SocialNetworkingAppShould {

  private static final Input.Type ANY_INPUT_TYPE = null;
  private static final String[] ANY_ARGUMENTS = null;
  private static final String ANY_USER_INPUT = "userInput";
  private static final Input ANY_INPUT = new Input(ANY_INPUT_TYPE, ANY_ARGUMENTS);

  @Mock InputParser inputParser;
  @Mock CommandsFactory commandsFactory;
  @Mock View view;
  @Mock Command command;

  private SocialNetworkingApp app;

  @Before public void setUp() {
    app = new SocialNetworkingApp(inputParser, commandsFactory, view);
  }

  @Test public void execute_command_for_user_input() {
    String userInput = givenUserInput();
    Input input = givenInputFor(userInput);
    Command command = givenCommandFor(input);

    app.execute();

    verify(command).execute();
  }

  private Command givenCommandFor(Input input) {
    given(commandsFactory.make(input)).willReturn(command);
    return command;
  }

  private String givenUserInput() {
    given(view.getUserInput()).willReturn(ANY_USER_INPUT);
    return ANY_USER_INPUT;
  }

  private Input givenInputFor(String userInput) {
    given(inputParser.parse(userInput)).willReturn(ANY_INPUT);
    return ANY_INPUT;
  }
}
