package pobj.pinboard.editor.commands;
import java.util.ArrayList;
import java.util.List;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandMove implements Command{
	private EditorInterface editor;
	private List<Clip> clips;
	private double dx, dy;
	
	public CommandMove(EditorInterface editorInterface, Clip toAdd, double dx, double dy) {
		// TODO Auto-generated constructor stub
		editor = editorInterface;
		clips = new ArrayList<Clip>();
		clips.add(toAdd);
		this.dx = dx;
		this.dy = dy;
		
	}
	
	public CommandMove(EditorInterface editorInterface, List<Clip> list, double dx, double dy) {
		// TODO Auto-generated constructor stub
		editor = editorInterface;
		clips = new ArrayList<Clip>();
		clips.addAll(list);
		this.dx = dx;
		this.dy = dy;
		
	}
	

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		for(Clip clip: clips) {
			if (editor.getBoard().getContents().contains(clip)) {
				clip.move(dx, dy);
			}
		}
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		for(Clip clip: clips) {
			if (editor.getBoard().getContents().contains(clip)) {
				clip.move(-dx, -dy);
			}
		}
	}

}
