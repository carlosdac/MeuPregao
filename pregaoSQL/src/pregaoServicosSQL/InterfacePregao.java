package pregaoServicosSQL;

import java.util.ArrayList;
import java.util.Date;

public interface InterfacePregao {
	
	// Só podemos ter um servico aberto por contratante em um dado momento.
	public int cadastrarServico(String emailContratante, String descricao, double valor, int prazoMaximo, int codTipoDeServico);

	// Lista todos os serviços que atendam aos critérios de pesquisa (>=)
	public ArrayList<Servico> listarServicos(double valor, int prazoMaximo, int tipo, boolean contratado, boolean finalizado, int avaliacaoMediaContratante);
	
	// Verifica que não existe já cadastrado como contratante. Email deve ser único.
	public void cadastrarContratante(String email, String nome, String telefone);

	// Verifica que não existe já cadastrado como prestador. Email deve ser único.
	public void cadastrarPrestador(String email, String nome, String telefone);

	// Verifica que prestador e serviço existem.
	public void adicionarServicoPrestador(String email, int tipo);
	
	// Verifica que não existe já cadastrado. Código e descrição devem ser únicos.
	public void adicionarTipoDeServico(int cod, String descr);

	// Cadastra proposta de um prestador para um serviço. Deve verificar compatibilidade entre os tipos de servicós
	// prestados pelo prestador e o serviço solicitado. Valor e prazo devem ser no máximo igual ao previsto.
	public void cadastrarProposta(int codigoServico, String emailPrestador, double valor, int prazo);
	
	// valor 0 significa todas ou então apenas valores abaixo de um valor. 
	// prazo 0 significa todas ou então apenas prazos menores que um máximo.
	// avaliacao 0 significa todas ou então apenas avaliações acima de um limite mínimo.
	// somente as não contratadas e finalizadas
	public ArrayList<Proposta> listarPropostas(int codigoServico, double valor, int prazoMaximo, int avaliacaoMediaPrestador);

	// Marca serviço como contratado liberando para o cadastro de outros serviços pelo contratante
	public void contratarProposta(int codigoServico, String emailPrestador);
	
	// Proposta deve ter sido contratada. Marca serviço como finalizado.
	public void finalizarServico(int codigoServico, Date data);

	// Servico deve ter sido finalizado. 
	public void avaliarPrestador(int codigoServico, int nota, String observacoes);
	
	// Servico deve ter sido finalizado.
	public void avaliarContratante(int codigoServico, int nota, String observacoes);
}
