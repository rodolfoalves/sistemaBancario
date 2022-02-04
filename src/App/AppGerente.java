package App;

import Agencia.Agencia;
import Pessoas.*;
import java.sql.*;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("EnhancedSwitchMigration")

public class AppGerente extends Gerente implements Agencia {

    public static Gerente gerente = new Gerente();

    public void iniciarAppGerente(){
        Scanner scanner = new Scanner(System.in);
        String login;
        String senha;

        System.out.println("Aplicativo Gerente Iniciado");

        int ok = 0;

        do {
            System.out.println("Informe Login");
            login = scanner.nextLine();
            System.out.println("Informe Senha");
            senha = scanner.nextLine();

            ok = validarGerente(login, senha);
        }while (ok == 0);

        menuGerente();
    }

    /**
    public static void openDataBase(){
        java.sql.Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            c.setAutoCommit(false);
        }

        catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void closeDataBase(){
        try {

        }
    }
     **/

    public int validarGerente(String login, String senha){

        java.sql.Connection c = null;
        Statement stmt = null;
        String login_teste = "";
        String senha_teste = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "select * from gerente where gerente.login = '" + login + "';" );

            while (rs.next()){
                login_teste = String.valueOf(rs.getString("login"));
                senha_teste = String.valueOf(rs.getString("senha"));

                if (login.equals(login_teste) && senha.equals(senha_teste)){

                    gerente.setNome(String.valueOf(rs.getString("nome")));
                    gerente.setCpf(String.valueOf(rs.getString("cpf")));
                    gerente.setMatricula(String.valueOf(rs.getString("matricula")));
                    gerente.setLogin(String.valueOf(rs.getString("login")));
                    gerente.setSenha(String.valueOf(rs.getString("senha")));
                    gerente.setId_agencia(String.valueOf(rs.getString("id_agencia")));

                    /**
                    System.out.println(gerente.getNome());
                    System.out.println(gerente.getCpf());
                    System.out.println(gerente.getMatricula());
                    System.out.println(gerente.getLogin());
                    System.out.println(gerente.getSenha());
                    System.out.println(gerente.getId_agencia());
                    **/


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

    public void menuGerente(){

        Scanner scanner = new Scanner(System.in);
        int menu;
        System.out.println("Seja Bem Vindo SR(A) " + gerente.getNome());

        do{
            System.out.println("Informe o que desejas fazer");
            System.out.println("1 para Adicionar Cliente");
            System.out.println("2 para Atualiza Contas");
            System.out.println("3 para Calcular Receita da Conta Corrente");
            System.out.println("4 para Calcular Receita da Poupança");
            System.out.println("5 para Calcular Receita da Agencia");
            System.out.println("0 para sair do programa");
            menu = Integer.parseInt(scanner.nextLine());

            switch (menu){
                case 1:
                    adicionarCliente();
                    break;
                case 2:
                    System.out.println("salves");
                    break;
                case 3:
                    calcularReceitaContaCorrente();
                    break;
                case 4:
                    calcularReceitaContaPoupanca();
                    break;
                case 5:
                    calcularReceitaAgencia();
                    break;
                case 0:
                    menu = 0;
                    break;
            }
        }while (menu != 0);
    }

    public void adicionarCliente(){
        Scanner scanner = new Scanner(System.in);

        Cliente cliente = new Cliente();

        /**
        System.out.println("Nome do Cliente:");
        String nome = scanner.nextLine();
        System.out.println("CPF do CLiente:");
        String cpf = scanner.nextLine();
        System.out.println("Matricula do CLiente");
        String matricula = scanner.nextLine();
        System.out.println("Login do CLiente");
        String login = scanner.nextLine();
        System.out.println("Senha do CLiente");
        String senha = scanner.nextLine();
        System.out.println("1 para Conta Corrente - 2 para Conta Poupança");
        String tipoConta = scanner.nextLine();
        **/

        System.out.println("Nome do Cliente:");
        cliente.setNome(scanner.nextLine());
        System.out.println("CPF do CLiente:");
        cliente.setCpf(scanner.nextLine());
        System.out.println("Matricula do CLiente");
        cliente.setMatricula(scanner.nextLine());
        System.out.println("Login do CLiente");
        cliente.setLogin(scanner.nextLine());
        System.out.println("Senha do CLiente");
        cliente.setSenha((scanner.nextLine()));
        System.out.println("1 para Conta Corrente - 2 para Conta Poupança");
        String tipoConta = scanner.nextLine();

        int valida = validaClienteExistente(cliente.getCpf());

        if (valida == 1){
            System.out.println("Cliente ja cadastrado no sistema");
        }
        else {
            cadastrarCliente(cliente, tipoConta);
        }

    }

    public int validaClienteExistente (String cpf){
        java.sql.Connection c = null;
        Statement stmt = null;
        String cpf_teste = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "select cpf from cliente where cliente.cpf = '" + cpf + "';" );

            while (rs.next()){
                cpf_teste = String.valueOf(rs.getString("cpf"));

                if (cpf.equals(cpf_teste)){
                    return 1;
                }
            }

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return 0;
    }
    public void cadastrarCliente(Cliente cliente, String tipoConta){

        String contaId = cadastrarConta(cliente.getSenha(), tipoConta);

        if (Integer.parseInt(contaId) > 0){
            java.sql.Connection c = null;
            Random random = new Random();
            Statement stmt = null;

            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
                //c.setAutoCommit(false);

                stmt = c.createStatement();
                String query = ("insert into cliente \n" + "values ('" + cliente.getNome() +"', '" + cliente.getCpf() + "', '" +
                        cliente.getMatricula() + "', '" + cliente.getLogin() + "', '" + cliente.getSenha() + "', '" + contaId + "')");
                stmt.executeUpdate(query);

                stmt.close();
                c.close();

            } catch ( Exception e ) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);
            }
        }
    }

    public String cadastrarConta(String senha, String tipoConta){
        java.sql.Connection c = null;
        Random random = new Random();
        Statement stmt = null;
        String max = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            //c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet maxConta = stmt.executeQuery( "select max(conta_id) from contaBancaria" );

            if (maxConta.next()){
                max = String.valueOf(maxConta.getString("max"));
                max = String.valueOf(Integer.parseInt(max) + 1);
            }

            if (tipoConta.equals("1")){
                String query = ("insert into contaBancaria (conta_id, numero, saldo, senha, limiteespecial, tipo, id_agencia)\n" +
                        "values ('" + max +"', '" + String.valueOf(random.nextInt(100000)) + "', '0', '" + senha + "', '100', '1', '1')");
                stmt.executeUpdate(query);
            }
            else if (tipoConta.equals("2")){
                String query = ("insert into contaBancaria (conta_id, numero, saldo, senha, tipo, id_agencia)\n" +
                        "values ('" + max +"', '" + String.valueOf(random.nextInt(100000)) + "', '0', '" + senha + "', '2', '1')");
                stmt.executeUpdate(query);
            }
            maxConta.close();
            stmt.close();
            c.close();

            return (max);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return ("-1");
    }

    @Override
    public void calcularReceitaAgencia() {
        java.sql.Connection c = null;
        Statement stmt = null;
        String total = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("select sum(saldo) from contabancaria where id_agencia = '" + gerente.getId_agencia() + "'");

            while (rs.next()){
                total = String.valueOf(rs.getString("sum"));
                System.out.println("Receita das contas da Agência  = " + total);
            }

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    @Override
    public void calcularReceitaContaCorrente() {
        java.sql.Connection c = null;
        Statement stmt = null;
        String total = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("select sum(saldo) from contabancaria where contabancaria.tipo = '1' and id_agencia = '" + gerente.getId_agencia() + "'");

            while (rs.next()){
                total = String.valueOf(rs.getString("sum"));
                System.out.println("Receita das contas do tipo CONTA CORRENTE = " + total);
            }

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    @Override
    public void calcularReceitaContaPoupanca() {
        java.sql.Connection c = null;
        Statement stmt = null;
        String total = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("select sum(saldo) from contabancaria where contabancaria.tipo = '2' and id_agencia = '" + gerente.getId_agencia() + "'");

            while (rs.next()){
                total = String.valueOf(rs.getString("sum"));
                System.out.println("Receita das contas do tipo CONTA POUPANCA = " + total);
            }

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }


}
