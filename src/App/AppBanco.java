package App;
import Agencia.*;

public class AppBanco {

    private Agencia banco;
    private AppCLiente appCLiente;
    private AppGerente appGerente;

    public Agencia getBanco() {
        return banco;
    }

    public void setBanco(Agencia banco) {
        this.banco = banco;
    }

    public AppCLiente getAppCLiente() {
        return appCLiente;
    }

    public void setAppCLiente(AppCLiente appCLiente) {
        this.appCLiente = appCLiente;
    }

    public AppGerente getAppGerente() {
        return appGerente;
    }

    public void setAppGerente(AppGerente appGerente) {
        this.appGerente = appGerente;
    }

    public static void main(String[] args){

    }
}
