import java.util.*;

public class Game {
  private char[][] gameArea = {{' ', '|', ' ', '|', ' '},
          {'-', '+','-','+', '-'},
          {' ', '|', ' ', '|', ' '},
          {'-', '+','-','+', '-'},
          {' ', '|', ' ', '|', ' '}};
  private char userChar;
  private char cpuChar;
  private int allSteps = 9;
  final private String userName = "user";
  final private String cpuName = "cpu";
  final private char xSymbol = 'X';
  final private char oSymbol = '0';
  final private ArrayList<Integer> playersPositions = new ArrayList<Integer>();
  final private ArrayList<Integer> cpuPositions = new ArrayList<Integer>();

  private void printGameArea () {
    System.out.println("\n\n------------------------------------------\n");
    for(char[] row: gameArea) {
      for (char cell: row) {
        System.out.print(cell);
      }
      System.out.println();
    }
  }

  private void setShot(int cell, String user) {
    char symbol = ' ';
    String errorBusyCell = "Error. This cell is busy";
    if (user.equals(userName)) {
      symbol = userChar;
      playersPositions.add(cell);
    } else if (user.equals(cpuName)) {
      symbol = cpuChar;
      cpuPositions.add(cell);
    }

    switch (cell) {
      case 1:
        gameArea[0][0] = symbol;
        break;
      case 2:
        gameArea[0][2] = symbol;
        break;
      case 3:
        gameArea[0][4] = symbol;
        break;
      case 4:
        gameArea[2][0] = symbol;
        break;
      case 5:
        gameArea[2][2] = symbol;
        break;
      case 6:
        gameArea[2][4] = symbol;
        break;
      case 7:
        gameArea[4][0] = symbol;
        break;
      case 8:
        gameArea[4][2] = symbol;
        break;
      case 9:
        gameArea[4][4] = symbol;
        break;
      default:
        System.out.println("You input not valid value");
        break;
    }

    allSteps -= 1;
  }

  private void inputUserShot() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Input cell: ");
    int cell = scanner.nextInt();
    while (playersPositions.contains(cell) || cpuPositions.contains(cell)) {
      System.out.println("Error. Position is taken!");
      cell = scanner.nextInt();
    }
    setShot(cell, userName);
  }

  private void cpuShot() {
    Random random = new Random();
    int cell = random.nextInt(9) + 1;
    while (playersPositions.contains(cell) || cpuPositions.contains(cell)) {
      cell = random.nextInt(9) + 1;
    }
    setShot(cell, cpuName);
  }

  private boolean getUserChar() {
    while (true) {
      Scanner scanner = new Scanner(System.in);
      System.out.println(String.format("Choose symbol (1) - '%s' or (2) - '%s'", xSymbol, oSymbol));
      int charVariant = 0;
      try {
        charVariant = scanner.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("Not a valid choice");
        continue;
      }

      switch (charVariant) {
        case 1:
          userChar = xSymbol;
          cpuChar = oSymbol;
          return true;
        case 2:
          userChar = oSymbol;
          cpuChar = xSymbol;
          return true;
        default:
          System.out.println("Not a valid choice");
      }
    }
  }

  private String checkWinner() {
    List<List> winningCombinations = new ArrayList<List>();
    winningCombinations.add(Arrays.asList(1, 2, 3)); // Top row
    winningCombinations.add(Arrays.asList(4, 5, 6)); // Middle row
    winningCombinations.add(Arrays.asList(7, 8, 9)); // Bottom row
    winningCombinations.add(Arrays.asList(1, 4, 7)); // Left column
    winningCombinations.add(Arrays.asList(2, 5, 8)); // Middle column
    winningCombinations.add(Arrays.asList(3, 6, 9)); // Right column
    winningCombinations.add(Arrays.asList(1, 5, 9)); // Top left to bottom
    winningCombinations.add(Arrays.asList(3, 5, 7)); // Top right to bottom

    for (List listCombinations: winningCombinations) {
      if (playersPositions.containsAll(listCombinations)) {
        return "Congratulations! You is winner!";
      } else if (cpuPositions.containsAll(listCombinations)) {
        return "Oh no! Cpu is winner :(";
      } else if (allSteps == 0) {
        return "No winner";
      }
    }

    return "";
  }

  private void runGame() {
    Random random = new Random();
    String whomStep = "";

    boolean firstShot = random.nextBoolean();
    if (firstShot) {
      whomStep = userName;
    } else {
      whomStep = cpuName;
    }

    while (true) {
      if (whomStep == userName) {
        inputUserShot();
        whomStep = cpuName;
      } else {
        cpuShot();
        whomStep = userName;
      }
      printGameArea();
      String resultWinner = checkWinner();
      if (resultWinner.length() > 0) {
        System.out.println(resultWinner);
        System.out.println("Game is end");
        break;
      }
    }
  }

  public void startGame() {
    if (!getUserChar()) {
      System.exit(0);
    }
    printGameArea();
    runGame();
  }
}
