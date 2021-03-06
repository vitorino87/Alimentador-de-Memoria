package controller;

import view.MainView;

/**
 * Esta classe trabalha com a classe TelaTemplate
 * Serve para ajudar a TelaTemplate executar suas a��es contribuindo 
 * no reaproveitamento de ambas as classes
 * � nesta classe em que irei alterar os m�todos da classe TelaTemplate
 * @author Tiago Vitorino
 * @since 17/02/2019
 */
public abstract class TelaAux extends MainView{	

	/**
	 * M�todo executado ao mover o dedo na tela da direita para esquerda
	 */
	public static void moverDireitaParaEsquerda() {
		// TODO Auto-generated method stub
		//System.out.println("Fui para esquerda");
		carregarIdeia();
		mudarACor();
	}

	/**
	 * M�todo executado ao mover o dedo na tela da esquerda para direita
	 */
	public static void moverEsquerdaParaDireita() {
		// TODO Auto-generated method stub
		//System.out.println("Fui para direita");
		carregarIdeiaAnterior();
		mudarACor();
	}		
}
