package Contas;

public interface Transacoes {
    boolean sacar(float valor, int senha);

    void depositar (float valor);

    boolean transferir (ContaBancaria contaBancaria, float valor, int senha);
}
