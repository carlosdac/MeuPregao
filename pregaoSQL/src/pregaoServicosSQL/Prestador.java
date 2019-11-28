package pregaoServicosSQL;

import java.util.ArrayList;

public class Prestador extends Usuario{
	
	ArrayList<TipoDeServico> servicoRealizado = new ArrayList<TipoDeServico>(); 
	
	public Prestador(String email, String nome, String telefone) {
		super(email, nome, telefone);
	}

	
}