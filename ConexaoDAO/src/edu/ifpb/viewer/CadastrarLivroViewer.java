package edu.ifpb.viewer;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

import edu.ifpb.controller.MenuController;

public class CadastrarLivroViewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022207868563420123L;
	private JTextField campoTitulo;
	private JTextField campoAutor;
	private JTextField campoISBN;
	private JTextField campoAno_publicacao;
	private JTextField campoCopias_disponiveis;
	private MenuController controller;

	public CadastrarLivroViewer(MenuController controller) {
		this.controller = controller;
		configurarJanela();
		adicionarComponentes();

	}

	private void configurarJanela() {
		setTitle("CADASTRO DE LIVRO");
		setSize(400, 300);
		setLocationRelativeTo(null);
	}

	private void adicionarComponentes() {
		JPanel painel = new JPanel(new GridLayout(6, 2, 10, 10));
		campoTitulo = new JTextField();
		campoAutor = new JTextField();
		campoISBN = new JTextField();
		campoAno_publicacao = new JTextField();
		campoCopias_disponiveis = new JTextField();
		JButton btnCadastrar = new JButton("Cadastrar");

		btnCadastrar.addActionListener(e -> cadastrarLivro());

		painel.add(new JLabel("Título:"));
		painel.add(campoTitulo);
		painel.add(new JLabel("Autor:"));
		painel.add(campoAutor);
		painel.add(new JLabel("ISBN:"));
		painel.add(campoISBN);
		painel.add(new JLabel("Ano (AAAA-MM-DD):"));
		painel.add(campoAno_publicacao);
		painel.add(new JLabel("Cópias:"));
		painel.add(campoCopias_disponiveis);
		painel.add(new JLabel(""));
		painel.add(btnCadastrar);

		add(painel);
	}

	public void cadastrarLivro() {
		try {
			String titulo = campoTitulo.getText().trim();
			String autor = campoAutor.getText().trim();
			String isbnTexto = campoISBN.getText().trim();
			String anoTexto = campoAno_publicacao.getText().trim();
			String copiasTexto = campoCopias_disponiveis.getText().trim();

			if (titulo.isEmpty() || autor.isEmpty() || isbnTexto.isEmpty() || anoTexto.isEmpty()
					|| copiasTexto.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			long ISBN;
			try {
				ISBN = Long.parseLong(isbnTexto);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "ISBN inválido! Insira um número válido.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			LocalDate anoPublicacao;
			try {
				anoPublicacao = LocalDate.parse(anoTexto);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Data inválida! Use o formato YYYY-MM-DD.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			int copias;
			try {
				copias = Integer.parseInt(copiasTexto);
				if (copias < 1) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Número de cópias inválido! Insira um número inteiro positivo.",
						"Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

			controller.cadastrarLivro(titulo, autor, ISBN, anoPublicacao, copias);

			JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
			this.dispose();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			
		}
		
		

	}
}
