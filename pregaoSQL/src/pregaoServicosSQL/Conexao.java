package pregaoServicosSQL;


import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	private static Connection[] conexao = new Connection[10];
	private static boolean conectou = false;
	private static int pos = 0;

	private Conexao(){
	}
	
	public static Connection getConexao(){
		if (pos == 10){
			pos = 0;
		}
		if (!conectou) {
			try {
  			  Class.forName("com.mysql.jdbc.Driver");
			  for (int i = 0; i < 10; i++){
				conexao[i] = DriverManager.getConnection("jdbc:mysql://localhost/pregao", "root", "Sonaisbest12");
			  }
			} catch (Exception e) {
			  System.exit(1);
			}
			conectou = true;	
		}
		return conexao[pos++];
	}
}