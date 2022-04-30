package pobj.pinboard.editor;

import java.util.Stack;

import pobj.pinboard.editor.commands.Command;

public class CommandStack {
	private Stack<Command> undo, redo;
	
	public CommandStack() {
		// TODO Auto-generated constructor stub
		undo = new Stack<Command>();
		redo = new Stack<Command>();
	}
	
	public void undo() {
		Command cmd = undo.pop();
		cmd.undo();
		redo.add(cmd);
		
	}
	
	public void redo() {
		Command cmd = redo.pop();
		cmd.execute();
		undo.add(cmd);
	}
	
	public boolean isUndoEmpty() { 
		return undo.isEmpty();
	}
	
	public boolean isRedoEmpty() {
		return redo.isEmpty();
	}
	
	public void addCommand(Command c) {
		undo.add(c);
		redo.clear();
	}
	
	

}
