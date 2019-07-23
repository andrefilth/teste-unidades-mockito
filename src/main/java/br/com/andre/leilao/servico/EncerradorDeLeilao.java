package br.com.andre.leilao.servico;

import br.com.andre.leilao.dominio.Leilao;
import br.com.andre.leilao.infra.dao.RepositorioDeLeiloes;

import java.util.Calendar;
import java.util.List;

public class EncerradorDeLeilao {

	private final RepositorioDeLeiloes dao;

	private final Carteiro carteiro;

	private int total = 0;

	public EncerradorDeLeilao(RepositorioDeLeiloes dao, Carteiro carteiro) {
		this.dao = dao;
		this.carteiro = carteiro;
	}

	public void encerra() {
//		LeilaoDao dao = new LeilaoDao();
		List<Leilao> todosLeiloesCorrentes = dao.correntes();

		for (Leilao leilao : todosLeiloesCorrentes) {
			try {
				if (comecouSemanaPassada(leilao)) {
					leilao.encerra();
					total++;
					dao.atualiza(leilao);
					carteiro.envia(leilao);
				}
			}catch (RuntimeException e){

			}

		}
	}

	private boolean comecouSemanaPassada(Leilao leilao) {
		return diasEntre(leilao.getData(), Calendar.getInstance()) >= 7;
	}

	private int diasEntre(Calendar inicio, Calendar fim) {
		Calendar data = (Calendar) inicio.clone();
		int diasNoIntervalo = 0;
		while (data.before(fim)) {
			data.add(Calendar.DAY_OF_MONTH, 1);
			diasNoIntervalo++;
		}

		return diasNoIntervalo;
	}

	public int getTotalEncerrados() {
		return total;
	}
}
