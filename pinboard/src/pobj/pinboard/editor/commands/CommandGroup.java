package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandGroup implements Command{
	private EditorInterface editor;
	private ClipGroup group;
	private List<Clip> clips;
	
	public CommandGroup(EditorInterface editorInterface, Clip clip) {
		clips = new ArrayList<Clip>();
		clips.add(clip);
		editor = editorInterface;	
	}
	
	public CommandGroup(EditorInterface editorInterface, List<Clip> list) {
		clips = new ArrayList<Clip>();
		editor = editorInterface;
		clips.addAll(list);
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		group = new ClipGroup();
		for(Clip clip:clips) {
			group.addClip(clip);
			editor.getBoard().removeClip(clip);
		}
		editor.getBoard().getContents().add(group);
		
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		editor.getBoard().removeClip(group);
		editor.getBoard().getContents().addAll(group.getClips());
		
	}

}
