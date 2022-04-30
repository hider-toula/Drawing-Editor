package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipEllipse extends AbstractClip {
	private double cx, cy, rx, ry;

	public ClipEllipse(double left, double top, double right, double bottom, Color color) {
		super(left, top, right, bottom, color);
		cx = (left + right) / 2;
		cy = (top + bottom) /2;
		rx = (right - left) / 2;
		ry = (bottom - top) / 2;
		
	}
	
	@Override
	public boolean isSelected(double x, double y) {
		// TODO Auto-generated method stub
		cx = (getLeft() + getRight()) / 2;
		cy = (getTop() + getBottom()) /2;
		rx = (getRight() - getLeft()) / 2;
		ry = (getBottom() - getTop()) / 2;
		return ((Math.pow(((x - cx) / rx), 2)) + (Math.pow(((y - cy)/ry), 2))) <= 1;}
	
	@Override
	public void draw(GraphicsContext ctx) {
		// TODO Auto-generated method stub
		ctx.setFill(Color.GRAY);
		//ctx.fillRect(getLeft(), getTop(), getRight()-getLeft() , getBottom()-getTop());
		ctx.setFill(getColor());
		ctx.fillOval(getLeft(), getTop(), rx * 2 , ry  * 2);
		
	}

	@Override
	public Clip copy() {
		// TODO Auto-generated method stub
		return new ClipEllipse(getLeft(), getTop(), getRight(), getBottom(), getColor());
	}

}
