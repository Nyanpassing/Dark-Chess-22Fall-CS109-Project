package main.stream;

import main.kernel.Kernel;
import main.kernel.Press;
import main.visual.Pic;

import java.io.*;

public class Config {
	static File file;
	static InputStream in;
	static OutputStream out;
	public static void init() {
		file=new File("config");
		try {
			in=new FileInputStream(file);
			int tmp=in.read();
			Press.able[13]=(tmp!=1);
			tmp=in.read();
			Press.able[19]=(tmp==1);
			Kernel.scale=(Press.able[19])?3:2;
			tmp=in.read();
			Press.able[20]=(tmp!=1);
			if(!Press.able[20])
				Pic.change_characters_visibility();
			in.close();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	static int fc=0;
	public static void exit() {
		if(fc!=0) {
			fc--;
			return;
		}
		fc=50;
		try {
			out=new FileOutputStream(file);
			out.write(Press.able[13]?0:1);
			out.write(Press.able[19]?1:0);
			out.write(Press.able[20]?0:1);
			out.close();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		System.exit(0);
	}
}