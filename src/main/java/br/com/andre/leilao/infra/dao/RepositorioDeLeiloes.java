package br.com.andre.leilao.infra.dao;

import br.com.andre.leilao.dominio.Leilao;

import java.util.List;

public interface RepositorioDeLeiloes {
    void salva(Leilao leilao);
    List<Leilao> encerrados();
    List<Leilao> correntes();
    void atualiza(Leilao leilao);
}
