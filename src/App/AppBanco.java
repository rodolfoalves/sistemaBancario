package App;
import Agencia.*;

import java.util.Scanner;

@SuppressWarnings("EnhancedSwitchMigration")
public class AppBanco {

    private static final AppCliente appCliente = new AppCliente();
    private static final AppGerente appGerente = new AppGerente();

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int menu = -1;

        do{
            System.out.println("Informe o que desejas fazer");
            System.out.println("1 para Carregar Módulo Gerente");
            System.out.println("2 para Carregar Módulo Cliente");
            System.out.println("0 para sair do programa");
            menu = Integer.parseInt(scanner.nextLine());

            switch (menu){
                case 1:
                    appGerente.iniciarAppGerente();
                    break;
                case 2:
                    appCliente.iniciarAppCliente();
                    break;
                case 0:
                    menu = 0;
                    break;
            }
        }while (menu != 0);
    }
}
