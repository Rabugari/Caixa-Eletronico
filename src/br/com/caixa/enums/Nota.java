package br.com.caixa.enums;

/**
 * Representa os valores de notas que pode conter um caixa eletronico
 * @author Massao
 */
public enum Nota {

	DEZ(10),
	VINTE(20),
	CINQUENTA(50),
	CEM(100);
	
	private int valor;
	
	Nota(final int valor) {
		this.valor=valor;
	}
	
	public int getValor() {
		return valor;
	}
}
