package core.graph;

import core.data_structure.*;
import core.modules.Module;
import core.message.*;

public class 
TetrisGraph 
extends Graph<Module, Message> {

	public
	TetrisGraph addModule(Module m) {
		this.addVertex(m);
		return this;
	}
	
	public
	TetrisGraph addMessage(Message msg, Module m_out, Module m_in) {
		this.addEdge(msg, m_out, m_in);
		// 注册接收邮箱
		m_in.setOwnMail(msg);
		return this;
	}
	
	public
	Dict<Module, Message> msgsToSend(Module m_out) {
		return this.outEdges(m_out);
	}
	
	public
	void mapMessage() {
		List<Module> modules = this.verteces();
	
		for (int i = 0; i < modules.length(); ++i) {
			Module module = modules.elem(i);
			Dict<Module, Message> msgs_to_send = this.msgsToSend(module);
			// 注册发送邮箱
			module.setMailToSend(msgs_to_send);
		}
	}
	
	// 让所有顶点的线程开始
	public
	void run() {
		List<Module> modules = this.verteces();
		for (int i = 0; i < modules.length(); ++i) {
			modules.elem(i).start();
		}
	}
}
