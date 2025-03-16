package edu.ifpb.dao;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ifpb.mode.Empregado;

public class LoginService extends GenericDAO<Empregado> {

	public static boolean validacaoLogin(String senha) {
		String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{6,}$";
		
		return senha.matches(regex);
	}

	public static String hashSenha(String senha) {
		try {
			MessageDigest algoritimo = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algoritimo.digest(senha.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();

			for (byte b : messageDigest) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Empregado Logar(String nome, String senha) throws SQLException {

		// Valida se a senha esta no padrao fornecido pelo Regex (6 caracteres, letras e
		// numeros)
		if (!validacaoLogin(senha)) {
			System.out.println("Senha incorreta");
			return null;
		}

		// Converte a senha para Hash
		String senhaHash = hashSenha(senha);

		String query = "SELECT idEmpregado, nome, senha FROM empregado WHERE nome = ? and senha = ?";

		try (PreparedStatement ps = getConnection().prepareStatement(query)) {

			ps.setString(1, nome);
			ps.setString(2, senhaHash);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Empregado(rs.getString("nome"), rs.getString("senha"));
				} else {
					System.out.println("Login falhou! Verifique suas credenciais.");
					return null;
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
			throw e;
		}
	}
}
