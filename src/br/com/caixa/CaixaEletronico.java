package br.com.caixa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static br.com.caixa.enums.Nota.*;

import br.com.caixa.enums.Nota;
import br.com.caixa.exceptions.CaixaSemNotasException;

/**
 * @author Massao
 */
public class CaixaEletronico {

	//Notas contidas dentro do caixa, tendo como chave/valor: valor da nota/quantidade da nota
	private Map<Nota, Long> notas;

	public CaixaEletronico(){};

	public CaixaEletronico(final Map<Nota, Long> notas){
		this.notas = notas;
	}

	public List<Nota> saca(long quantidadeDoSaque) throws IllegalArgumentException, CaixaSemNotasException {
		List<Nota> notasDoSaque = new ArrayList<>();

		if(quantidadeDoSaque < 0)
			throw new IllegalArgumentException("Não se pode sacar um valor negativo");
		
		while(quantidadeDoSaque>0){
			if(notaEhValida(quantidadeDoSaque,DEZ, VINTE))
				quantidadeDoSaque = alteraSaldo(quantidadeDoSaque, DEZ, notasDoSaque);

			if(notaEhValida(quantidadeDoSaque, VINTE, CINQUENTA))
				quantidadeDoSaque = alteraSaldo(quantidadeDoSaque, VINTE, notasDoSaque);

			if(notaEhValida(quantidadeDoSaque, CINQUENTA, CEM))
				quantidadeDoSaque = alteraSaldo(quantidadeDoSaque, CINQUENTA, notasDoSaque);

			if((quantidadeDoSaque >= CEM.getValor()) && getQuantidadeDeNotas(CEM) > 0)
				quantidadeDoSaque = alteraSaldo(quantidadeDoSaque, CEM, notasDoSaque);
		}
		return notasDoSaque;
	}

	private boolean notaEhValida(long quantidadeDoSaque, Nota notaMenor, Nota notaMaior) throws CaixaSemNotasException{
		return ((quantidadeDoSaque >= notaMenor.getValor() && quantidadeDoSaque < notaMaior.getValor()) && getQuantidadeDeNotas(notaMenor) > 0);
	}
	
	private long alteraSaldo(long quantidadeDoSaque, Nota notaASerSacada, List<Nota> notasDoSaque){
		notasDoSaque.add(notaASerSacada);
		long quantidade = notas.get(notaASerSacada);
		notas.put(notaASerSacada, new Long(--quantidade));
		
		quantidadeDoSaque -= notaASerSacada.getValor();
		
		return quantidadeDoSaque;
	}

	public void deposita(final Nota nota){
		Long quantidade = notas.get(nota);

		if(quantidade!=null)
			notas.put(nota, new Long(++quantidade));
		else
			notas.put(nota, 1L);
	}

	public long getQuantidadeDeNotas(final Nota nota) throws CaixaSemNotasException {
		if(notas!=null){
			Long quantidade = notas.get(nota);
			return quantidade!= null ? quantidade.longValue() : 0;
		}
		throw new CaixaSemNotasException("O Caixa está vazio.");
	}

	public final Map<Nota, Long> getNotas() {
		return notas;
	}
}
