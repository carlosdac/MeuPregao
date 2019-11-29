package felipeDaRochaTorres.pregaoServicosSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOContratante {

	public void inserir(Contratante c) throws SQLException, ClassNotFoundException {
		Connection con;
		try {
			con = Conexao.getConexao();
			Contratante cont= pesquisarPor(c.getNome());
			if(cont == null) {
				Statement st = con.createStatement();
				String cmd = "insert into contratante values (\'"+c.getEmail()+"\', \'"+c.getNome()+"\',\'"+c.getTelefone()+"\')";
				st.execute(cmd);
				st.close();
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void alterar(Contratante c, String nome, String email, String telefone, boolean servico) throws ClassNotFoundException, SQLException{
		//vai passar o contratante para alterar, ou vai ter que procurar
		Connection con;
		try {
			con = Conexao.getConexao();
			Contratante cont= pesquisarPor(c.getNome());
			if(cont != null) {
				Statement st = con.createStatement();
				String cmd = "update contratante set nome= \'"+nome+"\', email= \'"+email+"\', telefone= \'"+telefone+"\'"
						+ " where nome = \'"+cont.getNome()+"\'";
				st.execute(cmd);
				st.close();
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void remover(String nome) {
		Connection con;
		try {
			con = Conexao.getConexao();
			Contratante cont= pesquisarPor(nome);
			
			if(cont != null) {
				Statement st = con.createStatement();
				String cmd = "delete from contratante where nome = \'" +nome+"\'";
				System.out.print(cmd);
				st.execute(cmd);
				st.close();
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void removerTodos()throws ClassNotFoundException, SQLException {
		Connection con;
		con = Conexao.getConexao();
		Statement st = con.createStatement();
		
		String cmd = "SET foreign_key_checks = 0";
		st.execute(cmd);
		
		cmd = "delete from contratante";
		st.execute(cmd);
		
		cmd = "SET foreign_key_checks = 1";
		st.execute(cmd);
		
		st.close();	
		
	}
		

	public Contratante pesquisarPor(String nome) throws ClassNotFoundException, SQLException {
		Connection con;
		try {
			con = Conexao.getConexao();
	        Statement st = con.createStatement();
	        String cmd = "select * from contratante where nome = \'"+nome+"\'";
	        ResultSet rs = st.executeQuery(cmd);
	        
	        if(rs.next()) {
	        	String email = rs.getString("email");
	        	String telefone = rs.getString("telefone");
	        	Contratante c= new Contratante(email,nome,telefone);
	        	st.close();
	        	return c;
	        }   
	    	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return  null;
	}

	public ArrayList<Contratante> pesquisarTodos() {
		Connection con;
		ArrayList<Contratante> pesquisa = new ArrayList<Contratante>();
		try {
			con = Conexao.getConexao();
	        Statement st = con.createStatement();
	        String cmd = "select * from contratante";
	        ResultSet rs = st.executeQuery(cmd);
	        
	        if(rs.next()) {
	        	String email = rs.getString("email");
	        	String telefone = rs.getString("telefone");
	        	String nome= rs.getString("nome");
	        	Contratante p= new Contratante(email,nome,telefone);
	        	pesquisa.add(p);
	        }
	        
	        st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return pesquisa;
	}
	}