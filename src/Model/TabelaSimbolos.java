/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author Marco Antônio
 */
public class TabelaSimbolos {
    private int memoria;
    private TreeSet<Simbolo> simbolos;
    private TabelaSimbolos tabelaSimbolosPai;
    private int espacoVariaveisLocais;
    private int offsetVariavel;
    private int numeroParametros;
    
    public TabelaSimbolos(){
        this.simbolos = new TreeSet<>();
        this.offsetVariavel = 0;
        this.espacoVariaveisLocais = 4;
    }
    
    public void incrementaOffSetVariavel(int valor){
        this.offsetVariavel += valor;
    }
    
    public int getOffsetVariavel(){
        return this.offsetVariavel;
    }

    public int getMemoria() {
        return memoria;
    }

    public void setMemoria(int memoria) {
        this.memoria = memoria;
    }

    public TreeSet<Simbolo> getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(TreeSet<Simbolo> simbolos) {
        this.simbolos = simbolos;
    }

    public TabelaSimbolos getTabelaSimbolosPai() {
        return tabelaSimbolosPai;
    }

    public void setTabelaSimbolosPai(TabelaSimbolos tabelaSimbolosPai) {
        this.tabelaSimbolosPai = tabelaSimbolosPai;
    }

    public int getEspacoVariaveisLocais() {
        return espacoVariaveisLocais;
    }

    public void setEspacoVariaveisLocais(int espacoVariaveisLocais) {
        this.espacoVariaveisLocais = espacoVariaveisLocais;
    }
    
    
    
    public void addSimbolo(String lexema, String categoria, int nivel,
            int offset, int numeroParametros, String rotulo, TabelaSimbolos tabelaSimbolos) {
        if(categoria.equals("Variável")){
            this.espacoVariaveisLocais += 4;
        }
        
        if(categoria.equals("Parâmetro")){
            this.numeroParametros++;
        }
        Simbolo simbolo = new Simbolo(lexema, categoria, nivel, offset, numeroParametros, rotulo, tabelaSimbolos);
        this.simbolos.add(simbolo);
    }
    
    public void calculaOffsetParametros(){
        Iterator<Simbolo> it = this.simbolos.descendingIterator();
        Simbolo funcao = it.next();
        it = this.simbolos.descendingIterator();
        int i = 1;
        while(it.hasNext()){
            Simbolo simbolo = it.next();
            if(simbolo.getCategoria().equals("Parâmetro")){
                int offset = 12 + (this.numeroParametros - i)*4;
                simbolo.setOffset(offset);
                if(i == 1){
                    funcao.setOffset(offset+4);
                }
                i++;
            }
        }
        
    }
    
    public boolean verificarSimboloExistenteTabelaAtual(String lexema, String categoria){
        Simbolo simbolo = new Simbolo(lexema, categoria);
        return this.simbolos.contains(simbolo);
    }
    
    public Simbolo getElementoTabelaSimbolosAtual(String lexema, String categoria){
        Iterator<Simbolo> it = this.simbolos.iterator();
        while(it.hasNext()){
            Simbolo simbolo = it.next();
            if(simbolo.equals(new Simbolo(lexema, categoria)))
                return simbolo;
        }
        return null;
    }
    
    //Função que busca a variável nas tabelas de símbolo atual e corrente
    public Simbolo buscaVariavelOuParametro(TabelaSimbolos tabela, String lexema){
       if(tabela == null) return null;
       //boolean existe = tabela.simbolos.contains(new Simbolo(lexema, "Variável"));
       boolean existe = false;
       Iterator<Simbolo> it1 = tabela.simbolos.descendingIterator();
       while(it1.hasNext()){
           Simbolo simboloAux = it1.next();
           if(simboloAux.equals(new Simbolo(lexema, "Variável")) || simboloAux.equals(new Simbolo(lexema, "Parâmetro"))){
               existe = true;
               break;
           }
       }
       if(!existe){
            return this.buscaVariavelOuParametro(tabela.tabelaSimbolosPai, lexema);
       }
        Iterator<Simbolo> it = tabela.simbolos.iterator();
        while(it.hasNext()){
            Simbolo simbolo = it.next();
            if(simbolo.equals(new Simbolo(lexema, "Variável")) || simbolo.equals(new Simbolo(lexema, "Parâmetro")))
                return simbolo;
        }
       return null;
    }
    
    public Simbolo buscaFuncao(TabelaSimbolos tabela, String lexema){
       if(tabela == null) return null;
       boolean existe = false;
       Iterator<Simbolo> it1 = tabela.simbolos.descendingIterator();
       while(it1.hasNext()){
           Simbolo simboloAux = it1.next();
           if(simboloAux.equals(new Simbolo(lexema, "Função"))){
               existe = true;
               break;
           }
       }
       if(!existe){
            return this.buscaFuncao(tabela.tabelaSimbolosPai, lexema);
       }
        Iterator<Simbolo> it = tabela.simbolos.iterator();
        while(it.hasNext()){
            Simbolo simbolo = it.next();
            if(simbolo.equals(new Simbolo(lexema, "Função")))
                return simbolo;
        }
       return null;
    }
    
    public void atualizarNumeroParametros(){
        //Atualizando o número de parâmetros na tabela corrente
        Iterator<Simbolo> it = this.simbolos.descendingIterator();
        Simbolo simbolo = it.next();
        simbolo.setNumeroParametros(this.numeroParametros);
        
        //Atualizando o número de parâmetros na tabela pai
        TabelaSimbolos tabelaSimbolosPaiAux = this.tabelaSimbolosPai;
        it = tabelaSimbolosPaiAux.simbolos.iterator();
        while(it.hasNext()){
            Simbolo simboloAux  = it.next();
            if(simboloAux.equals(simbolo)){
                simboloAux.setNumeroParametros(this.numeroParametros);
                return;
            }
        }
    }
}
