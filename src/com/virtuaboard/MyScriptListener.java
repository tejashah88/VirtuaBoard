package com.virtuaboard;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger.Type;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Tool;
import com.leapmotion.leap.Vector;

/**
 * @author Tejas, and whoever is working on this
 * @version 10/23/15
 * 
 * This class is currently based on the SampleListener, which prints a lot of debug motion.
 * This class should track the change between the locations of the tip of the Finger(s)/Tools(s). 
 */

public class MyScriptListener extends Listener {
	private VectorListener vlisten;
	
	public MyScriptListener(VectorListener vl) {
		vlisten = vl;
	}
	
	@Override
	public void onInit(Controller controller) {
		System.out.println("Initialized");
	}
	
	@Override
	public void onConnect(Controller controller) {
		System.out.println("Connected");
		//controller.bugReport().beginRecording();
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}
	
	@Override
	public void onDisconnect(Controller controller) {
		// Note: not dispatched when running in a debugger.
		//controller.bugReport().endRecording();
		System.out.println("Disconnected");
	}
	
	@Override
	public void onExit(Controller controller) {
		System.out.println("Exited");
	}
	
	@Override
	public void onFrame(Controller controller) {
		// Get the most recent frame and report some basic information
		Frame frame = controller.frame();
		
		final boolean usingHand = true;
		
		if (usingHand) {
			if (frame.hands().count() == 0) {
				return;
			}
			
			Bone bone = frame.hands().get(0).fingers().fingerType(Type.TYPE_INDEX).get(0).bone(Bone.Type.TYPE_DISTAL);
			
			//Finger finger = hand.fingers().fingerType(Type.TYPE_INDEX).get(0);
			//Bone bone = hand.fingers().fingerType(Type.TYPE_INDEX).get(0).bone(Bone.Type.TYPE_DISTAL);
			Vector boneEnd = bone.center();
			vlisten.onVectorReceived(boneEnd);
		} else {
			if (frame.tools().count() == 0) {
				return;
			}
			
			Tool tool = frame.tools().get(0);
			Vector end = tool.tipPosition();
			vlisten.onVectorReceived(end);
		}
	
		/*
		// Get tools
		for (Tool tool : frame.tools()) {
			System.out.println("  Tool id: " + tool.id() + ", position: "
					+ tool.tipPosition() + ", direction: " + tool.direction());
		}
		
		GestureList gestures = frame.gestures();
		for (int i = 0; i < gestures.count(); i++) {
			Gesture gesture = gestures.get(i);
			
			switch (gesture.type()) {
				case TYPE_CIRCLE:
					CircleGesture circle = new CircleGesture(gesture);
					
					// Calculate clock direction using the angle between circle
					// normal and pointable
					String clockwiseness;
					if (circle.pointable().direction()
							.angleTo(circle.normal()) <= Math.PI / 2) {
						// Clockwise if angle is less than 90 degrees
						clockwiseness = "clockwise";
					} else {
						clockwiseness = "counterclockwise";
					}
					
					// Calculate angle swept since last frame
					double sweptAngle = 0;
					if (circle.state() != State.STATE_START) {
						CircleGesture previousUpdate = new CircleGesture(
								controller.frame(1).gesture(circle.id()));
						sweptAngle = (circle.progress()
								- previousUpdate.progress()) * 2 * Math.PI;
					}
					
					System.out.println("  Circle id: " + circle.id() + ", "
							+ circle.state() + ", progress: "
							+ circle.progress() + ", radius: " + circle.radius()
							+ ", angle: " + Math.toDegrees(sweptAngle) + ", "
							+ clockwiseness);
					break;
				case TYPE_SWIPE:
					SwipeGesture swipe = new SwipeGesture(gesture);
					System.out.println("  Swipe id: " + swipe.id() + ", "
							+ swipe.state() + ", position: " + swipe.position()
							+ ", direction: " + swipe.direction() + ", speed: "
							+ swipe.speed());
					break;
				case TYPE_SCREEN_TAP:
					ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
					System.out.println("  Screen Tap id: " + screenTap.id()
							+ ", " + screenTap.state() + ", position: "
							+ screenTap.position() + ", direction: "
							+ screenTap.direction());
					break;
				case TYPE_KEY_TAP:
					KeyTapGesture keyTap = new KeyTapGesture(gesture);
					System.out.println("  Key Tap id: " + keyTap.id() + ", "
							+ keyTap.state() + ", position: "
							+ keyTap.position() + ", direction: "
							+ keyTap.direction());
					break;
				default:
					System.out.println("Unknown gesture type.");
					break;
			}
		}
		
		if (!frame.hands().isEmpty() || !gestures.isEmpty()) {
			System.out.println();
		}*/
	}
}