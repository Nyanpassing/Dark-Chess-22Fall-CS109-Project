package main.stream;

import main.kernel.Judge;
import main.kernel.Kernel;
import main.kernel.Press;
import main.kit.Pos;
import main.kit.Text;
import main.visual.Anime;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Network {
	static ServerSocket server_s;
	static Socket socket;
	static InputStream in;
	static OutputStream out;
	static boolean is_server;
	static boolean start(boolean type,String ip) {
		is_server=type;
		socket=new Socket();
		if(type) {
			try {
				server_s=new ServerSocket(894);
			}catch(IOException e) {
				throw new RuntimeException(e);
				//TODO:
			}
			try {
				server_s.setSoTimeout(200);
				while((!socket.isConnected())&&Kernel.loc==4) {
					try {
						socket=server_s.accept();
					}
					catch(Exception e) {}
				}
				if(Kernel.loc!=4)
					return false;
			}
			catch(Exception e) {
				throw new RuntimeException(e);
				//TODO:
			}
		}
		else {
			try {
				socket=new Socket(ip,894);
			}catch(IOException e) {
				return false;
				//TODO:
			}
		}
		try {
			in=socket.getInputStream();
			out=socket.getOutputStream();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
			//TODO:
		}
		return true;
	}
	public static int type,top=0;
	public static Pos pos=new Pos();
	public static boolean available() {
		try {
			for(;top<5;top++) {
				if(in.available()==0)
					return false;
				switch(top) {
					case 0:type=in.read();break;
					case 1:pos.x=in.read();break;
					case 2:pos.x|=(in.read()<<8);break;
					case 3:pos.y=in.read();break;
					case 4:pos.y|=(in.read()<<8);break;
				}
				if(type==39&&top==0)
					top--;
			}
		}catch(Exception e) {
			Kernel.dis=true;
			return false;
		}
		return true;
	}
	public static void write(int type,Pos pos) {
		try {
			out.write(type);
			out.write(pos.x%(1<<8));
			out.write(pos.x>>8);
			out.write(pos.y%(1<<8));
			out.write(pos.y>>8);
			out.flush();
		}catch(Exception e) {
			Kernel.dis=true;
		}
	}
	public static boolean check() {
		try {
			out.write(39);
			return true;
		}catch(Exception e) {
			Kernel.dis=true;
			return false;
		}
	}
	public static void exit() {
		try {
			if(in!=null) in.close();
			if(out!=null) out.close();
			if(socket!=null) socket.close();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		if(is_server) {
			try {
				if(server_s!=null) server_s.close();
				if(socket!=null) socket.close();
			}
			catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void create_a_server() {
		try {
			Text.s=InetAddress.getLocalHost().getHostAddress();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		Thread thread=new Thread() {
			@Override
			public void run() {
				if(Network.start(true,"")) {
					Pos seed=new Pos(Judge.rand.nextInt(60000),Judge.rand.nextInt(60000));
					Kernel.dis=false;
					write(233,seed);
					Judge.rand=new Random(seed.x*60000L+seed.y);
					Press.todo[7]=true;
					Kernel.mode=true;
					Kernel.now=1;
				}
			}
		};
		thread.start();
	}
	public static void link_the_server() {
		Thread thread=new Thread() {
			@Override
			public void run() {
				if(Network.start(false,Text.s)) {
					Kernel.dis=false;
					while((!Kernel.dis)&&(!available())) {}
					if(!Kernel.dis) {
						Judge.rand=new Random(pos.x*60000L+pos.y);
						top=0;
						Press.todo[7]=true;
						Kernel.mode=true;
						Kernel.now=0;
						return;
					}
				}
				Anime.A3(2,-1);
				Anime.A5(6,-1);
			}
		};
		thread.start();
	}
}