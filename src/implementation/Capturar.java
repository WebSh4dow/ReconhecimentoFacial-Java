package implementation;

import java.awt.Frame;
import java.awt.event.KeyEvent;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class Capturar {
	
	public static void capture() throws Exception {
		KeyEvent tecla = null;
		OpenCVFrameConverter.ToMat convertMat = new OpenCVFrameConverter.ToMat();
		OpenCVFrameGrabber camera = new OpenCVFrameGrabber(0);
		camera.start();
		
		CanvasFrame canvasFrame = new CanvasFrame("Preview",CanvasFrame.getDefaultGamma()/camera.getGamma());
		org.bytedeco.javacv.Frame captureFrame = null;
		
		while ((captureFrame = camera.grab()) !=null) {
			if (canvasFrame.isVisible()) {
				canvasFrame.showImage(captureFrame);
			}
			
		}
		canvasFrame.dispose();
		camera.stop();
	}
}
