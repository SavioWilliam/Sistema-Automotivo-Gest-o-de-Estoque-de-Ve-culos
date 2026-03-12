package br.com.fecaf;

import java.util.Scanner;

public class sistema_frota {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        veiculoDAO dao = new veiculoDAO(); // Supondo que você tenha essa classe
        int opcao = 0;

        while (opcao != 5) { // O loop continua enquanto a opção não for sair
            System.out.println("\n--- MENU DE FROTA ---");
            System.out.println("1. Salvar Veículo");
            System.out.println("2. Filtrar Veículos");
            System.out.println("3. Listar todos");
            System.out.println("4. Deletar Veículo");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome o "enter" que fica no buffer

            switch (opcao) {
                case 1:
                    System.out.println("--- Cadastro de Veículo ---");
                    System.out.println("Id"); int id = Integer.parseInt(scanner.nextLine());;
                    System.out.print("Modelo: "); String modelo = scanner.nextLine();
                    System.out.print("Marca: "); String marca = scanner.nextLine();
                    System.out.print("Placa: "); String placa = scanner.nextLine();
                    System.out.print("Ano: "); int ano = scanner.nextInt();
                    System.out.print("Quilometragem: "); int km = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer após nextInt
                    System.out.print("Cor: "); String cor = scanner.nextLine();
                    System.out.print("Tipo: "); String tipo = scanner.nextLine();
                    System.out.print("Preço: "); int preco = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer
                    System.out.print("Status: "); String status = scanner.nextLine();

                    veiculo v = new veiculo(id, modelo, marca, placa, ano, km, cor, tipo, preco, status);
                    dao.salvar(v);
                    break;

                    case 2:
                    System.out.println("\nFiltrar por:");
                    System.out.println("1. Modelo | 2. Marca | 3. Placa | 4. Ano | 5. Cor | 6. ID");
                    System.out.print("Escolha o critério: ");
                    String criterio = scanner.next();
                    scanner.nextLine(); // Limpa buffer

                    System.out.print("Digite o valor para busca: ");
                    String valor = scanner.nextLine();

                    dao.filtrarVeiculos(criterio, valor);
                    break;

                case 3:
                    dao.listarTodos();
                    break;
                case 4:
                    System.out.print("Digite o ID para deletar: ");
                    id = Integer.parseInt(scanner.toString());
                    dao.excluir(id);
                    break;
                case 5:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        scanner.close();
    }
}








