package edu.ifpb.viewer;

import edu.ifpb.controller.MenuController;
import edu.ifpb.dao.Biblioteca;
import edu.ifpb.dao.LoginService;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

public class MainViewer extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6509527482067460698L;
	private MenuController controller;

	public MainViewer(MenuController controller) {
		this.controller = controller; 
		configurarJanela();
		adicionarComponentes();
	}

	public MainViewer() {
		Biblioteca biblioteca = new Biblioteca();
		LoginService loginService = new LoginService();
		this.controller = new MenuController(biblioteca, loginService); // Criação correta
		configurarJanela();
		adicionarComponentes();
	}

	private void configurarJanela() {
		setTitle("BIBLIOTECA MENU");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void adicionarComponentes() {
		JPanel painel = new JPanel(new GridLayout(7, 1, 10, 10));
		painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JButton btnCadastar = criarBotao("1 - Cadastrar livro", e -> abrirCadastroLivro());
		JButton btnBuscarLivro = criarBotao("2 - Buscar Livro", e -> abrirBuscarLivro());
		JButton btnEmprestimo = criarBotao("3 - Realizar emprestimo", e -> {
			try {
				abrirEmprestimo();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		JButton btnDevolucao = criarBotao("4 - Realizar devolução", e -> {
			try {
				abrirDevolucao();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		JButton btnRelatorioEmprestimo = criarBotao("5 - Relatorio livros mais emprestados", e -> relatorio());
		
		painel.add(btnCadastar);
		painel.add(btnBuscarLivro);
		painel.add(btnEmprestimo);
		painel.add(btnDevolucao);
		painel.add(btnRelatorioEmprestimo);
		add(painel);
		revalidate(); 
		repaint();
	}


	private JButton criarBotao(String texto, ActionListener listener) {
		JButton botao = new JButton(texto);
		botao.addActionListener(listener);
		botao.setFont(new Font("Arial", Font.BOLD, 14));
		return botao;
	}

	private void abrirCadastroLivro() {
		new CadastrarLivroViewer(controller).setVisible(true);
	}
	
	private void abrirBuscarLivro() {
		new BuscarLivro(controller).setVisible(true);
	}
	
	private void abrirEmprestimo() throws SQLException {
		new EmprestimoViewer(controller).setVisible(true);
	}
	
	private void abrirDevolucao() throws SQLException {
		new DevolucaoViewer(controller).setVisible(true);
	}
	
	private void relatorio() {
		new RelatorioEmprestimo(controller).setVisible(true);
	}

}
