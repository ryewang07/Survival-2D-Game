import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Panel extends JPanel implements ActionListener {
	JButton play, instructions, stats, exit;
	JComboBox<String> maps;
	public final Color LIGHT_BLUE = new Color(51, 153, 255);
	public final Color LIGHT_RED = new Color(255, 102, 102);
	public JLabel image, location;
	public JFrame currentFrame = new JFrame("The Lone Survivor: Main Menu");
	public int survivalTimeRecent, survivalTimeBest, scoreRecent, scoreBest;
	public String in, input;
	public Game game;
	
	//constructor that makes the menu
	public Panel() {
		loadMenu();
	}//end panel
	
	//build and load the menu
	public void loadMenu() {
		//set frame size once and for all
		currentFrame.setSize(1100, 700);
		currentFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			
		//clear everything that was here earlier
		this.removeAll();
		revalidate();
		currentFrame.repaint();
		currentFrame.setLocationRelativeTo(null);
			
		//Making the main menu panel
		setBounds(currentFrame.getBounds());
		setVisible(true);
		setLayout(null);
			
		//center align the title text
		JLabel title = new JLabel("<html><div style='text-align: center;'> The Lone Survivor </div></html>");
		
		//declare buttons to navigate the menu
		play = new JButton("Play");
		instructions = new JButton("Instructions");
		stats = new JButton("Stats");
		exit = new JButton("Exit");
		
		//increase title's text
		title.setFont (title.getFont().deriveFont (64.0f));
		
		//add everything to panel
		add(play);
		add(instructions);
		add(stats);
		add(exit);
		add(title);
			
		//set settings for frame and add panel to frame
		currentFrame.add(this);
		currentFrame.setResizable(false);
		currentFrame.setVisible(true);
		
		//add action listeners
		play.addActionListener(this);
		instructions.addActionListener(this);
		stats.addActionListener(this);
		exit.addActionListener(this);
		
		//set action commands
		play.setActionCommand("play");
		instructions.setActionCommand("instructions");
		stats.setActionCommand("stats");
		exit.setActionCommand("exit");
			
		//change locations of buttons
		title.setBounds(255, 0, 700, 200);
		play.setBounds(450, 225, 200, 50);
		instructions.setBounds(450, 325, 200, 50);
		stats.setBounds(450, 425, 200, 50);
		exit.setBounds(450, 525, 200, 50);
			
		//set button + background color(s)
		setBackground( Color.WHITE );
		play.setBackground(Color.GREEN);
		instructions.setBackground(LIGHT_BLUE);
		stats.setBackground(Color.YELLOW);
		exit.setBackground(LIGHT_RED);
	}//end loadMenu()
	
	public void instructions(JFrame frame) {
		frame.setTitle("The Lone Survivor: Instructions");
		
		//clear everything that was here earlier
		this.removeAll();
		revalidate();
		frame.repaint();
		
		//clean panels
		JPanel buttonBox = new JPanel();
		
		//set necessary settings of panel + panel for button
		setBounds(frame.getBounds());
		setVisible(true);
		setLayout(new GridLayout(3,3));
		buttonBox.setVisible(true);
		buttonBox.setLayout(null);
		
		//use vars to hold text to keep JLabels clean
		String insText = "<html>Your goal is to survive as long as possible using the weapons provided to you.</html>";
		String tipsText = "<html>You can acquire new weapons by eliminating more enemies and surviving for longer periods of time.</html>";
		
		//use HTML magic to center align text
		JLabel instructions = new JLabel("<html><div style='text-align: center;'>" + insText + "</div></html>");
		JLabel tips = new JLabel("<html><div style='text-align: center;'>" + tipsText + "</div></html>");
		JLabel binds = new JLabel(new ImageIcon("key template.png"));
		
		//declare jlabels to hold images
		JLabel insPic1 = new JLabel(new ImageIcon("instructions1.png"));
		JLabel insPic2 = new JLabel(new ImageIcon("instructions2.png"));
		JLabel tipsPic1 = new JLabel(new ImageIcon("tips1.png"));
		JLabel tipsPic2 = new JLabel(new ImageIcon("tips2.png"));
		JButton back = new JButton("Back");
		
		//add button to the panel and move to center
		buttonBox.add(back);
		back.setBounds(75, 60, 200, 100);
		
		//set button size
		back.setPreferredSize(new Dimension(20, 20));
		
		//increase text sizes
		instructions.setFont (instructions.getFont().deriveFont (30.0f));
		tips.setFont (tips.getFont().deriveFont (30.0f));
		
		//center align text
		instructions.setHorizontalAlignment(JLabel.CENTER);
		tips.setHorizontalAlignment(JLabel.CENTER);
		
		//add back button
		back.addActionListener(this);
		back.setActionCommand("toMenu");
		
		//add everything to panel
		add(insPic1);
		add(instructions);
		add(insPic2);
		add(buttonBox);
		add(binds);
		add(new JLabel(""));
		add(tipsPic1);
		add(tips);
		add(tipsPic2);
		
		//set background colors
		setBackground(Color.WHITE );
		buttonBox.setBackground(Color.WHITE);
		back.setBackground(Color.GREEN);
		
		//finalizing and displaying
		frame.add(this);
		frame.setResizable(false);
		frame.setVisible(true);
	}//end instructions
	
	
	public void stats(JFrame frame) {
		frame.setTitle("The Lone Survivor: Stats");
		
		//clear everything that was here earlier
		this.removeAll();
		revalidate();
		frame.repaint();
		
		//declare variables to hold the score and times
		//declare variables to hold the score and times
		JLabel recentTime = new JLabel("Recent Survival Time: " + survivalTimeRecent + " second(s)");
		JLabel recentScore = new JLabel("Recent Score: " + scoreRecent + " pts");
		JLabel bestTime = new JLabel("Best Survival Time: " + survivalTimeBest + " second(s)");
		JLabel bestScore = new JLabel("Best Score: " + scoreBest + " pts");
		JButton back = new JButton("Back");
		
		//change text sizes
		recentTime.setFont (recentTime.getFont().deriveFont (25.0f));
		recentScore.setFont (recentScore.getFont().deriveFont (25.0f));
		bestTime.setFont (bestTime.getFont().deriveFont (25.0f));
		bestScore.setFont (bestScore.getFont().deriveFont (25.0f));
		
		//set center alignments to text
		recentTime.setHorizontalAlignment(JLabel.CENTER);
		recentScore.setHorizontalAlignment(JLabel.CENTER);
		bestTime.setHorizontalAlignment(JLabel.CENTER);
		bestScore.setHorizontalAlignment(JLabel.CENTER);
	
		//add action listener and command
		back.addActionListener(this);
		back.setActionCommand("toMenu");
		
		//add everything to panel
		add(recentTime);
		add(recentScore);
		add(bestTime);
		add(bestScore);
		add(back);
		
		//set locations of the components
		recentTime.setBounds(350, 100, 410, 50);
		recentScore.setBounds(350, 175, 400, 50);
		bestTime.setBounds(350, 350, 400, 50);
		bestScore.setBounds(350, 425, 400, 50);
		back.setBounds(450, 525, 200, 50);
		
		//set background colors
		setBackground( Color.WHITE );
		back.setBackground(Color.GREEN);
		
		//finalizing and displaying
		frame.add(this);
		frame.setResizable(false);
		frame.setVisible(true);
	}//end stats
	
	public void playPanel(JFrame frame) {
		frame.setTitle("The Lone Survivor: Pre-Game");
		
		//clear everything that was here earlier
		this.removeAll();
		revalidate();
		frame.repaint();
		
		//declare the unique components
		location = new JLabel("Current Location: Dungeon");
		location.setBounds(250, -100, 600, 325);
		location.setFont (location.getFont().deriveFont (20.0f));
		location.setHorizontalAlignment(JLabel.CENTER);
		add(location);
		
		//map preview
		image = new JLabel(new ImageIcon("Dungeon.jpg"));
		image.setBounds(250, 85, 600, 325);
		add(image);
		
		//select options from JComboBox
		maps = new JComboBox<String>();
		maps.setBounds(468, 425, 150, 30);
		maps.addItem("Easy");
		maps.addItem("Normal");
		maps.addItem("Hard");
		maps.addActionListener(this);
		add(maps);
		
		//button to return to main menu
		JButton back = new JButton("Back");
		back.setBounds(305, 500, 200, 50);
		back.setBackground(LIGHT_RED);
		add(back);
		
		//button to start game
		JButton start = new JButton("Start");
		start.setBounds(579, 500, 200, 50);
		start.setBackground(Color.GREEN);
		add(start);
		
		//settings action commands
		start.addActionListener(this);
		back.addActionListener(this);
		start.setActionCommand("start");
		back.setActionCommand("toMenu");
		
		//background colors
		setBackground(Color.WHITE);
		maps.setBackground(Color.WHITE);
		
		//finalizing and displaying
		frame.add(this);
		frame.setResizable(false);
		frame.setVisible(true);

	}//end playPanel
	
	public void showCurrFrame(boolean open) {
		currentFrame.setVisible(open);
	}
	
	
	public void actionPerformed(ActionEvent choice){
		
        input = choice.getActionCommand();
		in = "";;
		//go to play menu
        if (input.equals("play")) {
			playPanel(currentFrame);
		}
		//go back to menu
		if (input.equals("toMenu")) {
			//menuReload(currentFrame);
			loadMenu();
		}
		//go to menu
        if (input.equals("instructions")) {
            instructions(currentFrame);
		}
		//go to stats
        if (input.equals("stats")) {
			stats(currentFrame);
		}
		//exit game
        if (input.equals("exit")) {
            System.exit(0);
        }
		//start game
		if (input.equals("start")) {
            //currentFrame.setVisible(false);
			currentFrame.dispose();
			game = new Game(location);
			updateData();
			System.out.println("game finished");
		}
		
		//need a fallback system to catch the exception given by not using play first
		//catch a potential exception from user not entering the play menu
		try {
		  in = (String) maps.getSelectedItem();
		}
		catch(Exception e) {
		  in = "";
		}
		
		//dungeon map
		if (in.equals("Easy")) {
            image.setIcon(new ImageIcon("Dungeon.jpg"));
			location.setText("Current Location: Dungeon");
			repaint();
		}
		//highway map
        if (in.equals("Normal")){
            image.setIcon(new ImageIcon("Highway.jpg"));
			location.setText("Current Location: Highway");
			repaint();
		}
		//castlegrounds map
        if (in.equals("Hard")){
            image.setIcon(new ImageIcon("Castlegrounds.jpg"));
			location.setText("Current Location: Castlegrounds");
			repaint();
		}
	}//end action performed
	
	//get the data from the game to update the values
	public void updateData() {
		survivalTimeRecent = game.getTimeRecent();
		survivalTimeBest = game.getTimeBest();
		
		scoreRecent = game.getScoreRecent();
		scoreBest = game.getScoreBest();
	}
}//end class