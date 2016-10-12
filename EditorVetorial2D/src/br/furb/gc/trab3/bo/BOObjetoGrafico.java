package br.furb.gc.trab3.bo;

import java.util.List;

import br.furb.gc.trab3.comp.BoundingBox;
import br.furb.gc.trab3.comp.Ponto4D;
import br.furb.gc.trab3.objeto.ObjetoGrafico;

/**
 * Classe responsável por manter as regras de negocio de objetos gráficos.
 * 
 * @author helinton.steffens
 *
 */
public final class BOObjetoGrafico {

	private static ObjetoGrafico objetoGrafico = null;
	
	private BOObjetoGrafico(){

	}

	/**
	 * Retorna o um {@link ObjetoGrafico} que esteja no ponto x,y informado.
	 * 
	 * @param lObjetos
	 * @param x
	 * @param y
	 * @return
	 */
	public static ObjetoGrafico selecionarObjetoGrafico(List<ObjetoGrafico>lObjetos, float x, float y){
		ObjetoGrafico objetoSelecionado = null;
		for (ObjetoGrafico objeto : lObjetos) {
			buscaObjetoMaisProximo(objeto, x, y);
			
			objetoSelecionado = objetoGrafico;
			objetoGrafico = null;
			if (objetoSelecionado != null) {
				break;
			}
		}

		return objetoSelecionado;
	}
	
	/**
	 * Retorna o ponto mais proximo ao x,y informado.
	 * 
	 * @param objetoGrafico
	 * @param x
	 * @param y
	 * @return
	 */
	public static Ponto4D selecionarVertice(ObjetoGrafico objetoGrafico, float x, float y){
		Ponto4D verticeSelecionado = new Ponto4D();
		if (objetoGrafico !=null) {
			Ponto4D pontoSelecionado = null;

			ObjetoGrafico objetoMaisProximoGrafico = objetoGrafico;

			double menorDistancia = Double.MAX_VALUE;

			for (Ponto4D ponto4d : objetoMaisProximoGrafico.getArestas()) {
				double distancia = Ponto4D.getDistanciaPontos(x, y, ponto4d.obterX(), ponto4d.obterY());
				if (menorDistancia > distancia) {
					menorDistancia = distancia;

					pontoSelecionado = ponto4d;
				}
			}

			verticeSelecionado = pontoSelecionado;
		}

		return verticeSelecionado;
	}

	/**
	 * Altera a cor de determinado {@link ObjetoGrafico} e seus repectivo filhos.
	 * 
	 * @param objetoGrafico
	 * @param red
	 * @param green
	 * @param blue
	 */
	public static void alterarCorObjetoGrafico(ObjetoGrafico objetoGrafico, float red, float green, float blue){
		objetoGrafico.setCorObjeto(new float[]{red, green, blue});

		if (objetoGrafico.getlObjetos() != null && !objetoGrafico.getlObjetos().isEmpty()) {
			for (ObjetoGrafico objeto : objetoGrafico.getlObjetos()) {
				alterarCorObjetoGrafico(objeto, red, green, blue);
			}
		}

	}
	
	/**
	 * Remove um determinado {@link ObjetoGrafico} de uma lista de {@link ObjetoGrafico} sendo observados os repectivos filhos.
	 * 
	 * @param objetos
	 * @param objetoHaExcluir
	 */
	public static void removeObjeto(List<ObjetoGrafico> objetos, ObjetoGrafico objetoHaExcluir){
		if (objetos.remove(objetoHaExcluir)) {
			return;
		}
		
		for (ObjetoGrafico objetoGrafico : objetos) {
			removeObjeto(objetoGrafico.getlObjetos(), objetoHaExcluir);
		}
	}
	
	private static void buscaObjetoMaisProximo(ObjetoGrafico objeto, float x, float y){
		for (ObjetoGrafico obj : objeto.getlObjetos()) {
			buscaObjetoMaisProximo(obj, x, y);
		}
		
		ObjetoGrafico objetoAux = getObjeto(objeto, x, y);
		if (objetoAux != null) {
			objetoGrafico = objetoAux;
		}
		
	}
	
	private static ObjetoGrafico getObjeto(ObjetoGrafico objetoGrafico, float x, float y){
		BoundingBox bbox = objetoGrafico.getBbox();
		if (x < bbox.obterMaiorX() && x > bbox.obterMenorX() && y < bbox.obterMaiorY() && y > bbox.obterMenorY()) {
			for (Ponto4D ponto : objetoGrafico.getArestas()) {
				if (isDentroPoligno(ponto.obterX(), ponto.obterY(), x, y)) {
					return objetoGrafico;
				}
			}
		}
		
		return null;
	}

	private static boolean isDentroPoligno(double ptoX1, double ptoY1, double ptoX2, double ptoY2){
		double distancia = Math.sqrt(Math.pow(ptoX2 - ptoX1, 2) + Math.pow(ptoY2 - ptoY1, 2));

		return distancia < 20;
	}

}
