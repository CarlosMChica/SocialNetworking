package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.Post;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Collections.emptyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class ViewShould {

  private static final String PROMPT = "> ";
  private static final List<Post> ANY_POSTS = emptyList();
  private static final List<String> ANY_FORMATTED_POSTS = emptyList();

  @Mock ConsoleWrapper console;
  @Mock PostFormatter formatter;

  private View view;

  @Before public void setUp() {
    view = new View(console, formatter);
  }

  @Test public void print_prompt_and_read_line_when_get_user_input() {
    view.getUserInput();

    verify(console).print(PROMPT);
    verify(console).readLine();
  }

  @Test public void print_formatted_timeline_posts() {
    given(formatter.formatTimeline(ANY_POSTS)).willReturn(ANY_FORMATTED_POSTS);

    view.printTimeline(ANY_POSTS);

    verify(console).printLines(ANY_FORMATTED_POSTS);
  }

  @Test public void print_formatted_wall_posts() {
    given(formatter.formatWall(ANY_POSTS)).willReturn(ANY_FORMATTED_POSTS);

    view.printWall(ANY_POSTS);

    verify(console).printLines(ANY_FORMATTED_POSTS);
  }

}