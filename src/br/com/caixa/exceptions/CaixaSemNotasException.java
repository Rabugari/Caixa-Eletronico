package br.com.caixa.exceptions;

/**
 * Exceção caso o caixa não contenha algumas notas
 * @author Massao
 */
@SuppressWarnings("serial")
public class CaixaSemNotasException extends Exception {

	public CaixaSemNotasException(String message){
		super(message);
	}
}
