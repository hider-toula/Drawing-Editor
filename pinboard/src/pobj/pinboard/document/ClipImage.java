package pobj.pinboard.document;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ClipImage extends AbstractClip{
	private File  path;
	private Image image;
	
	public  ClipImage(double left, double top, File path ) {
		// TODO Auto-generated constructor stub
		super(left, top, 0,0, Color.BLUE);
		try {
			
			this.path = path;
			image = new Image(new FileInputStream(path.getAbsoluteFile()));
			setBottom(top + image.getWidth());
			setRight(left + image.getHeight());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
	}

	@Override
	public void draw(GraphicsContext ctx) {
		// TODO Auto-generated method stub
		//ctx.clearRect(getLeft(), getTop(), image.getWidth(), image.getHeight());
		//ctx.strokeRect(getLeft()-5, getTop()-5, image.getWidth()+5, image.getHeight()+5 );
		ctx.drawImage(image, getLeft(), getTop());
		
	}

	@Override
	public Clip copy() {
		// TODO Auto-generated method stub
		return new ClipImage(getLeft(), getTop(),this.path);
	}

	public Image getImage() {
		return image;
	}

}
