package br.com.andre.leilao.servico;

import br.com.andre.leilao.dominio.Leilao;
import br.com.andre.leilao.infra.builders.CriadorDeLeilao;
import br.com.andre.leilao.infra.dao.RepositorioDeLeiloes;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EncerradorDeLeilaoTest {

    @Test
    public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras(){
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
                .naData(antiga).constroi();

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);

        when(daoFalso.correntes()).thenReturn(asList(leilao1, leilao2));
        EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(daoFalso);
        encerradorDeLeilao.encerra();

        assertEquals(2, encerradorDeLeilao.getTotalEncerrados());
        assertTrue(leilao1.isEncerrado());
        assertTrue(leilao2.isEncerrado());


    }



    @Test
    public void naoDeveEncerrarLeiloesCasoNaoHajaNenhum() {

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        when(daoFalso.correntes()).thenReturn(new ArrayList<>());

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
        encerrador.encerra();

        assertEquals(0, encerrador.getTotalEncerrados());
    }

    @Test
    public void deveAtualizarLeiloesEncerrados(){
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(antiga).constroi();

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        when(daoFalso.correntes()).thenReturn(asList(leilao1));

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
        encerrador.encerra();
        verify(daoFalso, times(1)).atualiza(leilao1);

    }

    @Test
    public void naoDeveEncerrarLeiloesQueComecaramMenosDeUmaSemanaAtras() {

        Calendar ontem = Calendar.getInstance();
        ontem.add(Calendar.DAY_OF_MONTH, -1);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(ontem).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
                .naData(ontem).constroi();

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        when(daoFalso.correntes()).thenReturn(asList(leilao1, leilao2));

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
        encerrador.encerra();

        assertEquals(0, encerrador.getTotalEncerrados());
        assertFalse(leilao1.isEncerrado());
        assertFalse(leilao2.isEncerrado());

        // verifys aqui
        verify(daoFalso, never()).atualiza(leilao1);
        verify(daoFalso, never()).atualiza(leilao2);
    }

    /**
     O método atLeastOnce() vai garantir que o método foi invocado no mínimo uma vez. Ou seja, se ele foi invocado 1, 2, 3 ou mais vezes, o teste passa. Se ele não for invocado, o teste vai falhar.

     O método atLeast(numero) funciona de forma análoga ao método acima, com a diferença de que passamos para ele o número mínimo de invocações que um método deve ter.

     Por fim, o método atMost(numero) nos garante que um método foi executado até no máximo N vezes. Ou seja, se o método tiver mais invocações do que o que foi passado para o atMost, o teste falha.

     Veja que existem diversas maneiras diferentes para garantir a quantidade de invocações de um método! Você pode escolher a melhor e mais elegante para seu teste!
     */
}
