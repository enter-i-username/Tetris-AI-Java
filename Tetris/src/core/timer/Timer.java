package core.timer;


public class Timer {
	
	public static abstract
	class Callback {
		
		private long _ticks;
		
		public
		Callback(long ticks) {
			this._ticks = ticks;
		}
		
		public
		long ticks() {
			return this._ticks;
		}
		
		public abstract
		void callback();
	}

	public
	void sleep(long millis)  {
		try {
			Thread.currentThread();
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private long _time_stamp;
	private long _time_interval;
	private long _millis_per_tick;
	private Callback[] _callbacks;
	
	public
	Timer() {
		this._time_stamp = 0;
	}
	
	public
	void registerEvent(long millis_per_tick, Callback[] callbacks) {
		this._millis_per_tick = millis_per_tick;
		this._callbacks = callbacks;
	}
	
	public
	void run() {
		while (true) {
			for (int i = 0; i < this._callbacks.length; ++i) {
				this.tic();
				this._callbacks[i].callback();
				this.toc();
				
				long delta_time = this._callbacks[i].ticks() * this._millis_per_tick - this.interval();
				
				// 没有超过ticks时间，就补齐
				if (delta_time >= 0) {
					this.sleep(delta_time);
				} 
				// 否则从其他地方抠出来
			}
		}
	}
	
	public 
	long tic() {
		this._time_stamp = System.currentTimeMillis();
		return this._time_stamp;
	}
	
	public
	long toc() {
		long ts = System.currentTimeMillis();
		this._time_interval = ts - this._time_stamp;
		this._time_stamp = ts;
		return this._time_stamp;
	}
	
	public
	long interval() {
		return this._time_interval;
	}
}
