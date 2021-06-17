package keyboard;

import java.awt.event.KeyEvent;
import java.util.EventListener;


interface KeyEventListener 
extends EventListener {

	public void KeyTyped(KeyEvent e);   
	public void KeyPressed(KeyEvent e);
	public void KeyReleased(KeyEvent e);  
}


