package carlosdelachica;

import carlosdelachica.command.Command;
import carlosdelachica.command.CommandsFactory;
import carlosdelachica.delivery_mechanism.ConsoleWrapper;
import carlosdelachica.delivery_mechanism.PostFormatter;
import carlosdelachica.delivery_mechanism.TimeAgoFormatter;
import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.input.Input;
import carlosdelachica.model.input.InputParser;
import carlosdelachica.model.post.PostRepository;
import carlosdelachica.model.user.UserRepository;

public class Application {

  public static void main(String[] args) {
    SocialNetworkingApp app = SocialNetworkAppFactory.make();
    run(app);
  }

  private static void run(SocialNetworkingApp app) {
    while (true) {
      app.run();
    }
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
      executeUserInput(view.getUserInput());
    }

    public void executeUserInput(String userInput) {
      executeCommandFor(parse(userInput));
    }

    private Input parse(String userInput) {
      return parser.parse(userInput);
    }

    private void executeCommandFor(Input input) {
      Command command = commandsFactory.make(input);
      command.execute();
    }
  }

  private static class SocialNetworkAppFactory {

    private static SocialNetworkingApp make() {
      Clock clock = new Clock();
      InputParser parser = new InputParser();
      TimeAgoFormatter timeAgoFormatter = new TimeAgoFormatter(clock);
      ConsoleWrapper console = new ConsoleWrapper();
      PostRepository postRepo = new PostRepository();
      UserRepository userRepo = new UserRepository();
      PostFormatter postFormatter = new PostFormatter(timeAgoFormatter);
      View view = new View(console, postFormatter);
      CommandsFactory commandsFactory = new CommandsFactory(clock, view, postRepo, userRepo);
      return new SocialNetworkingApp(parser, commandsFactory, view);
    }
  }
}
