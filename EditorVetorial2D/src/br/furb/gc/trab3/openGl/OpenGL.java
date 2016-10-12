package br.furb.gc.trab3.openGl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class OpenGL implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	protected GL gl;
	private GLU glu;
	protected GLAutoDrawable glDrawable;

	private float minX;
	private float maxX;
	private float minY;
	private float maxY;

	private int antigoX;
	private int antigoY;

	protected boolean editingMode = true;

	public OpenGL() {

	}

	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(getMinY(), getMaxX(), getMinY(), getMaxY());

	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
	}

	public GL getGl() {
		return gl;
	}

	public void setGl(GL gl) {
		this.gl = gl;
	}

	public GLU getGlu() {
		return glu;
	}

	public void setGlu(GLU glu) {
		this.glu = glu;
	}

	public GLAutoDrawable getGlDrawable() {
		return glDrawable;
	}

	public void setGlDrawable(GLAutoDrawable glDrawable) {
		this.glDrawable = glDrawable;
	}

	public float getMinX() {
		return minX;
	}

	public void setMinX(float minX) {
		this.minX = minX;
	}

	public float getMaxX() {
		return maxX;
	}

	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}

	public float getMinY() {
		return minY;
	}

	public void setMinY(float minY) {
		this.minY = minY;
	}

	public float getMaxY() {
		return maxY;
	}

	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}

	public int getAntigoX() {
		return antigoX;
	}

	public void setAntigoX(int antigoX) {
		this.antigoX = antigoX;
	}

	public int getAntigoY() {
		return antigoY;
	}

	public boolean isEditingMode() {
		return editingMode;
	}

	public void setAntigoY(int antigoY) {
		this.antigoY = antigoY;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		antigoX = e.getX();
		antigoY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		antigoX = e.getX();
		antigoY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			this.editingMode = !editingMode;
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
