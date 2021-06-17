package tetris;

import core.modules.brick.*;
import core.modules.disp.*;
import core.modules.interation.*;
import core.modules.timer.*;
import core.timer.Timer;
import display.ConsoleDisplay;
import core.modules.map.*;
import core.graph.*;

public 
class Game {

	public static 
	void main(String[] args) {
		
		// ��Ϸ�趨
		int num_rows = 24;
		int num_cols = 10;
		int difficulty = 0;
		int disp_freq = 20;
		
		if (args.length > 0) {
			if (args[0].toLowerCase().equals("easy")) {
				difficulty = 0;
			} else if (args[0].toLowerCase().equals("normal")) {
				difficulty = 1;
			} else if (args[0].toLowerCase().equals("hard")) {
				difficulty = 2;
			}
		}
		
		//////////
		// ģ�� //
		/////////
		
		// ����
		BrickModule brick_module  = new BrickModule(0, (num_cols - 1) / 2, 0);
		// ��ͼ
		MapModule map_module = new MapModule(
				5, 
				num_rows, num_cols, 
				brick_module
		);
		// ��ʾ
		DispModule disp_module = new DispModule(disp_freq, num_rows, num_cols);
		// ����
		InterModule inter_module = new InterModule(
				50, 
				true,	// �Ǽ��̲ٿ�
				new char[] {'w', 's', 'a', 'd', 'o', ' ', 'r'}
		);
		// ��ʱ��
		TimerModule timer_module = new TimerModule(1000 - 400 * difficulty);
		
		//////////////
		// ��Ϣ���� //
		/////////////
		
		// ��ͼ������
		MapMessage map_mail = new MapMessage();
		// ��ʾ������
		DispMessage disp_mail = new DispMessage();
		// ����������
		InterMessage inter_mail = new InterMessage();
		
		// ����ͼ
		TetrisGraph game = new TetrisGraph();
		// ���붥��
		game
		.addModule(map_module)
		.addModule(disp_module)
		.addModule(inter_module)
		.addModule(timer_module);
		// �����
		game
		.addMessage(disp_mail, 	map_module, 	disp_module)
		.addMessage(disp_mail,	inter_module,	disp_module)
		.addMessage(map_mail, 	inter_module, 	map_module)
		.addMessage(map_mail, 	timer_module, 	map_module)
		.addMessage(inter_mail, map_module, 	inter_module);
		
		// ���������
		game.mapMessage();
		
		
		// ��ʼ����
		Timer timer = new Timer();
		long start = timer.tic();
		for (int time_in_seconds = 3; time_in_seconds > 0; ) {
			
			ConsoleDisplay.start(num_rows, num_cols, time_in_seconds, 3);
			
			long dt = timer.toc() - start;
			if (dt >= 1000) {	// 1s
				--time_in_seconds;
				start = timer.tic();
			} else {
				timer.sleep(disp_freq);
			}
		}
		
		// ��ʼ��Ϸ
		game.run();
		
	}
}
