package pregaoServicosSQL;

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ContratanteTeste(); //sobrar torres e felipe
		//PrestadorTeste();//sobrar rocha e felipe 
		
		
		
		
	}

	public static void ContratanteTeste() throws ClassNotFoundException, SQLException {
		DAOContratante daoc = new DAOContratante();
		Contratante c = new Contratante("felipe@gmail.com","Felipe","3220");
		
		daoc.removerTodos();
		daoc.inserir(c);
		daoc.alterar(c, "Rocha", "rocha@gmail.com", "3220", true);
		daoc.inserir(c);
		daoc.alterar(c, "Torres", "torres@gmail.com", "2692", true);
		daoc.inserir(c);
		//daoc.remover("Rocha");
	}
	
	public static void PrestadorTeste() throws ClassNotFoundException, SQLException {
		DAOPrestador daop = new DAOPrestador(); 
		Prestador p = new Prestador("felipe2@gmail.com","Felipe2","3220");
		
		daop.removerTodos();
		daop.inserir(p);
		daop.alterar(p, "Rocha2", "rocha2@gmail.com", "3220");
		daop.inserir(p);
		daop.alterar(p, "Torres2", "torres2@gmail.com", "2692");
		daop.inserir(p);
		daop.remover("torres2");
		
	}
}
