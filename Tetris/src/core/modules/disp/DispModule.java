package core.modules.disp;

import core.brick.BrickInMap;
import core.display.Display;
import display.ConsoleDisplay;
import core.map.Map;
import core.message.Message;
import core.modules.MdlMsg;
import core.modules.Module;
import core.modules.interation.InterModule;
import core.modules.map.MapModule;

public 
class DispModule 
extends Module {
	
	public static
	class DispRegular
	extends Module.CheckEventRegularly {
		
		private ConsoleDisplay _disp;
		private int score;
		private boolean is_hosted_mode = false;
		private boolean is_game_over = false;
		private BrickInMap next_brick;

		public DispRegular(DispModule this_mod) {
			super(1, this_mod);
			_disp = new ConsoleDisplay();
		}

		@Override
		public 
		void callback() {
			DispModule disp_module = (DispModule) this.this_mod;
			Message own_mail = this.this_mod.ownMail();
			//List<Message> mails_to_send = this.this_mod.mailsToSend().values();
			
			// 清空队列
			if ( !own_mail.isEmpty() ) {
				// 判断消息是发给自己的
				if( own_mail.topMsg() instanceof DispMessage.DispMessageContent ) {
					Module mod = ( (MdlMsg.MdlMsgCtnt<?>) own_mail.topMsg() ).mod;
					// 判断消息的源头module
					if (mod instanceof InterModule) {
						// 托管模式
						DispMessage.DispMessageContent<?> msg_cntnt = (DispMessage.DispMessageContent<?>) own_mail.receive();
						this.is_hosted_mode = (boolean) msg_cntnt.data;
					} else if (mod instanceof MapModule) {
						// 接收消息
						DispMessage.DispMessageContent<?> msg_cntnt = (DispMessage.DispMessageContent<?>) own_mail.receive();
						DispMessage.DispMessageContent.Msgs msg = msg_cntnt.m;
						Map map = (Map) msg_cntnt.data;
						this.score = map.score;
						
						// 根据消息内容执行相应功能
						switch (msg) {
						case Disp_BrickOutOfBoundary:
							this._disp.addElem(map, Display.DispEvent.DispCollision);
							break;
						case Disp_RemoveFullRows:
							this._disp.addElem(map, Display.DispEvent.DispRemoveRows);
							break;
						case Disp_UpdateBrick:
							this._disp.addElem(map, Display.DispEvent.DispUpdateBrick);
							break;
						case Disp_BrickGetsStatic:
							this._disp.addElem(map, Display.DispEvent.DispStaticBrick);
							break;
						case Disp_RegenBrick:
							this.next_brick = ((MapModule) msg_cntnt.mod).next_brick;
							this._disp.addElem(map, Display.DispEvent.DispGenBrick);
							break;
						case Disp_GameOver:
							this.is_game_over = true;
							break;
						default:
							break;
						}
					}
				} else {
					// 不是发给自己的不管这则消息
					own_mail.receive();
				}
			}
			
			// 绘制图队列中的元素
			this._disp.display(disp_module.num_rows, 
							   disp_module.num_cols, 
							   this.score, 
							   this.is_hosted_mode, 
							   this.next_brick, 
							   this.is_game_over);
		}
	}
	
	int num_cols;
	int num_rows;
	
	public
	DispModule(long check_period_in_millis, int num_rows, int num_cols) {
		this.setupTimer(check_period_in_millis, 
				new DispRegular(this));
		this.num_rows = num_rows;
		this.num_cols = num_cols;
	}
}
