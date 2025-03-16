package edu.ifpb.viewer;

import java.awt.GridLayout;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.ifpb.controller.MenuController;
import edu.ifpb.mode.Livro;

public class DevolucaoViewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1576179277121038228L;
	private JTextField campoISBN;
	private JTextField campoQuantidade;
	private MenuController controller;

	public DevolucaoViewer(MenuController controller) throws SQLException {
		this.controller = controller;
		configurarJanela();
		adicionarComponentes();
	}

	private void configurarJanela() {
		setTitle("DEVOLUÇÃO");
		setSize(1080, 300);
		setLocationRelativeTo(null);
	}

	private void adicionarComponentes() throws SQLException {
		JPanel painel = new JPanel(new GridLayout(4, 5));
		campoISBN = new JTextField();
		campoQuantidade = new JTextField();
		JButton Devolucao = new JButton("devolução");

		Devolucao.addActionListener(e -> {
			try {
				devolucao();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		painel.add(new JLabel("ISBN:"));
		painel.add(new JLabel("Quantidade"));
		painel.add(campoISBN);
		painel.add(campoQuantidade);
		painel.add(Devolucao);
		add(painel);
		setVisible(true);
	}

	public void devolucao() throws IOException, SQLException {
		String ISBNtexto = campoISBN.getText();
		String QuantidadeTexto = campoQuantidade.getText();

		try {
			long ISBN = Long.parseLong(ISBNtexto);
			int quantidade = Integer.parseInt(QuantidadeTexto);
			Livro livro = controller.buscarLivro(ISBN);

			if (livro == null) {
				JOptionPane.showMessageDialog(this, "ISBN INCORRETO");
			}

			if (quantidade < 0) {
				JOptionPane.showMessageDialog(this, "INSIRA UM NUMERO VÁLIDO");
			}

			controller.cadastrarDevolucao(livro, quantidade);
			JOptionPane.showMessageDialog(this, "Devolução realizada com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "formato dos valores inseridos errado " + JOptionPane.ERROR_MESSAGE);

		}
	}
}
