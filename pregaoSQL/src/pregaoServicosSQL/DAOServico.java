package felipeDaRochaTorres.pregaoServicosSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DAOServico {
	public void inserir(Servico s) {
		Connection con;
		try {
			con = Conexao.getConexao();
			Statement st = con.createStatement();
			Servico cont= pesquisarPor(s.getCodigoServico());
			
			if(cont == null) {
				
				
				int contra=0,fina=0;
				
				if(s.isContratado()) {
					contra=1;
				}
				if(s.isFinalizado()) {
					fina=1;
				}
				
				DAOProposta daoP= new DAOProposta();
				Proposta p = daoP.pesquisarPor(s.getCodigoServico(), s.getValor());
				if(p==null) {//gambiarra para inserir a observacao do prestador
					p= new Proposta(1,"1",1,1);
					Prestador prestador = new Prestador("1","2","3");
					prestador.setAvaliacoesObservacoes2("");
					p.setPrestador(prestador);
				}
				
				String cmd = "insert into servico(descricao,valor,PrazoMaximo,ObservacoesContratante,ObservacoesPrestador,"
						+ "DataFinalizacao,NotaContratante,NotaPrestador,contratado,finalizado) values (\'"
						+s.getDescricao()+"\', "+s.getValor()+","+s.getPrazoMaximo()+",\'"
						+s.getContratante().getAvaliacoesObservacoes2()+"\',"
						+ "\'"+p.getPrestador().getAvaliacoesObservacoes2()
						+"\',"+s.getDataFinalizado().getTime()
						+","+s.getAvaliacaoMediaContratante()+","
						+p.getPrestador().getAvaliacaoMediaUsuario()+","+contra+","+fina+")";
				st.execute(cmd);
				
				cmd = "SET foreign_key_checks = 0";
				st.execute(cmd);
				
				cmd = "insert into serv_tipo(cod_Serv, cod_tipo) values ("+s.getCodigoServico()+","+s.getTipo()+")";
				st.execute(cmd);
				
				cmd = "insert into serv_contratante(cod_Serv, email_contratante) values ("
				+s.getCodigoServico()+",\'"+s.getEmailContratante()+"\')";
				st.execute(cmd);
				
				for(Proposta pro:s.propostas) {
					cmd = "insert into serv_props(cod_Serv, prestador, preco) values ("
							+s.getCodigoServico()+",\'"+pro.getPrestador().getNome()+"\',"+pro.getValor()+")";
							st.execute(cmd);
				}
				
				cmd = "SET foreign_key_checks = 1";
				st.execute(cmd);
				
				st.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void remover(int codigo) {
		Connection con;
		try {
			con = Conexao.getConexao();
			Servico cont= pesquisarPor(codigo);
			
			if(cont != null) {
				Statement st = con.createStatement();
				String cmd = "delete from servico where codigo = \'" +codigo+"\'";
				st.execute(cmd);
				
				cmd = "SET foreign_key_checks = 0";
				st.execute(cmd);
				
				cmd = "delete from serv_tipo where codigo = \'" +codigo+"\'";
				st.execute(cmd);
				
				cmd = "delete from serv_contratante where codigo = \'" +codigo+"\'";
				st.execute(cmd);
				
				cmd = "delete from serv_props where codigo = \'" +codigo+"\'";
				st.execute(cmd);
				
				cmd = "SET foreign_key_checks = 1";
				st.execute(cmd);
				
				st.close();
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void removerTodos()throws ClassNotFoundException, SQLException  {
		Connection con;
		con = Conexao.getConexao();
		Statement st = con.createStatement();
		String cmd = "delete from servico";
		st.execute(cmd);		
		
		cmd = "SET foreign_key_checks = 0";
		st.execute(cmd);
		
		cmd = "delete from serv_tipo ";
		st.execute(cmd);
		
		cmd = "delete from serv_contratante ";
		st.execute(cmd);
		
		cmd = "delete from serv_props";
		st.execute(cmd);
		
		cmd = "SET foreign_key_checks = 1";
		st.execute(cmd);

		
		st.close();	
	}
	
	public void alterar(Servico s) {
		Connection con;
		
		try {
			con = Conexao.getConexao();
			Statement st = con.createStatement();
			
			int contratado=0, finalizado=0;
			
			if(s.isContratado()) {
				contratado=1;
			}
			
			if(s.isFinalizado()) {
				finalizado=1;
			}
			
			String cmd = "update servico set contratado = "+contratado+",finalizado= "+finalizado+", "
					+ "DataFinalizacao="+s.getDataFinalizado().getTime()+" where codigo = "+s.getCodigoServico();
			st.execute(cmd);
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Servico pesquisarPor(int codigo) {
		Connection con;
		
		try {
			con = Conexao.getConexao();
			Statement st = con.createStatement();
			
			String cmd = "select * from servico where codigo = " + codigo;
			st.execute(cmd);
			
			ResultSet rs = st.executeQuery(cmd);
	
			if(rs.next()) {
				String descricao = rs.getString("descricao");
				double valor = rs.getDouble("valor");
				int prazoMaximo = rs.getInt("PrazoMaximo");
				String ObservacoesContratante= rs.getString("ObservacoesContratante");
				String ObservacoesPrestador = rs.getString("ObservacoesPrestador");
				long DataFinalizacao=rs.getLong("DataFinalizacao");
				int NotaContratante = rs.getInt("NotaContratante");
				int NotaPrestador = rs.getInt("NotaPrestador");
				
				boolean contratado;
				if(rs.getByte("contratado") == 1) {
					contratado = true;
				}else {
					contratado = false;
				}
				
				boolean finalizado;
				if(rs.getByte("finalizado") == 1) {
					finalizado = true;
				}else {
					finalizado = false;
				}
				
				String cmd3 = "SET foreign_key_checks = 0";
				st.execute(cmd3);
				
				String cmdP = "select *from serv_tipo where cod_Serv="+codigo;
				ResultSet rsP = st.executeQuery(cmdP);
				int CodTipServ=0;
				if(rsP.next())
				{
					CodTipServ= rsP.getInt("cod_tipo");
				}
				
				cmdP = "select *from serv_contratante where cod_Serv="+codigo;
				rsP = st.executeQuery(cmdP);
				String emailContrat="";
				if(rsP.next())
				{
					emailContrat= rsP.getString("email_contratante");
				}
				
				cmdP = "select *from serv_props where cod_Serv="+codigo;
				rsP = st.executeQuery(cmdP);
				
				double precoProp=0;
				ArrayList<Proposta> propostas_Serv= new ArrayList<Proposta>();
				
				if(rsP.next()){
					precoProp=rsP.getDouble("preco");
					
					DAOProposta daoP = new DAOProposta();
					Proposta prop = daoP.pesquisarPor(codigo,precoProp);
					prop.getPrestador().avaliacoesObservacoes.add(ObservacoesPrestador);
					prop.getPrestador().avaliacoesNota.add(NotaPrestador);
					
					propostas_Serv.add(prop);
				}
				
				
				
				Servico s = new Servico(emailContrat,descricao,valor,prazoMaximo, CodTipServ);
				Date data= new Date();
				data.setTime(DataFinalizacao);
				s.setDataFinalizado(data);
				s.setContratado(contratado);
				s.setFinalizado(finalizado);
				s.setAvaliacaoMediaContratante(NotaContratante);
				s.setObservacaoContratante(ObservacoesContratante);
				s.propostas=propostas_Serv;
				
				
				cmd3 = "SET foreign_key_checks = 1";
				st.execute(cmd3);
				return s;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<Servico> pesquisarTodos() {
		Connection con;
		
		try {
			con = Conexao.getConexao();
			Statement st = con.createStatement();
			
			String cmd = "select * from servico";
			
			ResultSet rs = st.executeQuery(cmd);
			
			ArrayList<Servico> pesquisa = new ArrayList<Servico>();
			
			if(rs.next()) {
				int codigo = rs.getInt("codigo");
				String descricao = rs.getString("descricao");
				double valor = rs.getDouble("valor");
				int prazoMaximo = rs.getInt("PrazoMaximo");
				String ObservacoesContratante= rs.getString("ObservacoesContratante");
				//String ObservacoesPrestador = rs.getString("ObservacoesPrestador");
				long DataFinalizacao=rs.getLong("DataFinalizacao");
				int NotaContratante = rs.getInt("NotaContratante");
				//int NotaPrestador = rs.getInt("NotaPrestador");
				
				boolean contratado;
				if(rs.getByte("contratado") == 1) {
					contratado = true;
				}else {
					contratado = false;
				}
				
				boolean finalizado;
				if(rs.getByte("finalizado") == 1) {
					finalizado = true;
				}else {
					finalizado = false;
				}
				
				String cmd3 = "SET foreign_key_checks = 0";
				st.execute(cmd3);
				
				String cmdP = "select * from serv_tipo where cod_Serv="+codigo;
				ResultSet rsP = st.executeQuery(cmdP);
				int CodTipServ=0;
				
				if(rsP.next())
				{
					CodTipServ= rsP.getInt("cod_tipo");
				}
				
				cmdP = "select * from serv_contratante where cod_Serv="+codigo;
				rsP = st.executeQuery(cmdP);
				String emailContrat="";
				if(rsP.next())
				{
					emailContrat= rsP.getString("email_contratante");
				}
				
				cmdP = "select *from serv_props where cod_Serv="+codigo;
				rsP = st.executeQuery(cmdP);
				
				double precoProp=0;
				ArrayList<Proposta> propostas_Serv= new ArrayList<Proposta>();
				
				if(rsP.next()){
					precoProp=rsP.getDouble("preco");
					
					DAOProposta daoP = new DAOProposta();
					Proposta prop = daoP.pesquisarPor(codigo, precoProp);
					
					propostas_Serv.add(prop);
				}
				
				DAOContratante daoC = new DAOContratante();
				Contratante c = new Contratante("!","2","3");
				for(Contratante co:daoC.pesquisarTodos()) {
					if(co.getEmail().equals(emailContrat)) {
						c=co;
					}
				}
				
				Servico s = new Servico(emailContrat,descricao,valor,prazoMaximo, CodTipServ);
				Date data= new Date();
				data.setTime(DataFinalizacao);
				s.setDataFinalizado(data);
				s.setContratado(contratado);
				s.setFinalizado(finalizado);
				s.setContratante(c);
				s.getContratante().avaliacoesNota.add(NotaContratante);
				s.getContratante().avaliacoesObservacoes.add(ObservacoesContratante);
				//s.setAvaliacaoMediaContratante(NotaContratante);
				//s.setObservacaoContratante(ObservacoesContratante);
				s.propostas=propostas_Serv;
				
				cmd3 = "SET foreign_key_checks = 1";
				st.execute(cmd3);
				
				pesquisa.add(s);
			}
			
			return pesquisa;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public void avaliarContratante (Servico s) {
		Connection con;
		try {
			con = Conexao.getConexao();
			Servico cont= pesquisarPor(s.getCodigoServico());
			
			if(cont != null) {
				Statement st = con.createStatement();
					
				String cmd = "update servico set ObservacoesContratante = \'"+s.getContratante().getAvaliacoesObservacoes2()+"\',"
						+"NotaContratante ="+s.getAvaliacaoMediaContratante()+" where codigo = "+s.getCodigoServico();
				
				st.execute(cmd);
				st.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void avaliarProposta (Proposta s) {
		Connection con;
		try {
			con = Conexao.getConexao();
			Servico cont= pesquisarPor(s.getCodigoServico());
			
			if(cont != null) {
				Statement st = con.createStatement();
					
				String cmd = "update servico set ObservacoesPrestador = \'"+s.getPrestador().getAvaliacoesObservacoes2()+"\',"
						+"NotaPrestador ="+s.getAvaliacaoMediaPrestador()+" where codigo = "+s.getCodigoServico();
				
				st.execute(cmd);
				st.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
}
