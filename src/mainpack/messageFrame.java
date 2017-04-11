package mainpack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import sun.audio.*;

/*
 * You can fix it like this:
 * Preferences -> Java -> Compiler -> Errors/Warnings -> Deprecated and restricted API ->
 * Forbidden reference -> change "error" to "warning"
*/ 
import com.sun.awt.AWTUtilities;

public class messageFrame extends JFrame implements Params{
//	private Font messageFont = new Font("MS PGothic",Font.PLAIN,22);
//	private final String assetsFolder = "Assets/";
	//{0} = yomeId; {1} = cardID; {2} = voiceFile
//	private final String questionImage = "img/{0}/question.png";
//	private final String ngImage = "img/{0}/ng.png";
//	private final String cardPath = "card/{0}/{1}/1.png";
//	private final String voiceFilePath = "voice/{0}/{1}/{2}.wav";
	
	private String yomeId = "154";
	private String cardId = "1";
	private String voiceId = "";
	private String question = "";
	private String okAnswer = "";
	private String ngAnswer = "";
	private String okChoiceVoice = "";
	private String ngChoice = "";
	
	private final int textPos_X = 20;
	private final int textPos_Y = 440;
	private final int textPos_okY = 510;
	private final int textPos_ngY = 570;
	
	private Color textFG = Color.white;
	private Color okChoiceFG = Color.white;
	private Color ngChoiceFG = Color.white; 
	
	private JFrame messageFrame;
	private ImageIcon imageIcon;
	private JLabel textField;
	private JLabel lab_okChoice;
	private JLabel lab_ngChoice;
	
	private AudioStream voice;
	private boolean showQuestion = true;
	
	public messageFrame(String arg_yomeID, String arg_cardID){
		yomeId = arg_yomeID;
		cardId = arg_cardID;
		
		messageFrame = new JFrame();
		messageFrame.setBounds(80, 0, 400, 300);
		messageFrame.setUndecorated(true);
		com.sun.awt.AWTUtilities.setWindowOpaque(messageFrame,false);
		messageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JLabel();
		textField.setBounds(textPos_X, textPos_Y, 400, 300);
		textField.setVerticalAlignment(textField.TOP);
		textField.setFont(messageFont);
		textField.setForeground(textFG);
		
		lab_okChoice = new JLabel();
		lab_okChoice.setBounds(textPos_X, textPos_okY, 400, 50);
		lab_okChoice.setVerticalAlignment(textField.TOP);
		lab_okChoice.setFont(messageFont);
		lab_okChoice.setForeground(okChoiceFG);
		lab_ngChoice = new JLabel();
		lab_ngChoice.setBounds(textPos_X, textPos_ngY, 400, 50);
		lab_ngChoice.setVerticalAlignment(textField.TOP);
		lab_ngChoice.setFont(messageFont);
		lab_ngChoice.setForeground(ngChoiceFG);
		
		JButton btn_Reset=new JButton("reset");
		btn_Reset.setBounds(0, 0, 80, 25);

		JPanel mainPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D gg = (Graphics2D)g;
				if (imageIcon != null){
					gg.drawImage(imageIcon.getImage(),0,0,messageFrame.getWidth(),
							messageFrame.getHeight(),messageFrame);
				}
			}
		};
		mainPanel.setLayout(null);
		mainPanel.add(btn_Reset);
		mainPanel.add(textField);
		mainPanel.add(lab_okChoice);
		mainPanel.add(lab_ngChoice);
		
		textField.setVisible(false);
		lab_okChoice.setVisible(false);
		lab_ngChoice.setVisible(false);
		
		messageFrame.setContentPane(mainPanel);
		
		mainPanel.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt){				
				if (showQuestion){
					ShowQuestion();
					showQuestion = false;
				}
			}
		});
		btn_Reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
//				messageFrame.setVisible(false);
				AudioPlayer.player.stop(voice);
				String card = MessageFormat.format(cardPath, yomeId, cardId);
				Showmessage(card, "", "");
				showQuestion = true;
			}
		});
		lab_okChoice.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt){
				String card = MessageFormat.format(cardPath, yomeId, cardId);
				String voiceFile = MessageFormat.format(voiceFilePath, yomeId, cardId, voiceId);
				Showmessage(card, voiceFile, okChoiceVoice);
			}
		});
		lab_ngChoice.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt){
				String card = MessageFormat.format(ngImage,yomeId);
				Showmessage(card, "", ngChoice);
			}
		});

		String card = MessageFormat.format(cardPath, yomeId, cardId);
		Showmessage(card, "", "");
		messageFrame.setVisible(true);
	}

	public void Showmessage(String card, String voiceFile, String text){
		if ("".equals(card)){
			imageIcon = new ImageIcon(assetsFolder+MessageFormat.format(questionImage,yomeId));
		}
		else{
			imageIcon = new ImageIcon(assetsFolder+card);
		}
		if (!"".equals(text)){
			textField.setText("<html>"+text+"</html>");
			textField.setVisible(true);
		}
		else{
			textField.setText("");
			textField.setVisible(false);
		}
		lab_okChoice.setText("");
		lab_okChoice.setVisible(false);
		lab_ngChoice.setText("");
		lab_ngChoice.setVisible(false);
		
		if (imageIcon != null){
			messageFrame.setBounds(80, 30, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		}

//		messageFrame.validate();
		messageFrame.repaint();
		messageFrame.setVisible(true);
		
		if (!"".equals(voiceFile)){
			try {
				InputStream in = new FileInputStream (assetsFolder+voiceFile);
				voice = new AudioStream (in);
				AudioPlayer.player.start(voice);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public void ShowQuestion(){
		imageIcon = new ImageIcon(assetsFolder+MessageFormat.format(questionImage,yomeId));
		textField.setText("<html>"+question+"</html>");
		lab_okChoice.setText(okAnswer);
		lab_ngChoice.setText(ngAnswer);
		
		if (imageIcon != null){
			messageFrame.setBounds(80, 30, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		}
		textField.setVisible(true);
		lab_okChoice.setVisible(true);
		lab_ngChoice.setVisible(true);

		messageFrame.repaint();
		messageFrame.setVisible(true);
	}
	
	public void setmessage(String arg_cardId, String arg_voiceId, String arg_question, 
			String arg_okAnswer, String arg_ngAnswer,
			String arg_okChoiceVoice, String arg_ngChoice){
		cardId = arg_cardId;
		voiceId = arg_voiceId;
		question = arg_question;
		okAnswer = arg_okAnswer;
		ngAnswer = arg_ngAnswer;
		okChoiceVoice = arg_okChoiceVoice;
		ngChoice = arg_ngChoice;
	}

}
