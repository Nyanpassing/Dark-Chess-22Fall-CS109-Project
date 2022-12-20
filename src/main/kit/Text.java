package main.kit;

import main.Launcher;
import main.kernel.Kernel;
import main.kernel.Press;
import main.visual.Pic;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Text {
	public static String s="";
	static boolean ctrl_pressed=false;
	public static void init() {
		Launcher.win.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(Kernel.loc!=5)
					return;
				char c=e.getKeyChar();
				if(c>='0'&&c<='9'&&s.length()<15)
					s=s+c;
				if(c=='.'&&s.length()<15)
					s=s+c;
				if(c=='\b'&&s.length()>0)
					s=s.substring(0,s.length()-1);
				if(c=='\n'&&Press.able[12]&&Kernel.loc==5)
					Press.todo[12]=true;
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==17)
					ctrl_pressed=true;
				if((e.getKeyCode()==90)&&ctrl_pressed&&Press.able[0]&&Kernel.loc==1)
					Press.todo[0]=true;
				if(Kernel.loc==7) {
					if(Press.able[16]&&e.getKeyCode()==37) Press.todo[16]=true;
					if(Press.able[17]&&e.getKeyCode()==39) Press.todo[17]=true;
					if(e.getKeyCode()==32) Press.todo[18]=true;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==17)
					ctrl_pressed=false;
			}
		});
	}
	public static void output(String s,int x,int y,int c,int len,Graphics2D g2d) {
		x+=7*(len-s.length());
		int id=0;
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)>='0'&&s.charAt(i)<='9')
				id=s.charAt(i)-'0';
			if(s.charAt(i)=='.')
				id=10;
			if(s.charAt(i)=='/')
				id=11;
			g2d.drawImage(Pic.num[c][id],x,y,null);
			x+=7;
		}
	}
}
