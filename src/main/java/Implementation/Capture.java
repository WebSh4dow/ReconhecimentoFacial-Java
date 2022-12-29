package Implementation;

import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_calib3d;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacpp.opencv_shape;
import org.bytedeco.javacpp.opencv_xfeatures2d;
import org.bytedeco.javacpp.helper.opencv_imgproc;
import org.bytedeco.javacpp.helper.opencv_core.AbstractMat;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;


public class Capture {

	@SuppressWarnings("resource")
	public static void capture() throws Exception {
		@SuppressWarnings("unused")
		KeyEvent tecla = null;
		OpenCVFrameConverter.ToMat convertMat = new OpenCVFrameConverter.ToMat();
		OpenCVFrameGrabber camera = new OpenCVFrameGrabber(0);

		camera.start();
		CascadeClassifier detectedFace = new CascadeClassifier();
		detectedFace.load("src/main/java/org/eiger/haarcascade_frontalface_alt.xml");
		
	
		
		CanvasFrame canvasFrame = new CanvasFrame("Preview", CanvasFrame.getDefaultGamma() / camera.getGamma());
		org.bytedeco.javacv.Frame captureFrame = null;
		Mat imgcolorful = new Mat();

		while ((captureFrame = camera.grab()) != null) {

			imgcolorful = convertMat.convert(captureFrame);

			Mat imgGrey = new Mat();
					
			org.bytedeco.javacpp.opencv_imgproc.cvtColor(imgcolorful, imgGrey,
			org.bytedeco.javacpp.opencv_imgproc.COLOR_BGRA2GRAY);
			
			
			RectVector facesDetectored = new RectVector();
			detectedFace.detectMultiScale(imgGrey, facesDetectored, 1.1, 2, 0, new Size(150, 150), new Size(500, 500));
			
			for (int i = 0; i < facesDetectored.size();i++) {
				Rect dadosFace = facesDetectored.get(0);
				org.bytedeco.javacpp.opencv_imgproc.rectangle(imgcolorful, dadosFace, new Scalar(0,0,255,0));
				
			}
			

			if (canvasFrame.isVisible()) {
				canvasFrame.showImage(captureFrame);
			}

		}
		canvasFrame.dispose();
		camera.stop();
	}

}
