package com.virtuaboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.leapmotion.leap.Vector;
import com.tejashah.ws.MyScriptCloud;
import com.tejashah.ws.api.Point;
import com.tejashah.ws.api.Stroke;

public class TestGUI extends JFrame {
	static final String myScriptApplicationKey = "118891a7-8d0d-4f6e-9c62-bf9919e9c92c";
	static final String altMyScriptApplicationKey = "16cb6bf8-8c08-4de8-858d-f66348a084f0";
	static final String myScriptRecognitionCloudURL = "http://cloud.myscript.com/api/v3.0/recognition/rest/text/doSimpleRecognition.json";
	
	Dimension d = new Dimension(645, 530);
	int drawWidth = 620, drawHeight = 420;
	Dimension d1 = new Dimension(drawWidth, drawHeight);
	MyPanel panel;
	TextField resultText;
	//TextArea history;
	
	static final int THRESHOLD = 30;
	static final int FRAME_RECORD_COUNT = 1;
	
	MyScriptCloud cloudSDK;
	
	public TestGUI() {
		super("VirtuaBoard");
		
		cloudSDK = new MyScriptCloud(myScriptRecognitionCloudURL, myScriptApplicationKey);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setSize(d);
		
		//setBackground(Color.RED);
		panel = new MyPanel();
		add(panel);
		
		final Font defaultFont = new Font("Arial Bold", Font.PLAIN, 36);
		final Color background = Color.BLACK;
		final Color foreground = Color.GREEN;
		
		resultText = new TextField(30);
		resultText.setFont(defaultFont);
		resultText.setBackground(background);
		resultText.setForeground(foreground);
		resultText.setEditable(false);
		add(resultText);
		
		/*history = new TextArea(4, 30);
		history.setFont(defaultFont);
		history.setEditable(false);
		add(history);*/
	}
	
	public class MyPanel extends JPanel {
		int count = 0;
		ArrayList<int[]> points = new ArrayList<>();
		boolean isDrawing = false;
		int x, y;
		
		ArrayList<Point> msPoints = new ArrayList<>();
		
		public MyPanel() {
			super();
			setSize(d1);
			setPreferredSize(d1);
			setBackground(Color.PINK);
			
			addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						points.clear();
						
						if (true) {
							Stroke stroke = new Stroke(msPoints.toArray(new Point[0]));
							try {
								String result = cloudSDK.recognize(new Stroke[] {stroke});
								resultText.setText(resultText.getText() + result);
								//history.append(result + "\n");
								System.out.println("Result: " + result);
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
						
						msPoints.clear();
					}
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			for (int i = 1; i < points.size(); i++) {
				int x1 = points.get(i-1)[0];
				int y1 = (points.get(i-1)[1]);
				
				int x2 = points.get(i)[0];
				int y2 = (points.get(i)[1]);
				
				g.drawLine(x1, y1, x2, y2);
			}
			
			g.drawOval(x, y, 3, 3);
		}
		
		public void updatePoints(Vector v) {
			int x = (int) v.getX() + this.getX() + (this.getWidth() / 2);
			int y = this.getHeight() - ((int) v.getY() + this.getY() + (int) (this.getHeight() / 6.5));
			
			this.x = x;
			this.y = y;
			
			if (v.getZ() <= THRESHOLD && v.getZ() >= -THRESHOLD) { //tis within range
				if (!isDrawing) {
					isDrawing = true;
					points.clear();
				}
				
				if (count == FRAME_RECORD_COUNT) {
					int[] point = {x, y};
					points.add(point);
					count = 0;
				} else {
					count++;
				}
			} else {
				if (isDrawing) {
					isDrawing = false;
					
					for (int[] arr : points) {
						Point p = new Point(arr[0], arr[1]);
						msPoints.add(p);
					}
				}
			}
			
			this.repaint();
		}
	}
	
	public void updatePoints(Vector v) {
		panel.updatePoints(v);
	}
}