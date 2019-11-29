package felipeDaRochaTorres.pregaoServicosSQL;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOTipoDeServico {
	
	public void inserir(TipoDeServico t) {
		Connection con;
		
		try {
			TipoDeServico a = pesquisarPor(t.getCodigo());
			
			if(a == null) {
				con = Conexao.getConexao();
				Statement st = con.createStatement();
				
				String cmd = "insert into tipodeservico(codigo, descricao) values (" 
				+ t.getCodigo() + ", \'" + t.getDescricao() + "\')";
				
				st.execute(cmd);
				st.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterar(TipoDeServico t,int codigo,String desc) {
		Connection con;
		
		try {
			TipoDeServico a = pesquisarPor(t.getCodigo());
			
			if(a != null) {
				con = Conexao.getConexao();
				Statement st = con.createStatement();
				
				String cmd = "update tipodeservico set descricao = \'" + desc + "\' where codigo = " + codigo;
				st.execute(cmd);
				st.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void remover(int codigo) {
		Connection con;
		
		try {
			TipoDeServico a = pesquisarPor(codigo);
			
			if(a != null) {
				con = Conexao.getConexao();
				Statement st = con.createStatement();
				
				String cmd = "delete from tipodeservico where codigo = " + codigo;
				
				st.execute(cmd);
				st.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removerTodos() {
		Connection con;
		
		try {
			con = Conexao.getConexao();
			Statement st = con.createStatement();
			
			String cmd = "delete from tipodeservico";
			
			st.execute(cmd);
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public TipoDeServico pesquisarPor(int cod) {
		Connection con;
		
		try {
			con = Conexao.getConexao();
			Statement st = con.createStatement();
			
			String cmd = "select * from tipodeservico where codigo = " + cod;
			
			ResultSet rs = st.executeQuery(cmd);
			if(rs.next()) {
				int codigo = rs.getInt("codigo");
				String descricao = rs.getString("descricao");
				
				TipoDeServico a = new TipoDeServico(codigo, descricao);
				return a;
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<TipoDeServico> pesquisarTodos() {
		Connection con;
		
		try {
			con = Conexao.getConexao();
			Statement st = con.createStatement();
			
			String cmd = "select * tipodeservico";
			
			ResultSet rs = st.executeQuery(cmd);
			ArrayList<TipoDeServico> a = new ArrayList<TipoDeServico>();
			while(rs.next()) {
				int codigo = rs.getInt("codigo");
				String descricao = rs.getString("descricao");
				
				TipoDeServico b = new TipoDeServico(codigo, descricao);
				a.add(b);
			}	
			
			return a;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}