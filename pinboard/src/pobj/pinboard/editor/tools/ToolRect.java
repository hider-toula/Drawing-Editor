package pobj.pinboard.editor.tools;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolRect implements Tool{
	private double x, y;
	private ClipRect clip;
	private String action;

	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		
		x = e.getX();
		y = e.getY();
		action = "press";
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		double left, top, right, bottom;
		left = Math.min(x, e.getX());
		right = Math.max(x, e.getX());
		top = Math.min(y, e.getY());
		bottom = Math.max(y, e.getY());
		clip = new ClipRect(left, top, right, bottom, Color.AQUA);
		action = "drag";
		
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		double left, top, right, bottom;
		left = Math.min(x, e.getX());
		right = Math.max(x, e.getX());
		top = Math.min(y, e.getY());
		bottom = Math.max(y, e.getY());
		clip = new ClipRect(left, top, right, bottom, Color.AQUA);
		//i.getBoard().addClip(clip);
		CommandAdd commandAdd = new CommandAdd(i, clip);
		commandAdd.execute();
		i.getUndoStack().addCommand(commandAdd);
		action = "release";
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		// TODO Auto-generated method stub
		i.getBoard().draw(gc);
		if(action.equals("drag")) {
			gc.strokeRect(clip.getLeft(), clip.getTop(), clip.getRight() - clip.getLeft(), clip.getBottom() - clip.getTop());
		}

	}

	@Override
	public String getName(EditorInterface editor) {
		// TODO Auto-generated method stub
		return "Rectangle tool";
	}
	

}
