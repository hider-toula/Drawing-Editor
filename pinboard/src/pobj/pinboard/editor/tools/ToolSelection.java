package pobj.pinboard.editor.tools;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandMove;

// it should work !!!

public class ToolSelection implements Tool{
	private double x, y;
	

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		if (!e.isShiftDown()) {
			i.getSelection().select(i.getBoard(), x, y);
		}else {
			i.getSelection().toggleSelect(i.getBoard(), x, y);
		}
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		double dx, dy;
		dx = e.getX() - x;
		dy = e.getY() - y;
		x = e.getX();
		y = e.getY();
		CommandMove move = new CommandMove(i, i.getSelection().getContents(), dx, dy);
		move.execute();
		i.getUndoStack().addCommand(move);
		
		
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub

		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		// TODO Auto-generated method stub
		i.getBoard().draw(gc);
		i.getSelection().drawFeedback(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		// TODO Auto-generated method stub
		return "Selection tool";
	}

}
