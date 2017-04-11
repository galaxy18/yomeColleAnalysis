package mainpack;

import java.awt.Font;

public interface Params {
	public Font messageFont = new Font("MS PGothic",Font.PLAIN,22);
	
	public final String assetsFolder = "Assets/";
	
	public final String questionImage = "img/{0}/question.png";
	public final String ngImage = "img/{0}/ng.png";
	public final String cardPath = "card/{0}/{1}/1.png";
	public final String voiceFilePath = "voice/{0}/{1}/{2}.wav";
}
