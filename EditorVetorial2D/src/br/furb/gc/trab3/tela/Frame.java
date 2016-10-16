package br.furb.gc.trab3.tela;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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
	private Mundo renderer;

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

		renderer = new Mundo(this);
		GLCanvas canvas = new GLCanvas(glCaps);
		add(canvas,BorderLayout.CENTER);
		canvas.addGLEventListener(renderer);        
		canvas.addKeyListener(renderer);
		canvas.addMouseListener(renderer);
		canvas.addMouseMotionListener(renderer);
		canvas.requestFocus();

		criaMenuAjuda();
	}

	private void criaMenuAjuda() {
		JMenuBar menuBar = new JMenuBar();

		// Create a menu
		JMenu menu = new JMenu("Lista Atalhos");
		menuBar.add(menu);
		
		menu.add("Tecla espaço - Altera para o modo de edição.");
		menu.add("Tecla A - Adiciona um objeto ao objeto selecionado.");
		menu.add("Tecla D - Remove o poligno selecionado e seus filhos.");
		menu.add("Tecla X - Remove uma aresta do objeto selecionado.");
		menu.add("Tecla R - Altera a cor do objeto e seus filhos para vermelho.");
		menu.add("Tecla G - Altera a cor do objeto e seus filhos para verde.");
		menu.add("Tecla B - Altera a cor do objeto e seus filhos para azul.");
		menu.add("Tecla ← - Translada o objeto para a esquerda.");
		menu.add("Tecla ↑ - Translada o objeto para cima.");
		menu.add("Tecla → - Translada o objeto para a direita.");
		menu.add("Tecla ↓ - Translada o objeto para baixo.");
		menu.add("Tecla 1 - Reduz a escala do objeto.");
		menu.add("Tecla 2 - Aumenta a escala do objeto.");
		menu.add("Tecla 3 - Rotaciona o objeto.");

		this.setJMenuBar(menuBar);
	}		

	public static void main(String[] args) {
		new Frame().setVisible(true);
	}

}
