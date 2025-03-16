package edu.ifpb.menu;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.ifpb.dao.Biblioteca;
import edu.ifpb.dao.LoginService;
import edu.ifpb.mode.Empregado;
import edu.ifpb.mode.Livro;

public class Menu {

	private Biblioteca b;
	private LoginService l;
	private Scanner sc;

	public Menu(Biblioteca biblioteca, LoginService loginService) {
		this.b = biblioteca;
		this.sc = new Scanner(System.in);
		this.l = loginService;

	}

	public void exibirMenu() throws SQLException {
		Biblioteca b = new Biblioteca();
		LoginService l = new LoginService();
		List<String> historico = b.relatorioEmprestimo();
		Map<String, Integer> relatorio = b.livrosMaisEmprestados();
		Scanner sc = new Scanner(System.in);

		// MÉTODO QUE VAI EXECUTAR O ARQUIVO TXT, ESTÁ COMENTADO PARA NÃO INSERIR
		// NOVAMENTE.
		// b.executarArquivo();

		/*boolean loginSucesso = false;

		while (!loginSucesso) {
			try {
				loginSucesso = fazerLogin(l, sc);
				if (!loginSucesso) {
					System.out.println("Credenciais inválidas. Tente novamente.\n");
				}
			} catch (SQLException e) {
				System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
				break; 
			}
		}

		if (loginSucesso) {
			System.out.println("Login bem-sucedido! Bem-vindo ao sistema.");
			
		}*/

		boolean continuar = true;
		while (continuar) {
			System.out.println("==== MENU ====");
			System.out.println("1 - cadastrar novo livro");
			System.out.println("2 - Buscar livro ");
			System.out.println("3 - Realizar emprestimo");
			System.out.println("4 - Realizar devolução");
			System.out.println("5 - Mostrar historico de emprestimos");
			System.out.println("6 - Relatorio de livros mais emprestados");
			System.out.println("7 - Sair do menu");
			
			System.out.println("Escolha uma opção:");
			int opcao = sc.nextInt();
			sc.nextLine();
			

			switch (opcao) {
			case 1:
				inserirNoMenu(b, sc);
				break;
			case 2:
				Livro livroBuscado = buscarLivro(b, sc);
				if (livroBuscado != null) {

					System.out.println("==== Livro encontrado ====");
					System.out.println("Título: " + livroBuscado.getTitulo());
					System.out.println("Autor: " + livroBuscado.getAutor());
					System.out.println("ISBN: " + livroBuscado.getISBN());
					System.out.println("Ano de Publicação: " + livroBuscado.getAno_publicacao());
					System.out.println("Cópias disponíveis: " + livroBuscado.getCopias_disponiveis());

				} else {
					System.out.println("Livro não encontrado");

				}
				break;
			case 3:
				Livro livroEmprestimo = null;
				try {
					livroEmprestimo = realizarEmprestimo(b, sc);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (livroEmprestimo != null) {
					System.out.println("Emprestimo concluído \n " + "informações do livro: ");
					System.out.println("Título: " + livroEmprestimo.getTitulo());
					System.out.println("Quantidade restantes:" + livroEmprestimo.getCopias_disponiveis());
				} else {
					System.out.println("Erro ao realizar emprestimo");
				}
				break;
			case 4:
				Livro livroDevolucao = null;
				try {
					livroDevolucao = realizarDevolucao(b, sc);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (livroDevolucao != null) {
					System.out.println("devolução concluída \n informações do livro: ");
					System.out.println("Titulo " + livroDevolucao.getTitulo() + " quantidade restante: "
							+ livroDevolucao.getCopias_disponiveis());
				}
				break;
			case 5:
				for (String registro : historico) {
					System.out.println(registro);

				}
				break;
			case 6:
				for (Map.Entry<String, Integer> entry : relatorio.entrySet()) {
					System.out.println("Livro: " + entry.getKey() + " Quantidade: " + entry.getValue());
				}
				break;
			case 7:
				continuar = false;
				System.out.println("Você saiu do menu");

			}
		}

	}

	 public boolean fazerLogin(String nome, String senha) throws SQLException {
	        return LoginService.Logar(nome, senha) != null;
	    }

		


	public static void inserirNoMenu(Biblioteca b, Scanner sc) {
		System.out.println("Insira os dados dos livros");

		System.out.print("Título: ");
		String titulo = sc.nextLine();

		System.out.print("Autor: ");
		String autor = sc.nextLine();

		System.out.print("ISBN: ");
		long ISBN = sc.nextLong();
		sc.nextLine();

		System.out.print("Ano de publicação: ");

		String dataTexto = sc.nextLine();

		LocalDate ano_publicacao = null;
		try {
			ano_publicacao = LocalDate.parse(dataTexto);
		} catch (Exception e) {
			System.out.println("Erro ao converter a data.");
			e.printStackTrace();
		}

		System.out.print("Número de cópias disponíveis: ");
		int copias_disponiveis = sc.nextInt();
		sc.nextLine();

		Livro livro = new Livro(titulo, autor, ISBN, ano_publicacao, copias_disponiveis);
		b.inserirLivro(livro);

	}

	public static Livro buscarLivro(Biblioteca b, Scanner sc) throws SQLException {
		System.out.println("Insira o ISBN para buscar o livro");

		System.out.println("ISBN: ");
		long isbn = sc.nextLong();
		Livro livro = b.buscarLivro(isbn);
		return livro;

	}

	public static Livro realizarEmprestimo(Biblioteca b, Scanner sc) throws SQLException {
		System.out.println("Insira o ISBN do livro para realizar o empréstimo");
		long isbn = sc.nextLong();

		Livro livro = b.buscarLivro(isbn);

		if (livro == null) {
			System.out.println("Livro não encontrado");
			return null;

		}

		System.out.println("Digite a quantidade de livros para realizar o emprestimo");
		int quantidade = sc.nextInt();
		if (quantidade <= 0) {
			System.out.println("Invalido");
			return null;
		}

		return b.emprestimo(livro, quantidade);
	}

	public static Livro realizarDevolucao(Biblioteca b, Scanner sc) throws SQLException {
		System.out.println("Insira o ISBN do livro para realizar a devolução");
		long isbn = sc.nextLong();
		Livro livro = b.buscarLivro(isbn);
		if (livro == null) {
			System.out.println("Livro não encontrado");
			return null;
		}
		System.out.println("Digite a quantidade de livros para realizar a devolução");
		int quantidade = sc.nextInt();
		if (quantidade <= 0) {
			System.out.println("Invalido");
			return null;
		}
		return b.devolucao(livro, quantidade);
	}

}
