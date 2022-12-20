package main.visual;

import main.kernel.Judge;
import main.kernel.Kernel;
import main.kernel.Press;
import main.kit.Pos;
import main.kit.Text;
import main.mode.Review;

import java.awt.*;

public class Draw {
	static int win_w=369,win_h=379;
	static Pos board_p=new Pos(48,10),main_p=new Pos(0,0);
	static {
		main_p.x=(win_w-251)/2;
		board_p.x+=main_p.x;
		board_p.y+=main_p.y;
	}
	public static int Tx(int x) {
		return x*38+board_p.x;
	}
	public static int Ty(int y) {
		return y*38+board_p.y;
	}
	static Color[] life_c=new Color[3];
	static Color cr=new Color(207,3,2),cb=new Color(0,0,0),cw=new Color(101,58,0);
	static {
		life_c[2]=new Color(34,177,76);
		life_c[1]=new Color(249,164,21);
		life_c[0]=new Color(217,27,11);
	}
	static int[] settings_buttons_id={13,14,19,20};
	public static void update(Graphics2D g2d,Pos c_pos) {
		//draw the background
		g2d.drawImage(Pic.background,0,0,null);
		//draw buttons
		for(int i:Press.but_l[Kernel.loc])
			g2d.drawImage(Pic.button[Press.able[i]?0:1][i],
						  Press.x[i],Press.y[i],null);
		for(int i:settings_buttons_id)
			g2d.drawImage(Pic.button[Press.able[i]?0:1][i],
						  Press.x[i],Press.y[i],null);

		if(Kernel.loc==5) { //link the server
			g2d.drawImage(Pic.text[0],120,79,null);
			Text.output(Text.s,132,117,0,Text.s.length(),g2d);
		}

		if(Kernel.loc==4) { //create a server
			g2d.drawImage(Pic.text[7],120,79+76,null);
			Text.output(Text.s,132,117+76,0,Text.s.length(),g2d);
		}

		if(Kernel.loc==0) //main
			g2d.drawImage(Pic.title,110,51,null);

		if((Kernel.loc!=1)&&(Kernel.loc!=7)) //not in game or review mode
			g2d.drawImage(Pic.bar[1],
						  0,win_h-19,null);

		if(Kernel.loc==6) //disconnected
			g2d.drawImage(Pic.text[4],120,79,null);

		if(Kernel.loc==1) { //in game
			//draw the cursor
			if(Kernel.z==0) {
				if(Pos.in_range(c_pos))
					g2d.drawImage(Pic.cursor[Judge.valid_sel(c_pos)?1:0][Judge.now+1],
								  Tx(c_pos.x),Ty(c_pos.y),null);
			}
			else {
				if(Pos.in_range(c_pos))
					g2d.drawImage(Pic.cursor[Judge.valid(Kernel.fr,c_pos)?1:0][Judge.now+1],
								  Tx(c_pos.x),Ty(c_pos.y),null);
				g2d.drawImage(Pic.cursor[1][1],
							  Tx(Kernel.fr.x),Ty(Kernel.fr.y),null);
			}
		}

		if(Kernel.loc==1||Kernel.loc==7) { //in game or review mode
			//draw the chess
			for(int i=0;i<4;i++)
				for(int j=0;j<8;j++)
					if(Judge.mask[i][j]==0)
						g2d.drawImage(Pic.chess[7],
									  Tx(i)+1,Ty(j)+1,null);
					else
					if(Judge.map[i][j]!=0)
						g2d.drawImage(Pic.chess[Judge.map[i][j]+7],
									  Tx(i)+1,Ty(j)+1,null);
			//draw the counter
			for(int i=0;i<7;i++)
				if(Judge.cnt[6-i]>0) {
					g2d.drawImage(Pic.icon_ch[0][i],
								  0+main_p.x,i*26+8+main_p.y,null);
					g2d.drawImage(Pic.dots[0][Judge.cnt[6-i]-1],
								  27+main_p.x,i*26+8+main_p.y,null);
				}
			for(int i=0;i<7;i++)
				if(Judge.cnt[8+i]>0) {
					g2d.drawImage(Pic.icon_ch[1][i],
								  227+main_p.x,i*26+8+main_p.y,null);
					g2d.drawImage(Pic.dots[1][Judge.cnt[8+i]-1],
								  221+main_p.x,i*26+8+main_p.y,null);
				}
			//show lives
			g2d.drawImage(Pic.bar[0],
						  0,win_h-19,null);
			if(Judge.life[0]>0) {
				g2d.setColor(life_c[(Judge.life[0]-1)/20]);
				g2d.fillRect(5,win_h-14,Judge.life[0],9);
				Text.output(""+Judge.life[0],70,win_h-15,(Judge.life[0]>20)?0:1,2,g2d);
			}
			else
				Text.output(""+0,70,win_h-15,1,2,g2d);
			if(Judge.life[1]>0) {
				g2d.setColor(life_c[(Judge.life[1]-1)/20]);
				g2d.fillRect(win_w-5-Judge.life[1],win_h-14,Judge.life[1],9);
				Text.output(""+Judge.life[1],275,win_h-15,(Judge.life[1]>20)?0:1,2,g2d);
			}
			else
				Text.output(""+0,275,win_h-15,1,2,g2d);
			if(Judge.life[0]>0&&Judge.life[1]>0) {
				int dl=Judge.life[0]*164/(Judge.life[0]+Judge.life[1]);
				g2d.setColor(cr);
				g2d.fillRect(102,win_h-14,dl,9);
				g2d.setColor(cb);
				g2d.fillRect(102+dl+1,win_h-14,164-dl,9);
			}
		}

		if(Kernel.loc==7) { //review mode
			//show the progress bar
			if(Review.fr_tmp.size()<100) {
				g2d.drawImage(Pic.bar[2],
							  0,win_h-45,null);
				Text.output(Review.now+"/"+Review.size(),233,win_h-41,0,5,g2d);
				int dl=Review.now*127/Review.size();
				g2d.setColor(cw);
				g2d.fillRect(102,win_h-40,dl,9);
			}
			else {
				g2d.drawImage(Pic.bar[3],
							  0,win_h-45,null);
				Text.output(Review.now+"/"+Review.size(),219,win_h-41,0,7,g2d);
				int dl=Review.now*113/Review.size();
				g2d.setColor(cw);
				g2d.fillRect(102,win_h-40,dl,9);
			}
		}
	}
}
