package br.com.andre.leilao.servico;

import br.com.andre.leilao.dominio.Leilao;
import br.com.andre.leilao.dominio.Pagamento;
import br.com.andre.leilao.infra.dao.RepositorioDeLeiloes;
import br.com.andre.leilao.infra.dao.RepositorioDePagamentos;

import java.util.Calendar;
import java.util.List;

public class GeradorDePagamento {

    private final RepositorioDePagamentos pagamentos;
    private final RepositorioDeLeiloes leiloes;
    private final Avaliador avaliador;

    public GeradorDePagamento(RepositorioDePagamentos pagamentos, RepositorioDeLeiloes leiloes, Avaliador avaliador) {
        this.pagamentos = pagamentos;
        this.leiloes = leiloes;
        this.avaliador = avaliador;
    }

    public void gera() {

        List<Leilao> leiloesEncerrados = leiloes.encerrados();
        for (Leilao leilao : leiloesEncerrados) {
            avaliador.avalia(leilao);

            Pagamento novoPagamento =
                    new Pagamento(avaliador.getMaiorLance(), Calendar.getInstance());
            pagamentos.salva(novoPagamento);
        }
    }
}
