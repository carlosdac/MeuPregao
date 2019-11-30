package felipeDaRochaTorres.pregaoServicosSQL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class MeuPregao implements InterfacePregao{
		ArrayList<Contratante> contratantes = new ArrayList<Contratante>();
		ArrayList<Prestador> prestadores = new ArrayList<Prestador>();
		ArrayList<Servico> servicos = new ArrayList<Servico>();
		ArrayList<TipoDeServico> tipos = new ArrayList<TipoDeServico>();
		
		public Servico pesquisarServicoTipo(int tipo) {
			for(Servico c:servicos) {
				if(c.getTipo()==tipo) {
					return c;
				}
			}
			return null;
		}
		
		public Servico pesquisarServicoCodigo(int codigo) {
			for(Servico c:servicos) {
				if(c.getCodigoServico()==codigo) {
					return c;
				}
			}
			return null;
		}
		
		public Contratante pesquisarContratante(String emailContratante) {
			if(emailContratante!="") {
				for(Contratante c:contratantes) {
					if(c.getEmail().equals(emailContratante)) {
						return c;
					}
				}
			}
			
			return null;
		}
		
		public Prestador pesquisarPrestador(String email) {
			for(Prestador p:prestadores) {
				if(p.getEmail().equals(email)) {
					return p;
				}
			}
			return null;
		}
		
		public TipoDeServico pesquisarTipoDeServico(int cod, String descr){
			for(TipoDeServico t:tipos) {
				if(t.getCodigo()==cod && t.getDescricao().equals(descr)) {
					return t;
				}
			}
			return null;
		}
		
		public boolean pesquisarTipoDeServicoCod(int cod){
			for(TipoDeServico t:tipos) {
				if(t.getCodigo()==cod) {
					return true;
				}
			}
			return false;
		}
		
		// Só podemos ter um servico aberto por contratante em um dado momento.
		public int cadastrarServico(String emailContratante, String descricao, double valor, int prazoMaximo, int codTipoDeServico) {
			Contratante c= new Contratante(emailContratante, "dcd", "evejvn");
			
			DAOContratante daoC = new DAOContratante();
			int achou=0;
			
			ArrayList<Contratante> c2 =daoC.pesquisarTodos();
			

			for(Contratante c1:c2) {
				if(c1.getEmail().equals(emailContratante.trim())){
					c=c1;
					achou=1;
				}
			}
			
			if(achou==0) {
				return -2;//n�o achou o contratante
			}
			
			
			DAOServico daoS = new DAOServico();
			
			for(Servico serv:daoS.pesquisarTodos()) {
				if(serv.getContratante().getEmail()==emailContratante && !serv.isContratado()) {
					return -1;//ja est� cadastrado e n�o foi contratado ainda
				}
			}
			
			Servico s = new Servico(emailContratante, descricao, valor, prazoMaximo, codTipoDeServico);
			s.setContratante(c);
			
			daoS.inserir(s);
			
			return 1;
		}

		// Lista todos os serviços que atendam aos critérios de pesquisa (>=)
		public ArrayList<Servico> listarServicos(double valor, int prazoMaximo, int tipo, boolean contratado, boolean finalizado, int avaliacaoMediaContratante){
			DAOServico daoS = new DAOServico();
			ArrayList<Servico> s = new  ArrayList<Servico>();
			 
			 for(Servico serv:daoS.pesquisarTodos()) {
				 if( (serv.isContratado()==contratado) && (serv.isFinalizado()==finalizado) && ( valor==0 || serv.getValor()>=valor) && (prazoMaximo==0 || serv.getPrazoMaximo()>=prazoMaximo ) && (tipo==0 || serv.getTipo()==tipo) && (avaliacaoMediaContratante==0 || serv.getAvaliacaoMediaContratante()>=avaliacaoMediaContratante)) {
						s.add(serv);
				 }
			 }
			 
			 return s;
		}
		
		
		// Verifica que não existe já cadastrado como contratante. Email deve ser único.
		public void cadastrarContratante(String email, String nome, String telefone) {
			DAOContratante daoC = new DAOContratante();
			Contratante c = new Contratante("1","2","3") ;
			try {
				c = daoC.pesquisarPor(nome);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(c!=null) {return;}//ja existe um contratante cadastrado com esses dados
			
			c = new Contratante(email,nome,telefone);
			try {
				daoC.inserir(c);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
		// Verifica que não existe já cadastrado como prestador. Email deve ser único.
		public void cadastrarPrestador(String email, String nome, String telefone) {
			DAOPrestador daoP = new DAOPrestador();
			Prestador p = new Prestador("1","2","3");
			try {
				p = daoP.pesquisarPor(nome);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(p!=null) {return;}//ja existe um contratante cadastrado com esses dados
			
			p = new Prestador(email,nome,telefone);
			try {
				daoP.inserir(p);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		// Verifica que prestador e serviço existem.
		public void adicionarServicoPrestador(String email, int tipo) {
			DAOTipoDeServico daoT = new DAOTipoDeServico();
			DAOPrestador daoP = new DAOPrestador();
			
			TipoDeServico t = daoT.pesquisarPor(tipo);
			if( t != null) {
				for(Prestador p:daoP.pesqusisarTodos()) {
					if(p.getEmail()== email) {
						daoP.inserirTipoDeServio(p, t);
					}
				}
			}
			
		}
		
		// Verifica que não existe já cadastrado. Código e descrição devem ser únicos.
		public void adicionarTipoDeServico(int cod, String descr) {
			DAOTipoDeServico daoT = new DAOTipoDeServico();
			if(daoT.pesquisarPor(cod) != null) {
				return;// ja existe
			}
			
			TipoDeServico t =new TipoDeServico(cod,descr);
			daoT.inserir(t);
			
		}

		// Cadastra proposta de um prestador para um serviço. Deve verificar compatibilidade entre os tipos de servicós
		// prestados pelo prestador e o serviço solicitado. Valor e prazo devem ser no máximo igual ao previsto.
		public void cadastrarProposta(int codigoServico, String emailPrestador, double valor, int prazo) {
			DAOProposta daoP = new DAOProposta();
			DAOPrestador daoPrest = new DAOPrestador();
			DAOServico daoS = new DAOServico();
			
			Prestador p = new Prestador("1","2","3");
			
			for(Prestador p1:daoPrest.pesqusisarTodos()) {
				if(p1.getEmail()==emailPrestador) {
					p=p1;
				}
			}
			if(p.getNome()=="2") {return;}//prestador nao existe
			
			Servico s = daoS.pesquisarPor(codigoServico);
			if(s==null) {return;}//Servico nao existe
			
			for(TipoDeServico prestado : p.servicoRealizado) {
				if(prestado.getCodigo()==s.getTipo()) {
					if(s.getPrazoMaximo()>=prazo && s.getValor()>=valor) {
						Proposta prop = new Proposta(codigoServico, emailPrestador, valor, prazo);
						prop.setPrestador(p);
						
						daoP.inserir(prop);
					}
				
				}
			}
			
		}
		
		// valor 0 significa todas ou então apenas valores abaixo de um valor. 
		// prazo 0 significa todas ou então apenas prazos menores que um máximo.
		// avaliacao 0 significa todas ou então apenas avaliações acima de um limite mínimo.
		// somente as não contratadas e finalizadas
		public ArrayList<Proposta> listarPropostas(int codigoServico, double valor, int prazoMaximo, int avaliacaoMediaPrestador){
			DAOServico daoS = new DAOServico();
			ArrayList<Proposta> prop = new ArrayList<Proposta>();
			
			for(Proposta p :daoS.pesquisarPor(codigoServico).propostas) {
					if( (valor==0 || p.getValor()<=valor) && (prazoMaximo==0 || p.getPrazo()<=prazoMaximo) && (avaliacaoMediaPrestador==0 || p.getAvaliacaoMediaPrestador()>=avaliacaoMediaPrestador)) {
						prop.add(p);
					}
				
			}
			
			return prop;
		}

		// Marca serviço como contratado liberando para o cadastro de outros serviços pelo contratante
		public void contratarProposta(int codigoServico, String emailPrestador) {
			DAOServico daoS = new DAOServico();
			Servico s = daoS.pesquisarPor(codigoServico);
			
			for(Proposta p : s.propostas) {
				if(p.getEmailPrestador()==emailPrestador) {
					//s.getContratante().setServicoEmVigor(true);
					//p.setContratado(true);
					s.setFinalizado(false);
					s.setContratado(true);
					
					daoS.alterar(s);
				}
			}
		}
		
		// Proposta deve ter sido contratada. Marca serviço como finalizado.
		public void finalizarServico(int codigoServico, Date data) {
			DAOServico daoS = new DAOServico();
			Servico s = daoS.pesquisarPor(codigoServico);
			if(s == null) {return;}//nao existe
			
			if(s.isContratado()) {
				s.setContratado(true);
				s.setDataFinalizado(data);
				s.setFinalizado(true);
				
				daoS.alterar(s);//finaliza e coloca a data no BD
			}
		}
		
		// Servico deve ter sido finalizado. 
		public void avaliarPrestador(int codigoServico, int nota, String observacoes) {
			DAOServico daoS = new DAOServico();
			Servico s = daoS.pesquisarPor(codigoServico);
			
			DAOProposta daoP = new DAOProposta();
			if(s != null && s.isFinalizado()) {
				//Proposta p = daoP.pesquisarPor(s.getCodigoServico(), s.)
				
				//p.getPrestador().avaliacoesNota.add(nota);
				//p.getPrestador().getAvaliacoesObservacoes().add(observacoes);
				//daoS.avaliarProposta(p);
			}
		}
		
		// Servico deve ter sido finalizado.
		public void avaliarContratante(int codigoServico, int nota, String observacoes) {
			DAOServico daoS = new DAOServico();
			Servico s = daoS.pesquisarPor(codigoServico);
			
			if(s!= null && s.isFinalizado()){
				s.getContratante().avaliacoesNota.add(nota);
				s.getContratante().getAvaliacoesObservacoes().add(observacoes);
				daoS.avaliarContratante(s);
			}
			
		}
}
