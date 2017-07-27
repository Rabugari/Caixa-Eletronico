package br.com.caixa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

		Map<Nota, Long> notasEmTransacao = notas;
		
		if(quantidadeDoSaque < 0)
			throw new IllegalArgumentException("Não se pode sacar um valor negativo");
		
		while(quantidadeDoSaque>0){
			
			if(verificaSeCaixaEstaVazio(notasEmTransacao))
				throw new CaixaSemNotasException("O Caixa não possui todo este dinheiro");
			
			if(notaEhValida(quantidadeDoSaque,DEZ, VINTE))
				quantidadeDoSaque = alteraSaldo(quantidadeDoSaque, DEZ, notasDoSaque, notasEmTransacao);

			if(notaEhValida(quantidadeDoSaque, VINTE, CINQUENTA))
				quantidadeDoSaque = alteraSaldo(quantidadeDoSaque, VINTE, notasDoSaque, notasEmTransacao);

			if(notaEhValida(quantidadeDoSaque, CINQUENTA, CEM))
				quantidadeDoSaque = alteraSaldo(quantidadeDoSaque, CINQUENTA, notasDoSaque, notasEmTransacao);

			if((quantidadeDoSaque >= CEM.getValor()) && getQuantidadeDeNotas(CEM) > 0)
				quantidadeDoSaque = alteraSaldo(quantidadeDoSaque, CEM, notasDoSaque, notasEmTransacao);
		}
		
		finalizaTransacao(notasEmTransacao);
		
		return notasDoSaque;
	}

	private boolean notaEhValida(final long quantidadeDoSaque, final Nota notaMenor, final Nota notaMaior) throws CaixaSemNotasException{
		return (getQuantidadeDeNotas(notaMaior) == 0 || quantidadeDoSaque < notaMaior.getValor())
				&& getQuantidadeDeNotas(notaMenor) > 0  
				&& quantidadeDoSaque >= notaMenor.getValor();
	}
	
	private long alteraSaldo(long quantidadeDoSaque, final Nota notaASerSacada, List<Nota> notasDoSaque, Map<Nota,Long> notasEmTransacao){
		notasDoSaque.add(notaASerSacada);
		long quantidade = notasEmTransacao.get(notaASerSacada);
		notasEmTransacao.put(notaASerSacada, new Long(--quantidade));
		
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
	
	private boolean verificaSeCaixaEstaVazio(Map<Nota, Long> notasEmTransacao){
		Stream<Long> filter = notasEmTransacao.values().stream().filter(nota -> nota.longValue() > 0L);
		return filter.count() ==0;
	}
	
	private void finalizaTransacao(Map<Nota, Long> notasEmTransacao) {
		this.notas = notasEmTransacao;
	}

	public final Map<Nota, Long> getNotas() {
		return notas;
	}
}
