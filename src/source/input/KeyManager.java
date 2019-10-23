package source.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private boolean[] keys, justPressed, cantPress;
	public boolean W, S, A, D;
	public boolean UP, DOWN, LEFT, RIGHT;
	public boolean esc, HOME, PG_DOWN, PG_UP;
	public boolean Q;
	
	public KeyManager(){
		keys = new boolean[256];
		justPressed = new boolean[keys.length];
		cantPress = new boolean[keys.length];
	}
	
	public void update(){
		for(int i = 0;i < keys.length;i++)
		{
			if(cantPress[i] && !keys[i]){
				cantPress[i] = false;
			}
			else if(justPressed[i]){
				cantPress[i] = true;
				justPressed[i] = false;
			}
			if(!cantPress[i] && keys[i]){
				justPressed[i] = true;
			}
		}
		
		W = keys[KeyEvent.VK_W];
		S = keys[KeyEvent.VK_S];
		A = keys[KeyEvent.VK_A];
		D = keys[KeyEvent.VK_D];
		UP = keys[KeyEvent.VK_UP];
		DOWN = keys[KeyEvent.VK_DOWN];
		LEFT = keys[KeyEvent.VK_LEFT];
		RIGHT = keys[KeyEvent.VK_RIGHT];
		
		Q = keys[KeyEvent.VK_Q];
		
		esc = keys[KeyEvent.VK_ESCAPE];
		HOME = keys[KeyEvent.VK_HOME];
		PG_DOWN = keys[KeyEvent.VK_PAGE_DOWN];
		PG_UP = keys[KeyEvent.VK_PAGE_UP];
		
		/*
		 *  in another class:
		 * if(handler.getKeyManager().up)
			yMove = -speed;
		 * 
		 */
	}
	
	public boolean keyJustPressed(int keyCode){
		if(keyCode < 0 || keyCode >= keys.length)
			return false;
		return justPressed[keyCode];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
