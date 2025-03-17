package br.com.fiap.bank_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do titular é obrigatório")
    private String nomeTitular;

    @NotBlank(message = "CPF do titular é obrigatório")
    private String cpfTitular;

    @PastOrPresent(message = "Data de abertura não pode ser no futuro")
    private LocalDate dataAbertura;

    @PositiveOrZero(message = "Saldo inicial não pode ser negativo")
    private double saldo;

    private boolean ativa;

    @Enumerated(EnumType.STRING)
    private TipoConta tipo;

    public Conta() {}

    public Conta(String nomeTitular, String cpfTitular, LocalDate dataAbertura, double saldo, TipoConta tipo) {
        this.nomeTitular = nomeTitular;
        this.cpfTitular = cpfTitular;
        this.dataAbertura = dataAbertura;
        this.saldo = saldo;
        this.ativa = true;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getCpfTitular() {
        return cpfTitular;
    }

    public void setCpfTitular(String cpfTitular) {
        this.cpfTitular = cpfTitular;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }
}
