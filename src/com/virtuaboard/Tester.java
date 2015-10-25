package com.virtuaboard;

import java.io.IOException;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Vector;

/**
 * @author Vivian, Grace, Akarsh, JC
 * @version 10/23/15
 * 			
 * This class is used to test/run the actual app.
 */
public class Tester {
	public static void main(String[] args) {
		final TestGUI gui = new TestGUI();
		gui.setVisible(true);
		
		// Create a sample listener and controller
		SampleListener listener = new SampleListener();
		MyScriptListener myListener = new MyScriptListener(
				new VectorListener() {
					@Override
					public void onVectorReceived(Vector v) {
						//float x = v.getX();
						//float y = v.getY();
						//float z = v.getZ();
						//System.out.println(x + ";" + y + ";" + z);
						gui.updatePoints(v);
					}
				});
				
		Controller controller = new Controller();
		
		// Have the sample listener receive events from the controller
		controller.addListener(myListener);
		
		// Keep this process running until Enter is pressed
		System.out.println("Press Enter to quit...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Remove the sample listener when done
		controller.removeListener(listener);
	}
}