package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandAdd implements Command{
	private EditorInterface editor;
	private List<Clip> clips ;
	public CommandAdd(EditorInterface editorInterface, Clip toAdd) {
		// TODO Auto-generated constructor stub
		editor = editorInterface;
		clips = new ArrayList<Clip>();
		clips.add(toAdd);
	}

	public CommandAdd(EditorInterface editorInterface, List<Clip> list) {
		// TODO Auto-generated constructor stub
		editor = editorInterface;
		clips = new ArrayList<Clip>();
		clips.addAll(list);
	}


	@Override
	public void execute() {
		// TODO Auto-generated method stub
		for(Clip clip: clips) {
			editor.getBoard().addClip(clip);
		}
		
	}
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		for(Clip clip: clips) {
			editor.getBoard().removeClip(clip);
		}
		
	}}
