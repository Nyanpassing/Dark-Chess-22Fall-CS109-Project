package main.mode;

import main.kit.Pos;
import main.kernel.Judge;
import main.kernel.Kernel;
import main.kernel.Press;

import java.util.ArrayList;

public class Review {
	public static ArrayList<Pos> fr_tmp=new ArrayList<>(),
	 					  		 to_tmp=new ArrayList<>();
	public static int now=0;
	public static boolean playing;

	public static void init() {
		fr_tmp.clear(); to_tmp.clear();
		now=0;
		playing=false;
		fr_tmp.addAll(Judge.fr_tmp);
		to_tmp.addAll(Judge.to_tmp);
		Judge.fr_tmp.clear();
		Judge.to_tmp.clear();
		Press.able[16]=false;
		Press.able[18]=true;
		if(fr_tmp.size()==0)
			Press.able[17]=false;
		Judge.reload(false);
	}
	public static int size() {
		return fr_tmp.size();
	}
	public static void back() {
		if(now==0) { System.out.println("error");return; }
		Judge.fr_tmp.remove(Judge.fr_tmp.size()-1);
		Judge.to_tmp.remove(Judge.to_tmp.size()-1);
		Judge.reload(false);
		now--;
		if(now==0)
			Press.able[16]=false;
	}
	public static void next() {
		if(now==fr_tmp.size()) { System.out.println("error");return; }
		Kernel.operate(fr_tmp.get(now));
		Kernel.operate(to_tmp.get(now));
		now++;
		if(now==fr_tmp.size()-1)
			Press.able[17]=false;
	}

	static int fc=0,z;
	public static void autoplay() {
		Press.able[18]=(!Press.able[18]);
		playing=true;
		z=fc=0;
	}
	public static void stop() {
		Press.able[18]=(!Press.able[18]);
		playing=false;
		Kernel.z=0;
	}
	public static void update() {
		if(now==fr_tmp.size())
			stop();
		if(!playing) return;
		fc++;
		if(z==0) {
			if(fc==2) {
				fc=0; z^=1;
				Kernel.operate(fr_tmp.get(now));
			}
		}
		else {
			if(fc==7) {
				fc=0; z^=1;
				Kernel.operate(to_tmp.get(now));
				now++;
			}
		}
	}
}
