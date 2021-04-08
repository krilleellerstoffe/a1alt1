import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The GUI for assignment 1, DualThreads
 */
public class GUIAssignment1 
{
	/**
	 * These are the components you need to handle.
	 * You have to add listeners and/or code
	 */
	private JFrame frame;		// The Main window
	private JButton btnDisplay;	// Start thread moving display
	private JButton btnDStop;	// Stop moving display thread
	private JButton btnTriangle;// Start moving graphics thread
	private JButton btnTStop;	// Stop moving graphics thread
	private JButton btnOpen;	// Open audio file 
	private JButton btnPlay;	// Start playing audio
	private JButton btnStop;	// Stop playing
	private JButton btnGo;		// Start game catch me
	private JPanel pnlMove;		// The panel to move display in
	private JPanel pnlRotate;	// The panel to move graphics in
	private JPanel pnlGame;		// The panel to play in
	private JLabel lblPlaying;	// Playing text
	private JLabel lblAudio;	// Audio file
	private JTextArea txtHits;	// Display hits
	private JComboBox<String> cmbSkill;	// Skill combo box, needs to be filled in


	private MusicPlayer musicPlayer = new MusicPlayer();
	/**
	 * Constructor
	 */
	public GUIAssignment1()
	{
	}

	/**
	 * Starts the application
	 */
	public void start()
	{
		frame = new JFrame();
		frame.setBounds(0, 0, 819, 438);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setTitle("Multiple Thread Demonstrator");
		InitializeGUI();					// Fill in components
		frame.setVisible(true);
		frame.setResizable(false);			// Prevent user from change size
		frame.setLocationRelativeTo(null);	// Start middle screen
	}
	
	/**
	 * Sets up the GUI with components
	 */
	private void InitializeGUI()
	{
		// The music player outer panel
		JPanel pnlSound = new JPanel();
		Border b1 = BorderFactory.createTitledBorder("Music Player");
		pnlSound.setBorder(b1);
		pnlSound.setBounds(12, 12, 450, 100);
		pnlSound.setLayout(null);
		
		// Add labels and buttons to this panel
		lblPlaying = new JLabel("Now Playing: ");	// Needs to be alteraed
		lblPlaying.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblPlaying.setBounds(128, 16, 300, 20);
		pnlSound.add(lblPlaying);

		JLabel lbl1 = new JLabel("Loaded Audio File:");
		lbl1.setBounds(10, 44, 130, 13);
		pnlSound.add(lbl1);

		lblAudio = new JLabel("...");				// Needs to be altered
		lblAudio.setBounds(115, 44, 300, 13);
		pnlSound.add(lblAudio);

		btnOpen = new JButton("Open");
		btnOpen.setBounds(6, 71, 75, 23);
		btnOpen.addActionListener(action -> {
			//Opens a dialogue to let the user choose some music
				JFileChooser fileChooser = new JFileChooser("a1alt1/resources/sounds/");
				fileChooser.setFileFilter(new FileNameExtensionFilter("MP3 files","mp3"));
				String fileName;
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					fileName = fileChooser.getSelectedFile().getPath();
					musicPlayer.setSound(fileName);
					lblAudio.setText(" " + fileName.substring(fileName.lastIndexOf("\\")+1));
					btnPlay.setEnabled(true);
				}
		});
		pnlSound.add(btnOpen);

		btnPlay = new JButton("Play");
		btnPlay.setBounds(88, 71, 75, 23);
		btnPlay.setEnabled(false);
		btnPlay.addActionListener(action -> {
					musicPlayer.startPlay();
					btnPlay.setEnabled(false);
					btnStop.setEnabled(true);
		});
		pnlSound.add(btnPlay);

		btnStop = new JButton("Stop");
		btnStop.setBounds(169, 71, 75, 23);
		btnStop.setEnabled(false);
		btnStop.addActionListener(action -> {
				musicPlayer.stopPlay();
				btnStop.setEnabled(false);
				btnPlay.setEnabled(true);
		});
		pnlSound.add(btnStop);


		frame.add(pnlSound);
		
		// The moving display outer panel
		JPanel pnlDisplay = new JPanel();
		Border b2 = BorderFactory.createTitledBorder("Display Thread");
		pnlDisplay.setBorder(b2);
		pnlDisplay.setBounds(12, 118, 222, 269);
		pnlDisplay.setLayout(null);

		pnlMove = new JPanel();
		pnlMove.setBounds(10,  19,  200,  200);
		Border b21 = BorderFactory.createLineBorder(Color.black);
		pnlMove.setBorder(b21);
		pnlDisplay.add(pnlMove);
		frame.add(pnlDisplay);


		RandomText randomText = new RandomText("Random text");
		pnlMove.add(randomText);

		// Add buttons and drawing panel to this panel
		btnDisplay = new JButton("Start Display");
		btnDisplay.setBounds(10, 226, 121, 23);
		btnDisplay.addActionListener(action -> {
			randomText.randomise();
			btnDisplay.setEnabled(false);
			btnDStop.setEnabled(true);
		});
		pnlDisplay.add(btnDisplay);

		btnDStop = new JButton("Stop");
		btnDStop.setBounds(135, 226, 75, 23);
		btnDStop.setEnabled(false);
		btnDStop.addActionListener(action -> {
			randomText.stopRunning();
			btnDisplay.setEnabled(true);
			btnDStop.setEnabled(false);
		});
		pnlDisplay.add(btnDStop);


				
		// The moving graphics outer panel
		JPanel pnlTriangle = new JPanel();
		Border b3 = BorderFactory.createTitledBorder("Triangle Thread");
		pnlTriangle.setBorder(b3);
		pnlTriangle.setBounds(240, 118, 222, 269);
		pnlTriangle.setLayout(null);

		pnlRotate = new JPanel();
		pnlRotate.setBounds(10,  19,  200,  200);
		Border b31 = BorderFactory.createLineBorder(Color.black);
		pnlRotate.setBorder(b31);
		pnlTriangle.add(pnlRotate);

		Triangle triangle = new Triangle();
		triangle.setBounds(5,  5,  190,  190);
		pnlRotate.add(triangle);

		// Add buttons and drawing panel to this panel
		btnTriangle = new JButton("Start Rotate");
		btnTriangle.setBounds(10, 226, 121, 23);
		btnTriangle.addActionListener(action -> {
				triangle.rotate();
				btnTriangle.setEnabled(false);
				btnTStop.setEnabled(true);
		});
		pnlTriangle.add(btnTriangle);
		// Add this to main window
		frame.add(pnlTriangle);

		btnTStop = new JButton("Stop");
		btnTStop.setBounds(135, 226, 75, 23);
		btnTStop.setEnabled(false);
		btnTStop.addActionListener(action -> {
				triangle.stopRotation();
				btnTStop.setEnabled(false);
				btnTriangle.setEnabled(true);
		});
		pnlTriangle.add(btnTStop);

		// The game outer panel
		JPanel pnlCatchme = new JPanel();
		Border b4 = BorderFactory.createTitledBorder("Catch Me");
		pnlCatchme.setBorder(b4);
		pnlCatchme.setBounds(468, 12, 323, 375);
		pnlCatchme.setLayout(null);
		
		// Add controls to this panel
		JLabel lblSkill = new JLabel("Skill:");
		lblSkill.setBounds(26, 20, 50, 13);
		pnlCatchme.add(lblSkill);

		JLabel lblInfo = new JLabel("Hit Image with Mouse");
		lblInfo.setBounds(107, 13, 150, 13);
		pnlCatchme.add(lblInfo);

		JLabel lblHits = new JLabel("Hits:");
		lblHits.setBounds(240, 20, 50, 13);
		pnlCatchme.add(lblHits);

		CatchMeButton btnCatchMe = new CatchMeButton();
		btnCatchMe.addActionListener(action -> {
			btnCatchMe.increaseHits();
			txtHits.setText(String.valueOf(btnCatchMe.getHits()));
			btnCatchMe.stopGame();
			btnGo.setEnabled(true);
		});

		cmbSkill = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"}); // Combo box to choose difficulty
		cmbSkill.setBounds(19, 41, 61, 23);
		cmbSkill.addActionListener(action -> {
			String difficulty = (String) cmbSkill.getSelectedItem();
			switch(difficulty) {
				case "Easy":
					btnCatchMe.setDelay(20);
					break;
				case "Medium":
					btnCatchMe.setDelay(10);
					break;
				case "Hard":
					btnCatchMe.setDelay(5);
					break;
			}
		});
		pnlCatchme.add(cmbSkill);



		btnGo = new JButton("GO");
		btnGo.setBounds(129, 41, 75, 23);
		btnGo.addActionListener(action -> {
			btnCatchMe.startGame();
			btnGo.setEnabled(false);
		});
		pnlCatchme.add(btnGo);

		txtHits = new JTextArea();			// Displays successful hits
		txtHits.setText(String.valueOf(btnCatchMe.getHits()));
		txtHits.setBounds(233, 41, 71, 23);
		Border b40 = BorderFactory.createLineBorder(Color.black);
		txtHits.setBorder(b40);
		pnlCatchme.add(txtHits);

		pnlGame = new JPanel();
		pnlGame.setBounds(19, 71, 285, 283);
		Border b41 = BorderFactory.createLineBorder(Color.black);
		pnlGame.setBorder(b41);

		pnlGame.add(btnCatchMe);


		pnlCatchme.add(pnlGame);
		frame.add(pnlCatchme);
	}
}
