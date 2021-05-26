import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;


public class SudokuBoard extends JFrame implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private int[][] temp = new int [9][9];
	private int canTry = 3;
	private int I, J;
	private Sudoku sudoku;
	private JPanel canvas;
	private JMenuBar menuBar;
	private JButton[][] grid;
	private Container con;
	private JPanel panel;
	private JLabel time;
	private JLabel numTry;
	private Timer timer;
	
	private Container initalize() {
		con = this.getContentPane();
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		time = new JLabel("00:00:00:00");
		numTry = new JLabel("You can try 3 times");
		panel.add(time);
		panel.add(numTry);
		con.add(panel, "North");
		createMenuBar();
		con.add(drawGrid());
		printGame();
		timer = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time.setText(next(time));
			}
		});
		timer.start();
		return con;
	}
	
	public String next(JLabel label) {
		String str[] = label.getText().split(":");
		int tt = Integer.parseInt(str[3]);
		int second = Integer.parseInt(str[2]);
		int minute = Integer.parseInt(str[1]);
		int hour = Integer.parseInt(str[0]);
		String total = "";
		int sum = tt + second * 100 + minute * 60 * 100 + hour * 60 * 60 * 100 + 1;
		if (sum % 100 > 9)
			total = ":" + sum % 100 + total;
		else
			total = ":0" + sum % 100 + total;
		sum /= 100;
		
		if (sum % 60 > 9)
			total = ":" + sum % 60 + total;
		else
			total = ":0" + sum % 60 + total;
		sum /= 60;
		
		if (sum % 60 > 9)
			total = ":" + sum % 60 + total;
		else
			total = ":0" + sum % 60 + total;
		sum /= 60;
		if (sum > 9)
			total = sum + total;
		else
			total = "0" + sum + total;
		return total;
	}
	
	public void dispose() {
		timer.stop();
		super.dispose();
	}
	
	private JPanel drawGrid() {
		canvas = new JPanel();
		grid = new JButton[9][9];
		canvas.setLayout(new GridLayout(9, 9));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				grid[i][j] = new JButton();
				grid[i][j].addActionListener(this);
				grid[i][j].addKeyListener(this);
				grid[i][j].setActionCommand(i + " " + j);
				grid[i][j].setBackground(Color.white);
				grid[i][j].setFont(new Font("Verdana", Font.BOLD, 30));
				grid[i][j].setForeground(Color.black);
				canvas.add(grid[i][j]);
			}
		}
		for (int i = 0; i < 9; i += 3) {
			for (int j = 0; j < 9; j += 3) {
				grid[i][j].setBorder(BorderFactory.createMatteBorder(3,3,1,1, Color.black));
				grid[i][j + 2].setBorder(BorderFactory.createMatteBorder(3,1,1,3, Color.black));
				grid[i + 2][j + 2].setBorder(BorderFactory.createMatteBorder(1,1,3,3, Color.black));
				grid[i + 2][j].setBorder(BorderFactory.createMatteBorder(1,3,3,1, Color.black));
				grid[i][j + 1].setBorder(BorderFactory.createMatteBorder(3,1,1,1, Color.black));
				grid[i + 1][j + 2].setBorder(BorderFactory.createMatteBorder(1,1,1,3, Color.black));
				grid[i + 2][j + 1].setBorder(BorderFactory.createMatteBorder(1,1,3,1, Color.black));
				grid[i + 1][j].setBorder(BorderFactory.createMatteBorder(1,3,1,1, Color.black));
				grid[i + 1][j + 1].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.black));
			}
		}
		return canvas;
	}
	
	private void printGame() {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				temp[r][c] = sudoku.get(r, c);
				if(sudoku.get(r, c) != 0) {
					grid[r][c].setText(sudoku.get(r, c) + "");
				}
			}
		}
	}
    
	private void createMenuBar() {
		menuBar = new JMenuBar();
		JMenu newGame = new JMenu("New Game");
		JMenu hint = new JMenu("Hint");
		menuBar.add(newGame);
		menuBar.add(hint);
		
		hint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sudoku.showHint();
				repaint();
			}
		});
		
		addToMenu(newGame, "Easy", new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                sudoku = new Sudoku("easy.txt");
                repaint();
            }
		});
		
		addToMenu(newGame, "Medium", new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				sudoku = new Sudoku("easy.txt");
                repaint();
            }
		});
		
		addToMenu(newGame, "Hard", new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				sudoku = new Sudoku("easy.txt");
                repaint();
            }
		});
		
		this.setJMenuBar(menuBar);
	}
	
	private void addToMenu(JMenu menu, String title, ActionListener listener) {
	    JMenuItem menuItem = new JMenuItem(title);
	    menu.add(menuItem);
	    menuItem.addActionListener(listener);
	}
	
	
	public SudokuBoard() {
		sudoku = new Sudoku("easy.txt");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku (@huquach)");
		this.setSize(700, 700);
		con = initalize();
		

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        
        repaint();
	} 
	
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SudokuBoard frame = new SudokuBoard();
				frame.setVisible(true);
			}
		});
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int v = e.getKeyCode();
		if ((v >= 49 && v <= 57) || (v >= 97 && v <= 105)) {
			if (v >= 49 && v <= 57)
				v -= 48;
			if (v >= 97 && v <= 105)
				v -= 96;
			if (temp[I][J] == 0) {
				grid[I][J].setText(v +"");
				if (sudoku.isValid(I, J, v)) {
					temp[I][J] = v;
					grid[I][J].setForeground(Color.BLUE);
					boolean check = true;
					for (int i = 0; i <  9; i++) {
						for (int j = 0; j < 9; j++) {
							if (temp[i][j] != 0)
								check = false;
						}
					}
					if (check) {
						sudoku.solve();
						JOptionPane.showMessageDialog(null, "You have filled out all the cell. \nNow it's time to check your work!");
					}
				} else {
					canTry--;
					temp[I][J] = -1;
					grid[I][J].setForeground(Color.RED);
					numTry.setText("Có thể sai " + canTry + " lần");
					if (canTry == -1) {
						JOptionPane.showMessageDialog(null, "You were wrong 3 times. \nPlay again!");
						numTry.setText("You lose");
						new SudokuBoard();
						this.dispose();
					}					
				}
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String s = e.getActionCommand();
		int k = s.indexOf(32);
		int i = Integer.parseInt(s.substring(0, k));
		int j = Integer.parseInt(s.substring(k + 1, s.length()));
		I = i;
		J = j;
		if (temp[I][J] > 0) {
			for (i = 0; i < 9; i++)
				for (j = 0; j < 9; j++)
					if (temp[i][j] == temp[I][J])
						grid[i][j].setBackground(Color.gray);
		}
		
	}
}
