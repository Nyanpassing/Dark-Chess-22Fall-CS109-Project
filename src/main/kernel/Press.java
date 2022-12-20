package main.kernel;

import main.Launcher;
import main.kit.Pos;
import main.mode.Review;
import main.kit.Text;
import main.stream.Audio;
import main.stream.Config;
import main.stream.Network;
import main.stream.Saves;
import main.visual.Anime;
import main.visual.Pic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Press {
	static int n=21;
	static JButton[] b=new JButton[n];
	public static boolean[] able=new boolean[n],
					 		visible=new boolean[n],
					 		todo=new boolean[n];
	public static int[] x={13,132,132,320,320,132, 13,132,132,132,132,132,132,13,13,132,13,320, 13,329,329},
			     		y={79,247,247,128, 79,133,128, 95, 95,133, 95,133,171, 5,37,171,79, 79,128,  5, 37},
				 		w={36,105,105, 36, 36,105, 36,105,105,105,105,105,105,27,27,105,36, 36, 36, 27, 27},
			     		h={36, 23, 23, 36, 36, 23, 36, 23, 23, 23, 23, 23, 23,27,27, 23,36, 36, 36, 27, 27};
	static {
		for(int i=0;i<n;i++)
			b[i]=new JButton();

		//button 0:undo
		b[0].addActionListener(e->{ if(able[0]) todo[0]=true; });

		//button 1:back
		b[1].addActionListener(e->{ if(able[1]) todo[1]=true; });

		//button 2:exit(main)
		b[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Config.exit();
			}
		});

		//button 3:exit(in the game)
		b[3].addActionListener(e->{ if(able[3]) todo[3]=true; });

		//button 4:save
		b[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(able[4]) {
					Audio.play("click");
					able[4]=false;
					Saves.save();
					able[4]=true;
				}
			}
		});

		//button 5:load
		b[5].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(able[5]) {
					Audio.play("click");
					able[15]=able[5]=false;
					int f=Saves.load();
					able[15]=able[5]=true;
					if(f==-1)
						return;
					if(f==0)
						todo[5]=true;
					else {
						Anime.A3(2,-1); Anime.A5(5,f+100);
					}
				}
			}
		});

		//button 6:reset
		b[6].addActionListener(e->{ if(able[6]) todo[6]=true; });

		//button 7:new game
		b[7].addActionListener(e->{ if(able[7]) todo[7]=true; });

		//button 8:single mode
		b[8].addActionListener(e->{ if(able[8]) todo[8]=true; });

		//button 9:connected mode
		b[9].addActionListener(e->{ if(able[9]) todo[9]=true; });

		//button 10:create the server
		b[10].addActionListener(e->{ if(able[10]) todo[10]=true; });

		//button 11:link the server
		b[11].addActionListener(e->{ if(able[11]) todo[11]=true; });

		//button 12:press to link
		b[12].addActionListener(e->{ if(able[12]) todo[12]=true; });

		//button 13:sound
		b[13].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				able[13]=(!able[13]);
				if(!able[13])
					able[14]=false;
				else
					Audio.play("click");
			}
		});

		//button 14:bgm
		b[14].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(able[13]) {
					able[14]=(!able[14]);
					if(able[14])
						Audio.bgm_start();
				}
			}
		});

		//button 15:review mode
		b[15].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(able[15]) {
					Audio.play("click");
					able[15]=able[5]=false;
					int f=Saves.load();
					able[15]=able[5]=true;
					if(f==-1)
						return;
					if(f==0)
						todo[15]=true;
					else {
						Anime.A3(2,-1); Anime.A5(5,f+100);
					}
				}
			}
		});

		//button 16:review mode: back
		b[16].addActionListener(e->{ if(able[16]) todo[16]=true; });

		//button 17:review mode: next
		b[17].addActionListener(e->{ if(able[17]) todo[17]=true; });

		//button 18:review mode: autoplay/stop
		b[18].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				todo[18]=true;
			}
		});

		//button 19:set up the scale
		b[19].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				able[19]=(!able[19]);
				todo[19]=true;
			}
		});

		//button 20:characters visible or not
		b[20].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				able[20]=(!able[20]);
				todo[20]=true;
			}
		});
	}
	public static void init1() {
		for(int i=0;i<n;i++) {
			b[i].setBounds(x[i]*Kernel.scale,y[i]*Kernel.scale,
					w[i]*Kernel.scale,h[i]*Kernel.scale);
			b[i].setBorderPainted(false);
			b[i].setFocusPainted(false);
			b[i].setContentAreaFilled(false);
		}

	}
	public static void init2() {
		for(int i=0;i<n;i++) {
			todo[i]=visible[i]=false;
			able[i]=true;
		}
		able[0]=false;
		able[14]=false;
	}
	public static void init3() {
		change_loc(0);
		ins(13); ins(14);
		ins(19); ins(20);
	}
	static void ins(int id) {
		visible[id]=true;
		Launcher.kernel.add(b[id]);
	}
	static void era(int id) {
		visible[id]=false;
		Launcher.kernel.remove(b[id]);
	}
	public static int[][] but_l={{2,9,8}, 		//loc_0: main
	 					  		 {0,4,6,3}, 	//loc_1: in the game
					  	  		 {1,5,7,15},	//loc_2: single mode
						  		 {1,10,11}, 	//loc_3: connected mode
						  		 {1}, 			//loc_4: create the server
						  		 {1,12}, 		//loc_5: link the server
						  		 {1}, 			//loc_6: disconnected
						  		 {3,16,17,18},	//loc_7: review mode
						  		 {},{}};
	static int[] to={-1,-1,-1,0,-1,1,-1,1,2,3,4 ,5 ,-1,-1,-1,7 };
	//				 0  1  2  3 4  5 6  7 8 9 10 11 12 13 14 15
	static void change_loc(int loc) {
		for(int i=0;i<but_l[Kernel.loc].length;i++)
			visible[but_l[Kernel.loc][i]]=false;
		for(int i=0;i<but_l[loc].length;i++)
			visible[but_l[loc][i]]=true;
		for(int i=0;i<but_l[Kernel.loc].length;i++)
			Launcher.kernel.remove(b[but_l[Kernel.loc][i]]);
		for(int i=0;i<but_l[loc].length;i++)
			Launcher.kernel.add(b[but_l[loc][i]]);
		Kernel.loc=loc;
		Anime.init();
	}
	static void act(int id,boolean op) {
		switch(id) {
			case 0 -> { //undo
				if(Judge.fr_tmp.size()==0)
					break;
				Judge.fr_tmp.remove(Judge.fr_tmp.size()-1);
				Judge.to_tmp.remove(Judge.to_tmp.size()-1);
				Judge.reload(false);
				Anime.init();
			}
			case 1 -> { //back
				if(Kernel.loc==4||Kernel.loc==5) {
					Network.exit();
					change_loc(3);
				}
				else
					change_loc(0);
			}
			case 3 -> { //exit(in the game)
				Judge.now=Judge.win=0;
				if(Kernel.mode) {
					Network.exit();
					Kernel.mode=false;
				}
			}
			case 5 -> {
				Kernel.z=0;
			}
			case 6,7 -> {
				Judge.init_game(true);
				Kernel.z=0;
			}
			case 12 -> { //press to link
				Network.link_the_server();
			}
			case 10 -> { //create a server
				change_loc(to[id]);
				Anime.A4();
				Network.create_a_server();
			}
			case 11 -> { //link the server
				Text.s="";
			}
			case 15 -> { //review mode
				Kernel.z=0;
				Review.init();
			}
			case 16 -> { //review mode: back
				Review.back();
				Anime.init();
			}
			case 17 -> { //review mode: next
				Review.next();
			}
			case 18 -> { //review mode: autoplay/stop
				if(able[18])
					Review.autoplay();
				else
					Review.stop();
			}
			case 19 -> { //set up the scale
				Kernel.scale=(able[19])?3:2;
				for(int i=0;i<n;i++)
					b[i].setBounds(x[i]*Kernel.scale,y[i]*Kernel.scale,
								   w[i]*Kernel.scale,h[i]*Kernel.scale);
				int wt=Kernel.win_w*Kernel.scale,ht=Kernel.win_h*Kernel.scale;
				Launcher.win.setSize(wt,ht);
				Launcher.win.setVisible(true);
				Launcher.win.setSize(wt*2-Launcher.kernel.getWidth(),ht*2-Launcher.kernel.getHeight());
			}
			case 20 -> { //characters visible or not
				Pic.change_characters_visibility();
			}
		}
		switch(id) {
			case 3,5,7,8,9,11,15 -> {
				change_loc(to[id]);
			}
		}
		if(id==5&&(Judge.win!=0))
			Anime.A3(3,-1);
		if(op)
			todo[id]=false;
	}
	static Pos p_pos=new Pos(0,0);
	static void update() {
		//check the able
		if(Kernel.loc==1) { //in the game
			able[0]=(Judge.fr_tmp.size()>0);
			able[6]=true;
			if(Kernel.mode&&(Kernel.now==0))
				able[0]=able[6]=false;
		}
		if(Kernel.loc==7) { //review mode
			able[16]=(Review.now>0)&&(!Review.playing);
			able[17]=(Review.now<Review.size())&&(!Review.playing);
		}

		boolean f=false;
		for(int i=0;i<n;i++)
			if(todo[i]) {
				act(i,true);
				f=true;
				if(Kernel.mode&&(i==0||i==6)) {
					p_pos.x=i;
					Network.write(2,p_pos);
					Kernel.now^=1;
				}
			}
		if(f)
			Audio.play("click");
	}
}
