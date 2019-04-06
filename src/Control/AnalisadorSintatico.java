/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Token;
import java.util.List;

/**
 *
 * @author marco
 */
public class AnalisadorSintatico {
    
    private List<Token> tokens;
    
    public AnalisadorSintatico(List<Token> tokens){
        this.tokens = tokens;
    }
    
    public void analisar(){
        boolean erro = false;
        Token token = this.getProximoToken();
        System.out.println(tokens);
        //erro= this.programa(token);
        if(erro){
            System.out.println("Erro na analise sintática");
        }else {
            System.out.println("Analise sintática concluída");
        }
    }
   
    
    private Token getProximoToken(){
        Token token = this.tokens.get(0);
        this.tokens.remove(0);
        return token;
    }
    
}
