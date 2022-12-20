package main.kernel;

import main.kit.Pos;

import java.util.ArrayList;
import java.util.Random;

class Chess {
	public static int[][] map=new int[4][8],mask=new int[4][8];
	static int[] order_tmp={ 1, 1, 1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7,
							-1,-1,-1,-1,-1,-2,-2,-3,-3,-4,-4,-5,-5,-6,-6,-7},
				 order=new int[32];
	public static Random rand=new Random();
	static void init_chess() {
		order[0]=order_tmp[0];
		for(int i=1;i<32;i++) {
			order[i]=order_tmp[i];
			int id=rand.nextInt(i);
			int tmp=order[i];
			order[i]=order[id];
			order[id]=tmp;
		}
		for(int i=0;i<4;i++)
			for(int j=0;j<8;j++) {
				map[i][j]=order[i*8+j];
				mask[i][j]=0;
			}
	}
	static int abs(int x) {
		return (x<0)?(-x):x;
	}
	static int sig(int x) {
		if(x<0) return -1;
		if(x>0) return 1;
		return 0;
	}
	public static boolean valid(Pos fr,Pos to) {
		if((!Pos.in_range(fr))||(!Pos.in_range(to)))
			return false;
		int dx=to.x-fr.x,dy=to.y-fr.y;
		if(mask[fr.x][fr.y]==0)
			return dx==0&&dy==0;
		int tfr=abs(map[fr.x][fr.y]),tto=abs(map[to.x][to.y]);
		if(tfr==2) {
			if(dx!=0&&dy!=0)
				return false;
			if(dx==0&&dy==0)
				return false;
			int xd=sig(dx),yd=sig(dy);
			int x_=fr.x,y_=fr.y,cnt=0;
			while(Pos.in_range(x_,y_)) {
				if(map[x_][y_]!=0)
					cnt++;
				if(cnt==3) break;
				x_+=xd; y_+=yd;
			}
			return cnt==3&&to.x==x_&&to.y==y_;
		}
		if(map[fr.x][fr.y]==0||mask[to.x][to.y]==0)
			return false;
		if(dx==0&&dy==0)
			return false;
		if(abs(dx)+abs(dy)!=1)
			return false;
		if(map[to.x][to.y]==0)
			return true;
		if(sig(map[fr.x][fr.y])==sig(map[to.x][to.y]))
			return false;
		if(tfr==1&&tto==7)
			return true;
		return tfr>=tto;
	}
	static int modify(Pos fr,Pos to) {
		if(mask[fr.x][fr.y]==0) {
			mask[fr.x][fr.y]=1;
			return map[fr.x][fr.y];
		}
		int tmp=map[to.x][to.y];
		mask[to.x][to.y]=1;
		map[to.x][to.y]=map[fr.x][fr.y];
		map[fr.x][fr.y]=0;
		return tmp;
	}
	static void display() {
		for(int i=0;i<4;i++) {
			for(int j=0;j<8;j++)
				if(mask[i][j]==0)
					System.out.print("  #");
				else
					System.out.printf("% 3d",map[i][j]);
			System.out.print("\n");
		}
	}
}

public class Judge extends Chess {
	public static int[][] map_tmp=new int[4][8];
	public static ArrayList<Pos> fr_tmp=new ArrayList<>(),
						  	     to_tmp=new ArrayList<>();
	public static int win=0,now=0;
	public static int[] life=new int[2],cnt=new int[15],
						point={0,1,5,5,5,5,10,30};
	public static boolean valid_sel(Pos fr) {
		if((!Pos.in_range(fr))||(win!=0))
			return false;
		if(now==0||mask[fr.x][fr.y]==0)
			return true;
		if(map[fr.x][fr.y]*now<=0)
			return false;
		return true;
	}
	static void act(Pos fr,Pos to,boolean op) {
		if(op) {
			fr_tmp.add(fr.clone());
			to_tmp.add(to.clone());
		}
		if(now==0) {
			now=-sig(modify(fr,to));
			return;
		}
		if(mask[fr.x][fr.y]==0) {
			modify(fr,to);
			now=-now;
			return;
		}
		int tmp=modify(fr,to),sg;
		sg=sig(tmp);
		cnt[tmp+7]++;
		life[(sg+1)/2]-=point[abs(tmp)];
		if(life[(sg+1)/2]<=0)
			win=-sg;
		now=-now;
	}
	static void init_game(boolean op) {
		if(op) {
			init_chess();
			for(int i=0;i<4;i++)
				for(int j=0;j<8;j++)
					map_tmp[i][j]=map[i][j];
			fr_tmp.clear(); to_tmp.clear();
		}
		win=now=0;
		life[0]=life[1]=60;
		for(int i=0;i<15;i++)
			cnt[i]=0;
	}
	public static int reload(boolean op) {
		init_game(false);
		for(int i=0;i<4;i++)
			for(int j=0;j<8;j++) {
				map[i][j]=map_tmp[i][j];
				mask[i][j]=0;
			}
		for(int i=0;i<fr_tmp.size();i++) {
			if(op) {
				if(!valid_sel(fr_tmp.get(i)))
					return 1;
				if(!valid(fr_tmp.get(i),to_tmp.get(i)))
					return 2;
			}
			act(fr_tmp.get(i),to_tmp.get(i),false);
		}
		return 0;
	}
}