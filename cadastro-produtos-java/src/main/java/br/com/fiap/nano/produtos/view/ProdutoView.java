package br.com.fiap.nano.produtos.view;

import br.com.fiap.nano.produtos.model.Categoria;
import br.com.fiap.nano.produtos.model.Produto;
import br.com.fiap.nano.produtos.repository.ProdutoCollectionRepository;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutoView {

    public static Produto select() {
        // @formatter:off
        Produto ret = (Produto) JOptionPane.showInputDialog(
                null, // componente pai. Como não temos será null
                "Selecione um produto",
                "Menu",
                JOptionPane.QUESTION_MESSAGE,
                null, // icone
                ProdutoCollectionRepository.findAll().toArray(), // produtos cadastrados
                1);

        return ret;
        // @formatter:on
    }

    /**
     * Captura os dados de um novo produto.
     * Retorna null se o usuário cancelar em qualquer etapa.
     */
    public static Produto form() {

        Categoria categoria = CategoriaView.select(null);
        if (categoria == null) return null;

        String nome = lerNome(null);
        if (nome == null) return null;

        String descricao = lerDescricao(null);
        if (descricao == null) return null;

        BigDecimal preco = lerPreco(null);
        if (preco == null) return null;

        Produto ret = new Produto();
        ret.setNome(nome)
                .setCategoria(categoria)
                .setDescricao(descricao)
                .setPreco(preco)
                .setDataCadastro(LocalDateTime.now());

        return ret;
    }

    /**
     * Altera os dados de um produto existente. Os campos já vêm preenchidos
     * e o usuário altera o que for necessário. Se cancelar, nada é alterado.
     */
    public static void update(Produto produto) {

        Categoria categoria = CategoriaView.select(produto.getCategoria());
        if (categoria == null) return;

        String nome = lerNome(produto.getNome());
        if (nome == null) return;

        String descricao = lerDescricao(produto.getDescricao());
        if (descricao == null) return;

        BigDecimal preco = lerPreco(produto.getPreco());
        if (preco == null) return;

        produto.setCategoria(categoria)
                .setNome(nome)
                .setDescricao(descricao)
                .setPreco(preco);

        sucesso(produto);
        show(produto);
    }

    private static String lerNome(String valorAtual) {
        String nome;
        do {
            nome = JOptionPane.showInputDialog("Nome do Produto", valorAtual);

            if (nome == null) return null; // cancelou

            nome = nome.trim();
            if (nome.length() < 3)
                JOptionPane.showMessageDialog(null, "O nome do produto precisa ter no mínimo 3 dígitos");

        } while (nome.length() < 3);

        return nome;
    }

    private static String lerDescricao(String valorAtual) {
        String descricao;
        do {
            descricao = JOptionPane.showInputDialog("Descrição do Produto", valorAtual);

            if (descricao == null) return null; // cancelou

            descricao = descricao.trim();
            if (descricao.length() < 5)
                JOptionPane.showMessageDialog(null, "A descrição do produto precisa ter no mínimo 5 dígitos");

        } while (descricao.length() < 5);

        return descricao;
    }

    private static BigDecimal lerPreco(BigDecimal valorAtual) {
        double p;
        do {
            String digitado = JOptionPane.showInputDialog("Preço do Produto", valorAtual);

            if (digitado == null) return null; // cancelou

            try {
                p = Double.parseDouble(digitado.trim().replace(",", "."));
            } catch (NumberFormatException e) {
                p = 0;
            }

            if (p <= 0) JOptionPane.showMessageDialog(null, "Valor inválido");

        } while (p <= 0);

        return BigDecimal.valueOf(p);
    }

    public static void sucesso() {
        JOptionPane.showMessageDialog(null, "Produto salvo com sucesso!");
    }

    public static void sucesso(Produto produto) {
        System.out.println(produto);
        JOptionPane.showMessageDialog(null, "Produto " + produto.getNome().toUpperCase() + " salvo com sucesso!");
    }

    public static void show(Produto p) {
        System.out.println(p);
        String produtoString = String.format(
                "PRODUTO: %s%nDESCRIÇÃO: %s%nCATEGORIA: %s%nPREÇO: %,.2f",
                p.getNome(),
                p.getDescricao(),
                p.getCategoria(),
                p.getPreco());
        JOptionPane.showMessageDialog(null, produtoString);
    }
}
