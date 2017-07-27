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
import br.com.caixa.exceptions.CaixaSemNotasException;

@RunWith(JUnit4.class)
public class CaixaEletronicoTest {

	private CaixaEletronico caixaEletronico;
	
	@Before
	public void setup(){
		Map<Nota, Long> notas = new HashMap<>();
		notas.put(DEZ, 5L);
		notas.put(VINTE, 2L);
		notas.put(CINQUENTA, 1L);
		notas.put(CEM, 3L);
		
		this.caixaEletronico = new CaixaEletronico(notas);
	}
	
	@Test
	public void verificaQuantidadeAposAdicionarNotaDe10() throws CaixaSemNotasException{
		caixaEletronico.deposita(DEZ);
		assertEquals(caixaEletronico.getQuantidadeDeNotas(DEZ), 6, 6);
	}
	
	@Test
	public void saqueDe60() throws IllegalArgumentException, CaixaSemNotasException{
		List<Nota> notasRetornadas = caixaEletronico.saca(40);
		assertEquals(Arrays.asList(VINTE, VINTE), notasRetornadas);
	}
	
	@Test
	public void saqueDe170() throws IllegalArgumentException, CaixaSemNotasException{
		List<Nota> notasRetornadas = caixaEletronico.saca(170);
		assertEquals(Arrays.asList(CEM, CINQUENTA, VINTE), notasRetornadas);
	}
	
	@Test
	public void saqueDe380() throws IllegalArgumentException, CaixaSemNotasException{
		List<Nota> notasRetornadas = caixaEletronico.saca(380);
		assertEquals(Arrays.asList(CEM, CEM, CEM, CINQUENTA, VINTE, DEZ), notasRetornadas);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaSacarValorInvalido() throws IllegalArgumentException, CaixaSemNotasException{
		caixaEletronico.saca(-50);
	}
	
	@Test
	public void verificaQuantidadeDeNotasAposOSaque() throws CaixaSemNotasException {
		caixaEletronico.saca(100);
		assertEquals(caixaEletronico.getQuantidadeDeNotas(CEM), 2L);
	}
	
	@Test(expected = CaixaSemNotasException.class)
	public void sacouMaisQueLimite() throws CaixaSemNotasException{
		caixaEletronico.saca(450);
	}
}
