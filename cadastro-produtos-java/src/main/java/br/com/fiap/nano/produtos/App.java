package br.com.fiap.nano.produtos;

import br.com.fiap.nano.produtos.model.Categoria;
import br.com.fiap.nano.produtos.model.Produto;
import br.com.fiap.nano.produtos.repository.CategoriaCollectionRepository;
import br.com.fiap.nano.produtos.repository.ProdutoCollectionRepository;
import br.com.fiap.nano.produtos.view.CategoriaView;
import br.com.fiap.nano.produtos.view.Opcao;
import br.com.fiap.nano.produtos.view.OpcaoView;
import br.com.fiap.nano.produtos.view.ProdutoView;

import javax.swing.*;
import java.util.List;

public class App {

    public static void main(String[] args) {

        Opcao opcao = null;

        do {
            opcao = OpcaoView.select();
            switch (opcao) {
                case CADASTRAR_CATEGORIA -> cadastrarCategoria();
                case CADASTRAR_PRODUTO -> cadastrarProduto();
                case ALTERAR_PRODUTO -> alterarProduto();
                case CONSULTAR_PRODUTO_POR_ID -> consultarProdutoPorId();
                case CONSULTAR_PRODUTO_POR_CATEGORIA -> consultarProdutoPorCategoria();
                default -> { }
            }
        } while (opcao != Opcao.ENCERRAR_SISTEMA);

        System.exit(0);
    }

    private static void consultarProdutoPorCategoria() {
        Categoria categoria = CategoriaView.select(null);
        if (categoria == null) return; // cancelou

        List<Produto> produtos = ProdutoCollectionRepository.findByCategoria(categoria);
        if (produtos.isEmpty())
            JOptionPane.showMessageDialog(null, "Não encontramos produtos cadastrados para a categoria " + categoria.getNome());
        produtos.forEach(System.out::println);
        produtos.forEach(ProdutoView::show);
    }

    private static void consultarProdutoPorId() {
        long id = 0L;
        do {
            String digitado = JOptionPane.showInputDialog("Informe o id do produto");
            if (digitado == null) return; // cancelou

            try {
                id = Long.parseLong(digitado.trim());
            } catch (NumberFormatException e) {
                id = 0L;
            }

            if (id <= 0) JOptionPane.showMessageDialog(null, "Id inválido!");

        } while (id <= 0);

        Produto p = ProdutoCollectionRepository.findById(id);
        if (p != null) {
            ProdutoView.show(p);
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!");
        }
    }

    private static void alterarProduto() {
        Produto produto = ProdutoView.select();
        if (produto == null) return; // cancelou

        ProdutoView.update(produto);
    }

    private static void cadastrarProduto() {
        Produto produto = ProdutoView.form();
        if (produto == null) return; // cancelou

        if (ProdutoCollectionRepository.save(produto) != null) {
            ProdutoView.sucesso(produto);
        } else {
            JOptionPane.showMessageDialog(null, "Já existe o produto " + produto.getNome() + " nessa categoria!");
        }
    }

    public static void cadastrarCategoria() {
        CategoriaView view = new CategoriaView();
        Categoria categoria = CategoriaView.form();
        if (categoria == null) return; // cancelou

        if (CategoriaCollectionRepository.save(categoria) != null) {
            view.sucesso(categoria);
        } else {
            JOptionPane.showMessageDialog(null, "A categoria " + categoria.getNome().toUpperCase() + " já existe!");
        }
    }
}
