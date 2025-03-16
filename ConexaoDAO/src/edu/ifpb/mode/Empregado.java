package edu.ifpb.mode;

public class Empregado {
	
	private String nome;
	private String senha;
	
	public Empregado(String nome, String senha) {
		super();
		this.nome = nome;
		this.senha = senha;
	}

	public Empregado() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
