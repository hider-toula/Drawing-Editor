package pobj.pinboard.editor.tools;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolImage implements Tool{
	private double x, y;
	private ClipImage image;
	private File file;
	
	public ToolImage(File file) {
		this.file = file;
	}
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		image = new ClipImage(x, y, file);
		
		
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		image.setLeft(x);
		image.setTop(y);

	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		image = new ClipImage(x, y, file);
		CommandAdd commandAdd = new CommandAdd(i, image);
		commandAdd.execute();
		//i.getBoard().addClip(image);
		i.getUndoStack().addCommand(commandAdd);
		
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		// TODO Auto-generated method stub
		i.getBoard().draw(gc);		
		//gc.clearRect(image.getLeft(), image.getTop(), image.getImage().getWidth(), image.getImage().getHeight());
		gc.drawImage(image.getImage(), image.getLeft(), image.getTop());
	}

	@Override
	public String getName(EditorInterface editor) {
		// TODO Auto-generated method stub
		return "Image tool";
	}

}
