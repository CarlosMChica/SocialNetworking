package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.Input;

import static carlosdelachica.model.Input.Type.FOLLOW;
import static carlosdelachica.model.Input.Type.POST;
import static carlosdelachica.model.Input.Type.READ;
import static carlosdelachica.model.Input.Type.WALL;

public class InputParser {

  private static final String POST_ACTION = " -> ";
  private static final String FOLLOW_ACTION = " follows ";
  private static final String WALL_ACTION = " wall ";

  public Input parse(String input) {
    if (isPostType(input)) {
      return postInput(input);
    }
    if (isFollowType(input)) {
      return followInput(input);
    }
    if (isWallType(input)) {
      return wallInput(input);
    }
    return readInput(input);
  }

  private Input readInput(String input) {
    return new Input(READ, input);
  }

  private Input postInput(String input) {
    return new Input(POST, postArguments(input));
  }

  private Input followInput(String input) {
    return new Input(FOLLOW, FollowArguments(input));
  }

  private Input wallInput(String input) {
    return new Input(WALL, wallArguments(input));
  }

  private String[] postArguments(String input) {
    return argumentsFor(POST_ACTION, input);
  }

  private String[] FollowArguments(String input) {
    return argumentsFor(FOLLOW_ACTION, input);
  }

  private String[] wallArguments(String input) {
    return argumentsFor(WALL_ACTION, input);
  }

  private boolean isWallType(String input) {
    return isAction(WALL_ACTION, input);
  }

  private boolean isFollowType(String input) {
    return isAction(FOLLOW_ACTION, input);
  }

  private boolean isPostType(String input) {
    return isAction(POST_ACTION, input);
  }

  private boolean isAction(String action, String input) {
    return argumentsFor(action, input).length == 2;
  }

  private String[] argumentsFor(String action, String input) {
    return input.split(action);
  }
}
