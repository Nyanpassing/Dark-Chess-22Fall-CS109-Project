package main;

import main.kernel.Kernel;
import main.kernel.Press;
import main.kit.Text;
import main.stream.Audio;
import main.stream.Config;
import main.visual.Pic;

import javax.swing.*;

public class Launcher {
	public static JFrame win=new JFrame();
	public static Kernel kernel;
	public static void start() {
		Press.init2();
		Config.init();
		Press.init1();
		Text.init();

		kernel=new Kernel();
		win.setTitle("Dark Chess");
		win.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		int wt=Kernel.win_w*Kernel.scale,ht=Kernel.win_h*Kernel.scale;
		win.setSize(wt,ht);
		win.add(kernel);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(win);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}

		Press.init3();

		Audio.play("click");

		win.setIconImage(Pic.icon);
		win.setVisible(true);
		win.setSize(wt*2-kernel.getWidth(),ht*2-kernel.getHeight());
		win.setResizable(false);

		kernel.start();
		Config.exit();
	}
}
