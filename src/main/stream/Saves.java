package main.stream;

import main.kernel.Judge;
import main.Launcher;
import main.kit.Pos;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Saves {
	static JFileChooser chooser=new JFileChooser();
	static {
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setCurrentDirectory(new File("saves/"));
		chooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isDirectory()||f.getName().endsWith(".save");
			}
			@Override
			public String getDescription() {
				return "*.save";
			}
		});
	}
	static File file;
	static Scanner in;
	static PrintStream out;
	static int[][] map_tmp=new int[4][8];
	static ArrayList<Pos> fr_tmp=new ArrayList<>(),
						  to_tmp=new ArrayList<>();
	static Date date;
	static String S(int v) {
		return (v<10?"0":"")+v;
	}
	public static void save() {
		for(int i=0;i<4;i++)
			for(int j=0;j<8;j++)
				map_tmp[i][j]=Judge.map_tmp[i][j];
		for(int i=0;i<Judge.fr_tmp.size();i++) {
			fr_tmp.add(Judge.fr_tmp.get(i).clone());
			to_tmp.add(Judge.to_tmp.get(i).clone());
		}
		chooser.setDialogTitle("\u4fdd\u5b58\u5b58\u6863");
		date=new Date();
		chooser.setSelectedFile(new File("saves/"
				+(date.getYear()+1900)+"."+S(date.getMonth()+1)+"."+S(date.getDate())+'_'
				+S(date.getHours())+"."+S(date.getMinutes())+"."+S(date.getSeconds())));
		if(chooser.showOpenDialog(Launcher.kernel)==JFileChooser.APPROVE_OPTION) {
			file=chooser.getSelectedFile();
			if(!file.getAbsolutePath().endsWith(".save"))
				file=new File(file.getAbsolutePath()+".save");
			try {
				out=new PrintStream(file);
				out.print("Dark_Chess_save:");
				out.printf("%c%c%c",(char)33+fr_tmp.size()%(1<<6),
									(char)33+(fr_tmp.size()>>6)%(1<<6),
									(char)33+(fr_tmp.size()>>12)%(1<<6));
				for(int i=0;i<4;i++)
					for(int j=0;j<8;j++)
						out.printf("%c",(char)33+7+map_tmp[i][j]);
				for(int i=0;i<fr_tmp.size();i++)
					out.printf("%c%c",(char)33+fr_tmp.get(i).x*8+fr_tmp.get(i).y,
									  (char)33+to_tmp.get(i).x*8+to_tmp.get(i).y);
				out.close();
			}catch(Exception e) {
				throw new RuntimeException(e);
				//TODO:
			}
		}
		fr_tmp.clear();
		to_tmp.clear();
	}
	static int T(int id,String s) {
		return s.charAt(id+19)-33;
	}
	static int[] cnt=new int[15];
	public static int load_() {
		chooser.setDialogTitle("\u5bfc\u5165\u5b58\u6863");
		chooser.setCurrentDirectory(new File("saves/"));
		chooser.setSelectedFile(null);
		if(chooser.showOpenDialog(Launcher.kernel)==JFileChooser.APPROVE_OPTION) {
			file=chooser.getSelectedFile();
			if(!file.getAbsolutePath().endsWith(".save"))
				return 3;
			try {
				in=new Scanner(file);
				String s=in.nextLine();
				if(s.length()!=file.length())
					return 4;
				if(!s.startsWith("Dark_Chess_save:"))
					return 5;
				int size=T(-3,s)|(T(-2,s)<<6)|(T(-1,s)<<12);
				if(s.length()!=size*2+19+32)
					return 6;
				for(int i=0;i<15;i++)
					cnt[i]=0;
				for(int i=0;i<32;i++) {
					if(T(i,s)<0||T(i,s)>=15)
						return 7;
					cnt[T(i,s)]++;
				}
				int f=0;
				for(int i=0;i<15;i++) {
					switch(i) {
						case 0,14 -> { if(cnt[i]!=1) f=1;}
						case 6,8 -> { if(cnt[i]!=5) f=1; }
						case 7 -> {}
						default -> { if(cnt[i]!=2) f=1; }
					}
				}
				if(f==1)
					return 7;
				for(int i=0;i<4;i++)
					for(int j=0;j<8;j++)
						Judge.map_tmp[i][j]=T(i*8+j,s)-7;
				Judge.fr_tmp.clear(); Judge.to_tmp.clear();
				for(int i=0;i<size;i++) {
					int x1,y1,x2,y2;
					x1=T(32+i*2+0,s)>>3; y1=T(32+i*2+0,s)%8;
					x2=T(32+i*2+1,s)>>3; y2=T(32+i*2+1,s)%8;
					if(!(Pos.in_range(x1,y1)&&Pos.in_range(x2,y2)))
						return 1;
					Judge.fr_tmp.add(new Pos(x1,y1));
					Judge.to_tmp.add(new Pos(x2,y2));
				}
				f=Judge.reload(true);
				if(f!=0)
					return f;
			}catch(FileNotFoundException e) {
				//TODO:
				throw new RuntimeException(e);
			}
		}
		else
			return -1;
		return 0;
	}
	public static int load() {
		int tmp=load_();
		if(in!=null)
			in.close();
		if(tmp!=0)
			Judge.now=Judge.win=0;
		return tmp;
	}
}
