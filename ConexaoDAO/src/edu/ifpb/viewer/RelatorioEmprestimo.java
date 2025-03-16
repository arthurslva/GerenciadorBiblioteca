package edu.ifpb.viewer;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.ifpb.controller.MenuController;

public class RelatorioEmprestimo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3639122679920049847L;
	private MenuController controller;

	public RelatorioEmprestimo(MenuController controller) {
		this.controller = controller;
		configurarJanela();
		adicionarComponentes();
	}

	public void configurarJanela() {
		setTitle("RELATORIO EMPRESTIMOS");
		setSize(400, 300);
		setLocationRelativeTo(null);
	}

	public void adicionarComponentes() {
		JPanel painel = new JPanel(new GridLayout(4, 0));
		JButton relatorio = new JButton("RELATORIO");

		relatorio.addActionListener(e -> Relatorio());
		painel.add(relatorio);
		add(painel);
		setVisible(true);

	}
	
	public Map<String, Integer> Relatorio() {
		    Map<String, Integer> relatorio = controller.relatorioEmprestimo(null); 
		    
		    StringBuilder mensagem = new StringBuilder("Livros mais emprestados:\n");

		    for (Map.Entry<String, Integer> entry : relatorio.entrySet()) {
		        mensagem.append("Título: ").append(entry.getKey())
		                .append(" - Empréstimos: ").append(entry.getValue()).append("\n");
		    }

		    JOptionPane.showMessageDialog(null, mensagem.toString(), "Relatório de Empréstimos", JOptionPane.INFORMATION_MESSAGE);
		    
		    return relatorio;
		}
	}


