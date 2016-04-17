package carlosdelachica.delivery_mechanism;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ConsoleWrapperShould {

  private static final String NEW_LINE = "\n";
  private static final String ANY_LINE = "line";
  private static final String ANY_READ_LINE = "userInput";
  private static final List<String> ANY_LINES = asList(ANY_LINE, ANY_LINE);
  private static final String ANY_LINES_PRINTED = ANY_LINE + NEW_LINE + ANY_LINE + NEW_LINE;

  private ByteArrayOutputStream outputStream;
  private ConsoleWrapper console;

  @Before public void setUp() {
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    System.setIn(new ByteArrayInputStream(ANY_READ_LINE.getBytes()));
    console = new ConsoleWrapper();
  }

  @Test public void print_to_command_line() {
    console.print(ANY_LINE);

    assertThat(outputStream.toString(), is(ANY_LINE));
  }

  @Test public void print_lines_to_command_line() {
    console.printLines(ANY_LINES);

    assertThat(outputStream.toString(), is(ANY_LINES_PRINTED));
  }

  @Test public void read_line_from_command_line() {
    String readLine = console.readLine();

    assertThat(readLine, is(ANY_READ_LINE));
  }
}