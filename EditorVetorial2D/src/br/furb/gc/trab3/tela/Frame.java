package br.furb.gc.trab3.tela;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import br.furb.gc.trab3.ambiente.Mundo;

/**
 * Classe responsável por criar tela e suas respectivas configurações.
 * 
 * @author helinton.steffens
 *
 */
public class Frame extends JFrame{

	private static final long serialVersionUID = 1L;
	private Mundo renderer = new Mundo();
	
	private int janelaLargura  = 400, janelaAltura = 400;

	
	public Frame() {		
		super("Editor Vetorial 2D");   
		setBounds(300,250,janelaLargura,janelaAltura+22);  // 400 + 22 da borda do titulo da janela
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		
		/* Cria um objeto GLCapabilities para especificar 
		 * o numero de bits por pixel para RGBA
		 */
		GLCapabilities glCaps = new GLCapabilities();
		glCaps.setRedBits(8);
		glCaps.setBlueBits(8);
		glCaps.setGreenBits(8);
		glCaps.setAlphaBits(8); 

		/* Cria um canvas, adiciona ao frame e objeto "ouvinte" 
		 * para os eventos Gl, de mouse e teclado
		 */
		GLCanvas canvas = new GLCanvas(glCaps);
		add(canvas,BorderLayout.CENTER);
		canvas.addGLEventListener(renderer);        
		canvas.addKeyListener(renderer);
		canvas.addMouseListener(renderer);
		canvas.addMouseMotionListener(renderer);
		canvas.requestFocus();
		
	}		
	
	public static void main(String[] args) {
		new Frame().setVisible(true);
	}
	
}
