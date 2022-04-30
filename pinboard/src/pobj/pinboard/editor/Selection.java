package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;

public class Selection {
	private List<Clip> clips;
	public Selection() {
		// TODO Auto-generated constructor stub
		clips = new ArrayList<>();
	}
	
	public void select(Board board, double x, double y) {
		clear();
		for(Clip clip: board.getContents()) {
			if(clip.isSelected(x, y)) {
				clips.add(clip);
				break;
			}
		}
	}
	
	public void toggleSelect(Board board, double x, double y){
		
		for(Clip clip: board.getContents()) {
			if (clip.isSelected(x, y)) {
				if (clips.contains(clip)) {
					clips.remove(clip);
				}else {
					clips.add(clip);
				}
			}
		}
	}
	
	public void clear() {
		clips = new ArrayList<Clip>();
		
	}
	public List<Clip> getContents(){
		return new ArrayList<Clip>(clips);
		
	}
	
	public void drawFeedback(GraphicsContext gc) {

		gc.setStroke(Color.BLUE);
		for(Clip clip: clips) {
			gc.strokeRect(clip.getLeft(), clip.getTop(),
					clip.getRight() - clip.getLeft(),
					clip.getBottom() - clip.getTop());
		}
	}

}
