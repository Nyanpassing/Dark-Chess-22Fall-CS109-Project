package main.kernel;

import main.Launcher;
import main.kit.Pos;
import main.mode.Review;
import main.stream.Audio;
import main.stream.Config;
import main.stream.Network;
import main.visual.Anime;
import main.visual.Draw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Kernel extends JPanel {
	public static Timer timer;
	static Pos m_pos=new Pos();
	public static int scale=3,win_w=369,win_h=379;
	public static boolean mode=false;
	static boolean m_clicked=false;
	public static int now=0;
	public static int loc=9;
	public Kernel() {
		this.setLayout(null);
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				m_pos.x=e.getX(); m_pos.y=e.getY();
				m_clicked=true;
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				m_pos.x=e.getX(); m_pos.y=e.getY();
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				m_pos.x=e.getX(); m_pos.y=e.getY();
			}
		});
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		update_all(g);
	}
	public void start() {
		while(Launcher.win.isVisible()) {
			try {
				Thread.sleep(30-System.currentTimeMillis()%30);
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
			repaint();
		}
	}

	static Pos board_p=new Pos(48,10),main_p=new Pos(0,0);
	static {
		main_p.x=(win_w-251)/2;
		board_p.x+=main_p.x;
		board_p.y+=main_p.y;
	}
	static Pos c_pos=new Pos();
	public static int z=0;
	public static Pos fr=new Pos(),to=new Pos();
	public static void operate(Pos pos) {
		if(z==0) {
			if(Judge.valid_sel(pos)) {
				fr.x=pos.x; fr.y=pos.y;
				Audio.play("click");
				z^=1;
			}
		}
		else {
			z^=1;
			if(Judge.valid(fr,pos)) {
				Anime.init();
				to.x=pos.x; to.y=pos.y;
				if(fr.x==to.x&&fr.y==to.y) {
					Anime.A1(Judge.map[fr.x][fr.y]+7,
							 Draw.Tx(fr.x)+1,Draw.Ty(fr.y)+1);
				}
				else {
					Anime.A2(Judge.map[fr.x][fr.y]+7,
							 (Judge.mask[to.x][to.y]==0)?(-1):(Judge.map[to.x][to.y]+7),
							 Draw.Tx(fr.x)+1,Draw.Ty(fr.y)+1,
							 Draw.Tx(to.x)+1,Draw.Ty(to.y)+1);
				}
				if(Judge.map[fr.x][fr.y]*Judge.map[to.x][to.y]<0)
					Anime.A3(1,(Judge.map[to.x][to.y]<0)?0:1);
				Judge.act(fr,to,true);
				if(Judge.win!=0)
					Anime.A3(3,-1);
				now^=1;
			}
		}
	}
	public static boolean dis=true;
	void update_all(Graphics g) {
		Launcher.win.requestFocus();
		Graphics2D g2d=(Graphics2D)g.create();
		if(loc==1) { //in game
			//check the network
			if(mode&&(dis||(!Network.check()))) {
				mode=false;
				Judge.now=Judge.win=0;
				Network.exit();
				Press.change_loc(6);
			}
			//in connected mode: gain the information
			if(mode) {
				if(now==0)
					m_clicked=false;
				while(Network.available()) {
					if(now==0) {
						if(Network.type==0) {
							c_pos.x=Network.pos.x;
							c_pos.y=Network.pos.y;
							operate(c_pos);
						}
						if(Network.type==1) {
							c_pos.x=Network.pos.x;
							c_pos.y=Network.pos.y;
						}
					}
					if(Network.type==2) {
						Press.act(Network.pos.x,false);
						now^=1;
					}
					Network.top=0;
				}
			}

			if((!mode)||now==1) {
				c_pos.x=m_pos.x-scale/2-scale*(board_p.x+1);
				c_pos.y=m_pos.y-scale/2-scale*(board_p.y+1);
				c_pos.x=(c_pos.x<0)?(-1):(c_pos.x/38/scale);
				c_pos.y=(c_pos.y<0)?(-1):(c_pos.y/38/scale);
				if(Pos.in_range(c_pos)&&m_clicked) {
					operate(c_pos);
					if(mode)
						Network.write(0,c_pos);
					m_clicked=false;
				}
				if(mode)
					Network.write(1,c_pos);
			}
		}
		//press:
		Press.update();

		g2d.scale(scale,scale);

		//review mode:
		if(loc==7&&Review.playing)
			Review.update();
		//draw:
		Draw.update(g2d,c_pos);
		//animation:
		Anime.update(g2d);

		g2d.dispose();
	}
}