package core.interaction;

import core.brick.*;
import core.data_structure.*;

public abstract
class Interaction {
	
	// 消息发布的回调函数
	public static abstract
	interface Callback {
		public 
		abstract void callback(byte motion);
	}
	
	private Dict<Integer, Byte> _key_to_motion;
	protected Callback _cbk;
	
	public final static byte HOSTED = 100;
	
	public 
	Interaction(char[] keys, Callback cbk) {
		this._key_to_motion = new Dict<Integer, Byte>();
		
		this._key_to_motion.add((int) keys[0], BrickInMap.Motion.UP);
		this._key_to_motion.add((int) keys[1], BrickInMap.Motion.DOWN);
		this._key_to_motion.add((int) keys[2], BrickInMap.Motion.LEFT);
		this._key_to_motion.add((int) keys[3], BrickInMap.Motion.RIGHT);
		this._key_to_motion.add((int) keys[4], BrickInMap.Motion.CLOCKWISE);
		this._key_to_motion.add((int) keys[5], BrickInMap.Motion.CRUSH);
		this._key_to_motion.add((int) keys[6], HOSTED);
		
		this._cbk = cbk;
	}
	
	/**
	 * 外部事件到event映射的API
	 * @param event -外部触发的事件集需映射到_key_to_motion字典中
	 */
	public
	void checkEvent(int event) {
		Byte motion = this._key_to_motion.search(event);
		
		if (motion != null) {
			this._cbk.callback(motion);
		}
	}
	
	// 需要重载checkInputInBlockingMode方法，阻塞检测设备输入
	public abstract
	void checkInputInBlockingMode();
}


