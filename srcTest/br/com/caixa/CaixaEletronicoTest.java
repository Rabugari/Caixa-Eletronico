package br.com.caixa;

import static br.com.caixa.enums.Nota.CEM;
import static br.com.caixa.enums.Nota.CINQUENTA;
import static br.com.caixa.enums.Nota.DEZ;
import static br.com.caixa.enums.Nota.VINTE;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.com.caixa.enums.Nota;
import br.com.caixa.exceptions.CaixaSemNotas;

@RunWith(JUnit4.class)
public class CaixaEletronicoTest {

	private CaixaEletronico caixa;
	
	@Before
	public void setup(){
		Map<Nota, Long> notas = new HashMap<>();
		notas.put(DEZ, 5L);
		notas.put(VINTE, 2L);
		notas.put(CINQUENTA, 1L);
		notas.put(CEM, 3L);
		
		this.caixa = new CaixaEletronico(notas);
	}
	
	@Test
	public void verificaQuantidadeAposAdicionarNotaDe10() throws CaixaSemNotas{
		caixa.adicionaNota(DEZ);
		assertEquals(caixa.getQuantidadeDeNotas(DEZ), 6, 6);
	}
	
	@Test
	public void saqueDe40(){
		List<Nota> notasRetornadas = caixa.saque(40);
		assertEquals(Arrays.asList(VINTE, VINTE), notasRetornadas);
	}
	
	@Test
	public void saqueDe140(){
		List<Nota> notasRetornadas = caixa.saque(140);
		assertEquals(Arrays.asList(CEM, VINTE, VINTE), notasRetornadas);
	}
	
	@Test
	public void saqueDe350(){
		List<Nota> notasRetornadas = caixa.saque(350);
		assertEquals(Arrays.asList(CEM, CEM, CEM, CINQUENTA), notasRetornadas);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaSacarValorInvalido(){
		caixa.saque(-50);
	}
}
