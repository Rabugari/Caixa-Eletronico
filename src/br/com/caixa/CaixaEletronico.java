package br.com.caixa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static br.com.caixa.enums.Nota.*;

import br.com.caixa.enums.Nota;
import br.com.caixa.exceptions.CaixaSemNotas;

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

	public List<Nota> saque(long quantidadeDoSaque) throws IllegalArgumentException {
		List<Nota> notasDoSaque = new ArrayList<>();

		if(quantidadeDoSaque < 0)
			throw new IllegalArgumentException();
		
		while(quantidadeDoSaque>0){
			if(notaEhValida(quantidadeDoSaque,DEZ, VINTE))
				quantidadeDoSaque = alteraSaldo(quantidadeDoSaque, DEZ, notasDoSaque);

			if(notaEhValida(quantidadeDoSaque, VINTE, CINQUENTA))
				quantidadeDoSaque = alteraSaldo(quantidadeDoSaque, VINTE, notasDoSaque);

			if(notaEhValida(quantidadeDoSaque, CINQUENTA, CEM))
				quantidadeDoSaque = alteraSaldo(quantidadeDoSaque, CINQUENTA, notasDoSaque);

			if((quantidadeDoSaque >= CEM.getValor()) && notas.get(CEM) != null)
				quantidadeDoSaque = alteraSaldo(quantidadeDoSaque, CEM, notasDoSaque);
		}
		return notasDoSaque;
	}

	private boolean notaEhValida(long quantidadeDoSaque, Nota notaMenor, Nota notaMaior){
		return ((quantidadeDoSaque >= notaMenor.getValor() && quantidadeDoSaque < notaMaior.getValor()) && notas.get(notaMenor) != null);
	}
	
	private long alteraSaldo(long quantidadeDoSaque, Nota notaASerSacada, List<Nota> notasDoSaque){
		notasDoSaque.add(notaASerSacada);
		long quantidade = notas.get(notaASerSacada);
		notas.put(notaASerSacada, new Long(--quantidade));
		
		quantidadeDoSaque -= notaASerSacada.getValor();
		
		return quantidadeDoSaque;
	}


	public void adicionaNota(final Nota nota){
		Long quantidade = notas.get(nota);

		if(quantidade!=null)
			notas.put(nota, new Long(++quantidade));
		else
			notas.put(nota, 1L);
	}

	public long getQuantidadeDeNotas(final Nota nota) throws CaixaSemNotas {
		if(notas!=null){
			return notas.get(nota).longValue();
		}
		throw new CaixaSemNotas();
	}

	public final Map<Nota, Long> getNotas() {
		return notas;
	}
}
