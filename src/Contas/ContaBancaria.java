package Contas;

import Pessoas.Cliente;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class ContaBancaria implements ContaCorrente, ContaPoupanca{
    private String id;
    private String numero;
    private String saldo;
    private String senha;
    private String tipo;
    private String agencia;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getSenha() {return senha; }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgencia() {return agencia;}

    public void setAgencia(String agencia) {this.agencia = agencia;}

    @Override
    public void tarifar(float tarifa) {

    }

    @Override
    public void render(float taxa) {

    }

    @Override
    public boolean sacar(Cliente cliente, float valor, String senha) {
        if (cliente.getSenha().equals(senha)){
            if (Float.parseFloat(cliente.getConta().getSaldo()) > valor){
                System.out.println("Transacao Autorizada");
                return true;
            }
            System.out.println("Saldo nao disponivel");
            return false;
        }
        System.out.println("Senha Invalida");
        return false;
    }

    @Override
    public boolean depositar(Cliente cliente, float valor, String senha) {

        if (cliente.getSenha().equals(senha)){
            System.out.println("Transacao Autorizada");
            return true;
        }
        System.out.println("Senha Invalida");
        return false;
    }

    @Override
    public boolean transferir(Cliente cliente, String contaBancaria, float valor, String senha) {

        if (cliente.getSenha().equals(senha)){
            if (validarContaExistente(contaBancaria)){
                atualizarTransferencia(cliente,contaBancaria,valor);
                System.out.println("Transferencia executada com sucesso");
            }
            else {
                System.out.println("Conta para transferir invalida");
            }
        }
        return false;
    }

    @Override
    public void apresentarSaldo(Cliente cliente) {
        java.sql.Connection c = null;
        Statement stmt = null;
        String saldo_teste = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            //c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "select saldo from contabancaria where contabancaria.conta_id = '" + cliente.getConta().getId() + "';" );

            while (rs.next()){
                saldo_teste = String.valueOf(rs.getString("saldo"));

                System.out.println("O saldo da sua conta é : " + saldo_teste);
            }
            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void atualizarSaldoBase(Cliente cliente){
        java.sql.Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            //c.setAutoCommit(false);

            stmt = c.createStatement();


            String query = ("update contabancaria set saldo = '" + cliente.getConta().getSaldo() + "' where contabancaria.conta_id = '" + cliente.getConta().getId() + "'");
            //System.out.println(query);
            stmt.executeUpdate(query);

            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public boolean validarContaExistente (String contaBancaria){
        java.sql.Connection c = null;
        Statement stmt = null;
        String conta_teste = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            //c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "select numero from contabancaria where contabancaria.numero = '" + contaBancaria + "';" );

            while (rs.next()){
                conta_teste = String.valueOf(rs.getString("numero"));

                if (contaBancaria.equals(conta_teste)){
                    return true;
                }
            }
            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return false;
    }

    public void atualizarTransferencia(Cliente cliente, String contaBancaria, float valor){
        java.sql.Connection c = null;
        Random random = new Random();
        Statement stmt = null;
        String saldo = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "arquivo41");
            //c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet dadosConta = stmt.executeQuery( "select * from contabancaria where contabancaria.numero = '" + contaBancaria + "';");

            if (dadosConta.next()){
                saldo = String.valueOf(dadosConta.getString("saldo"));
            }

            String querySaque = ("update contabancaria set saldo = '" + (Float.parseFloat(saldo) + valor) + "' where contabancaria.numero = '" + contaBancaria + "'");
            System.out.println(querySaque);
            stmt.executeUpdate(querySaque);

            String queryDeposito = ("update contabancaria set saldo = '" + (Float.parseFloat(cliente.getConta().getSaldo()) - valor) +
                    "' where contabancaria.conta_id = '" + cliente.getConta().getId() + "'");
            stmt.executeUpdate(queryDeposito);

            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }
}
