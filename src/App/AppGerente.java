package App;

import java.sql.*;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("EnhancedSwitchMigration")
public class AppGerente {

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

        menuGerente(login, senha);
    }

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

            ResultSet rs = stmt.executeQuery( "select login,senha from gerente where gerente.login = '" + login + "';" );

            while (rs.next()){
                login_teste = String.valueOf(rs.getString("login"));
                senha_teste = String.valueOf(rs.getString("senha"));

                if (login.equals(login_teste) && senha.equals(senha_teste)){
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

    public void menuGerente(String login, String senha){

        Scanner scanner = new Scanner(System.in);
        int menu;
        System.out.println("Seja Bem Vindo SR(A) " + login);

        do{
            System.out.println("Informe o que desejas fazer");
            System.out.println("1 para Adicionar Cliente");
            System.out.println("2 para Atualiza Contas");
            System.out.println("3 para Calcular Receita da Conta Corrente");
            System.out.println("4 para Calcular Receita da Poupança");
            System.out.println("0 para sair do programa");
            menu = Integer.parseInt(scanner.nextLine());

            switch (menu){
                case 1:
                    adicionarCliente();
                    break;
                case 2:
                    System.out.println('2');
                    break;
                case 0:
                    menu = 0;
                    break;
            }
        }while (menu != 0);
    }

    public void adicionarCliente(){
        Scanner scanner = new Scanner(System.in);

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

        int valida = validaClienteExistente(cpf);

        if (valida == 1){
            System.out.println("Cliente ja cadastrado no sistema");
        }
        else {
            cadastrarCliente(nome, cpf, matricula, login, senha, tipoConta);
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
    public void cadastrarCliente(String nome, String cpf, String matricula, String login, String senha, String tipoConta){

        cadastrarConta(senha, tipoConta);
    }

    public void cadastrarConta(String senha, String tipoConta){
        java.sql.Connection c = null;
        Random random = new Random();
        Statement stmt = null;
        String max = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet maxConta = stmt.executeQuery( "select max(conta_id) from contaBancaria" );

            if (maxConta.next()){
                max = String.valueOf(maxConta.getString("max"));
                max = String.valueOf(Integer.parseInt(max) + 1);
            }

            if (tipoConta.equals("1")){
                stmt.executeUpdate("insert into contaBancaria (conta_id, numero, saldo, senha, limiteespecial, tipo)\n" +
                        "values ('" + max +"', '" + String.valueOf(random.nextInt(10000)) + "', '0', '" + senha + "', '100', '1')");
            }
            else if (tipoConta.equals("2")){
                stmt.executeUpdate("insert into contaBancaria (conta_id, numero, saldo, senha, tipo)\n" +
                        "values ('" + max +"', '" + String.valueOf(random.nextInt(10000)) + "', '0', '" + senha + "', '2')");
            }

            //ResultSetMetaData resultSetMetaData = maxConta.getMetaData();
            //System.out.println(resultSetMetaData.getColumnCount());

            maxConta.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }
}
