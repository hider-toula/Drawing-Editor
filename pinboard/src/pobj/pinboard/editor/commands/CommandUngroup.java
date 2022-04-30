package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandUngroup implements Command{
	private EditorInterface editor;
	private List<ClipGroup> clips;
	private ClipGroup  group;
	public CommandUngroup(EditorInterface editorInterface, ClipGroup group) {
		clips = new ArrayList<ClipGroup>();
		clips.add(group);
		editor = editorInterface;
		this.group = group;
	}
	
	public CommandUngroup(EditorInterface editorInterface, List<ClipGroup> list) {
		clips = new ArrayList<ClipGroup>();
		clips.addAll(list);
		editor = editorInterface;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("Ungroup");
		/*for(ClipGroup group: clips) {
			editor.getBoard().removeClip(group);
			System.out.println(group.getClips().size());
			editor.getBoard().addClip(group.getClips());
		}*/
		editor.getBoard().addClip(group.getClips());
		editor.getBoard().removeClip(group);
		System.out.println(group.getClips().size());
		
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		editor.getBoard().addClip(group);
		editor.getBoard().removeClip(group.getClips());
	}

}
