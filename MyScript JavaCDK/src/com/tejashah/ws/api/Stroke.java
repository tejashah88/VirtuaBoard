package com.tejashah.ws.api;

public class Stroke {

	private Point[] coords;

	// cache
	private Box bbox;

	public Stroke(Point... coords) {
		if (coords.length == 0) {
			throw new IllegalArgumentException();
		}
		for (Point p : coords) {
			if (p == null) {
				throw new IllegalArgumentException();
			}
		}
		this.coords = coords.clone();
	}

	public Box getBoundingBox() {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		if (bbox == null) {
			for (Point p : coords) {
				int x = (int)p.x;
				int y = (int)p.y;
				minX = Math.min(minX, x);
				maxX = Math.max(maxX, x);
				minY = Math.min(minY, y);
				maxY = Math.max(maxY, y);

			}
			bbox = new Box(minX, minY, maxX - minX, maxY - minY);
		}
		return bbox;
	}

	public Point[] getPoints() {
		return coords;
	}

	public float[] getX() {
		float[] res = new float[coords.length];
		for (int i = coords.length; --i >= 0;) {
			res[i] = coords[i].x;
		}
		return res;
	}

	public float[] getY() {
		float[] res = new float[coords.length];
		for (int i = coords.length; --i >= 0;) {
			res[i] = coords[i].y;
		}
		return res;
	}

}