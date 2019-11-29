package felipeDaRochaTorres.pregaoServicosSQL;

public class Contratante extends Usuario{
	private boolean servicoEmVigor;
	
	public Contratante(String email, String nome, String telefone) {
		super(email, nome, telefone);
	}
	
	public boolean isServicoEmVigor() {
		return servicoEmVigor;
	}
	public void setServicoEmVigor(boolean contratoEmVigor) {
		this.servicoEmVigor = contratoEmVigor;
	}

}
