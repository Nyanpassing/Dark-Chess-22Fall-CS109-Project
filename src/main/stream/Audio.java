package main.stream;

import main.kernel.Press;

import javax.sound.sampled.*;
import java.io.File;

public class Audio {
	static Thread play;
	public static void play(String name) {
		if(!Press.able[13])
			return;
		if(play!=null&&play.isAlive())
			play.interrupt();
		play=new Thread() {
			@Override
			public void run() {
				AudioInputStream stream;
				AudioFormat format;
				try {
					stream=AudioSystem.getAudioInputStream(new File("sound/"+name+".wav"));
				}catch(Exception e) {
					throw new RuntimeException(e);
				}
				format=stream.getFormat();
				DataLine.Info info=new DataLine.Info(SourceDataLine.class,format,AudioSystem.NOT_SPECIFIED);
				SourceDataLine line=null;
				byte buf[]=new byte[1024];
				int cnt=0;
				try {
					line=(SourceDataLine)AudioSystem.getLine(info);
					line.open(format);
					line.start();
					while(!isInterrupted()) {
						cnt=stream.read(buf,0,buf.length);
						if(cnt==-1)
							break;
						line.write(buf,0,cnt);
					}
				}catch(Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
		play.start();
	}
	public static void bgm_start() {
		Thread thread=new Thread() {
			@Override
			public void run() {
				AudioInputStream stream;
				AudioFormat format;
				try {
					stream=AudioSystem.getAudioInputStream(new File("sound/bgm_start.wav"));
				}catch(Exception e) {
					throw new RuntimeException(e);
				}
				format=stream.getFormat();
				DataLine.Info info=new DataLine.Info(SourceDataLine.class,format,AudioSystem.NOT_SPECIFIED);
				SourceDataLine line=null;
				byte buf[]=new byte[1024];
				int cnt=0;
				try {
					line=(SourceDataLine)AudioSystem.getLine(info);
					line.open(format);
					line.start();
					while(Press.able[14]) {
						while(Press.able[14]) {
							cnt=stream.read(buf,0,buf.length);
							if(cnt==-1)
								break;
							line.write(buf,0,cnt);
						}
						stream=AudioSystem.getAudioInputStream(new File("sound/bgm_loop.wav"));
					}
				}catch(Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
		thread.start();
	}
}
