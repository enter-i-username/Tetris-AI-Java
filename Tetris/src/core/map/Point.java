package core.map;

public class Point {
	public static 
	class State {
		public static final int BLANK    = 0;
		public static final int DYNAMIC  = 2;
		public static final int STATIC   = 1;
	}
	public int state = State.BLANK;
}
