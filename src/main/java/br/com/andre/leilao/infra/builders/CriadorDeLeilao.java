package br.com.andre.leilao.infra.builders;

import br.com.andre.leilao.dominio.Leilao;
import br.com.andre.leilao.dominio.Usuario;

import java.util.Calendar;

public class CriadorDeLeilao {
    private String objetoLeiloado;
    private Calendar dataAntiga;
    private Usuario usuario;
    private double valor;

    public CriadorDeLeilao para(String objetoLeiloado) {
        this.objetoLeiloado = objetoLeiloado;
        return this;
    }

    public CriadorDeLeilao naData(Calendar antiga) {
        this.dataAntiga = antiga;
        return this;
    }

    public Leilao constroi() {
        return new Leilao(objetoLeiloado, dataAntiga);
    }

    public CriadorDeLeilao lance(Usuario usuario, double valor) {
        this.usuario = usuario;
        this.valor = valor;
        return this;
    }
}
