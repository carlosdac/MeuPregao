package pregaoServicosSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOPrestador {
	
	public void inserir(Prestador c) throws SQLException, ClassNotFoundException {
		Connection con;
		try {
			con = Conexao.getConexao();
			Prestador cont= pesquisarPor(c.getNome());

			if(cont == null) {
				Statement st = con.createStatement();
				String cmd = "insert into prestador values (\'"+c.getEmail()+"\', \'"+c.getNome()+"\',\'"+c.getTelefone()+"\')";
				st.execute(cmd);
				
				for(TipoDeServico t:c.servicoRealizado) {
					cmd = "insert into prest_servPossiveis values(\'"+c.getNome()+"\',"+t+")";
					st.execute(cmd);
				}
				st.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void alterar(Prestador c, String nome, String email, String telefone) throws ClassNotFoundException, SQLException{
		//vai passar o contratante para alterar, ou vai ter que procurar?
		Connection con;
		try {
			con = Conexao.getConexao();
			Prestador cont= pesquisarPor(c.getNome());
			if(cont != null) {
				Statement st = con.createStatement();
				String cmd = "update prestador set nome= \'"+nome+"\', email= \'"+email+"\', telefone= \'"+telefone+"\' where nome = \'"+cont.getNome()+"\'";
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
			Prestador cont= pesquisarPor(nome);
			
			if(cont != null) {
				Statement st = con.createStatement();
				String cmd = "delete from prestador where nome = \'" +nome+"\'";
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
		String cmd = "delete from prestador";
		st.execute(cmd);
		st.close();	
	}
		

	public Prestador pesquisarPor(String nome)throws ClassNotFoundException, SQLException {
		Connection con;
		try {
			con = Conexao.getConexao();
	        Statement st = con.createStatement();
	        String cmd = "select * from prestador where nome = \'"+nome+"\'";
	        ResultSet rs = st.executeQuery(cmd);
	        
	        if(rs.next()) {
	        	String email = rs.getString("email");
	        	String telefone = rs.getString("telefone");
	        	Prestador p= new Prestador(email,nome,telefone);
	        	
	        	String cmdP = "select * from prest_servPossiveis where prestador = \'"+nome+"\'";
	        	ResultSet rsP = st.executeQuery(cmdP);
	        	
	        	if(rsP.next())
	        	{
	        		DAOTipoDeServico servicoP= new DAOTipoDeServico();
	        		TipoDeServico tp = servicoP.pesquisarPor(rsP.getInt("cod_tipo"));
	        		p.servicoRealizado.add(tp);
	        	}
	        	st.close();
	        	return p;
	        }   
	    	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return  null;
	}

	public ArrayList<Prestador> pesqusisarTodos() {
		Connection con;
		ArrayList<Prestador> pesquisa = new ArrayList<Prestador>();
		try {
			con = Conexao.getConexao();
	        Statement st = con.createStatement();
	        String cmd = "select * from prestador";
	        ResultSet rs = st.executeQuery(cmd);
	        
	        if(rs.next()) {
	        	String email = rs.getString("email");
	        	String telefone = rs.getString("telefone");
	        	String nome= rs.getString("nome");
	        	Prestador p= new Prestador(email,nome,telefone);
	        	pesquisa.add(p);
	        }
	        
	        st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return pesquisa;
	}
}
