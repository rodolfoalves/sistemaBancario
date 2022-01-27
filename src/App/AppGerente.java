package App;

import java.sql.*;
import java.sql.Statement;
import java.util.Scanner;

public class AppGerente {

    public void iniciarAppGerente(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Aplicativo Gerente Iniciado");

        int ok = 0;

        do {
            System.out.println("Informe Login");
            String login = scanner.nextLine();
            System.out.println("Informe Senha");
            String senha = scanner.nextLine();

            ok = validarGerente(login, senha);
        }while (ok == 0);
    }

    public int validarGerente(String login, String senha){

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

            ResultSet rs = stmt.executeQuery( "select login,senha from gerente where gerente.login = '" + login + "';" );

            while (rs.next()){
                login_teste = String.valueOf(rs.getString("login"));
                senha_teste = String.valueOf(rs.getString("senha"));
                //System.out.println( "Login = " + login_teste );
                //System.out.println( "Senha = " + senha_teste );
            }

            if (login.equals(login_teste) && senha.equals(senha_teste)){
                return 1;
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
}
