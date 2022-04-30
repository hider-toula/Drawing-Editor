package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipGroup extends AbstractClip implements Composite {
	private List<Clip> clips;
	public ClipGroup() {
		// TODO Auto-generated constructor stub
		super(9999, 9999, -9999, -9999, Color.TRANSPARENT);
		clips = new ArrayList<Clip>();
		
	}

	@Override
	public List<Clip> getClips() {
		// TODO Auto-generated method stub
		return  new ArrayList<Clip>(clips);
	}
	
	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		// TODO Auto-generated method stub
		setLeft(getLeft() + left);
		setTop(getTop() + top);
		setRight(right + getRight());
		setBottom(bottom  + getBottom());
	}
	
	@Override
	public void move(double x, double y) {
		// TODO Auto-generated method stub
		super.move(x, y);
		for(Clip clip: clips) {
			clip.move(x, y);
		}
	}

	@Override
	public void addClip(Clip toAdd) {
		// TODO Auto-generated method stub
		clips.add(toAdd);
		double left = getLeft(), top=getTop(), right=getRight(), bottom=getBottom();
		for(Clip clip: clips) {
			left = Math.min(left, clip.getLeft());
			top = Math.min(top, clip.getTop());
			right = Math.max(right, clip.getRight());
			bottom = Math.max(bottom, clip.getBottom());
		}
		setBottom(bottom);
		setLeft(left);
		setTop(top);
		setRight(right);
		
	}

	@Override
	public void removeClip(Clip toRemove) {
		// TODO Auto-generated method stub
		clips.remove(toRemove);
		double left = 9999, top=9999, right=-9999, bottom=-9999;
		for(Clip clip: clips) {
			left = Math.min(left, clip.getLeft());
			top = Math.min(top, clip.getTop());
			right = Math.max(right, clip.getRight());
			bottom = Math.max(bottom, clip.getBottom());
		}
		setBottom(bottom);
		setLeft(left);
		setTop(top);
		setRight(right);
		
	}

	@Override
	public void draw(GraphicsContext ctx) {
		// TODO Auto-generated method stub
		for(Clip clip:clips) {
			clip.draw(ctx);
		}
		
	}

	@Override
	public Clip copy() {
		// TODO Auto-generated method stub
		ClipGroup group = new ClipGroup();
		for(Clip clip: clips) {
			group.addClip(clip.copy());
		}
		return group;
		
	}

}
