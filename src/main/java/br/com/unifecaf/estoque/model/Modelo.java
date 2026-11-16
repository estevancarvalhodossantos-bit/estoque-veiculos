package br.com.unifecaf.estoque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "modelos")
public class Modelo extends EntidadeBase {

    @Column(nullable = false, length = 80)
    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    protected Modelo() {
        // exigido pelo JPA
    }

    public Modelo(String nome, Marca marca) {
        this.nome = nome;
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
