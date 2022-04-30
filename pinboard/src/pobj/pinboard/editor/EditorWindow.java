package pobj.pinboard.editor;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.commands.Command;
import pobj.pinboard.editor.commands.CommandAdd;
import pobj.pinboard.editor.commands.CommandGroup;
import pobj.pinboard.editor.commands.CommandMove;
import pobj.pinboard.editor.commands.CommandUngroup;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolRect;
import pobj.pinboard.editor.tools.ToolSelection;


public class EditorWindow implements EditorInterface, ClipboardListener{
	private Board board;
	private Stage stage;
	private Tool tool;
	private Command command;
	private Selection selection;
	// UI
	private Label label;
	private MenuBar menuBar;
	private Menu fileMenu, editMenu, toolMenu;
	private MenuItem boxToolItem, ecllipseToolItem, imageToolItem, newItem, 
	closeItem, copyItem, deletItem, pasteItem, groupeItem, ungroupeItem, undoItem, redoItem;
	private Canvas canvas;
	private Button btnBox, btnEcllipse, btnImage, btnSelect;
	private ToolBar toolBar;
	private CommandStack stack;
	
	public EditorWindow(Stage stage) {
		this.stage = stage;
		board = new Board();
		selection = new Selection();
		Clipboard.getInstance().addListener(this);
		stack = new CommandStack();
		
		label = new Label("No tool selected");
		// MenuBar
		menuBar = new MenuBar();
		// Menus
		fileMenu = new Menu("File");
		editMenu = new Menu("Edit");
		toolMenu = new Menu("Tools");
		
		boxToolItem = new MenuItem("Box");
		ecllipseToolItem = new MenuItem("Eclipse");
		imageToolItem  = new MenuItem("Image");
		
		boxToolItem.setOnAction(e-> {
			tool = new ToolRect();
			label.textProperty().set(tool.getName(this));
			});
		
		ecllipseToolItem.setOnAction(e->{
			tool = new ToolEllipse();
			label.textProperty().set(tool.getName(this));
		});
		
		imageToolItem.setOnAction(e->{
			FileChooser chooser = new FileChooser();
			File file = chooser.showOpenDialog(stage);
			tool = new ToolImage(file);
			label.textProperty().set(tool.getName(this));
			chooser = null;
			file = null;
		});
		toolMenu.getItems().addAll(boxToolItem, ecllipseToolItem, imageToolItem);
		
		menuBar.getMenus().addAll(fileMenu, editMenu, toolMenu);
		//----------------------------------------
		// ToolBar
		toolBar = new ToolBar();
		
		btnBox = new Button("Box");

		btnEcllipse = new Button("Ellipse");

		btnImage = new Button("Image");
		
		btnSelect = new Button("Select");
		
		toolBar.getItems().addAll(btnBox, btnEcllipse, btnImage, btnSelect);
		
		newItem = new MenuItem("New");
		closeItem = new MenuItem("Close");
		fileMenu.getItems().addAll(newItem, closeItem);
		newItem.setOnAction((e)-> {
			new EditorWindow(new Stage()).clipboardChanged();;
		});		
		closeItem.setOnAction((e)-> {
			stage.close();
			Clipboard.getInstance().removeListener(this);
			});
		//--------------------------------------
		
		
		Separator separator = new Separator();
		
		
		btnEcllipse.setOnMouseClicked((e)->{
			tool = new ToolEllipse();
			label.textProperty().set(tool.getName(this));
		
		});
		btnBox.setOnMouseClicked((e)->{
			tool = new ToolRect();
			label.textProperty().set(tool.getName(this));
		});
		
		
		btnImage.setOnMouseClicked((e)->{
			FileChooser chooser = new FileChooser();
			File file = chooser.showOpenDialog(stage);
			tool = new ToolImage(file);
			label.textProperty().set(tool.getName(this));
		});
		
		btnSelect.setOnMouseClicked(e->{
			tool = new ToolSelection();
			label.textProperty().set(tool.getName(this));
		});
		
		copyItem = new MenuItem("Copy");
		deletItem = new MenuItem("Delet");
		pasteItem = new MenuItem("Paste");
		groupeItem = new MenuItem("Groupe");
		ungroupeItem = new MenuItem("Ungroup");
		undoItem = new MenuItem("Undo");
		redoItem = new MenuItem("Redo");
		pasteItem.setDisable(true);
		
		editMenu.getItems().addAll(copyItem, pasteItem, deletItem, groupeItem, ungroupeItem, undoItem, redoItem);
		copyItem.setOnAction(e->{
			Clipboard.getInstance().copyToClipboard(selection.getContents());
			//getBoard().addClip(Clipboard.getInstance().copyFromClipboard());

		});
		pasteItem.setOnAction(e->{
			//getBoard().addClip(Clipboard.getInstance().copyFromClipboard());
			command = new CommandAdd(this, Clipboard.getInstance().copyFromClipboard());
			stack.addCommand(command);
			command.execute();
			getBoard().draw(canvas.getGraphicsContext2D());
		});
		deletItem.setOnAction(e->{
			getBoard().removeClip(selection.getContents());
			Clipboard.getInstance().clear();
			getBoard().draw(canvas.getGraphicsContext2D());
		});
		
		groupeItem.setOnAction(e->{
			//board.addClip(group);
			command = new CommandGroup(this, selection.getContents());
			command.execute();
			stack.addCommand(command);
			draw(canvas.getGraphicsContext2D());
			
		});
		
		ungroupeItem.setOnAction(e->{
			for(Clip clip: selection.getContents()) {
				if( clip instanceof ClipGroup) {
					command = new CommandUngroup(this, (ClipGroup)clip);
					command.execute();
					stack.addCommand(command);
					break;
					
				}
			}
		
			draw(canvas.getGraphicsContext2D());
		});
		
		undoItem.setOnAction(e->{
			stack.undo();
			draw(canvas.getGraphicsContext2D());
		});
		
		redoItem.setOnAction(e->{
			stack.redo();
			draw(canvas.getGraphicsContext2D());
			});
		//********************************************
		canvas = new Canvas(800,600);
		canvas.setOnMousePressed((e)->{
			try {
				tool.press(this, e);
				draw(canvas.getGraphicsContext2D());
			}catch (NullPointerException ex) {
				// TODO: handle exception
			}
			
		});
		
		canvas.setOnMouseDragged((e)->{
			try{
			tool.drag(this, e);
			draw(canvas.getGraphicsContext2D());
			}
			catch (NullPointerException ex) {
				// TODO: handle exception
			}
		});
		canvas.setOnMouseReleased((e)->{
			try{
				tool.release(this, e);
				draw(canvas.getGraphicsContext2D());
			}
			catch (NullPointerException y) {
				// TODO: handle exception
			}
			
		});
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(menuBar, toolBar, canvas, separator, label);
		stage.setScene(new Scene(vBox));
		stage.setTitle("PinBoard-untitled");
		stage.show();

	}

	@Override
	public Board getBoard() {
		// TODO Auto-generated method stub
		return board;
	}

	@Override
	public Selection getSelection() {
		// TODO Auto-generated method stub
		return selection;
	}

	@Override
	public CommandStack getUndoStack() {
		// TODO Auto-generated method stub
		return stack;
	}
	public void draw(GraphicsContext gc) {
		tool.drawFeedback(this, gc);
	}

	@Override
	public void clipboardChanged() {
		// TODO Auto-generated method stub
		pasteItem.setDisable(Clipboard.getInstance().isEmpty());
		
	}
		
	

}
