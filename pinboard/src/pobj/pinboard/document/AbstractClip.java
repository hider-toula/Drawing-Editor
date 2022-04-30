package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class AbstractClip implements Clip {
	private Color color;
	private double left, top, right, bottom;
	
	public  AbstractClip(double left, double top, double right, double bottom, Color color) {
		// TODO Auto-generated constructor stub
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.color = color;
	}
	
	@Override
	public double getBottom() {
		// TODO Auto-generated method stub
		return bottom;
	}

	@Override
	public abstract void draw(GraphicsContext ctx);

	@Override
	public double getTop() {
		// TODO Auto-generated method stub
		return top;
	}

	@Override
	public double getLeft() {
		// TODO Auto-generated method stub
		return left;
	}

	@Override
	public double getRight() {
		// TODO Auto-generated method stub
		return right;
	}

	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		// TODO Auto-generated method stub
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		
	}

	@Override
	public void move(double x, double y) {
		// TODO Auto-generated method stub
		left += x;
		top += y;
		right += x;
		bottom += y;
		
		
	}

	@Override
	public boolean isSelected(double x, double y) {
		// TODO Auto-generated method stub
		if((left<= x && x<= right) && (top <= y && y <= bottom)) return true;
		return false;
	}

	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		color = c;
		
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public abstract Clip copy();

	public void setLeft(double left) {
		this.left = left;
	}

	public void setTop(double top) {
		this.top = top;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public void setBottom(double bottom) {
		this.bottom = bottom;
	}
	
	
}
