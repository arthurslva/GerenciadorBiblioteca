package edu.ifpb.viewer;

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.ifpb.controller.MenuController;
import edu.ifpb.mode.Livro;

public class BuscarLivro extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3314323526047936213L;
	private JTextField campoISBN;
	private MenuController controller;
	
	public BuscarLivro(MenuController controller) {
		this.controller = controller;
		configurarJanela();
		adicionarComponentes();
	}

	private void configurarJanela() {
		setTitle("BUSCAR LIVRO");
		setSize(400, 300);
		setLocationRelativeTo(null);
		
	}
	
	private void adicionarComponentes() {
		JPanel painel = new JPanel (new GridLayout(3, 2));
		campoISBN = new JTextField();
		JButton pesquisar = new JButton ("Pesquisar");
		
		pesquisar.addActionListener(e -> {
			try {
				buscarLivro();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		painel.add(new JLabel("ISBN:"));
		painel.add(campoISBN);
		painel.add(pesquisar);
		add(painel);
		setVisible(true);
	}
	
	public void buscarLivro() throws IOException {
		String ISBNTexto = campoISBN.getText();
		long ISBN = Long.parseLong(ISBNTexto);
		Livro livro = controller.buscarLivro(ISBN);
		
		if (livro != null) {
			String mensagem = "Titulo: " + livro.getTitulo() + " Copias disponiveis: " + livro.getCopias_disponiveis();
			  JOptionPane.showMessageDialog(this, mensagem, "Detalhes do Livro", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Livro não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } 
}

