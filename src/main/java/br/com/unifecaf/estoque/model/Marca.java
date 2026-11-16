package br.com.unifecaf.estoque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "marcas")
public class Marca extends EntidadeBase {

    @Column(nullable = false, unique = true, length = 60)
    private String nome;

    protected Marca() {
        // exigido pelo JPA
    }

    public Marca(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
