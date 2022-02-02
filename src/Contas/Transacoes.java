package Contas;
import Pessoas.Cliente;

public interface Transacoes {
    boolean sacar(Cliente cliente, float valor, String senha);

    boolean depositar (Cliente cliente, float valor, String senha);

    boolean transferir (Cliente cliente, String contaBancaria, float valor, String senha);

    void apresentarSaldo(Cliente cliente);
}
