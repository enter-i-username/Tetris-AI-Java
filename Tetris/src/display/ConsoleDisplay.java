package display;

import core.brick.BrickInMap;
import core.display.*;
import core.map.Point;

public 
class ConsoleDisplay 
extends Display{
	
	@Override
	public 
	void display(int num_rows, int num_cols, int score, boolean is_hosted_mode, BrickInMap next_brick, boolean is_game_over) {
		
		int[][] map = fusedPixels(num_rows, num_cols);
		int[][] next_brick_array = next_brick != null ? next_brick.toArray() : null;
		
		
		if (map != null) {
			String clear_screen = "";
			String pic = "";
			
			for (int i = 0; i < num_rows + 3; ++i)
				clear_screen += '\n';
			
			pic += is_hosted_mode ? "��R���˳��й�ģʽ\n" : "��R�������й�ģʽ\n";
			
			
			for (int i = 0; i < num_cols + 2; ++i) {
				pic += "00";
			}
			pic += "    ��һ���飺";
			pic += '\n';
			
			for (int i = 0; i < num_rows; ++i) {
				
				if (i >= 1) {
					pic += "||";
					for (int j = 0; j < num_cols; ++j) {
						switch (map[j][i]) {
						case Point.State.BLANK: pic += i == 4 ? "00" : "  "; break;
						case Point.State.STATIC: pic += "[]"; break;
						case Point.State.DYNAMIC: pic += "()"; break;
						case Display.State.NEW_BLANK: pic += "  "; break;
						case Display.State.CROSS: pic += "XX"; break;
						}
					}
					pic += "||";
					
					if (is_game_over) {
						pic += "   ��Ϸ������";
					} else if (next_brick_array != null 
						&& i <= 3) {
						pic += "    ";
						for (int j = 0; j < 4; ++j) {
							pic += next_brick_array[j][i] == 1 ? "[]" : "  ";
						}
					}
					
					pic += '\n';
				}
			}
			
			
			for (int i = 0; i < num_cols + 2; ++i) {
				pic += "00";
			}
			pic += '\n';
			
			System.out.print(clear_screen + "��ǰ�÷֣�" + String.valueOf(score) + "\n" + pic);
		}
		
	}

	public static
	void start(int num_rows, int num_cols, int time, int total_time) {
		String clear_screen = "";
		String pic = "";
		
		for (int i = 0; i < num_rows + 3; ++i)
			clear_screen += '\n';
		
		pic += "����˹����\n";
		pic += "��Ϸ���Ͽ�ʼ��" + String.valueOf(time) + "S\n";
		
		for (int i = 0; i < num_cols + 2; ++i) {
			pic += "00";
		}
		pic += '\n';
		
		
		for (int i = 0; i < num_rows; ++i) {
			if (i >= 1) {
				pic += "||";
				for (int j = 0; j < num_cols; ++j) {
					if (i <= num_rows * time / total_time)
						pic += "[]";
					else
						pic += "  ";
				}
				pic += "||";
				pic += '\n';
			}
		}
		
		
		for (int i = 0; i < num_cols + 2; ++i) {
			pic += "00";
		}
		pic += '\n';
		
		System.out.print(clear_screen + pic);
	}
}
