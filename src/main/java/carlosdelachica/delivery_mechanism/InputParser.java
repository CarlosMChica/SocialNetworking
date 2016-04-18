package carlosdelachica.delivery_mechanism;

import carlosdelachica.model.Input;

import static carlosdelachica.model.Input.Type.FOLLOW;
import static carlosdelachica.model.Input.Type.POST;
import static carlosdelachica.model.Input.Type.READ;
import static carlosdelachica.model.Input.Type.WALL;

public class InputParser {

  private static final String POST_ACTION = " -> ";
  private static final String FOLLOW_ACTION = " follows ";
  private static final String WALL_ACTION = " wall";
  private static final String SPACE = " ";

  public Input parse(String input) {
    if (isPostType(input)) {
      return postInput(input);
    }
    if (isFollowType(input)) {
      return followInput(input);
    }
    if (isReadType(input)) {
      return readInput(input);
    }
    if (isWallType(input)) {
      return wallInput(input);
    }
    return null;
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

  private boolean isReadType(String input) {
    return !input.contains(SPACE);
  }

  private boolean isWallType(String input) {
    return numberOfArguments(WALL_ACTION, input) == 1;
  }

  private boolean isFollowType(String input) {
    return numberOfArguments(FOLLOW_ACTION, input) == 2;
  }

  private boolean isPostType(String input) {
    return numberOfArguments(POST_ACTION, input) == 2;
  }

  private int numberOfArguments(String action, String input) {
    return argumentsFor(action, input).length;
  }

  private String[] argumentsFor(String action, String input) {
    return input.split(action);
  }
}
