package carlosdelachica.delivery_mechanism;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class ViewShould {

  private static final String PROMPT = "> ";

  @Mock ConsoleWrapper console;

  private View view;

  @Before public void setUp() {
    view = new View(console);
  }

  @Test public void print_prompt_and_read_line_when_get_user_input() {
    view.getUserInput();

    verify(console).print(PROMPT);
    verify(console).readLine();
  }
}