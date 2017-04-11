package yomecolle;

import mainpack.messageFrame;

public class Yomecolle {
	public static void main(String[] args) throws Exception {
		String _yomeId = "154";
		
		String file = "card/154/1/1.png";
		String voiceFile = "voice/154/1/1.wav";
		String text = "test1";
		
		String _cardId = "1";
		String _voiceId = "1";
		String _question = "test2";
		String _okAnswer = "test3";
		String _ngAnswer = "test4";
		String _okChoiceVoice = "test5";
		String _ngChoice = "test6";

		messageFrame message = new messageFrame(_yomeId, _cardId);
//		message.Showmessage(file, "", "");
		message.setmessage(_cardId, _voiceId, _question, _okAnswer, _ngAnswer, _okChoiceVoice, _ngChoice);
	}
}
