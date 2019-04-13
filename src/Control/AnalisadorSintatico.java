/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Simbolo;
import Model.Token;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author marco
 */
public class AnalisadorSintatico {

    private List<Token> tokens;
    private TreeSet<Simbolo> simbolos;
    private ArrayList<Simbolo> simbolosParaAdd;
    private int endereco;
    private int id;
    private boolean erro;
    private String msgErro;

    public AnalisadorSintatico(List<Token> tokens) {
        this.tokens = tokens;
        this.simbolos = new TreeSet<>();
    }

    public String analisar() {
        this.msgErro = "";
        this.endereco = 0;
        this.simbolosParaAdd = new ArrayList<>();
        this.simbolos = new TreeSet<>();
        this.erro = false;
        System.out.println(tokens);
        this.programa();
        if (this.erro) {
            System.out.println("Erro na analise sintática");
            System.out.println("ERRO -> "+ this.msgErro);
        } else {
            System.out.println("Analise sintática concluída");
        }
        return this.msgErro;
    }

    public TreeSet<Simbolo> getSimbolos() {
        return simbolos;
    }

    private void addSimbolo(String lexema, String categoria, String tipo, String endereco) {
        Simbolo simbolo = new Simbolo(lexema, categoria, tipo, endereco);
        this.simbolos.add(simbolo);
    }

    private void addListaSimbolos(String tipo) {
        for (Simbolo simbolo : this.simbolosParaAdd) {
            simbolo.setTipo(tipo);
            this.simbolos.add(simbolo);
        }
        this.simbolosParaAdd.clear();
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
                this.addSimbolo(token.getLexema(), "Program", "", "");

                this.corpo();
                if (this.erro) {
                    return;
                }

                this.id++;
                token = this.getProximoToken();
                if (!token.getLexema().equals(".")) {
                    this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha()-1, "Ponto no fim do arquivo.");
                    this.erro = true;
                }
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Id inválido.");
                this.erro = true;
            }
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Palavra reservada program.");
            this.erro = true;
        }
    }

    private void corpo() {
        this.declara();
        if (this.erro) {
            return;
        }

        this.rotina();
        if (this.erro) {
            return;
        }

        this.bloco();
        if (this.erro) {
            return;
        }
    }

    private void declara() {

        this.id++;
        Token token = getProximoToken();
        if (token.getLexema().equals("var")) {
            this.dvar();
            if (this.erro) {
                return;
            }

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
                this.addSimbolo(token.getLexema(), "Procedure", "", "");

                this.parametros();
                if (this.erro) {
                    return;
                }

                this.id++;
                token = getProximoToken();
                if (token.getLexema().equals(";")) {
                    this.corpo();
                    if (this.erro) {
                        return;
                    }

                    this.id++;
                    token = getProximoToken();
                    if (token.getLexema().equals(";")) {
                        this.rotina();
                    } else {
                        this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um ;");
                        this.erro = true;
                    }
                } else {
                    this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um ;");
                    this.erro = true;
                }
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Id inválido");
                this.erro = true;
            }
        } else if (token.getLexema().equals("function")) {
            this.id++;
            token = getProximoToken();
            if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
                Simbolo simbolo = new Simbolo(token.getLexema(), "Função", "", "");
                this.simbolosParaAdd.add(simbolo);

                this.parametros();
                if (this.erro) {
                    return;
                }

                this.id++;
                token = getProximoToken();
                if (token.getLexema().equals(":")) {
                    this.tipo();
                    if (this.erro) {
                        return;
                    }

                    this.id++;
                    token = getProximoToken();
                    if (token.getLexema().equals(";")) {
                        this.corpo();
                        if (this.erro) {
                            return;
                        }

                        this.id++;
                        token = getProximoToken();
                        if (token.getLexema().equals(";")) {
                            this.rotina();
                            if (this.erro) {
                                return;
                            }

                        } else {
                            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um ;");
                            this.erro = true;
                        }
                    } else {
                        this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um ;");
                        this.erro = true;
                    }
                } else {
                    this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um :");
                    this.erro = true;
                }
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Id inválido");
                this.erro = true;
            }
        } else {
            //vazio da produção
            this.id--;
        }
    }

    private void bloco() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("begin")) {
            this.sentencas();
            if (this.erro) {
                return;
            }

            this.id++;
            token = this.getProximoToken();
            if (!token.getLexema().equals("end")) {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um end");
                this.erro = true;
            }
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um begin");
            this.erro = true;
        }
    }

    private void sentencas() {
        this.comando();
        if (this.erro) {
            return;
        }
        this.id++;
        Token token = getProximoToken();
        if (token.getLexema().equals(";")) {
            this.sentencasP();
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um ;");
            this.erro = true;
        }
    }

    private void sentencasP() {
        this.id++;
        Token token = this.getProximoToken();
        this.id--;
        if (token.getLexema().equals("read") || token.getLexema().equals("write") || token.getLexema().equals("for")
                || token.getLexema().equals("repeat") || token.getLexema().equals("while")
                || token.getLexema().equals("if") || (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada"))) {
            this.sentencas();
        }
    }

    private void dvar() {
        this.variaveis();
        if (this.erro) {
            return;
        }
        this.id++;
        Token token = getProximoToken();
        if (token.getLexema().equals(":")) {
            this.tipo();
            if (this.erro) {
                return;
            }

            this.id++;
            token = getProximoToken();
            if (token.getLexema().equals(";")) {
                this.dvarP();
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um ;");
                this.erro = true;
            }
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um :");
            this.erro = true;
        }
    }

    /*Procrastinação*/
    private void dvarP() {
        this.id++;
        Token token = getProximoToken();
        this.id--;
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.dvar();
        }
    }

    private void variaveis() {
        this.id++;
        Token token = getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            Simbolo simbolo = new Simbolo(token.getLexema(), "Variável", "", this.endereco++ + "");
            this.simbolosParaAdd.add(simbolo);
            this.variaveisLinha();
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Id inválido");
            this.erro = true;
        }
    }

    private void variaveisLinha() {
        this.id++;
        Token token = getProximoToken();
        if (token.getLexema().equals(",")) {
            this.id++;
            token = getProximoToken();

            if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
                Simbolo simbolo = new Simbolo(token.getLexema(), "Variável", "", this.endereco++ + "");
                this.simbolosParaAdd.add(simbolo);
                this.variaveisLinha();
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Id inválido");
                this.erro = true;
            }
        } else {
            this.id--;
        }
    }

    private void tipo() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("Array")) {
            this.addListaSimbolos("Array");
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("[")) {
                this.indice();
                if (this.erro) {
                    return;
                }

                this.id++;
                token = this.getProximoToken();
                if (token.getLexema().equals("]")) {
                    this.id++;
                    token = this.getProximoToken();
                    if (token.getLexema().equals("of")) {
                        this.tipo();
                        if (this.erro) {
                            return;
                        }
                    } else {
                        this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um of");
                        this.erro = true;
                    }
                } else {
                    this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um ]");
                    this.erro = true;
                }
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um [");
                this.erro = true;
            }

        } else if (!token.getLexema().equals("integer") && !token.getLexema().equals("Real")) {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta o tipo do array (integer ou real)");
            this.erro = true;
        } else {
            this.addListaSimbolos(token.getLexema().equals("integer") ? "Inteiro" : "Real");
        }
    }

    private void indice() {
        this.id++;
        Token token = this.getProximoToken();
        if (!token.getTipo().equals("cInt")) {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Não é um número inteiro");
            this.erro = true;
        }
    }

    private void parametros() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("(")) {
            this.listaParametros();
            if (this.erro) {
                return;
            }

            this.id++;
            token = this.getProximoToken();
            if (!token.getLexema().equals(")")) {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um )");
                this.erro = true;
            }
        } else {
            this.id--;
        }
    }

    private void listaParametros() {
        this.listaId();
        if (this.erro) {
            return;
        }

        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals(":")) {
            this.tipo();
            if (this.erro) {
                return;
            }

            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals(";")) {
                this.listaParametros();
            } else {
                this.id--;
            }
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), ";");
            this.erro = true;
        }
    }

    private void listaId() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            Simbolo simbolo = new Simbolo(token.getLexema(), "Parâmetro", "", this.endereco++ + "");
            this.simbolosParaAdd.add(simbolo);
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
                Simbolo simbolo = new Simbolo(token.getLexema(), "Parâmetro", "", this.endereco++ + "");
                this.simbolosParaAdd.add(simbolo);
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
                if (this.erro) {
                    return;
                }

                this.id++;
                token = this.getProximoToken();
                if (!token.getLexema().equals(")")) {
                    this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um )");
                    this.erro = true;
                }
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta (");
                this.erro = true;
            }
        } else if (token.getLexema().equals("write")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("(")) {
                this.varWrite();
                if (this.erro) {
                    return;
                }

                this.id++;
                token = this.getProximoToken();
                if (!token.getLexema().equals(")")) {
                    this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta )");
                    this.erro = true;
                }
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um (");
                this.erro = true;
            }
        } else if (token.getLexema().equals("for")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
                this.id++;
                token = this.getProximoToken();
                if (token.getLexema().equals(":=")) {
                    this.expressao();
                    if (this.erro) {
                        return;
                    }

                    this.id++;
                    token = this.getProximoToken();
                    if (token.getLexema().equals("to")) {
                        this.expressao();
                        if (this.erro) {
                            return;
                        }

                        this.id++;
                        token = this.getProximoToken();
                        if (token.getLexema().equals("do")) {
                            this.bloco();
                        } else {
                            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um do");
                            this.erro = true;
                        }
                    } else {
                        this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um to");
                        this.erro = true;
                    }
                } else {
                    this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um :=");
                    this.erro = true;
                }
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Id inválido");
                this.erro = true;
            }
        } else if (token.getLexema().equals("repeat")) {
            this.sentencas();
            if (this.erro) {
                return;
            }
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("until")) {
                this.expressaoLogica();
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um until");
                this.erro = true;
            }
        } else if (token.getLexema().equals("while")) {
            this.expressaoLogica();
            if (this.erro) {
                return;
            }

            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("do")) {
                this.bloco();
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um do");
                this.erro = true;
            }
        } else if (token.getLexema().equals("if")) {
            this.expressaoLogica();
            if (this.erro) {
                return;
            }

            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("then")) {
                this.bloco();
                if (this.erro) {
                    return;
                }

                this.pfalsa();
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um then");
                this.erro = true;
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
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Id inválido");
            this.erro = true;
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
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Id inválido");
            this.erro = true;
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
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Id inválido");
            this.erro = true;
        }
    }

    private void expressao() {
        this.termo();
        if (this.erro) {
            return;
        }

        this.expressaoLinha();
    }

    private void expressaoLinha() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("+") || token.getLexema().equals("-")) {
            this.termo();
            if (this.erro) {
                return;
            }

            this.expressaoLinha();
        } else {
            this.id--;
        }
    }

    private void expressaoLogica() {
        this.termoLogico();
        if (this.erro) {
            return;
        }

        this.expressaoLogicaLinha();
    }

    private void expressaoLogicaLinha() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("or")) {
            this.termoLogico();
            if (this.erro) {
                return;
            }

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
            if (this.erro) {
                return;
            }

            this.id++;
            token = this.getProximoToken();
            if (!token.getLexema().equals(")")) {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um )");
                this.erro = true;
            }
        } else {
            this.id--;
        }
    }

    private void listaArg() {
        this.expressao();
        if (this.erro) {
            return;
        }

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
        if (this.erro) {
            return;
        }

        this.termoLogicoLinha();
    }

    private void termoLogicoLinha() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("and")) {
            this.fatorLogico();
            if (this.erro) {
                return;
            }
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
            if (this.erro) {
                return;
            }

            this.id++;
            token = this.getProximoToken();
            if (!token.getLexema().equals(")")) {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um )");
                this.erro = true;
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
        if (this.erro) {
            return;
        }

        this.relacao();
        if (this.erro) {
            return;
        }

        this.expressao();
    }

    private void relacao() {
        this.id++;
        Token token = this.getProximoToken();
        if (!token.getLexema().equals("=") && !token.getLexema().equals(">") && !token.getLexema().equals("<")
                && !token.getLexema().equals(">=") && !token.getLexema().equals("<=") && !token.getLexema().equals("<>")) {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um operador lógico (=, >, <, >=, <=, <>)");
            this.erro = true;
        }
    }

    private void termo() {
        this.fator();
        if (this.erro) {
            return;
        }

        this.termoLinha();
    }

    private void termoLinha() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("*") || token.getLexema().equals("/") || token.getLexema().equals("and")) {
            this.fator();
            if (this.erro) {
                return;
            }

            this.termoLinha();
        } else {
            this.id--;
        }
    }

    private void fator() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("(")) {
            this.expressao();
            if (this.erro) {
                return;
            }

            this.id++;
            token = this.getProximoToken();
            if (!token.getLexema().equals(")")) {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um )");
                this.erro = true;
            }
        } else if (!token.getClasse().equals("cInt") && !token.getClasse().equals("cReal")) {
            this.id--;
            this.fatorP();
        }
    }

    private void fatorP() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.id++;
            token = this.getProximoToken();
            this.id -= 2;
            if (token.getLexema().equals("(")) {
                this.funcao();
            } else {
                this.variaveis();
            }
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Id inválido");
            this.erro = true;
        }
    }

    private void funcao() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.argumentos();
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Id inválido");
            this.erro = true;
        }
    }

    private void variavel() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("[")) {
                this.expressao();
                if (this.erro) {
                    return;
                }
                this.id++;
                token = this.getProximoToken();
                if (!token.getLexema().equals("]")) {
                    this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um ]");
                    this.erro = true;
                }
            } else {
                this.id--;
            }
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Id inválido");
            this.erro = true;
        }
    }

}
