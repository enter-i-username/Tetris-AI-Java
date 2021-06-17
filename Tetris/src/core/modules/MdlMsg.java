package core.modules;

import core.message.*;

public
class MdlMsg {
	
	public static
	class MdlMsgCtnt<DataType> 
	extends Message.MsgContent<DataType> {

		public Module mod;
		
		public MdlMsgCtnt(Module mod, DataType data) {
			super(data);
			this.mod = mod;
		}
	}
}

