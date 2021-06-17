package keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import core.interaction.Interaction;


public
class KeyBrickEvent 
extends Interaction {
	
	@SuppressWarnings("serial")
	private
	class InnerKeyBrickEvent
	extends JFrame {
		
		private CheckKeyEventInWindow _ckew;
		
		public
		InnerKeyBrickEvent(Interaction inter) {
			this._ckew = new CheckKeyEventInWindow(inter);
		}
		
		public 
		CheckKeyEventInWindow myWindow() {
			return this._ckew;
		}
		
		private
		class CheckKeyEventInWindow
		extends JPanel
		implements KeyListener {
			
			private Interaction _inter;
			
			public
			CheckKeyEventInWindow(Interaction inter) {
				this._inter = inter;
			}
			
			@Override
			public 
			void keyTyped(KeyEvent e) {}
		 
			// °´¼ü¼àÌýÆ÷
			@Override
			public 
			void keyPressed(KeyEvent e) {
				this._inter.checkEvent((int) e.getKeyChar());
			}
		 
			// ÊÍ·Å¼àÌýÆ÷
			@Override
			public 
			void keyReleased(KeyEvent e) {}
		}
	}
	
	
	private InnerKeyBrickEvent _kbe;
	
	public 
	KeyBrickEvent(char[] keys, Interaction.Callback cbk) {
		super(keys, cbk);
		
		this._kbe = new InnerKeyBrickEvent(this);
	}
	
	@Override
	public
	void checkInputInBlockingMode() {
		_kbe.add(this._kbe.myWindow());
		_kbe.addKeyListener(this._kbe.myWindow());// ×¢²á¼àÌýÆ÷
		_kbe.setSize(400, 400);
		_kbe.setTitle("Check Keyboard");
		_kbe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_kbe.setVisible(true);
	}
}



