# minesweeper-GUI

<p>Minesweeper game (with timer and solver) created using Java Swing.</p>

<p>
Prerequisite: <br>
java version "1.8.0_172" <br>
Java(TM) SE Runtime Environment (build 1.8.0_172-b11) <br>
</p>

<p>Run Minesweeper_v6.jar to play the game.</p>

<h2>Descriptions:</h2>
  <ul>
    <li>"src/minesweeper/" folder contains the java code
    <li>"bin/" folder contains the icons for the game
  </ul>
<h3>src/minesweeper/</h3>
  <ul>
    <li>Board.java --> Object to contain instances of the Box.java class. Represents the minesweeper board/grid.
    <li>Box.java --> Object to represent individual boxes in the Minesweeper game. Object is to be contained in a Board.java instance. Is clickable, can contain bomb, and can be flagged.
    <li>GameInterface.java --> Contains the code for graphical user interface (GUI).
    <li>Solver.java --> Code for solving the game. A delay is included in the code to make the solving process visible.
    <li>SpringUtilities.java --> Code to arrange the layout of the boxes. Taken from https://docs.oracle.com/javase/tutorial/uiswing/layout/spring.html
  </ul>
