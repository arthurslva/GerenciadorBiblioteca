package edu.ifpb.mode;

import java.time.LocalDate;
import java.util.Objects;

public class Livro {
	
	private String titulo;
	private String autor;
	private long ISBN;
	private LocalDate ano_publicacao;
	private int copias_disponiveis;
	private LocalDate Data_emprestimo;
	
	public Livro (){}

	public Livro(String titulo, String autor, long ISBN, LocalDate ano_publicacao, int copias_disponiveis) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.ISBN = ISBN;
		this.ano_publicacao = ano_publicacao;
		this.copias_disponiveis = copias_disponiveis;
		
	}

	public Livro(long ISBN) {
		// TODO Auto-generated constructor stub
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public long getISBN() {
		return ISBN;
	}

	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}

	public LocalDate getAno_publicacao() {
		return ano_publicacao;
	}

	public void setAno_publicacao(LocalDate ano_publicacao) {
		this.ano_publicacao = ano_publicacao;
	}

	public int getCopias_disponiveis() {
		return copias_disponiveis;
	}

	public void setCopias_disponiveis(int copias_disponiveis) {
		this.copias_disponiveis = copias_disponiveis;
	}
	
	public LocalDate getData_emprestimo() {
		return Data_emprestimo;
	}

	public void setData_emprestimo(LocalDate localDate) {
		Data_emprestimo = localDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ISBN);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		return ISBN == other.ISBN;
	}

	@Override
	public String toString() {
		return "Livro titulo:" + titulo + ", autor:" + autor + ", ISBN:" + ISBN + ", ano_publicacao:" + ano_publicacao
				+ ", copias_disponiveis:" + copias_disponiveis;
	}

	
	
	
	
	

}
