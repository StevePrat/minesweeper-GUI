package minesweeper;

import java.util.*;
import java.awt.Point;
import java.time.*;

public class Board {
	
	private GameInterface intrface;
	private Map<Point,Box> board;
	private int hSize;
	private int vSize;
	private Set<Point> bombs;
	private Set<Point> flags;
	private boolean gameOver;
	private String statusMsg;
	private LocalTime startTime;
	private LocalTime currentTime;
	private Duration duration;
	private TimerTask updater;
	private Timer timer;
	private Solver solver;
	private boolean isSolverOn;
	
	public Board(GameInterface gameInterface, int hSize, int vSize) {
		intrface = gameInterface;
		board = new HashMap<Point,Box>();
		bombs = new HashSet<Point>();
		flags = new HashSet<Point>();
		this.hSize = hSize;
		this.vSize = vSize;
		for (int x=0; x<hSize; x++) {
			for (int y=0; y<vSize; y++) {
				board.put(new Point(x,y), new Box(this,x,y));
			}
		}
		gameOver = false;
		updater = new StatusUpdater();
		isSolverOn = false;
		startTimer();
	}
	
	public void startTimer() {
		startTime = LocalTime.now();
		timer = new Timer();
		timer.scheduleAtFixedRate(updater, 0, 500);
	}
	
	public void stopTimer() {
		timer.cancel();
	}
	
	public void solverOn() {
		isSolverOn = true;
	}
	
	public void solverOff() {
		isSolverOn = false;
	}
	
	public boolean getSolverStatus() {
		return isSolverOn;
	}
	
	public void solve() {
		solver = new Solver(this);
		solver.start();
	}
	
	public Collection<Box> getAllBoxes() {
		return board.values();
	}
	
	public Box getBox(int x, int y) {
		return board.get(new Point(x,y));
	}
	
	private void setBox(int x, int y, Box box) {
		board.put(new Point(x,y), box);
	}
	
	public int getWidth() {
		return hSize;
	}
	
	public int getHeight() {
		return vSize;
	}
	
	public void addBomb(int x, int y) {
		bombs.add(new Point(x,y));
	}
	
	public Set<Point> getBombs() {
		return bombs;
	}
	
	public void putBombs(int n) {
		/**
		 * Put n bombs randomly. 
		 * Does not remove previously placed bombs. 
		 * Updates the nearbyBombs on neighboring boxes.
		 * Updates status message
		 */
		Random rnd = new Random();
		int x;
		int y;
		Box box;
		for (int i=0; i<n; i++) {
			x = rnd.nextInt(hSize);
			y = rnd.nextInt(vSize);
			box = getBox(x,y);
			while (box.hasBomb()) {
				x = rnd.nextInt(hSize);
				y = rnd.nextInt(vSize);
				box = getBox(x,y);
			}
			box.putBomb();
		}
		updateStatus();
	}
	
	public void countNearbyBombs() {
		for (Box box:board.values()) {
			box.countNearbyBombs();
		}
	}
	
	public void addFlag(int x, int y) {
		flags.add(new Point(x,y));
		updateStatus();
	}
	
	public void removeFlag(int x, int y) {
		flags.remove(new Point(x,y));
		updateStatus();
	}
	
	public int countDeployedFlags() {
		return flags.size();
	}
	
	public int countRemainingFlags() {
		return bombs.size() - flags.size();
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public void gameOver() {
		gameOver = true;
		Box box;
		for (Point p:bombs) {
			box = getBox(p.x,p.y);
			box.displayBombIcon();
		}
		timer.cancel();
		
		if (getSolverStatus()) {
			String timeStr = durationToString(duration);
			setStatusMsg("Elapsed Time: " + timeStr + " | Solver accidentally hit a bomb");
			intrface.updateStatus();
			System.out.println("Solver accidentally hit a bomb");
		} else {
			intrface.gameOver();
		}
	}
	
	public boolean isSuccessful() {
		/* Check whether all boxes without bomb have been clicked */
		for (Box box:board.values()) {
			if (!box.isClicked() && !bombs.contains(box.getPoint())) {
				return false; // code stops here if not successful yet
			}
		}
		
		/* Code will only reach here if game is successful */
		return true;
	}
	
	public void checkForSuccess() {
		/**
		 * Check whether the game has been successfully completed, perform furter actions if true
		 */
		if (isSuccessful()) {
			gameSuccess();
		}
	}
	
	public void gameSuccess() {
		Box box;
		for (Point p:bombs) {
			box = getBox(p.x,p.y);
			box.displayFlagIcon();
		}
		timer.cancel();
		
		if (getSolverStatus()) {
			String timeStr = durationToString(duration);
			setStatusMsg("Elapsed Time: " + timeStr + " | Solver successfully finished the game");
			intrface.updateStatus();
			System.out.println("Solver finished the game");
		} else {
			intrface.gameSuccess();
		}
	}
	
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	
	public String getStatusMsg() {
		return statusMsg;
	}
	
	public void updateStatus() {
		updater.run();
	}
	
	public void printBoard() {
		/**
		 * For console debugging purposes
		 */
		for (int i=0; i<getHeight(); i++) {
			for (int j=0; j<getWidth(); j++) {
				Box box = getBox(i,j);
				String valStr = Integer.toString(box.displayValue());
				while (valStr.length() < 2) {
					valStr = "0" + valStr;
				}
				System.out.print(valStr + " ");
			}
			System.out.println();
		}
	}
	
	private static String durationToString(Duration d) {
		long mins,secs;
		mins = d.getSeconds() / 60;
		secs = d.getSeconds() % 60;
		StringBuilder sb = new StringBuilder();
		if (mins < 10) {
			sb.append("0");
		}
		sb.append(mins);
		sb.append(":");
		if (secs < 10) {
			sb.append("0");
		}
		sb.append(secs);
		return sb.toString();
	}
	
	class StatusUpdater extends TimerTask implements Runnable {
		
		@Override
		public void run() {
			currentTime = LocalTime.now();
			duration = Duration.between(startTime, currentTime);
			String timeStr = durationToString(duration);
			setStatusMsg("Elapsed Time: " + timeStr + " | Remaining Flags: " + Integer.toString(countRemainingFlags()));
			intrface.updateStatus();		
		}
		
	}
	
}
