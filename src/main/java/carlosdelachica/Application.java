package carlosdelachica;

import carlosdelachica.command.Command;
import carlosdelachica.command.CommandsFactory;
import carlosdelachica.console.ConsoleWrapper;
import carlosdelachica.console.Input;
import carlosdelachica.console.InputParser;
import carlosdelachica.console.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.PostRepository;

public class Application {

  public static void main(String[] args) {
    InputParser parser = new InputParser();
    CommandsFactory commandsFactory = new CommandsFactory(new Clock(), new PostRepository());
    View view = new View(new ConsoleWrapper());

    new SocialNetworkingApp(parser, commandsFactory, view).run();
  }

  public static class SocialNetworkingApp {

    private final InputParser parser;
    private final CommandsFactory commandsFactory;
    private final View view;

    SocialNetworkingApp(InputParser parser, CommandsFactory factory, View view) {
      this.parser = parser;
      this.commandsFactory = factory;
      this.view = view;
    }

    public void run() {
      while (true) {
        execute();
      }
    }

    void execute() {
      executeCommandFor(getNextInput());
    }

    private Input getNextInput() {
      return parser.parse(view.getUserInput());
    }

    private void executeCommandFor(Input input) {
      Command command = commandsFactory.make(input);
      command.execute();
    }
  }
}
