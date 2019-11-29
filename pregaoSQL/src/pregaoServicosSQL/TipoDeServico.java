package felipeDaRochaTorres.pregaoServicosSQL;

public class TipoDeServico {
	private int cod;
	private String descr;
	
	public TipoDeServico(int cod, String descr) {
		this.cod = cod;
		this.descr = descr;
	}
	
	public String getDescricao() {
		return descr;
	}
	public void setDescricao(String descr) {
		this.descr = descr;
	}
	public int getCodigo() {
		return cod;
	}
	public void setCodigo(int cod) {
		this.cod = cod;
	}
	
}
