package pobj.pinboard.editor.tools;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolEllipse implements Tool{
	private double x, y;
	private ClipEllipse ellipse;
	private String action;

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		action = "presse";
		
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		double left, top, right, bottom;
		left = Math.min(x, e.getX());
		right = Math.max(x, e.getX());
		top = Math.min(y, e.getY());
		bottom = Math.max(y, e.getY());
		action = "drag";
		ellipse = new ClipEllipse(left, top, right, bottom, Color.GREEN);
		
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		double left, top, right, bottom;
		left = Math.min(x, e.getX());
		right = Math.max(x, e.getX());
		top = Math.min(y, e.getY());
		bottom = Math.max(y, e.getY());
		action = "release";
		ellipse = new ClipEllipse(left, top, right, bottom, Color.GREEN);
		//i.getBoard().addClip(ellipse);
		CommandAdd commandAdd = new CommandAdd(i, ellipse);
		commandAdd.execute();
		i.getUndoStack().addCommand(commandAdd);
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		// TODO Auto-generated method stub
		i.getBoard().draw(gc);
		if (action.contentEquals("drag")) {
			gc.strokeOval(ellipse.getLeft(), ellipse.getTop(), ellipse.getRight() - ellipse.getLeft(), ellipse.getBottom() - ellipse.getTop());
		}
		
		
	}

	@Override
	public String getName(EditorInterface editor) {
		// TODO Auto-generated method stub
		return "Ellipse tool";
	}

}
