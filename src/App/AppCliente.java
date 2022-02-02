package App;

import Pessoas.*;
import Contas.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class AppCliente extends Cliente{

    //variável chave/global de cliente
    public static Cliente cliente = new Cliente();

    //variável chave/global da conta do cliente
    public static ContaBancaria contaBancaria = new ContaBancaria();

    public void iniciarAppCliente(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Aplicativo Cliente Iniciado");
        int ok = 0;

        do {
            System.out.println("Informe Login");
            cliente.setLogin(scanner.nextLine());
            System.out.println("Informe Senha");
            cliente.setSenha(scanner.nextLine());

            ok = validarCliente(cliente);
        }while (ok == 0);


        menuCliente();
    }

    public int validarCliente(Cliente cliente){

        Scanner in = new Scanner(System.in);

        java.sql.Connection c = null;
        Statement stmt = null;

        String login_teste = "";
        String senha_teste = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "select * from cliente where cliente.login = '" + cliente.getLogin() + "';" );

            while (rs.next()){
                login_teste = String.valueOf(rs.getString("login"));
                senha_teste = String.valueOf(rs.getString("senha"));

                if (cliente.getLogin().equals(login_teste) && cliente.getSenha().equals(senha_teste)){

                    cliente.setNome(String.valueOf(rs.getString("nome")));
                    cliente.setCpf(String.valueOf(rs.getString("cpf")));
                    cliente.setMatricula(String.valueOf(rs.getString("matricula")));
                    cliente.setLogin(String.valueOf(rs.getString("login")));
                    cliente.setSenha(String.valueOf(rs.getString("senha")));
                    cliente.setId_conta(String.valueOf(rs.getString("conta_id")));

                    ResultSet cs = stmt.executeQuery( "select * from contabancaria where contabancaria.conta_id = '" + cliente.getId_conta() + "';" );

                    while (cs.next()){
                        contaBancaria.setId(String.valueOf(cs.getString("conta_id")));
                        contaBancaria.setNumero(String.valueOf(cs.getString("numero")));
                        contaBancaria.setSaldo(String.valueOf(cs.getString("saldo")));
                        contaBancaria.setSenha(String.valueOf(cs.getString("senha")));
                        contaBancaria.setTipo(String.valueOf(cs.getString("tipo")));
                    }

                    cliente.setConta(contaBancaria);
                    return 1;
                }
            }

            System.out.println("Login Ou Senha Incorretas");

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        return 0;
    }

    public void menuCliente(){
        Scanner scanner = new Scanner(System.in);
        int menu;
        System.out.println("Seja Bem Vindo SR(A) " + cliente.getNome());

        do{
            System.out.println("Informe o que desejas fazer");
            System.out.println("1 para Sacar Valor");
            System.out.println("2 para Depositar Valor");
            System.out.println("3 para Transferir Valor");
            System.out.println("4 para Verificar Saldo");
            System.out.println("0 para sair do programa");
            menu = scanner.nextInt();

            switch (menu) {
                case 1 -> {
                    System.out.println("Informe o valor que desejas sacar da sua conta");
                    float valorSaque = scanner.nextFloat();
                    System.out.println("Informe a sua senha por favor");
                    String senha = scanner.next();

                    boolean autorizacao = cliente.getConta().sacar(cliente, valorSaque, senha);

                    if (autorizacao) {
                        cliente.getConta().setSaldo(String.valueOf(Float.parseFloat(cliente.getConta().getSaldo()) - valorSaque));
                        cliente.getConta().atualizarSaldoBase(cliente);
                    }
                }
                case 2 -> {
                    System.out.println("Informe o valor que desejas depositar da sua conta");
                    float valorSaque = scanner.nextFloat();
                    System.out.println("Informe a sua senha por favor");
                    String senha = scanner.next();

                    boolean autorizacao = cliente.getConta().depositar(cliente, valorSaque, senha);

                    if (autorizacao) {
                        cliente.getConta().setSaldo(String.valueOf(Float.parseFloat(cliente.getConta().getSaldo()) + valorSaque));
                        cliente.getConta().atualizarSaldoBase(cliente);
                    }

                }
                case 3 -> {
                    System.out.println("Informe o valor que desejas trasnferir");
                    float valorSaque = scanner.nextFloat();
                    System.out.println("Informe a conta para a qual deseja transferir");
                    String contaTransferencia = scanner.next();
                    System.out.println("Informe a sua senha por favor");
                    String senha = scanner.next();

                    boolean transferir = cliente.getConta().transferir(cliente, contaTransferencia, valorSaque, senha);
                }
                case 4 -> {
                    cliente.getConta().apresentarSaldo(cliente);
                }
                case 0 -> menu = 0;
            }
        }while (menu != 0);
    }
}
