package Model;

import java.util.Objects;

public class Simbolo implements Comparable {
    private String lexema;
    private String funcao;

    public Simbolo(String lexema, String funcao) {
        this.lexema = lexema;
        this.funcao = funcao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.lexema);
        hash = 47 * hash + Objects.hashCode(this.funcao);
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
        if (!Objects.equals(this.funcao, other.funcao)) {
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
        if (!Objects.equals(this.funcao, other.funcao)) {
            return -1;
        }
        return 0;
    }
    
    
}
