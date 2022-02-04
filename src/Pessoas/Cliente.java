package Pessoas;

import Contas.ContaBancaria;

public class Cliente extends ContaBancaria implements Pessoa{

    private ContaBancaria conta;
    private String nome;
    private String cpf;
    private String matricula;
    private String login;
    private String id_conta;

    public ContaBancaria getConta() {
        return conta;
    }
    public void setConta(ContaBancaria conta) {
        this.conta = conta;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getId_conta() {return id_conta;}
    public void setId_conta(String id_conta) {this.id_conta = id_conta;}
}
