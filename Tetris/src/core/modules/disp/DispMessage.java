package core.modules.disp;

import core.message.Message;
import core.modules.MdlMsg;
import core.modules.Module;

public 
class DispMessage
extends Message {
	
	public static
	class DispMessageContent<DataType>
	extends MdlMsg.MdlMsgCtnt<DataType> {
		public DispMessageContent(Module mod, Msgs m, DataType data) {
			super(mod, data);
			this.m = m;
		}
		
		public
		enum Msgs {
			Disp_BrickOutOfBoundary,
			Disp_RemoveFullRows,
			Disp_UpdateBrick,
			Disp_BrickGetsStatic,
			Disp_RegenBrick,
			Disp_HostedModeChanged,
			Disp_GameOver,
		}
		
		public Msgs m;
	}
}


