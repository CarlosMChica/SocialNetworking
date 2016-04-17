package carlosdelachica;

import carlosdelachica.command.Command;
import carlosdelachica.command.CommandsFactory;
import carlosdelachica.delivery_mechanism.ConsoleWrapper;
import carlosdelachica.delivery_mechanism.InputParser;
import carlosdelachica.delivery_mechanism.PostFormatter;
import carlosdelachica.delivery_mechanism.TimeAgoFormatter;
import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.Input;
import carlosdelachica.model.PostRepository;
import carlosdelachica.model.UserRepository;

public class Application {

  public static void main(String[] args) {
    InputParser parser = new InputParser();
    Clock clock = new Clock();
    View view = new View(new ConsoleWrapper(), new PostFormatter(new TimeAgoFormatter(clock)));
    CommandsFactory commandsFactory =
        new CommandsFactory(clock, view, new PostRepository(), new UserRepository());

    new SocialNetworkingApp(parser, commandsFactory, view).run();
  }

  public static class SocialNetworkingApp {

    private final InputParser parser;
    private final CommandsFactory commandsFactory;
    private final View view;

    public SocialNetworkingApp(InputParser parser, CommandsFactory factory, View view) {
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
