package pregaoServicosSQL;

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
			Servico cont= pesquisarPor(s.getCodigoServico());
			
			if(cont == null) {
				Statement st = con.createStatement();
				
				int contra=0,fina=0;
				
				if(s.isContratado()) {
					contra=1;
				}
				if(s.isFinalizado()) {
					fina=1;
				}
				
				DAOProposta daoP= new DAOProposta();
				Proposta p = daoP.pesquisarPor(s.getCodigoServico(), s.getValor());
				
				String cmd = "insert into servico(descricao,valor,PrazoMaximo,ObservacoesContratante,ObservacoesPrestador,DataFinalizacao,"
						+ "NotaContratante,NotaPrestador,contratado,finalizado) values (\'"+s.getDescricao()+"\', "+s.getValor()+","+s.getPrazoMaximo()+",\'"
						+s.getContratante().getAvaliacoesObservacoes2()+"\',\'"+p.getPrestador().getAvaliacoesObservacoes2()+"\',"+s.getDataFinalizado().getTime()+","
						+s.getAvaliacaoMediaContratante()+","+p.getPrestador().getAvaliacaoMediaUsuario()+","+contra+","+fina;
				st.execute(cmd);
				cmd = "insert into serv_tipo(cod_Serv, cod_tipo) values ("+s.getCodigoServico()+","+s.getTipo()+")";
				st.execute(cmd);
				cmd = "insert into serv_contratante(cod_Serv, email_contratante) values ("+s.getCodigoServico()+",\'"+s.getEmailContratante()+"\')";
				st.execute(cmd);
				cmd = "insert into serv_props(cod_Serv, prestador, preco) values ("+s.getCodigoServico()+",\'"+p.getPrestador().getNome()+"\',"+p.getValor()+")";
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
		String cmd = "delete from prestador";
		st.execute(cmd);
		st.close();	
	}
	
	public void alterar(Servico s) {
		
	}
	
	public Servico pesquisarPor(int codigo) {
		Connection con;
		
		try {
			con = Conexao.getConexao();
			Statement st = con.createStatement();
			
			String cmd = "select * from servico where codigoSevico = " + codigo;
			
			ResultSet rs = st.executeQuery(cmd);
	
			if(rs.next()) {
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
				String nomePrestador="";
				double precoProp=0;
				if(rsP.next())
				{
					nomePrestador= rsP.getString("prestador");
					precoProp=rsP.getDouble("preco");
				}
				
				DAOProposta daoP = new DAOProposta();
				Proposta pro = daoP.pesquisarPor(codigo, precoProp);
				
				Servico s = new Servico(emailContrat,descricao,valor,prazoMaximo, CodTipServ);
				Date data= new Date();
				data.setTime(DataFinalizacao);
				s.setDataFinalizado(data);
				s.setContratado(contratado);
				s.setFinalizado(finalizado);
				s.setAvaliacaoMediaContratante(NotaContratante);
				s.setObservacaoContratante(ObservacoesContratante);
				s.propostas.add(pro);
				
				return s;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<Servico> pesquisarTodos() {
		return null;
	}
}
