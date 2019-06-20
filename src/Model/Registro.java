package Model;

import java.util.Objects;

public class Registro implements Comparable {
    private String nome;
    private String categoria;
    private String tipo;
    private String endereco;
    
    public Registro(String nome, String categoria, String tipo, String endereco) {
        this.nome = nome;
        this.categoria = categoria;
        this.tipo = tipo;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEndereco() {
        return endereco;
    }
    
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.nome);
        hash = 47 * hash + Objects.hashCode(this.categoria);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Registro other = (Registro) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object obj) {
        if (obj == null) {
            return -1;
        }
        if (getClass() != obj.getClass()) {
            return -1;
        }
        final Registro other = (Registro) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return -1;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return -1;
        }
        return 0;
    }
}
