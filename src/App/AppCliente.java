package App;

import Pessoas.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class AppCliente extends Cliente{

    //variável chave/global de cliente
    public static Cliente cliente = new Cliente();

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

                    /**
                     System.out.println(String.valueOf(rs.getString("nome")));
                     System.out.println(String.valueOf(rs.getString("cpf")));
                     System.out.println(String.valueOf(rs.getString("matricula")));
                     System.out.println(String.valueOf(rs.getString("login")));
                     System.out.println(String.valueOf(rs.getString("senha")));
                     System.out.println(String.valueOf(rs.getString("conta_id")));
                    **/

                    cliente.setNome(String.valueOf(rs.getString("nome")));
                    cliente.setCpf(String.valueOf(rs.getString("cpf")));
                    cliente.setMatricula(String.valueOf(rs.getString("matricula")));
                    cliente.setLogin(String.valueOf(rs.getString("login")));
                    cliente.setSenha(String.valueOf(rs.getString("senha")));
                    cliente.setTipo(String.valueOf(rs.getString("conta_id")));

                    //System.out.println(cliente.getNome() + cliente.getCpf() + cliente.getMatricula() + cliente.getLogin() + cliente.getSenha() + cliente.getTipo());
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

    }
}
