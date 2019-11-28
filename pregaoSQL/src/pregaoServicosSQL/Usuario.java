package pregaoServicosSQL;

import java.util.ArrayList;

public class Usuario {
	private String email;
	private String nome;
	private String telefone;
	private int avaliacaoMediaUsuario;
	
	ArrayList<String> avaliacoesObservacoes = new ArrayList<String>();
	ArrayList<Integer> avaliacoesNota = new ArrayList<Integer>();
	
	public Usuario(String email, String nome, String telefone) {
		this.setEmail(email);
		this.setNome(nome);
		this.setTelefone(telefone);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public int getAvaliacaoMediaUsuario() {
		if(this.avaliacoesNota.size()!=0) {
			avaliacaoMediaUsuario=0;
			for(int i:this.avaliacoesNota) {
				avaliacaoMediaUsuario+=i;
			}
			return avaliacaoMediaUsuario/this.avaliacoesNota.size();
		}
		return 0;
	}

	public ArrayList<String> getAvaliacoesObservacoes() {
		return avaliacoesObservacoes;
	}
	
	public String getAvaliacoesObservacoes2() {
		return avaliacoesObservacoes.get(avaliacoesObservacoes.size());
	}

	public void setAvaliacoesObservacoes(ArrayList<String> avaliacoesObservacoes) {
		this.avaliacoesObservacoes = avaliacoesObservacoes;
	}
	
	public void setAvaliacoesObservacoes2(String s) {
		this.avaliacoesObservacoes.add(s);
	}

	
}
