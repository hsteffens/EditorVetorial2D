package br.furb.gc.trab3.ambiente;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import br.furb.gc.trab3.bo.BOObjetoGrafico;
import br.furb.gc.trab3.comp.Camera2D;
import br.furb.gc.trab3.comp.Ponto4D;
import br.furb.gc.trab3.objeto.ObjetoGrafico;
import br.furb.gc.trab3.openGl.OpenGL;

/**
 * Classe que representa o ambiente do sistema.
 * 
 * @author helinton.steffens
 *
 */
public class Mundo extends OpenGL{

	private Camera2D camera;
	private List<ObjetoGrafico> lObjetos = new ArrayList<ObjetoGrafico>();
	private ObjetoGrafico objetoSelecionado;
	
	private float[] corFundo = new float[]{1.0f,0.0f,0.0f};

	public Mundo() {

	}
	public Mundo(Frame frame) {
		this.setFrame(frame);
	}

	public Camera2D getCamera() {
		return camera;
	}
	public void setCamera(Camera2D camera) {
		this.camera = camera;
	}
	public List<ObjetoGrafico> getlObjetos() {
		return lObjetos;
	}
	public void setlObjetos(List<ObjetoGrafico> lObjetos) {
		this.lObjetos = lObjetos;
	}
	public ObjetoGrafico getObjetoSelecionado() {
		if (objetoSelecionado == null && !lObjetos.isEmpty()) {
			objetoSelecionado = lObjetos.get(0);
		}
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(ObjetoGrafico objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}
	public float[] getCorFundo() {
		return corFundo;
	}
	public void setCorFundo(float[] corFundo) {
		this.corFundo = corFundo;
	}

	public int getXRefereteACamera() {
		return (int) (super.getAntigoX() + getCamera().getxMin());
	};

	public int getYRefereteACamera() {
		return (int) (getCamera().getyMax() - super.getAntigoY());
	};
	
	@Override
	public void display(GLAutoDrawable arg0) {
		setMinX(getCamera().getxMin());
		setMaxX(getCamera().getxMax());
		setMinY(getCamera().getyMin());
		setMaxY(getCamera().getyMax());

		super.display(arg0);

		for (ObjetoGrafico objetoGrafico : lObjetos) {
			objetoGrafico.draw();
		}
		
		if (getObjetoSelecionado() != null) {
			getObjetoSelecionado().desenhaBoundingBox();
		}

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		camera = new Camera2D(-200.0f, 200.0f, -200.0f, 200.0f);
		super.init(drawable);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);

		draw(e.getButton());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!isEditingMode() && getObjetoSelecionado() != null && getObjetoSelecionado().getVerticeSelecionado() != null){
			Ponto4D ponto = getObjetoSelecionado().getVerticeSelecionado();

			ponto.atribuirX(getXRefereteACamera());
			ponto.atribuirY(getYRefereteACamera());
		}else{
			int movtoX = e.getX() - getAntigoX();
			int movtoY = e.getY() - getAntigoY();

			ObjetoGrafico objetoGrafico = getObjetoSelecionado();
			Ponto4D ponto = null;
			
			if (objetoGrafico.getArestas() != null && !objetoGrafico.getArestas().isEmpty()) {
				int index = objetoGrafico.getArestas().size() - 1;
				ponto = objetoGrafico.getArestas().get(index);

				ponto.atribuirX(ponto.obterX() + movtoX);
				ponto.atribuirY(ponto.obterY() - movtoY);
			}

		}
		super.mousePressed(e);
		glDrawable.display();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			if (editingMode) {
				ObjetoGrafico objetoGrafico = getObjetoSelecionado().adicionaFilho();
				setObjetoSelecionado(objetoGrafico);
			}
			break;
		case KeyEvent.VK_D:
			if (editingMode) {
				BOObjetoGrafico.removeObjeto(getlObjetos(), getObjetoSelecionado());
				setObjetoSelecionado(null);
			}
			break;
		case KeyEvent.VK_X:
			if (editingMode && getObjetoSelecionado() != null) {
				Ponto4D pontoSelecionado = getObjetoSelecionado().getVerticeSelecionado();
				if (pontoSelecionado != null) {
					getObjetoSelecionado().removerAresta(pontoSelecionado);
				}
			}
			break;
		case KeyEvent.VK_R:
			if (!editingMode && objetoSelecionado != null) {
				BOObjetoGrafico.alterarCorObjetoGrafico(objetoSelecionado, 1.0f, 0.0f, 0.0f);
			}
			break;
		case KeyEvent.VK_G:
			if (!editingMode && objetoSelecionado != null) {
				BOObjetoGrafico.alterarCorObjetoGrafico(objetoSelecionado, 0.0f, 1.0f, 0.0f);
			}
			break;
		case KeyEvent.VK_B:
			if (!editingMode && objetoSelecionado != null) {
				BOObjetoGrafico.alterarCorObjetoGrafico(objetoSelecionado, 0.0f, 0.0f, 1.0f);
			}
			break;
		case KeyEvent.VK_RIGHT:
			getObjetoSelecionado().translacaoXYZ(2.0,0.0,0.0);
			break;
		case KeyEvent.VK_LEFT:
			getObjetoSelecionado().translacaoXYZ(-2.0,0.0,0.0);
			break;
		case KeyEvent.VK_UP:
			getObjetoSelecionado().translacaoXYZ(0.0,2.0,0.0);
			break;
		case KeyEvent.VK_DOWN:
			getObjetoSelecionado().translacaoXYZ(0.0,-2.0,0.0);
			break;
		case KeyEvent.VK_1:
			getObjetoSelecionado().escalaXYZPtoFixo(0.5, new Ponto4D().inverterSinal(getObjetoSelecionado().getBbox().obterCentro()));
			break;
		case KeyEvent.VK_2:
			getObjetoSelecionado().escalaXYZPtoFixo(2.0, new Ponto4D().inverterSinal(getObjetoSelecionado().getBbox().obterCentro()));
			break;
		case KeyEvent.VK_3:
			getObjetoSelecionado().rotacaoZPtoFixo(10.0,new Ponto4D().inverterSinal(getObjetoSelecionado().getBbox().obterCentro()));
			break;
		default:
			break;
		}
		glDrawable.display();
	}

	private void draw(int botaoPressionado) {
		if (isEditingMode()) {
			if (getlObjetos().isEmpty() || getObjetoSelecionado().getPrimitiva() == GL.GL_LINE_LOOP) {
				ObjetoGrafico objetoGrafico = new ObjetoGrafico();
				objetoGrafico.setPrimitiva(botaoPressionado ==  MouseEvent.BUTTON3 ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);
				objetoGrafico.setGl(gl);

				Ponto4D ponto4d = new Ponto4D(getXRefereteACamera(), getYRefereteACamera(), 0, 0);
				objetoGrafico.adicionarAresta(ponto4d);

				lObjetos.add(objetoGrafico);
				setObjetoSelecionado(objetoGrafico);
			}else{
				getObjetoSelecionado().setPrimitiva(botaoPressionado ==  MouseEvent.BUTTON3 ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);

				Ponto4D ponto4d = new Ponto4D(getXRefereteACamera(), getYRefereteACamera(), 0, 0);

				getObjetoSelecionado().adicionarAresta(ponto4d);

			}
		}else{
			if (!editingMode) {
				setObjetoSelecionado(BOObjetoGrafico.selecionarObjetoGrafico(getlObjetos(), getXRefereteACamera(), getYRefereteACamera()));
				if (getObjetoSelecionado() != null) {
					getObjetoSelecionado().setVerticeSelecionado(BOObjetoGrafico.selecionarVertice(getObjetoSelecionado(), getXRefereteACamera(), getYRefereteACamera()));
				}
			}

		}
		glDrawable.display();
	}


}
