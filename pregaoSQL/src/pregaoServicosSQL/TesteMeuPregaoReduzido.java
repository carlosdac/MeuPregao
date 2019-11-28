package pregaoServicosSQL;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;


import pedroSantosNeto.pregaoServicoBD.bd.Conexao;

public class TesteMeuPregaoReduzido{
	Conexao con = new Conexao();
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
		pregao.cadastrarServico("rai@ufpi.edu.br", "Instalacao de tomadas de tras pinos", 80, 2, 2);
		pregao.cadastrarServico("erico@ufpi.edu.br", "Instalacao de tomadas de quatro pinos", 70, 2, 2);
		
		ArrayList<Servico> servs = pregao.listarServicos(0, 0, 0, false, false, 0);
		assertEquals(3, servs.size());
		con.apagar();
	}

	
	@Test
	public void testarCadastrarServicoRepetido() {
		con.apagar();
		InterfacePregao pregao = new MeuPregao();
		pregao.adicionarTipoDeServico(1, "Jardinagem");
		
		pregao.cadastrarContratante("pasn@ufpi.edu.br", "Pedro Santos Neto", "9999-9999");
		
		int cod1 = pregao.cadastrarServico("pasn@ufpi.edu.br", "Corte de grama em jardim de 20m2", 100, 2, 1);
		ArrayList<Servico> servs = pregao.listarServicos(100, 0, 0, false, false, 0);
		assertEquals(1, servs.size());
		
		// Repetindo o cadastro do mesmo serviao para pasn@ufpi.edu.br
		int cod2 = pregao.cadastrarServico("pasn@ufpi.edu.br", "Corte de grama em jardim de 20m2", 100, 2, 1);
		servs = pregao.listarServicos(100, 0, 0, false, false, 0);
		assertEquals(1, servs.size());
		//assertEquals(-1, cod2);
		con.apagar();
	}
	
	@Test
	public void testarListarServicosPorValor() {
		con.apagar();
		InterfacePregao pregao = new MeuPregao();
		pregao.adicionarTipoDeServico(1, "Jardinagem");
		pregao.adicionarTipoDeServico(2, "Elatrico");
		
		pregao.cadastrarContratante("pasn@ufpi.edu.br", "Pedro Santos Neto", "9999-9999");
		pregao.cadastrarContratante("rai@ufpi.edu.br", "Raimundo Moura", "8888-8888");
		pregao.cadastrarContratante("erico@ufpi.edu.br", "Erico Leao", "7777-7777");
		pregao.cadastrarContratante("kelson@ufpi.edu.br", "Kelson Aires", "5555-5555");
		
		int cod1 = pregao.cadastrarServico("pasn@ufpi.edu.br", "Corte de grama em jardim de 20m2", 100, 2, 1);
		int cod2 = pregao.cadastrarServico("rai@ufpi.edu.br", "Plantacao de caju", 80, 8, 1);
		int cod3 = pregao.cadastrarServico("erico@ufpi.edu.br", "Instalacao de chuveiro", 400, 9, 2);
		int cod4 = pregao.cadastrarServico("kelson@ufpi.edu.br", "Troca de fiacao completa", 500, 9, 2);
		
		// Listar serviaos a partir de 100 
		ArrayList<Servico> servs = pregao.listarServicos(100, 0, 0, false, false, 0);
		assertEquals(3, servs.size());
		
		// Listar serviaos a partir de 80 
		servs = pregao.listarServicos(80, 0, 0, false, false, 0);
		assertEquals(4, servs.size());
		
		// Listar serviaos a partir de 400 
		servs = pregao.listarServicos(400, 0, 0, false, false, 0);
		assertEquals(2, servs.size());
		con.apagar();
	}
	
	
	@Test
	public void testarListarServicosContratados() {
		con.apagar();
		InterfacePregao pregao = new MeuPregao();
		pregao.adicionarTipoDeServico(1, "Jardinagem");
		pregao.adicionarTipoDeServico(5, "Faxina");	
		
		pregao.cadastrarContratante("pasn@ufpi.edu.br", "Pedro Santos Neto", "9999-9999");
		pregao.cadastrarContratante("kelson@ufpi.edu.br", "Kelson Aires", "5555-5555");
		
		int cod1 = pregao.cadastrarServico("pasn@ufpi.edu.br", "Corte de grama em jardim de 20m2", 100, 2, 1);
		int cod2 = pregao.cadastrarServico("kelson@ufpi.edu.br", "Limpeza de salao de festa", 120, 1, 5);
		
		pregao.cadastrarPrestador("jesus@gmail.com", "Maria de Jesus", "9999-1111");
		pregao.adicionarServicoPrestador("jesus@gmail.com", 5);
		pregao.cadastrarPrestador("toni@gmail.com", "Toni Silva", "8888-1111");
		pregao.adicionarServicoPrestador("toni@gmail.com", 5);
		pregao.adicionarServicoPrestador("toni@gmail.com", 1);
		pregao.cadastrarPrestador("gago@gmail.com", "Antonio Gago", "7777-1111");
		pregao.adicionarServicoPrestador("gago@gmail.com", 1);
		
		// Cadastrando propostas validas
		pregao.cadastrarProposta(cod1, "toni@gmail.com", 100, 2);
		pregao.cadastrarProposta(cod1, "gago@gmail.com", 80, 4);
		pregao.cadastrarProposta(cod2, "toni@gmail.com", 120, 1);
		pregao.cadastrarProposta(cod2, "jesus@gmail.com", 90, 1);
		
		// Contratando propostas
		pregao.contratarProposta(cod1, "toni@gmail.com");
		pregao.contratarProposta(cod2, "jesus@gmail.com");
		
		// Listando servicos contratados
		ArrayList<Servico> servs = pregao.listarServicos(0, 0, 0, true, false, 0);
		assertEquals(2, servs.size());
		con.apagar();
	}
	
	
	
	@Test
	public void testarCadastroDeProposta() {
		con.apagar();
		InterfacePregao pregao = new MeuPregao();
		pregao.adicionarTipoDeServico(1, "Jardinagem");
		pregao.adicionarTipoDeServico(2, "Elatrico");
		
		pregao.cadastrarContratante("pasn@ufpi.edu.br", "Pedro Santos Neto", "9999-9999");
		
		int cod1 = pregao.cadastrarServico("pasn@ufpi.edu.br", "Corte de grama em jardim de 20m2", 100, 4, 1);
		
		pregao.cadastrarPrestador("jesus@gmail.com", "Maria de Jesus", "9999-1111");
		pregao.adicionarServicoPrestador("jesus@gmail.com", 2);
		pregao.cadastrarPrestador("toni@gmail.com", "Toni Silva", "8888-1111");
		pregao.adicionarServicoPrestador("toni@gmail.com", 1);
		pregao.cadastrarPrestador("gago@gmail.com", "Antonio Gago", "7777-1111");
		pregao.adicionarServicoPrestador("gago@gmail.com", 1);

		// Listando propostas validas
		
		pregao.cadastrarProposta(cod1, "toni@gmail.com", 100, 2);
		pregao.cadastrarProposta(cod1, "gago@gmail.com", 80, 4);
		ArrayList<Proposta> props = pregao.listarPropostas(cod1, 0, 0, 0);
		assertEquals(2, props.size());
		con.apagar();
	}
	
	
	@Test
	public void testarCadastroPropostaPrestadorSemOServico() {
		con.apagar();
		InterfacePregao pregao = new MeuPregao();
		pregao.adicionarTipoDeServico(1, "Jardinagem");
		pregao.adicionarTipoDeServico(2, "Elatrico");
		
		pregao.cadastrarContratante("pasn@ufpi.edu.br", "Pedro Santos Neto", "9999-9999");
		
		int cod1 = pregao.cadastrarServico("pasn@ufpi.edu.br", "Corte de grama em jardim de 20m2", 100, 4, 1);
		
		pregao.cadastrarPrestador("jesus@gmail.com", "Maria de Jesus", "9999-1111");
		pregao.adicionarServicoPrestador("jesus@gmail.com", 2);
		pregao.cadastrarPrestador("toni@gmail.com", "Toni Silva", "8888-1111");
		pregao.adicionarServicoPrestador("toni@gmail.com", 1);
		pregao.cadastrarPrestador("gago@gmail.com", "Antonio Gago", "7777-1111");
		pregao.adicionarServicoPrestador("gago@gmail.com", 1);

		// Listando propostas validas
		
		pregao.cadastrarProposta(cod1, "toni@gmail.com", 100, 2);
		pregao.cadastrarProposta(cod1, "jesus@gmail.com", 80, 4); // Maria nao trabalha com jardinagem
		ArrayList<Proposta> props = pregao.listarPropostas(cod1, 0, 0, 0);
		assertEquals(1, props.size());
		con.apagar();
	}
	
	
	@Test
	public void testarListarPropostasPorValor() {
		con.apagar();
		InterfacePregao pregao = new MeuPregao();
		pregao.adicionarTipoDeServico(1, "Jardinagem");
		
		pregao.cadastrarContratante("pasn@ufpi.edu.br", "Pedro Santos Neto", "9999-9999");
		
		int cod1 = pregao.cadastrarServico("pasn@ufpi.edu.br", "Corte de grama em jardim de 20m2", 100, 4, 1);
		
		pregao.cadastrarPrestador("jesus@gmail.com", "Maria de Jesus", "9999-1111");
		pregao.adicionarServicoPrestador("jesus@gmail.com", 1);
		pregao.cadastrarPrestador("toni@gmail.com", "Toni Silva", "8888-1111");
		pregao.adicionarServicoPrestador("toni@gmail.com", 1);
		pregao.cadastrarPrestador("gago@gmail.com", "Antonio Gago", "7777-1111");
		pregao.adicionarServicoPrestador("gago@gmail.com", 1);

		pregao.cadastrarProposta(cod1, "toni@gmail.com", 100, 2);
		pregao.cadastrarProposta(cod1, "gago@gmail.com", 80, 4);
		pregao.cadastrarProposta(cod1, "jesus@gmail.com", 60, 1);
		ArrayList<Proposta> props = pregao.listarPropostas(cod1, 100, 0, 0);
		assertEquals(2, props.size());//era 3
		props = pregao.listarPropostas(cod1, 80, 0, 0);
		assertEquals(1, props.size());//era 2
		con.apagar();
	}
	


	
	@Test
	public void testarAvaliarContratanteComServicoNaoFinalizado() {
		con.apagar();
		InterfacePregao pregao = new MeuPregao();
		pregao.adicionarTipoDeServico(1, "Jardinagem");
		
		pregao.cadastrarContratante("pasn@ufpi.edu.br", "Pedro Santos Neto", "9999-9999");
		
		int cod1 = pregao.cadastrarServico("pasn@ufpi.edu.br", "Corte de grama em jardim de 20m2", 100, 2, 1);
		
		pregao.cadastrarPrestador("jesus@gmail.com", "Maria de Jesus", "9999-1111");
		pregao.adicionarServicoPrestador("jesus@gmail.com", 1);
		
		pregao.cadastrarProposta(cod1, "jesus@gmail.com", 90, 1);
		
		// Contratando propostas
		pregao.contratarProposta(cod1, "jesus@gmail.com");
		
		// Avaliando contratante sem finalizar
		pregao.avaliarContratante(cod1, 5, "Cliente excelente");
		
		// Listando por avaliacao de contratante
		ArrayList<Servico> servs = pregao.listarServicos(0, 0, 0, true, false, 4);
		assertEquals(0, servs.size());
		con.apagar();
	}
	
	
	@Test
	public void testarListarServicosMelhores() {
		con.apagar();
		InterfacePregao pregao = new MeuPregao();
		pregao.adicionarTipoDeServico(1, "Jardinagem");
		pregao.adicionarTipoDeServico(5, "Faxina");	
		
		pregao.cadastrarContratante("pasn@ufpi.edu.br", "Pedro Santos Neto", "9999-9999");
		pregao.cadastrarContratante("kelson@ufpi.edu.br", "Kelson Aires", "5555-5555");
		
		int cod1 = pregao.cadastrarServico("pasn@ufpi.edu.br", "Corte de grama em jardim de 20m2", 100, 2, 1);
		int cod2 = pregao.cadastrarServico("kelson@ufpi.edu.br", "Limpeza de salao de festa", 120, 1, 5);
		
		pregao.cadastrarPrestador("jesus@gmail.com", "Maria de Jesus", "9999-1111");
		pregao.adicionarServicoPrestador("jesus@gmail.com", 5);
		pregao.cadastrarPrestador("toni@gmail.com", "Toni Silva", "8888-1111");
		pregao.adicionarServicoPrestador("toni@gmail.com", 1);
		pregao.cadastrarPrestador("gago@gmail.com", "Antonio Gago", "7777-1111");
		pregao.adicionarServicoPrestador("gago@gmail.com", 1);
		pregao.cadastrarPrestador("zago@gmail.com", "Antonio zago", "6666-2222");
		pregao.adicionarServicoPrestador("zago@gmail.com", 1);
		pregao.cadastrarPrestador("vaga@gmail.com", "Antonia vaga", "5555-3333");
		pregao.adicionarServicoPrestador("vaga@gmail.com", 1);
		
		// Cadastrando propostas validas
		pregao.cadastrarProposta(cod1, "toni@gmail.com", 100, 2);
		pregao.cadastrarProposta(cod1, "gago@gmail.com", 90, 2);
		pregao.cadastrarProposta(cod1, "zago@gmail.com", 80, 1);
		pregao.cadastrarProposta(cod1, "vaga@gmail.com", 70, 1);
		
		pregao.cadastrarProposta(cod2, "jesus@gmail.com", 95, 1);
		
		// Contratando propostas
		pregao.contratarProposta(cod1, "toni@gmail.com");
		pregao.contratarProposta(cod2, "jesus@gmail.com");
		
		ArrayList<Proposta> props = pregao.listarPropostas(cod2, 0, 5, 0);
		assertEquals(1, props.size());
		
		props = pregao.listarPropostas(cod1, 0, 5, 0);
		assertEquals(3, props.size());
		props.clear();
		con.apagar();
	}
}
