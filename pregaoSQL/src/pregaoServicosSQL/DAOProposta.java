package felipeDaRochaTorres.pregaoServicosSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOProposta {
	
	public void inserir(Proposta u) {
		Connection con;
		try {
			con = Conexao.getConexao();
			Proposta p= pesquisarPor(u.getCodigoServico(),u.getValor());
			
			if(p == null) {
				Statement st = con.createStatement();
				
				String cmd = "insert into proposta (preco,prazo,nome_prest,email) values("
						+u.getValor()+","+u.getPrazo()+",\'"+u.getPrestador().getNome()+"\' , \'"+u.getEmailPrestador()+"\')";
				st.execute(cmd);
				
				String cmd3 = "SET foreign_key_checks = 0";
				st.execute(cmd3);
				
				cmd = "insert into prest_proposta values(\'"+u.getPrestador().getNome()+"\',\'"+u.getValor()+"\' , "
						+ "\'"+u.getEmailPrestador()+"\')";
				st.execute(cmd);
				
				cmd3 = "SET foreign_key_checks = 1";
				st.execute(cmd3);
				st.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void remover(int codigo, int valor) {
		Connection con;
		try {
			con = Conexao.getConexao();
			Proposta p= pesquisarPor(codigo,valor);
			
			if(p != null) {
				Statement st = con.createStatement();
				String cmd = "delete from proposta where valor = " +valor+", cod_servico= "+codigo;
				st.execute(cmd);
				
				DAOProposta daoP = new DAOProposta();
				Proposta prop = daoP.pesquisarPor(codigo,valor);
				
				String cmd3 = "SET foreign_key_checks = 0";
				st.execute(cmd3);
				
				cmd="delete from prest_proposta where prestador ="+prop.getPrestador().getNome();
				st.execute(cmd);
				
				cmd3 = "SET foreign_key_checks = 1";
				st.execute(cmd3);
				
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
		String cmd = "delete from proposta";
		
		String cmd3 = "SET foreign_key_checks = 0";
		st.execute(cmd3);
		
		cmd="delete from prest_proposta";
		st.execute(cmd);
		
		cmd3 = "SET foreign_key_checks = 1";
		st.execute(cmd3);
		
		
		st.execute(cmd);
		st.close();	
	}
	
	public void alterar(Proposta u, double preco, int prazo, String nome_prest, String email) {
		//vai passar o contratante para alterar, ou vai ter que procurar
		Connection con;
		try {
			con = Conexao.getConexao();
			Proposta p= pesquisarPor(u.getCodigoServico(),u.getValor());
			
			if(p != null) {
					Statement st = con.createStatement();
					String cmd = "update proposta set preco= "+preco+", prazo= "+prazo+", nome_prest= \'"+nome_prest+"\' , email = \'"
					+email+" \' where cod_servico = "+u.getCodigoServico()+", preco = "+u.getValor();
					st.execute(cmd);
					
					cmd="update prest_proposta set prestador = \'"+nome_prest+"\' where prestador = "+u.getPrestador().getNome();
					st.close();
				}	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public Proposta pesquisarPor(int codigo, double valor) {
		Connection con;
		try {
			con = Conexao.getConexao();
	        Statement st = con.createStatement();
	        String cmd = "select * from proposta where preco = "+valor+" AND cod_servico = "+codigo;
	        st.execute(cmd);
	        ResultSet rs = st.executeQuery(cmd);
	        
	        if(rs.next()) {
	        	double preco = rs.getDouble("preco");
	        	int prazo = rs.getInt("prazo");
	        	int cod_servico = rs.getInt("cod_servico");
	        	String nome_prest = rs.getString("nome_prest");
	        	String email = rs.getString("email");
	        	
	        	Proposta p = new Proposta(cod_servico,email,preco,prazo);
	        	
	        	DAOPrestador daoP = new DAOPrestador();
	        	p.setPrestador(daoP.pesquisarPor(nome_prest));
	        	//tabela não necessaria, pois já temos os dados seletados;
	        	return p;
	        }   
	    	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public ArrayList<Proposta> pesquisarTodos() {
		Connection con;
		try {
			con = Conexao.getConexao();
	        Statement st = con.createStatement();
	        String cmd = "select * from proposta";
	        ResultSet rs = st.executeQuery(cmd);
	        ArrayList<Proposta> pesquisa = new ArrayList<Proposta>();
	        
	        if(rs.next()) {
	        	double preco = rs.getDouble("preco");
	        	int prazo = rs.getInt("prazo");
	        	int cod_servico = rs.getInt("cod_servico");
	        	String nome_prest = rs.getString("nome_prest");
	        	String email = rs.getString("email");
	        	
	        	Proposta p = new Proposta(cod_servico,email,preco,prazo);
	        	
	        	DAOPrestador daoP = new DAOPrestador();
	        	p.setPrestador(daoP.pesquisarPor(nome_prest));
	
	        	pesquisa.add(p);
	        }   
	        return pesquisa;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
