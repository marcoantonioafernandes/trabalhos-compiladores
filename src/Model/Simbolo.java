package Model;

import java.util.Objects;

public class Simbolo implements Comparable {
    private String lexema;
    private String categoria;
    private int nivel;
    private int offset;
    private int numeroParametros;
    private String rotulo;
    private TabelaSimbolos tabelaSimbolos;
    //Variáveis abaixo serão excluídas após a modificação do programa
    private String endereco; 
    private String tipo;

    public Simbolo(String lexema, String categoria, int nivel,  int offset, int numeroParametros, 
            String rotulo, TabelaSimbolos tabelaSimbolos) {
        this.lexema = lexema;
        this.categoria = categoria;
        this.nivel = nivel;
        this.offset = offset;
        this.numeroParametros = numeroParametros;
        this.rotulo = rotulo;
        this.endereco = endereco;
        this.tabelaSimbolos = tabelaSimbolos;
    }
    
    public Simbolo(String lexema, String categoria){
        this.lexema = lexema;
        this.categoria = categoria;
    }
    
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getNumeroParametros() {
        return numeroParametros;
    }

    public void setNumeroParametros(int numeroParametros) {
        this.numeroParametros = numeroParametros;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public TabelaSimbolos getTabelaSimbolos() {
        return tabelaSimbolos;
    }

    public void setTabelaSimbolos(TabelaSimbolos tabelaSimbolos) {
        this.tabelaSimbolos = tabelaSimbolos;
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
