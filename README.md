# boardgame
This is my java project for the board game sudoku
There are two classes in this project
1. Class Sudoku: This class is the main idea of a sample sudoku game
  a. Constructor: load the file txt and then configure the board to create a new game
  b. Method get: get the value at a specific cell with given row and column
  c. Method set: set the value at a specific cell(row, col)
  d. Methods isInRow, isInCol, isInBox: these are the helper methods for the method isValid
  e. Method isValid: check whether the value can is legal to be in the specific place (row, col)
  f. Method solve: solve the sudoku board
  g. Method load: load the board from the txt file
  h. Method configureBoard: rearrange the column and row to create a new board
  i. Method showHint: give a value at a random place in the board
 2. Class SodokuBoard: This class is the GUI of the game
  a. Method initialize: initilize the the GUI
  b. Method next: this helps to run the time
  c. Method dispose: stop the time
  d. Method drawGrid: draw the grid 9 x 9
  e. Method printGame: print the number to the grid of the GUI
  f. Method createMenuBar: create the menu bar
  g. Method addToMenu: this is the helper methods to add the JMenuItem into the Menu
  h. Constructor
  i. Main: run the whole program
  j. Methods keyTyped, keyPressed, keyReleased, actionPerformed: these are the override methods when implementing ActionListener and KeyListener, but I implement the code for only keyTyped and actionPerformed to support the behaviours that I want for the game => when clicking the on a cell, I can type the number I want at that place. 
