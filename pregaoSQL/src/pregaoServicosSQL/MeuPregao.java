package pregaoServicosSQL;

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
			Contratante c = pesquisarContratante(emailContratante);		 
			if(c==null || c.isServicoEmVigor()) {return -1;}//ja tem um contrato ativo no momento ou nao existe
			if(pesquisarTipoDeServicoCod(codTipoDeServico)==false){return -1;}
			
			c.setServicoEmVigor(true);
			Servico s = new Servico(emailContratante, descricao, valor, prazoMaximo, codTipoDeServico);
			s.setContratante(c);
			s.setCodigoServico(servicos.size()+1);
			servicos.add(s);
			
			return servicos.size();
		}

		// Lista todos os serviços que atendam aos critérios de pesquisa (>=)
		public ArrayList<Servico> listarServicos(double valor, int prazoMaximo, int tipo, boolean contratado, boolean finalizado, int avaliacaoMediaContratante){
			 ArrayList<Servico> s = new  ArrayList<Servico>();
			 
			 for(Servico serv:servicos) {
				 if( (serv.isContratado()==contratado) && (serv.isFinalizado()==finalizado) && ( valor==0 || serv.getValor()>=valor) && (prazoMaximo==0 || serv.getPrazoMaximo()>=prazoMaximo ) && (tipo==0 || serv.getTipo()==tipo) && (avaliacaoMediaContratante==0 || serv.getAvaliacaoMediaContratante()>=avaliacaoMediaContratante)) {
						s.add(serv);
				 }
			 }
			 
			 return s;
		}
		
		
		// Verifica que não existe já cadastrado como contratante. Email deve ser único.
		public void cadastrarContratante(String email, String nome, String telefone) {
			if(pesquisarContratante(email) == null) {
				Contratante c = new Contratante(email, nome, telefone);
				contratantes.add(c);
			}
		}
		
		
		
		// Verifica que não existe já cadastrado como prestador. Email deve ser único.
		public void cadastrarPrestador(String email, String nome, String telefone) {
			if(pesquisarPrestador(email) == null) {
				Prestador p = new Prestador(email, nome, telefone);
				prestadores.add(p);
			}
		}

		// Verifica que prestador e serviço existem.
		public void adicionarServicoPrestador(String email, int tipo) {
			Servico s = pesquisarServicoTipo(tipo);
				if( s != null) {
					for(Prestador p:prestadores) {
						if(p.getEmail().equals(email)) {
							TipoDeServico t = new TipoDeServico(s.getCodigoServico(), s.getDescricao());
							p.servicoRealizado.add(t);
						}
					}
				}
			
		}
		
		// Verifica que não existe já cadastrado. Código e descrição devem ser únicos.
		public void adicionarTipoDeServico(int cod, String descr) {
			if(pesquisarTipoDeServico(cod,descr)==null) {
				TipoDeServico t = new TipoDeServico(cod,descr);
				tipos.add(t);
			}
		}

		// Cadastra proposta de um prestador para um serviço. Deve verificar compatibilidade entre os tipos de servicós
		// prestados pelo prestador e o serviço solicitado. Valor e prazo devem ser no máximo igual ao previsto.
		public void cadastrarProposta(int codigoServico, String emailPrestador, double valor, int prazo) {
			Prestador p = pesquisarPrestador(emailPrestador);
			if(p==null) {return;}
			Servico s = pesquisarServicoCodigo(codigoServico);
			if(s==null) {return;}
			
			for(TipoDeServico prestado : p.servicoRealizado) {
				if(prestado.getCodigo()==s.getCodigoServico()) {
					if(s.getPrazoMaximo()>=prazo && s.getValor()>=valor) {
						Proposta prop = new Proposta(codigoServico, emailPrestador, valor, prazo);
						prop.setPrestador(p);
						s.propostas.add(prop);
					}
				}
			}
		}
		
		// valor 0 significa todas ou então apenas valores abaixo de um valor. 
		// prazo 0 significa todas ou então apenas prazos menores que um máximo.
		// avaliacao 0 significa todas ou então apenas avaliações acima de um limite mínimo.
		// somente as não contratadas e finalizadas
		public ArrayList<Proposta> listarPropostas(int codigoServico, double valor, int prazoMaximo, int avaliacaoMediaPrestador){
			ArrayList<Proposta> prop = new ArrayList<Proposta>();
			Servico s = pesquisarServicoCodigo(codigoServico);
			
			for(Proposta p : s.propostas) {
					if( (valor==0 || p.getValor()<=valor) && (prazoMaximo==0 || p.getPrazo()<=prazoMaximo) && (avaliacaoMediaPrestador==0 || p.getAvaliacaoMediaPrestador()>=avaliacaoMediaPrestador)) {
						prop.add(p);
					}
				
			}
			
			return prop;
		}

		// Marca serviço como contratado liberando para o cadastro de outros serviços pelo contratante
		public void contratarProposta(int codigoServico, String emailPrestador) {
			Servico s = pesquisarServicoCodigo(codigoServico);
			
			for(Proposta p : s.propostas) {
				if(p.getEmailPrestador()==emailPrestador) {
					p.setContratado(true);
					s.setFinalizado(false);
					s.setContratado(true);
					s.getContratante().setServicoEmVigor(true);
				}
			}
		}
		
		// Proposta deve ter sido contratada. Marca serviço como finalizado.
		public void finalizarServico(int codigoServico, Date data) {
			Servico s = pesquisarServicoCodigo(codigoServico);
			if(s == null) {return;}
			
			if(s.isContratado()) {
				s.setContratado(true);
				s.setDataFinalizado(data);
				s.setFinalizado(true);
			}
		}
		// Servico deve ter sido finalizado. 
		public void avaliarPrestador(int codigoServico, int nota, String observacoes) {
			Servico s = pesquisarServicoCodigo(codigoServico);
			if(s != null && s.isFinalizado()) {
				for(Proposta p : s.propostas) {
					if(p.isContratado()) {
						p.getPrestador().avaliacoesNota.add(nota);
						p.getPrestador().getAvaliacoesObservacoes().add(observacoes);
					}
				}
			}
		}
		
		// Servico deve ter sido finalizado.
		public void avaliarContratante(int codigoServico, int nota, String observacoes) {
			Servico s = pesquisarServicoCodigo(codigoServico);
			if(s!= null && s.isFinalizado()){
				s.getContratante().avaliacoesNota.add(nota);
				s.getContratante().getAvaliacoesObservacoes().add(observacoes);
			}
			
		}
}
