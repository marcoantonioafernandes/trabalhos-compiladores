package Model;

import java.util.Objects;

public class Simbolo implements Comparable {
    private String lexema;
    private String categoria;
    private String tipo;
    private String endereco;
    
    public Simbolo(String lexema, String categoria, String tipo, String endereco) {
        this.lexema = lexema;
        this.categoria = categoria;
        this.tipo = tipo;
        this.endereco = endereco;
    }

    public String getLexema() {
        return lexema;
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
        hash = 47 * hash + Objects.hashCode(this.lexema);
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
        final Simbolo other = (Simbolo) obj;
        if (!Objects.equals(this.lexema, other.lexema)) {
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
        final Simbolo other = (Simbolo) obj;
        if (!Objects.equals(this.lexema, other.lexema)) {
            return -1;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return -1;
        }
        return 0;
    }
}
