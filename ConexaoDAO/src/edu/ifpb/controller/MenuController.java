package edu.ifpb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import edu.ifpb.dao.Biblioteca;
import edu.ifpb.dao.LoginService;
import edu.ifpb.menu.Menu;
import edu.ifpb.mode.Livro;

public class MenuController {

	private Menu menu;
	Biblioteca b = new Biblioteca();

	public MenuController(Biblioteca biblioteca, LoginService loginService) {
		this.menu = new Menu(biblioteca, loginService);
	}

	public boolean fazerLogin(String nome, String senha) throws IOException, SQLException {
		return menu.fazerLogin(nome, senha);
	}

	public void cadastrarLivro(String titulo, String autor, long ISBN, LocalDate ano, int copias) {
		Livro livro = new Livro(titulo, autor, ISBN, ano, copias);
		b.inserirLivro(livro);

	}
	
	public Livro buscarLivro(long ISBN) throws IOException{
		return b.buscarLivro(ISBN);
	}
	
	public Livro cadastrarEmprestimo(Livro livro, int quantidade) throws IOException, SQLException{
		return b.emprestimo(livro, quantidade);
	}
	
	public Livro cadastrarDevolucao(Livro livro, int quantidade) throws SQLException, SQLException {
		return b.devolucao(livro, quantidade);
	}

	public Map<String, Integer> relatorioEmprestimo (Livro livro) {
		return b.livrosMaisEmprestados();
	}

	}

