package main.kit;

public class Pos {
	public int x,y;
	public Pos() { x=y=0; }
	public Pos(int x_,int y_) {
		x=x_; y=y_;
	}
	@Override
	public Pos clone() {
		return new Pos(this.x,this.y);
	}
	public static boolean in_range(int x,int y) {
		return x<4&&0<=x&&y<8&&0<=y;
	}
	public static boolean in_range(Pos p) {
		return in_range(p.x,p.y);
	}
}