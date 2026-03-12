package br.com.fecaf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class veiculoDAO {

    // Método para Salvar Veículo
    public void salvar(veiculo v) {
        // 1. Remova o 'id' e o primeiro '?' da lista
        String sql = "INSERT INTO tb_veiculos (modelo, marca, placa, ano, quilometragem, cor, tipo, preco, status) VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection conn = conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 2. Comece os índices a partir do 1 para o modelo, ignorando o ID
            ps.setString(1, v.getModelo());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getPlaca());
            ps.setInt(4, v.getAno());
            ps.setInt(5, v.getQuilometragem()); // Verifique se o nome do método é getKm ou getQuilometragem
            ps.setString(6, v.getCor());
            ps.setString(7, v.getTipo());
            ps.setInt(8, (int) v.getPreco());
            ps.setString(9, v.getStatus());

            ps.executeUpdate(); // Use executeUpdate para INSERT
            System.out.println("Veículo salvo com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace(); // Isso vai te dar o detalhe exato do erro no console
        }
    }

    public List<veiculo> listarTodos() {
        List<veiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_veiculos";

        try (Connection conn = conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Criamos o objeto usando o construtor definido em veiculo.java
                veiculo v = new veiculo(
                        rs.getInt("id"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getString("placa"),
                        rs.getInt("ano"),
                        rs.getInt("quilometragem"),
                        rs.getString("cor"),
                        rs.getString("tipo"),
                        (int) rs.getDouble("preco"), // Ajuste para int se necessário
                        rs.getString("status")
                );
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    public static void filtrarVeiculos(String criterio, String valor) {
        String coluna = "";

        // Mapeia a escolha do usuário para a coluna real do banco de dados
        switch (criterio) {
            case "1":
                coluna = "modelo";
                break;
            case "2":
                coluna = "marca";
                break;
            case "3":
                coluna = "placa";
                break;
            case "4":
                coluna = "ano";
                break;
            case "5":
                coluna = "cor";
                break;
            case "6":
                coluna = "id";
                break;
            default:
                System.out.println("Critério inválido!");
                return;
        }

        // Monta a query com a coluna escolhida
        String sql = "SELECT * FROM tb_veiculos WHERE " + coluna + " LIKE ?";

        try (Connection conn = conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + valor + "%");

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n--- Resultados da Busca por " + coluna + " ---");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + " | " +
                            "Modelo: " + rs.getString("modelo") + " | " +
                            "Marca: " + rs.getString("marca") + " | " +
                            "Placa: " + rs.getString("placa"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao filtrar: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM tb_veiculos WHERE id = ?";

        try (Connection conn = conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            // executeUpdate retorna quantas linhas foram apagadas
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Sucesso: Veículo com ID " + id + " excluído!");
            } else {
                System.out.println("Aviso: Nenhum veículo encontrado com o ID " + id);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao excluir: " + e.getMessage());
        }
    }
    public void alterar(veiculo v) {
        String sql = "UPDATE tb_veiculos SET modelo=?, marca=?, placa=?, ano=?, quilometragem=?, cor=?, tipo=?, preco=?, status=? WHERE id=?";

        try (Connection conn = conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getModelo());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getPlaca());
            ps.setInt(4, v.getAno());
            ps.setInt(5, v.getQuilometragem()); // ou v.getQuilometragem()
            ps.setString(6, v.getCor());
            ps.setString(7, v.getTipo());
            ps.setDouble(8, v.getPreco());
            ps.setString(9, v.getStatus());
            ps.setInt(10, v.getId()); // Onde a mágica acontece

            ps.executeUpdate();
            System.out.println("Veículo atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}



