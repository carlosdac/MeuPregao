	package felipeDaRochaTorres.pregaoServicosSQL;

import java.util.ArrayList;
import java.util.Date;

public class Servico {
	private Contratante contratante;
	private double valor;
	private int prazoMaximo;
	private int tipo;
	private int codigoServico;
	private boolean contratado;
	private boolean finalizado;
	private String emailContratante;
	private String descricao;
	private Date dataFinalizado;
	
	ArrayList<Proposta> propostas = new ArrayList<Proposta>();
	
	public Servico(String emailContratante, String descricao, double valor, int prazoMaximo, int tipo) {
		this.setEmailContratante(emailContratante);
		this.setDescricao(descricao);
		this.setValor(valor);
		this.setPrazoMaximo(prazoMaximo);
		this.setTipo(tipo);
		setContratado(false);
		setFinalizado(false);
		this.dataFinalizado=new Date();//iniciando para nao ir null para o BD
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getPrazoMaximo() {
		return prazoMaximo;
	}

	public void setPrazoMaximo(int prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getAvaliacaoMediaContratante() {
		return contratante.getAvaliacaoMediaUsuario();
	}

	public void setAvaliacaoMediaContratante(int avaliacaoMediaContratante) {
		contratante.avaliacoesNota.add(avaliacaoMediaContratante);
	}

	public int getCodigoServico() {
		return codigoServico;
	}

	public void setCodigoServico(int codigoServico) {
		this.codigoServico = codigoServico;
	}

	public boolean isContratado() {
		return contratado;
	}

	public void setContratado(boolean contratado) {
		this.contratado = contratado;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public String getEmailContratante() {
		return emailContratante;
	}

	public void setEmailContratante(String emailContratante) {
		this.emailContratante = emailContratante;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Contratante getContratante() {
		return contratante;
	}

	public void setContratante(Contratante contratante) {
		this.contratante = contratante;
	}

	public Date getDataFinalizado() {
		return dataFinalizado;
	}

	public void setDataFinalizado(Date dataFinalizado) {
		this.dataFinalizado = dataFinalizado;
	}
	
	public void setObservacaoContratante(String s) {
		this.getContratante().setAvaliacoesObservacoes2(s);
	}
}
