package edu.ifpb.viewer;

import edu.ifpb.controller.MenuController;
import edu.ifpb.dao.Biblioteca;
import edu.ifpb.dao.LoginService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginViewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6597796865651816585L;
	private JTextField campoNome;
    private JPasswordField campoSenha; 
    private JButton botaoFazerLogin;
    private MenuController controller;

    public LoginViewer(MenuController controller) {
    	
    	LoginService loginService = new LoginService();
        Biblioteca biblioteca = new Biblioteca();
        this.controller = new MenuController(biblioteca, loginService); 
        configurarJanela();
        adicionarComponentes();
    }
    
    private void configurarJanela() {
        setTitle("LOGIN");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
    }

    private void adicionarComponentes() {
        JPanel painel = new JPanel(new GridLayout(10, 2, 2, 2)); // 3 linhas, 2 colunas
        painel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        
        campoNome = new JTextField();
        campoSenha = new JPasswordField();
        botaoFazerLogin = new JButton("ENTRAR");

        
        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);
        painel.add(new JLabel("Senha:"));
        painel.add(campoSenha);
        painel.add(new JLabel("")); 
        painel.add(botaoFazerLogin); 

     
        botaoFazerLogin.addActionListener(e -> realizarLogin());

        this.add(painel);
    }

    private void realizarLogin() {
        String nome = campoNome.getText();
        String senha = new String(campoSenha.getPassword()); 

        try {
            if (controller.fazerLogin(nome, senha)) {
                
                JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
                this.dispose(); // Fechar LoginViewer
                MainViewer mn = new MainViewer(this.controller);
                mn.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                    this, 
                    "Nome ou senha incorretos!", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (SQLException | IOException ex) {
           
            JOptionPane.showMessageDialog(
                this, 
                "Erro: " + ex.getMessage(), 
                "Falha no Sistema", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}