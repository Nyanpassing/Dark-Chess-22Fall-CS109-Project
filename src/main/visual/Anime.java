package main.visual;

import main.stream.Audio;
import main.kernel.Judge;
import main.kernel.Kernel;
import main.kit.Text;

import java.awt.*;

public class Anime {
	static int a1_fc=-1,a1_id=0,a1_x=0,a1_y=0;
	static int a2_fc=-1,a2_id1=0,a2_x1=0,a2_y1=0,
			   a2_id2=0,a2_x2=0,a2_y2=0;
	static int a3_fc=0,a3_id=0,a3_now=0;
	static int a4_fc=-1;
	static int a5_fc=-1,a5_id=0,a5_ec=0;

	static void D0(int id,Graphics2D g2d) {
		g2d.drawImage(Pic.chara[0][id],
				0,Kernel.win_h-110-19,null);
	}
	static void D1(int id,Graphics2D g2d) {
		g2d.drawImage(Pic.chara[1][id],
				Kernel.win_w-176,Kernel.win_h-110-19,null);
	}
	public static void init() {
		a1_fc=a2_fc=a4_fc=a5_fc=-1;
		a3_fc=a3_id=0;
	}
	public static void update(Graphics2D g2d) {
		//animation 1:flip the chess
		if(a1_fc>=0) {
			int cid=0,fid=0;
			switch(a1_fc/2) {
				case 0: cid=7; fid=0; break;
				case 1: cid=7; fid=1; break;
				case 2: cid=7; fid=2; break;
				case 3: cid=a1_id; fid=1; break;
				case 4: cid=a1_id; fid=0; break;
				case 5: a1_fc=-1; Audio.play("place"); break;
			}
			if(a1_fc>=0) {
				a1_fc++;
				g2d.drawImage(Pic.chess_ani[fid][cid],
						a1_x,a1_y,null);
			}
		}
		//animation 2:move the chess
		if(a2_fc>=0) {
			int f_tot=6;
			if(a2_fc==f_tot) {
				Audio.play("place");
				a2_fc=-1;
			}
			int dx=(a2_x1*(f_tot-a2_fc)+a2_x2*a2_fc)/f_tot,
				dy=(a2_y1*(f_tot-a2_fc)+a2_y2*a2_fc)/f_tot;
			if(a2_fc>=0) {
				a2_fc++;
				if(a2_id2!=7) {
					if(a2_id2<0)
						g2d.drawImage(Pic.chess[7],
								a2_x2,a2_y2,null);
					else
						g2d.drawImage(Pic.chess[a2_id2],
								a2_x2,a2_y2,null);
				}
				else
					g2d.drawImage(Pic.chess_ani[2][0],
							a2_x2,a2_y2,null);
				g2d.drawImage(Pic.chess[a2_id1],
						dx,dy,null);
			}
		}
		//animation 3:change the posture
		if(a3_id==0) { //wait
			if(a3_fc<320) {
				D0(0,g2d); D1(0,g2d);
			}
			if(a3_fc>=320&&a3_fc<640) {
				D0((Judge.now==-1)?6:0,g2d);
				D1((Judge.now==1 )?6:0,g2d);
			}
			if(a3_fc>=640&&a3_fc<660) {
				D0((Judge.now==-1)?7:4,g2d);
				D1((Judge.now==1 )?7:4,g2d);
			}
			if(a3_fc>=660&&a3_fc<680) {
				D0((Judge.now==-1)?7:5,g2d);
				D1((Judge.now==1 )?7:5,g2d);
			}
			if(a3_fc==679)
				a3_fc=639;
			a3_fc++;
		}
		if(a3_id==1) { //capture
			D0((a3_now==0)?2:1,g2d);
			D1((a3_now==0)?1:2,g2d);
			a3_fc++;
			if(a3_fc>=30)
				a3_id=a3_fc=0;
		}
		if(a3_id==2) { //error
			D0(2,g2d); D1(2,g2d);
			a3_fc++;
			if(a3_fc>=30)
				a3_id=a3_fc=0;
		}
		if(a3_id==3) { //win
			D0((Judge.win==1)?3:1,g2d);
			D1((Judge.win==1)?1:3,g2d);
			g2d.drawImage(Pic.win[(Judge.win==1)?1:0],115,334,null);
		}
		//animation 4:wait for link
		if(a4_fc>=0) {
			g2d.drawImage(Pic.text[a4_fc/10+1],120,79,null);
			a4_fc++;
			if(a4_fc==10*3)
				a4_fc=0;
		}
		//animation 5:error
		if(a5_fc>=0) {
			g2d.drawImage(Pic.text[a5_id],120,(a5_id==5)?16:79,null);
			if(a5_id==5)
				Text.output(""+a5_ec,120+78,16+36,1,3,g2d);
			a5_fc++;
			if(a5_fc==30)
				a5_fc=-1;
		}
	}

	public static void A1(int id,int x,int y) {
		a1_fc=0; a1_id=id; a1_x=x; a1_y=y;
	}
	public static void A2(int id1,int id2,int x1,int y1,int x2,int y2) {
		a2_fc=0; a2_id1=id1; a2_id2=id2;
		a2_x1=x1; a2_y1=y1;
		a2_x2=x2; a2_y2=y2;
	}
	public static void A3(int id,int now) {
		a3_fc=0; a3_id=id; a3_now=now;
	}
	public static void A4() {
		a4_fc=0;
	}
	public static void A5(int id,int ec) {
		a5_fc=0; a5_id=id; a5_ec=ec;
	}
}