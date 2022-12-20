package main.visual;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pic {
	static BufferedImage chess_buf;
	static BufferedImage[] chess=new BufferedImage[15];
	static {
		try {
			chess_buf=ImageIO.read(new File("img/chess.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<15;i++)
			chess[i]=chess_buf.getSubimage(0,i*38,38,38);
	}

	static BufferedImage cursor_buf;
	static BufferedImage[][] cursor=new BufferedImage[2][3];
	static {
		try {
			cursor_buf=ImageIO.read(new File("img/cursor.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<2;i++)
			for(int j=0;j<3;j++)
				cursor[i][j]=cursor_buf.getSubimage(i*41,j*41,41,41);
	}

	static BufferedImage background;
	static {
		try {
			background=ImageIO.read(new File("img/background.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	static BufferedImage icon_ch_buf;
	static BufferedImage[][] icon_ch=new BufferedImage[2][7];
	static {
		try {
			icon_ch_buf=ImageIO.read(new File("img/icon_ch.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<2;i++)
			for(int j=0;j<7;j++)
				icon_ch[i][j]=icon_ch_buf.getSubimage(i*24,j*26,24,26);
	}

	static BufferedImage dots_buf;
	static BufferedImage[][] dots=new BufferedImage[2][7];
	static {
		try {
			dots_buf=ImageIO.read(new File("img/dots.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<2;i++)
			for(int j=0;j<5;j++)
				dots[i][j]=dots_buf.getSubimage(i*3,j*26,3,26);
	}

	static BufferedImage chess_ani_buf;
	static BufferedImage[][] chess_ani=new BufferedImage[3][15];
	static {
		try {
			chess_ani_buf=ImageIO.read(new File("img/chess_ani.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<3;i++)
			for(int j=0;j<15;j++)
				chess_ani[i][j]=chess_ani_buf.getSubimage(i*38,j*38,38,38);
	}

	static BufferedImage chara_buf;
	static BufferedImage[][] chara=new BufferedImage[2][8],chara_null=new BufferedImage[2][8];
	static {
		try {
			chara_buf=ImageIO.read(new File("img/chara.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<2;i++)
			for(int j=0;j<8;j++)
				chara[i][j]=chara_buf.getSubimage(i*176,j*110,176,110);
	}
	public static void change_characters_visibility() {
		BufferedImage[][] tmp=chara_null;
		chara_null=chara; chara=tmp;
	}


	static BufferedImage bar_buf;
	static BufferedImage[] bar=new BufferedImage[4];
	static {
		try {
			bar_buf=ImageIO.read(new File("img/bar.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<4;i++)
			bar[i]=bar_buf.getSubimage(0,i*19,369,19);
	}

	static BufferedImage num_buf;
	public static BufferedImage[][] num=new BufferedImage[2][12];
	static {
		try {
			num_buf=ImageIO.read(new File("img/num.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<2;i++)
			for(int j=0;j<12;j++)
				num[i][j]=num_buf.getSubimage(j*7,i*11,7,11);
	}

	static BufferedImage button_buf;
	static BufferedImage[][] button=new BufferedImage[2][21];
	static {
		try {
			button_buf=ImageIO.read(new File("img/button.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<2;i++)
			for(int j=0;j<21;j++)
				button[i][j]=button_buf.getSubimage(105*i,36*j,105,36);
	}

	static BufferedImage text_buf;
	static BufferedImage[] text=new BufferedImage[8];
	static {
		try {
			text_buf=ImageIO.read(new File("img/text.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<8;i++)
			text[i]=text_buf.getSubimage(0,66*i,129,66);
	}

	static BufferedImage title;
	static {
		try {
			title=ImageIO.read(new File("img/title.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	static BufferedImage win_buf;
	static BufferedImage[] win=new BufferedImage[2];
	static {
		try {
			win_buf=ImageIO.read(new File("img/win.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<2;i++)
			win[i]=win_buf.getSubimage(0,31*i,139,31);
	}

	public static BufferedImage icon;
	static {
		try {
			icon=ImageIO.read(new File("img/icon.png"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}