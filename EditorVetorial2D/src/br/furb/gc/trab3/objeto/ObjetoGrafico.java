package br.furb.gc.trab3.objeto;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import br.furb.gc.trab3.comp.BoundingBox;
import br.furb.gc.trab3.comp.Ponto4D;
import br.furb.gc.trab3.comp.Transformacao4D;
import br.furb.gc.trab3.openGl.OpenGL;

/**
 * Classe que representa um objeto gráfico em grafo de cena.
 * 
 * @author helinton.steffens
 *
 */
public class ObjetoGrafico extends OpenGL {

	private int primitiva = GL.GL_LINE_STRIP;
	private float[] corObjeto = new float[]{0.0f,0.0f,0.0f};

	private List<ObjetoGrafico> lObjetos = new ArrayList<ObjetoGrafico>();
	private List<Ponto4D> arestas = new ArrayList<Ponto4D>();
	
	private BoundingBox bbox;
	private Ponto4D verticeSelecionado;
	private Transformacao4D matrizObjeto = new Transformacao4D();

	private  Transformacao4D matrizGlobal = new Transformacao4D();
	private  Transformacao4D matrizTmpTranslacao = new Transformacao4D();
	private  Transformacao4D matrizTmpTranslacaoInversa = new Transformacao4D();
	private  Transformacao4D matrizTmpEscala = new Transformacao4D();		
	
	public BoundingBox getBbox() {
		return bbox;
	}

	public void setBbox(BoundingBox bbox) {
		this.bbox = bbox;
	}

	public List<ObjetoGrafico> getlObjetos() {
		return lObjetos;
	}

	public void setlObjetos(List<ObjetoGrafico> lObjetos) {
		this.lObjetos = lObjetos;
	}

	public int getPrimitiva() {
		return primitiva;
	}
	
	public void setPrimitiva(int primitiva) {
		this.primitiva = primitiva;
	}

	public float[] getCorObjeto() {
		return corObjeto;
	}

	public void setCorObjeto(float[] corObjeto) {
		this.corObjeto = corObjeto;
	}
	
	public List<Ponto4D> getArestas() {
		return arestas;
	}

	public Ponto4D getVerticeSelecionado() {
		return verticeSelecionado;
	}

	public void setVerticeSelecionado(Ponto4D verticeSelecionado) {
		this.verticeSelecionado = verticeSelecionado;
	}
	
	public Transformacao4D getMatrizObjeto() {
		return matrizObjeto;
	}

	public void setMatrizObjeto(Transformacao4D matrizObjeto) {
		this.matrizObjeto = matrizObjeto;
	}

	public Transformacao4D getMatrizGlobal() {
		return matrizGlobal;
	}

	public void setMatrizGlobal(Transformacao4D matrizGlobal) {
		this.matrizGlobal = matrizGlobal;
	}

	public Transformacao4D getMatrizTmpTranslacao() {
		return matrizTmpTranslacao;
	}

	public void setMatrizTmpTranslacao(Transformacao4D matrizTmpTranslacao) {
		this.matrizTmpTranslacao = matrizTmpTranslacao;
	}

	public Transformacao4D getMatrizTmpTranslacaoInversa() {
		return matrizTmpTranslacaoInversa;
	}

	public void setMatrizTmpTranslacaoInversa(
			Transformacao4D matrizTmpTranslacaoInversa) {
		this.matrizTmpTranslacaoInversa = matrizTmpTranslacaoInversa;
	}

	public  Transformacao4D getMatrizTmpEscala() {
		return matrizTmpEscala;
	}

	public  void setMatrizTmpEscala(Transformacao4D matrizTmpEscala) {
		this.matrizTmpEscala = matrizTmpEscala;
	}

	public void setArestas(List<Ponto4D> arestas) {
		this.arestas = arestas;
	}
	
	public void adicionarAresta(Ponto4D ponto) {
		this.arestas.add(ponto);
	}
	
	public void removerAresta(Ponto4D ponto) {
		this.arestas.remove(ponto);
	}
	
	/**
	 * Adiciona um {@link ObjetoGrafico} a lista de {@link ObjetoGrafico} com as mesmas caracteristicas do objeto pai.
	 * 
	 * @return
	 */
	public ObjetoGrafico adicionaFilho(){
		try {
			ObjetoGrafico objeto = (ObjetoGrafico) this.clone();
			getlObjetos().add(objeto);
			
			return objeto;
		} catch (CloneNotSupportedException e) {
			throw new IllegalArgumentException("Deu problema no Clone");
		}
	}
	
	/**
	 * Realiza a translação no objeto atual a partir dos parametros informados.
	 * 
	 * @param tx
	 * @param ty
	 * @param tz
	 */
	public void translacaoXYZ(double tx, double ty, double tz) {
		Transformacao4D matrizTranslate = new Transformacao4D();
		matrizTranslate.atribuirTranslacao(tx,ty,tz);
		matrizObjeto = matrizTranslate.transformMatrix(matrizObjeto);		
	}
	
	/**
	 * Realiza a transformação da escala do objeto em relação ao ponto informado.
	 * 
	 * @param escala
	 * @param ptoFixo
	 */
	public void escalaXYZPtoFixo(double escala, Ponto4D ptoFixo) {
		matrizGlobal.atribuirIdentidade();

		matrizTmpTranslacao.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);

		matrizTmpEscala.atribuirEscala(escala, escala, 1.0);
		matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);

		ptoFixo.inverterSinal(ptoFixo);
		matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);

		matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);
	}
	
	/**
	 * Realiza a rotação do objeto atual em relação ao ponto informado.
	 * 
	 * @param angulo
	 * @param ptoFixo
	 */
	public void rotacaoZPtoFixo(double angulo, Ponto4D ptoFixo) {
		matrizGlobal.atribuirIdentidade();

		matrizTmpTranslacao.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);

		matrizTmpEscala.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
		matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);

		ptoFixo.inverterSinal(ptoFixo);
		matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);

		matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);
	}

	/**
	 * Desenha a bbox do objeto gráfico.
	 */
	public void desenhaBoundingBox(){
		if (bbox != null) {
			bbox.desenharOpenGLBBox(getGl(), getCorObjeto()[0], getCorObjeto()[1], getCorObjeto()[2]);
		}
	}
	
	@Override
	public void display(GLAutoDrawable arg0) {
		super.display(arg0);
		draw();
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		super.init(drawable);
	}
	
	/**
	 * Configura o {@link OpenGL} para poder desenhar o objeto atual.
	 */
	public void draw(){
//		gl.glLineWidth(3.0f);
//		gl.glPushMatrix();
//		gl.glMultMatrixd(getMatrizObjeto().GetDate(), 0);

		draw(this);

//		gl.glPopMatrix();
	}
	
	private void draw(ObjetoGrafico objetoGrafico){
		for (ObjetoGrafico objeto : objetoGrafico.getlObjetos()) {
			draw(objeto);
		}
		drawObject(objetoGrafico);
	}

	private void drawObject(ObjetoGrafico objetoGrafico) {
		gl.glLineWidth(3.0f);
		gl.glPushMatrix();
		gl.glMultMatrixd(getMatrizObjeto().GetDate(), 0);
		gl.glMultMatrixd(objetoGrafico.getMatrizObjeto().GetDate(), 0);

		objetoGrafico.getGl().glBegin(objetoGrafico.getPrimitiva());
		objetoGrafico.getGl().glColor3f(objetoGrafico.getCorObjeto()[0], objetoGrafico.getCorObjeto()[1], objetoGrafico.getCorObjeto()[2]);
		objetoGrafico.setBbox(null);
		
		for (Ponto4D ponto4d : objetoGrafico.getArestas()) {
			if (objetoGrafico.getBbox() == null) {
				objetoGrafico.setBbox(new BoundingBox(ponto4d.obterX() -1, ponto4d.obterY()-1, 0, ponto4d.obterX() + 1, ponto4d.obterY() + 1, 0));
			}
			//Trata para quando for o primeiro ponto
			if (objetoGrafico.getArestas().size() == 1) {
				int incremento = 1;
				objetoGrafico.getGl().glVertex2d(ponto4d.obterX() + incremento, ponto4d.obterY() + incremento);
			}
			objetoGrafico.getGl().glVertex2d(ponto4d.obterX(), ponto4d.obterY());
			objetoGrafico.getBbox().atualizarBBox(ponto4d);
		}

		if (objetoGrafico.getBbox() != null) {
			objetoGrafico.getBbox().processarCentroBBox();
		}
		
		objetoGrafico.getGl().glEnd();
		objetoGrafico.getGl().glFlush();
		
		gl.glPopMatrix();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		ObjetoGrafico objetoGrafico = new ObjetoGrafico();
		objetoGrafico.setCorObjeto(this.getCorObjeto());
		objetoGrafico.setGl(this.getGl());
		objetoGrafico.setMatrizObjeto(new Transformacao4D());
		objetoGrafico.setMatrizGlobal(new Transformacao4D());
		objetoGrafico.setMatrizTmpEscala(new Transformacao4D());
		objetoGrafico.setMatrizTmpTranslacao(new Transformacao4D());
		objetoGrafico.setMatrizTmpTranslacaoInversa(new Transformacao4D());
		objetoGrafico.setArestas(new ArrayList<Ponto4D>());
		objetoGrafico.setPrimitiva(GL.GL_LINE_STRIP);
		return objetoGrafico;
	}
}
