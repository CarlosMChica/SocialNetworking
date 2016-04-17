package carlosdelachica.delivery_mechanism;

import java.util.Scanner;

public class ConsoleWrapper {

  private Scanner scanner = new Scanner(System.in);

  String readLine() {
    return scanner.nextLine();
  }

  public void print(String line) {
    System.out.print(line);
  }
}
