package edu.ifpb.main;

import java.io.IOException;
import java.sql.SQLException;

import edu.ifpb.dao.Biblioteca;
import edu.ifpb.dao.LoginService;
import edu.ifpb.menu.Menu;

public class TesteDAO {
	public static void main(String[] args) throws IOException, SQLException {

		Biblioteca b = new Biblioteca();
		LoginService l = new LoginService();

		Menu menu = new Menu(b, l);
		menu.exibirMenu();

	}
}
