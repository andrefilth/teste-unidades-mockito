package br.com.andre.leilao.infra.dao;

import br.com.andre.leilao.dominio.Pagamento;

public interface RepositorioDePagamentos {

    void salva (Pagamento pagamentos);
}
