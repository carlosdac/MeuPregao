package felipeDaRochaTorres.pregaoServicosSQL;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;




class teste {

	Conexao con = new Conexao();
	//Connection con = Conexao.getConexao();
	@Test
	public void testarCadastroDeServico() {

		con.apagar();
		InterfacePregao pregao = new MeuPregao();
		pregao.adicionarTipoDeServico(1, "Jardinagem");
		pregao.adicionarTipoDeServico(2, "Eletrico");
		
		pregao.cadastrarContratante("pasn@ufpi.edu.br", "Pedro Santos Neto", "9999-9999");
		pregao.cadastrarContratante("rai@ufpi.edu.br", "Raimundo Moura", "8888-8888");
		pregao.cadastrarContratante("erico@ufpi.edu.br", "Erico Leao", "7777-7777");
		
		pregao.cadastrarServico("pasn@ufpi.edu.br", "Corte de grama em jardim de 20m2", 100, 2, 1);
		ArrayList<Servico> servs = pregao.listarServicos(0, 0, 0, false, false, 0);
		assertEquals(1, servs.size());
		pregao.cadastrarServico("rai@ufpi.edu.br", "Instalacao de tomadas de tras pinos", 80, 2, 1);
		servs = pregao.listarServicos(0, 0, 0, false, false, 0);
		assertEquals(2, servs.size());
		pregao.cadastrarServico("erico@ufpi.edu.br", "Instalacao de tomadas de quatro pinos", 70, 2, 1);
		
		servs = pregao.listarServicos(0, 0, 0, false, false, 0);
		assertEquals(3, servs.size());
		con.apagar();
	}


}
