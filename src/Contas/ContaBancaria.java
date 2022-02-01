package Contas;

import java.util.ArrayList;

public class ContaBancaria implements ContaCorrente, ContaPoupanca{
    private int numero;
    private float saldo;
    private String senha;
    private String tipo;
    private ArrayList<String> extrato = new ArrayList<String>();

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getSenha() {return senha; }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ArrayList<String> getExtrato() {
        return extrato;
    }

    public void setExtrato(ArrayList<String> extrato) {
        this.extrato = extrato;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public void tarifar(float tarifa) {

    }

    @Override
    public void render(float taxa) {

    }

    @Override
    public boolean sacar(float valor, int senha) {
        return false;
    }

    @Override
    public void depositar(float valor) {

    }

    @Override
    public boolean transferir(ContaBancaria contaBancaria, float valor, int senha) {
        return false;
    }
}
