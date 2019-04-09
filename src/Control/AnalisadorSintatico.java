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
    private int id;

    public AnalisadorSintatico(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void analisar() {
        System.out.println(tokens);
        this.programa();
        Token token = getProximoToken();
        if (!token.getLexema().equals("EOF")) {
            System.out.println("Erro na analise sintática");
            System.out.println(this.getProximoToken());
        } else {
            System.out.println("Analise sintática concluída");
        }
    }

    private Token getProximoToken() {
        Token token = this.tokens.get(this.id);
        return token;
    }

    private void programa() {
        this.id = 0;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("program")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
                this.corpo();
            }
        }
    }

    private void corpo() {
        this.declara();

        this.rotina();

        this.bloco();
    }

    private void declara() {
        this.id++;
        Token token = getProximoToken();
        if (token.getLexema().equals("var")) {
            this.dvar();
            this.declara();
        } else {
            this.id--;
        }
    }

    private void rotina() {
        this.id++;
        Token token = getProximoToken();

        if (token.getLexema().equals("procedure")) {
            this.id++;
            token = getProximoToken();
            if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
                this.parametros();
                this.id++;
                token = getProximoToken();
                if (token.getLexema().equals(";")) {
                    this.corpo();
                    this.id++;
                    token = getProximoToken();
                    if (token.getLexema().equals(";")) {
                        this.rotina();
                    } else {
                        this.id--;
                    }
                } else {
                    this.id--;
                }
            } else {
                this.id--;
            }
        } else if (token.getLexema().equals("function")) {
            this.id++;
            token = getProximoToken();
            if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
                this.parametros();
                this.id++;
                token = getProximoToken();
                if (token.getLexema().equals(":")) {
                    this.tipo();
                    this.id++;
                    token = getProximoToken();
                    if (token.getLexema().equals(";")) {
                        this.corpo();
                        this.id++;
                        token = getProximoToken();
                        if (token.getLexema().equals(";")) {
                            this.rotina();
                        } else {
                            this.id--;
                        }
                    } else {
                        this.id--;
                    }
                } else {
                    this.id--;
                }
            } else {
                this.id--;
            }
        } else {
            this.id--;
        }
    }

    private void bloco() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("begin")) {
            this.sentencas();
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("end")) {
                this.id++;
            } else {
                this.id--;
            }
        } else {
            this.id--;
        }
    }

    private void sentencas() {
        this.comando();
        this.variaveis();
        this.id++;
        Token token = getProximoToken();
        if (token.getLexema().equals(";")) {
            this.sentencas();
        } else {
            this.id--;
        }
    }

    private void dvar() {
        this.variaveis();
        this.id++;
        Token token = getProximoToken();
        if (token.getLexema().equals(":")) {
            this.tipo();
            this.id++;
            token = getProximoToken();
            if (token.getLexema().equals(";")) {
                this.dvar();
            }
        } else {
            this.id--;
        }
    }

    private void variaveis() {
        this.id++;
        Token token = getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.variaveisLinha();
        } else {
            this.id--;
        }
    }

    private void variaveisLinha() {
        this.id++;
        Token token = getProximoToken();
        if (token.getLexema().equals(",")) {
            this.id++;
            token = getProximoToken();

            if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
                this.variaveisLinha();
            } else {
                this.id--;
            }
        } else {
            this.id--;
        }
    }

    private void tipo() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("Array")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("[")) {
                this.indice();
                this.id++;
                token = this.getProximoToken();
                if (token.getLexema().equals("]")) {
                    this.id++;
                    token = this.getProximoToken();
                    if (token.getLexema().equals("of")) {
                        this.tipo();
                    }
                }
            } else {
                this.id--;
            }

        } else if (!token.getLexema().equals("integer") && !token.getLexema().equals("Real")) {
            this.id--;
        }
    }

    private void indice() {
        this.id++;
        Token token = this.getProximoToken();
        if (!token.getTipo().equals("cInt")) {
            this.id--;
        }
    }

    private void parametros() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("(")) {
            this.listaParametros();
            this.id++;
            token = this.getProximoToken();
            if (!token.getLexema().equals(")")) {
                this.id--;
            }
        } else {
            this.id--;
        }
    }

    private void listaParametros() {
        this.listaId();
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals(":")) {
            this.tipo();
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals(";")) {
                this.listaParametros();
            } else {
                this.id--;
            }
        } else {
            this.id--;
        }
    }

    private void listaId() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.listaIdLinha();
        } else {
            this.id--;
        }
    }

    private void listaIdLinha() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals(",")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
                this.listaIdLinha();
            } else {
                this.id--;
            }
        } else {
            this.id--;
        }
    }

    private void comando() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("read")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("(")) {
                this.varRead();
                this.id++;
                token = this.getProximoToken();
                if (!token.getLexema().equals(")")) {
                    this.id--;
                }
            } else {
                this.id--;
            }
        } else if (token.getLexema().equals("write")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("(")) {
                this.varWrite();
                this.id++;
                token = this.getProximoToken();
                if (!token.getLexema().equals(")")) {
                    this.id--;
                }
            } else {
                this.id--;
            }
        } else if (token.getLexema().equals("for")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
                this.id++;
                token = this.getProximoToken();
                if (token.getLexema().equals(":=")) {
                    this.expressao();
                    this.id++;
                    token = this.getProximoToken();
                    if (token.getLexema().equals("to")) {
                        this.expressao();
                        this.id++;
                        token = this.getProximoToken();
                        if (token.getLexema().equals("do")) {
                            this.bloco();
                        } else {
                            this.id--;
                        }
                    } else {
                        this.id--;
                    }
                } else {
                    this.id--;
                }
            } else {
                this.id--;
            }
        } else if (token.getLexema().equals("repeat")) {
            this.sentencas();
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("until")) {
                this.id++;
                token = this.getProximoToken();
                if (token.getLexema().equals("(")) {
                    this.expressaoLogica();
                    this.id++;
                    token = this.getProximoToken();
                    if (!token.getLexema().equals(")")) {
                        this.id--;
                    }
                } else {
                    this.id--;
                }
            } else {
                this.id--;
            }
        } else if (token.getLexema().equals("while")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("(")) {
                this.expressaoLogica();
                this.id++;
                token = this.getProximoToken();
                if (token.getLexema().equals(")")) {
                    this.id++;
                    token = this.getProximoToken();
                    if (token.getLexema().equals("do")) {
                        this.bloco();
                    } else {
                        this.id--;
                    }

                } else {
                    this.id--;
                }
            } else {
                this.id--;
            }
        } else if (token.getLexema().equals("if")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("(")) {
                this.expressaoLogica();
                this.id++;
                token = this.getProximoToken();
                if (token.getLexema().equals(")")) {
                    this.id++;
                    token = this.getProximoToken();
                    if (token.getLexema().equals("then")) {
                        this.bloco();
                        this.pfalsa();
                    } else {
                        this.id--;
                    }

                } else {
                    this.id--;
                }
            } else {
                this.id--;
            }
        } else if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals(":=")) {
                this.expressao();
            } else {
                //chamada_procedimento
                this.argumentos();
            } 
        } else {
            this.id--;
        }
    }

    private void varRead() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals(",")) {
                this.varRead();
            } else {
                this.id--;
            }
        }
    }

    private void varWrite() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals(",")) {
                this.varRead();
            } else {
                this.id--;
            }
        }
    }

    private void expressao() {
        this.termo();
        this.expressaoLinha();
    } 
    
    private void expressaoLinha(){
        this.id++;
        Token token = this.getProximoToken();
        if(token.getLexema().equals("+") || token.getLexema().equals("-")){
            this.termoLogico();
            this.expressaoLinha();
        } else {
            this.id--;
        }
    }

    private void expressaoLogica() {
        this.termoLogico();
        this.expressaoLogicaLinha();
    }

    private void expressaoLogicaLinha() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("or")) {
            this.termoLogico();
            this.expressaoLogicaLinha();
        } else {
            this.id--;
        }
    }

    /*
    private void chamadaProcedimento() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.argumentos();
        } else {
            this.id--;
        }
    }
*/
    private void pfalsa() {
        this.expressao();
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("else")) {
            this.bloco();
        } else {
            this.id--;
        }
    }

    private void argumentos() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("(")) {
            this.listaArg();
            this.id++;
            token = this.getProximoToken();
            if (!token.getLexema().equals(")")) {
                this.id--;
            }
        } else {
            this.id--;
        }
    }

    private void listaArg() {
        this.expressao();
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals(",")) {
            this.listaArg();
        } else {
            this.id--;
        }
    }

    private void termoLogico() {
        this.fatorLogico();
        this.termoLogicoLinha();
    }

    private void termoLogicoLinha() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("and")) {
            this.fatorLogico();
            this.termoLogicoLinha();
        } else {
            this.id--;
        }
    }

    private void fatorLogico() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("(")) {
            this.expressaoLogica();
            this.id++;
            token = this.getProximoToken();
            if (!token.getLexema().equals(")")) {
                this.id--;
            }
        } else if (token.getLexema().equals("not")) {
            this.fatorLogico();
        } else if (!token.getLexema().equals("true") && !token.getLexema().equals("false")) {
            this.id--;
            this.relacional();
        }
    }

    private void relacional() {
        this.expressao();
        this.relacao();
        this.expressao();
    }
    
    private void relacao(){
        this.id++;
        Token token = this.getProximoToken();
        if (!token.getLexema().equals("=") && !token.getLexema().equals(">") && !token.getLexema().equals("<") &&
                !token.getLexema().equals(">=") && !token.getLexema().equals("<=") && !token.getLexema().equals("<>") ) {
            this.id--;
        }
    }
    
    private void termo(){
        this.fator();
        this.termoLinha();
    }
    
    private void termoLinha(){
        this.id++;
        Token token = this.getProximoToken();
        if(token.getLexema().equals("*") || token.getLexema().equals("/") || token.getLexema().equals("and")){
            this.fator();
            this.termoLinha();
        } else {
            this.id--;
        }
    }
    
    private void fator(){
        this.id++;
        Token token = this.getProximoToken();
        if(token.getLexema().equals("(")){
            this.expressao();
            this.id++;
            token = this.getProximoToken();
            if(!token.getLexema().equals(")")){
                this.id--;
            }
        } else if(!token.getClasse().equals("cInt") && !token.getClasse().equals("cReal")){
            //NÃO SEI SE ESTÁ CERTO, POIS ELE VAI TESTAR AS DUAS CONDIÇÕES
            this.id++;
            token = this.getProximoToken();
            this.id-=2;
            if(token.getLexema().equals("(")){
                this.funcao();
            } else {
                this.variaveis();
            }
        }
    }
    
    private void funcao(){
        this.id++;
        Token token = this.getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.argumentos();
        } else {
            this.id--;
        }
    }
    
    private void variavel(){
        this.id++;
        Token token = this.getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.id++;
            token = this.getProximoToken();
            if(token.getLexema().equals("[")){
                this.expressao();
                this.id++;
                token = this.getProximoToken();
                if(!token.getLexema().equals("]")){
                    this.id--;
                }
            } else {
                this.id--;
            }
        } else {
            this.id--;
        }
    }

}
