package br.com.caixa.exceptions;

/**
 * Exce��o caso o caixa n�o contenha algumas notas
 * @author Massao
 */
@SuppressWarnings("serial")
public class CaixaSemNotasException extends Exception {

	public CaixaSemNotasException(String message){
		super(message);
	}
}
