package br.com.fecaf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/frota_veiculos";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado. Verifique o .jar!");
            return null;
        } catch (SQLException e) {
            System.err.println("Erro crítico na Conexão: " + e.getMessage());
            return null;
        }
    }
}