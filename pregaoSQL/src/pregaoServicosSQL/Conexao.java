package felipeDaRochaTorres.pregaoServicosSQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Conexao {
	private static Connection[] conexao = new Connection[10];
	private static boolean conectou = false;
	private static int pos = 0;
	
	public static Connection getConexao(){
		if (pos == 10){
			pos = 0;
		}
		if (!conectou) {
			try {
  			  Class.forName("com.mysql.jdbc.Driver");
			  for (int i = 0; i < 10; i++){
				conexao[i] = DriverManager.getConnection("jdbc:mysql://localhost/pregao", "root", "eld58f75");
			  }
			} catch (Exception e) {
			  System.exit(1);
			}
			conectou = true;	
		}
		return conexao[pos++];
	}
	
	public void apagar() {
		Connection con;
		try {
			con = Conexao.getConexao();
			Statement st = con.createStatement();
			
			String cmd = "SET foreign_key_checks = 0";
			st.execute(cmd);
			
			cmd="delete from servico";
			st.execute(cmd);
			
			cmd="delete from tipoDeServico";
			st.execute(cmd);
			
			cmd="delete from prestador ";
			st.execute(cmd);
			
			cmd="delete from proposta  ";
			st.execute(cmd);
			
			cmd="delete from contratante";
			st.execute(cmd);
			
			cmd="delete from serv_props";
			st.execute(cmd);
			
			cmd="delete from serv_contratante";
			st.execute(cmd);
			
			cmd="delete from serv_tipo ";
			st.execute(cmd);
			
			cmd="delete from prest_proposta  ";
			st.execute(cmd);
			
			cmd="delete from prest_servPossiveis";
			st.execute(cmd);
			
			cmd = "SET foreign_key_checks = 1";
			st.execute(cmd);
			
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}