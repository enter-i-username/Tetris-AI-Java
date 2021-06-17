package core.modules.timer;

import core.brick.BrickInMap;
import core.data_structure.*;
import core.message.Message;
import core.modules.Module;
import core.modules.map.MapMessage;

public class TimerModule 
extends Module {
	
	public static
	class TimerRegular
	extends Module.CheckEventRegularly {
		public TimerRegular(TimerModule this_mod) {
			super(1, this_mod);
		}

		@Override
		public 
		void callback() {
			List<Message> mails_to_send = this.this_mod.mailsToSend().values();
			for (int i = 0; i < mails_to_send.length(); ++i) {
				// 只给map message发送
				if (mails_to_send.elem(i) instanceof MapMessage) {
					mails_to_send.elem(i).post(
						new MapMessage.MapMessageContent<Byte>
						(
							this.this_mod, 
							MapMessage.MapMessageContent.Msgs.Map_BrickMoved,
							// 向下
							BrickInMap.Motion.DOWN
						)
					);
				}
			}
		}
	}
	
	public
	TimerModule(long check_period_in_millis) {
		this.setupTimer(check_period_in_millis, 
				new TimerRegular(this));
	}
}
