import java.util.Scanner;

public class loja {
    static Scanner sc = new Scanner(System.in);

    // Catálogo da loja (produtos disponíveis)
    static int[] cod = {101,102,103};
    static String[] nome = {"Mouse Gamer","Teclado","Headset"};
    static double[] preco = {150,350,420.5};
    static int[] estoque = {10,5,8};

    // Carrinho do cliente (venda do momento)
    static int[] carrinhoCod = new int[10], carrinhoQtd = new int[10];
    static int itensCarrinho = 0;

    // Histórico de pedidos finalizados
    static int[] pedidos = new int[100];
    static double[] totalPedidos = new double[100];
    static int[][] hist = new int[500][3]; // [númeroPedido, códigoProduto, qtdComprada]
    static int qtdPedidos = 0, histItens = 0;

    public static void main(String[] args) {
        int op;
        do {
            // Menu principal
            System.out.println("\n1-Prateleira 2-Carrinho+ 3-VerCarrinho 4-FecharCompra 5-Histórico 6-Procurar 7-Repor 8-Baixo 0-Sair");
            op = sc.nextInt();

            // Chamando os métodos de acordo com a opção
            if (op == 1) verPrateleira();
            else if (op == 2) addCarrinho();
            else if (op == 3) verCarrinho();
            else if (op == 4) fecharCompra();
            else if (op == 5) verHistorico();
            else if (op == 6) procurar();
            else if (op == 7) repor();
            else if (op == 8) baixo();

        } while (op != 0); // Sai quando o usuário digitar 0
    }

    // Mostra os produtos da prateleira
    static void verPrateleira() {
        for (int i = 0; i < cod.length; i++) {
            if (estoque[i] > 0) {
                System.out.println(cod[i] + " - " + nome[i] + " R$" + preco[i] + " Estoque:" + estoque[i]);
            }
        }
    }

    // Adiciona produto no carrinho
    static void addCarrinho() {
        System.out.print("Código: ");
        int c = sc.nextInt();
        System.out.print("Qtd: ");
        int q = sc.nextInt();

        for (int i = 0; i < cod.length; i++) {
            if (cod[i] == c) {
                if (estoque[i] >= q) {
                    // Guarda no carrinho
                    carrinhoCod[itensCarrinho] = c;
                    carrinhoQtd[itensCarrinho] = q;
                    itensCarrinho++;
                    System.out.println("Adicionado!");
                } else {
                    System.out.println("Sem estoque!");
                }
                return; // Sai do método
            }
        }
        System.out.println("Não achei esse produto!");
    }

    // Mostra o que tem no carrinho e calcula total
    static void verCarrinho() {
        double t = 0;
        for (int j = 0; j < itensCarrinho; j++) {
            for (int i = 0; i < cod.length; i++) {
                if (carrinhoCod[j] == cod[i]) {
                    double sub = preco[i] * carrinhoQtd[j];
                    t += sub;
                    System.out.println(nome[i] + " x" + carrinhoQtd[j] + " = R$" + sub);
                }
            }
        }
        System.out.println("TOTAL R$" + t);
    }

    // Finaliza a compra e gera histórico
    static void fecharCompra() {
        if (itensCarrinho == 0) {
            System.out.println("Carrinho vazio!");
            return;
        }

        qtdPedidos++;
        int pedido = 1000 + qtdPedidos; // Número do pedido começa em 1001
        double tot = 0;

        // Percorre os itens do carrinho e baixa do estoque
        for (int j = 0; j < itensCarrinho; j++) {
            for (int i = 0; i < cod.length; i++) {
                if (carrinhoCod[j] == cod[i]) {
                    tot += preco[i] * carrinhoQtd[j];
                    estoque[i] -= carrinhoQtd[j]; // tira do estoque

                    // Salva no histórico
                    hist[histItens][0] = pedido;
                    hist[histItens][1] = cod[i];
                    hist[histItens][2] = carrinhoQtd[j];
                    histItens++;
                }
            }
        }

        // Guarda o pedido finalizado
        pedidos[qtdPedidos - 1] = pedido;
        totalPedidos[qtdPedidos - 1] = tot;

        System.out.println("Pedido " + pedido + " fechado. Total R$" + tot);

        // Esvazia o carrinho
        itensCarrinho = 0;
    }

    // Mostra todos os pedidos já feitos
    static void verHistorico() {
        for (int i = 0; i < qtdPedidos; i++) {
            System.out.println("Pedido " + pedidos[i] + " R$" + totalPedidos[i]);
        }
    }

    // Procura uma venda específica pelo número do pedido
    static void procurar() {
        System.out.print("Número do pedido: ");
        int p = sc.nextInt();
        double t = 0;
        boolean ok = false;

        for (int i = 0; i < histItens; i++) {
            if (hist[i][0] == p) {
                ok = true;
                for (int j = 0; j < cod.length; j++) {
                    if (hist[i][1] == cod[j]) {
                        double sub = preco[j] * hist[i][2];
                        t += sub;
                        System.out.println(nome[j] + " x" + hist[i][2] + " = R$" + sub);
                    }
                }
            }
        }
        if (ok) System.out.println("TOTAL R$" + t);
        else System.out.println("Não achei!");
    }

    // Dono repõe estoque
    static void repor() {
        System.out.print("Código: ");
        int c = sc.nextInt();
        System.out.print("Qtd: ");
        int q = sc.nextInt();

        for (int i = 0; i < cod.length; i++) {
            if (cod[i] == c) {
                estoque[i] += q;
                System.out.println("Novo estoque: " + estoque[i]);
                return;
            }
        }
        System.out.println("Não achei!");
    }

    // Mostra produtos com estoque baixo
    static void baixo() {
        for (int i = 0; i < cod.length; i++) {
            if (estoque[i] <= 3) {
                System.out.println(nome[i] + " Estoque:" + estoque[i]);
            }
        }
    }
}
