package edu.ifpb.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.ifpb.mode.Livro;

public class Biblioteca extends GenericDAO<Livro> {

	public void inserirLivro(Livro livro) {
		String query = "INSERT INTO livro (titulo, autor, ISBN, ano_publicacao, copias_disponiveis) VALUES (?,?,?,?,?)";

		try (PreparedStatement ps = getConnection().prepareStatement(query)) {
			ps.setString(1, livro.getTitulo());
			ps.setString(2, livro.getAutor());
			ps.setLong(3, livro.getISBN());
			ps.setDate(4, java.sql.Date.valueOf(livro.getAno_publicacao()));
			ps.setInt(5, livro.getCopias_disponiveis());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public void executarArquivo() throws IOException {
		List<String> linhas = Files.readAllLines(Path.of("dados/livros.txt"));
		for (String lista : linhas) {
			String[] partes = lista.split(",");

			String titulo = partes[0].trim();
			String autor = partes[1].trim();
			long ISBN = Long.parseLong(partes[2].trim());
			LocalDate ano_publicacao = LocalDate.parse(partes[3].trim());
			int copias_disponiveis = Integer.parseInt(partes[4].trim());
			Livro livro = new Livro(titulo, autor, ISBN, ano_publicacao, copias_disponiveis);
			inserirLivro(livro);
		}

	}

	public Livro buscarLivro(long ISBN) {
		String query = "SELECT * FROM livro WHERE ISBN = ?";
		try (ResultSet rs = select(query, ISBN)) {
			if (rs != null && rs.next()) {
				return new Livro(rs.getString("titulo"), rs.getString("autor"), rs.getLong("ISBN"),
						rs.getDate("ano_publicacao").toLocalDate(), rs.getInt("copias_disponiveis"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void updateLivro(Livro livro) throws SQLException {
		String query = "UPDATE livro SET copias_disponiveis = ? where ISBN = ? ";
		try (PreparedStatement ps = getConnection().prepareStatement(query)) {
			ps.setInt(1, livro.getCopias_disponiveis());
			ps.setLong(2, livro.getISBN());

			ps.executeUpdate();

		}
	}

	public Livro emprestimo(Livro livro, int quantidade) throws SQLException {
		if (livro == null) {
			System.out.println("Não encontrado");
			return null;
		}
		if (livro.getCopias_disponiveis() == 0) {
			System.out.println("Não há cópias disponíveis para empréstimo.");
			return null;
		}

		if (livro.getCopias_disponiveis() >= quantidade) {
			livro.setCopias_disponiveis(livro.getCopias_disponiveis() - quantidade);
			updateLivro(livro);
		}

		livro.setData_emprestimo(LocalDate.now());
		inserirEmprestimo(livro, quantidade);
		return livro;

	}

	public Livro devolucao(Livro livro, int quantidade) throws SQLException {
		if (livro == null) {
			System.out.println("não encontrado");
		}
		if (quantidade <= 0) {
			System.out.println("ERRO");
		} else {
			livro.setCopias_disponiveis(livro.getCopias_disponiveis() + quantidade);
			updateLivro(livro);
		}
		return livro;
	}

	public Livro inserirEmprestimo(Livro livro, int quantidade) {
		String query = "INSERT INTO emprestimo (ISBN_livro, titulo, quantidade_emprestada, data_Emprestimo) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = getConnection().prepareStatement(query)) {
			ps.setLong(1, livro.getISBN());
			ps.setString(2, livro.getTitulo());
			ps.setInt(3, quantidade);
			ps.setDate(4, java.sql.Date.valueOf(livro.getData_emprestimo()));

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return livro;

	}

	public List<String> relatorioEmprestimo() {
		List<String> historico = new ArrayList<>();
		String query = "SELECT titulo, Data_emprestimo, quantidade_emprestada FROM emprestimo";

		try (ResultSet rs = select(query)) {
			while (rs != null && rs.next()) {
				historico.add("Livro: " + rs.getString("titulo") + " Data de emprestimo: "
						+ rs.getDate("Data_emprestimo").toLocalDate() + " Quantidade de emprestimo: "
						+ rs.getInt("quantidade_emprestada"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return historico;
	}
	
	public Map<String, Integer> livrosMaisEmprestados(){
		Map<String, Integer> relatorio = new LinkedHashMap<>();
		String query = "SELECT titulo, quantidade_emprestada " +
                "FROM emprestimo " +
                "ORDER BY quantidade_emprestada + 0 DESC";
		
		try (ResultSet rs = select(query)){
			while(rs != null && rs.next()) {
				String titulo = rs.getString("titulo");
				int quantidade = rs.getInt("quantidade_emprestada");
				
				relatorio.put(titulo, quantidade);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return relatorio;
		
		
	}
	
}
