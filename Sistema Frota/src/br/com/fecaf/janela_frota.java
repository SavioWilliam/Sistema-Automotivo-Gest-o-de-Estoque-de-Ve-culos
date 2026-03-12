package br.com.fecaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class janela_frota extends JFrame {
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private veiculoDAO dao = new veiculoDAO();

    public janela_frota() {
        setTitle("Gerenciamento de Frota - Sávio");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- PAINEL SUPERIOR: FILTRO E BUSCA ---
        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[] opcoes = {"Modelo", "Marca", "Placa"};
        JButton btnListar = new JButton("Listar Todos");

        painelFiltro.add(new JLabel("Filtrar por:"));
        painelFiltro.add(btnListar);

        // --- PAINEL DE BOTÕES DE AÇÃO ---
        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        painelAcoes.add(btnNovo);
        painelAcoes.add(btnEditar);
        painelAcoes.add(btnExcluir);

        JPanel painelNorte = new JPanel(new BorderLayout());
        painelNorte.add(painelFiltro, BorderLayout.WEST);
        painelNorte.add(painelAcoes, BorderLayout.EAST);
        add(painelNorte, BorderLayout.NORTH);

        // --- TABELA ---
        String[] colunas = {"ID", "Modelo", "Marca", "Placa", "Ano", "KM", "Cor", "Tipo", "Preço", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        tabela.setAutoCreateRowSorter(true); // Permite ranquear clicando nas colunas
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // --- AÇÕES DOS BOTÕES ---

        btnListar.addActionListener(e -> carregarTabela(dao.listarTodos()));

        btnNovo.addActionListener(e -> abrirFormulario(null));

        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                // Criando o objeto com os dados da linha selecionada
                int id = Integer.parseInt(modeloTabela.getValueAt(linha, 0).toString());
                veiculo vSelecionado = new veiculo(
                        id,
                        modeloTabela.getValueAt(linha, 1).toString(),
                        modeloTabela.getValueAt(linha, 2).toString(),
                        modeloTabela.getValueAt(linha, 3).toString(),
                        Integer.parseInt(modeloTabela.getValueAt(linha, 4).toString()),
                        Integer.parseInt(modeloTabela.getValueAt(linha, 5).toString()),
                        modeloTabela.getValueAt(linha, 6).toString(),
                        modeloTabela.getValueAt(linha, 7).toString(),
                        (int) Double.parseDouble(modeloTabela.getValueAt(linha, 8).toString()),
                        modeloTabela.getValueAt(linha, 9).toString()
                );
                abrirFormulario(vSelecionado);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um veículo!");
            }
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                int id = Integer.parseInt(modeloTabela.getValueAt(linha, 0).toString());
                dao.excluir(id);
                carregarTabela(dao.listarTodos());
            }
        });

        carregarTabela(dao.listarTodos());
        setLocationRelativeTo(null);
    }

    private void carregarTabela(List<veiculo> lista) {
        modeloTabela.setRowCount(0);
        for (veiculo v : lista) {
            modeloTabela.addRow(new Object[]{
                    v.getId(), v.getModelo(), v.getMarca(), v.getPlaca(),
                    v.getAno(), v.getQuilometragem(), v.getCor(), v.getTipo(),
                    v.getPreco(), v.getStatus()
            });
        }
    }

    // FORMULÁRIO DE CADASTRO/EDIÇÃO (Corrigido para evitar erro de symbol)
    private void abrirFormulario(veiculo vParaEditar) {
        JDialog dialog = new JDialog(this, vParaEditar == null ? "Novo" : "Editar", true);
        dialog.setLayout(new GridLayout(11, 2, 5, 5));

        // Declaramos como final para o botão confirmar não dar erro de escopo
        final JTextField tModelo = new JTextField(vParaEditar == null ? "" : vParaEditar.getModelo());
        final JTextField tMarca = new JTextField(vParaEditar == null ? "" : vParaEditar.getMarca());
        final JTextField tPlaca = new JTextField(vParaEditar == null ? "" : vParaEditar.getPlaca());
        final JTextField tAno = new JTextField(vParaEditar == null ? "" : String.valueOf(vParaEditar.getAno()));
        final JTextField tKm = new JTextField(vParaEditar == null ? "" : String.valueOf(vParaEditar.getQuilometragem()));
        final JTextField tCor = new JTextField(vParaEditar == null ? "" : vParaEditar.getCor());
        final JTextField tTipo = new JTextField(vParaEditar == null ? "" : vParaEditar.getTipo());
        final JTextField tPreco = new JTextField(vParaEditar == null ? "" : String.valueOf(vParaEditar.getPreco()));
        final JTextField tStatus = new JTextField(vParaEditar == null ? "" : vParaEditar.getStatus());

        dialog.add(new JLabel("Modelo:")); dialog.add(tModelo);
        dialog.add(new JLabel("Marca:")); dialog.add(tMarca);
        dialog.add(new JLabel("Placa:")); dialog.add(tPlaca);
        dialog.add(new JLabel("Ano:")); dialog.add(tAno);
        dialog.add(new JLabel("KM:")); dialog.add(tKm);
        dialog.add(new JLabel("Cor:")); dialog.add(tCor);
        dialog.add(new JLabel("Tipo:")); dialog.add(tTipo);
        dialog.add(new JLabel("Preço:")); dialog.add(tPreco);
        dialog.add(new JLabel("Status:")); dialog.add(tStatus);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(e -> {
            try {
                veiculo vNovo = new veiculo(
                        vParaEditar == null ? 0 : vParaEditar.getId(),
                        tModelo.getText(), tMarca.getText(), tPlaca.getText(),
                        Integer.parseInt(tAno.getText()), Integer.parseInt(tKm.getText()),
                        tCor.getText(), tTipo.getText(), (int) Double.parseDouble(tPreco.getText()), tStatus.getText()
                );

                if (vParaEditar == null) dao.salvar(vNovo); else dao.alterar(vNovo);

                carregarTabela(dao.listarTodos());
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erro nos dados numéricos!");
            }
        });

        dialog.add(new JLabel("")); dialog.add(btnConfirmar);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new janela_frota().setVisible(true);
    }
}