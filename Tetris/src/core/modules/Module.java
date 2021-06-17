package core.modules;

import core.data_structure.*;
import core.message.*;
import core.timer.*;

public abstract 
class Module//<DerivedMail> 
extends Thread {
	
	// 需要继承该类并重载callback方法
	public abstract static
	class CheckEventRegularly
	extends Timer.Callback {
		protected Module this_mod;
	
		public
		CheckEventRegularly(long ticks, Module this_mod) {
			super(ticks);
			this.this_mod = this_mod;
		}

		public abstract
		void callback();
	}
	
	private Message _own_mail;
	private Dict<Module, Message> _mails_to_send;
	private Timer	_timer;
	
	
	protected
	Module() {}
	
	public
	void setOwnMail(Message own_mail) {
		this._own_mail = own_mail;
	}
	
	public
	void setMailToSend(Dict<Module, Message> mails_to_send) {
		this._mails_to_send = mails_to_send;
	}
	
	public
	void clearMail() {
		while ( !this._own_mail.isEmpty() ) {
			this._own_mail.receive();
		}
	}
	
	public 
	Dict<Module, Message> mailsToSend() {
		return this._mails_to_send;
	}
	
	public 
	Message ownMail() {
		return this._own_mail;
	}

	public 
	void setupTimer(long check_period_in_millis, CheckEventRegularly regular) {
		
		Timer.Callback[] callback_list = new Timer.Callback[] {
			regular,
		};
		
		this._timer = new Timer();
		
		this._timer.registerEvent(check_period_in_millis, callback_list);
	}
	
	public 
	void setTimer(Timer timer) {
		this._timer = timer;
	}
	
	@Override
	public
	void run() {
		if (this._timer != null)
			this._timer.run();
	}
}