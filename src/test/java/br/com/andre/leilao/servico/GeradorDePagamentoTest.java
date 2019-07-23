package br.com.andre.leilao.servico;

import br.com.andre.leilao.dominio.Leilao;
import br.com.andre.leilao.dominio.Pagamento;
import br.com.andre.leilao.dominio.Usuario;
import br.com.andre.leilao.infra.builders.CriadorDeLeilao;
import br.com.andre.leilao.infra.dao.RepositorioDeLeiloes;
import br.com.andre.leilao.infra.dao.RepositorioDePagamentos;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static java.util.Arrays.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.*;
import static org.mockito.Mockito.*;

public class GeradorDePagamentoTest {



    private RepositorioDeLeiloes leiloes;
    private Avaliador avaliador;
    private RepositorioDePagamentos pagamentos;
    private GeradorDePagamento gerador;

    @Before
    public void init(){
        this.leiloes = mock(RepositorioDeLeiloes.class);
        this.avaliador = new Avaliador();
        this.pagamentos = mock(RepositorioDePagamentos.class);
    }

    @Test
    public void deveGerarPagamentoParaUmLeilaoEncerrado() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation")
                .lance(new Usuario("José da Silva"), 2000.0)
                .lance(new Usuario("Maria Pereira"), 2500.0)
                .constroi();

        when(leiloes.encerrados()).thenReturn(asList(leilao));
        when(avaliador.getMaiorLance()).thenReturn(2500.0);
        this.gerador = new GeradorDePagamento(pagamentos, leiloes, avaliador);

        gerador.gera();

        ArgumentCaptor<Pagamento> argumento = forClass(Pagamento.class);
        verify(pagamentos).salva(argumento.capture());
        Pagamento pagamentoGerado = argumento.getValue();
        assertEquals(2500.0, pagamentoGerado.getValor(), 0.00001);
    }

}