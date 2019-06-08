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
        /* if (this.erro) {
         System.out.println("Erro na analise sintática");
         System.out.println("ERRO -> "+ this.msgErro);
         } else {
         System.out.println("Analise sintática concluída");
         } */
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
                //AÇÃO {A01}
                this.id++;
                token = getProximoToken();
                if (!token.getLexema().equals(";")) {
                    this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha(), "Falta um ;");
                    this.erro = true;
                    return;
                }

                this.corpo();
                if (this.erro) {
                    return;
                }

                this.id++;
                token = this.getProximoToken();
                if (!token.getLexema().equals(".")) {
                    this.msgErro = String.format("Erro: \n(%03d) - %s",
                            token.getLinha() - 1, "Ponto no fim do arquivo.");
                    this.erro = true;
                }
                //AÇÃO {A45}
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
        //AÇÃO {A44}

        this.bloco();
        if (this.erro) {
            return;
        }
        //AÇÃO {A46}
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
                //AÇÃO {A04}
                this.parametros();
                if (this.erro) {
                    return;
                }
                //AÇÃO {A48}

                this.id++;
                token = getProximoToken();
                if (token.getLexema().equals(";")) {
                    this.corpo();
                    if (this.erro) {
                        return;
                    }
                    //AÇÃO {A56}

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
                //AÇÃO {A05}
                this.parametros();
                if (this.erro) {
                    return;
                }
                //AÇÃO {A48}

                this.id++;
                token = getProximoToken();
                if (token.getLexema().equals(":")) {
                    this.tipo();
                    if (this.erro) {
                        return;
                    }
                    //AÇÃO {A47}

                    this.id++;
                    token = getProximoToken();
                    if (token.getLexema().equals(";")) {
                        this.corpo();
                        if (this.erro) {
                            return;
                        }
                        //AÇÃO {A56}

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
            //AÇÃO {A02}

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
            //AÇÃO {A03}
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
                //AÇÃO {A03}
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
        if (token.getLexema().equals("integer")) {
            this.addListaSimbolos("Inteiro");
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
            //AÇÃO {A06}

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
            //AÇÃO {A07}
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
                //AÇÃO {A07}
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
                this.expWrite();
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
        } else if (token.getLexema().equals("writeln")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("(")) {
                this.expWrite();
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
                //AÇÃO {A57}
                this.id++;
                token = this.getProximoToken();
                if (token.getLexema().equals(":=")) {
                    this.expressao();
                    if (this.erro) {
                        return;
                    }
                    //AÇÃO {A11}

                    this.id++;
                    token = this.getProximoToken();
                    if (token.getLexema().equals("to")) {
                        this.expressao();
                        if (this.erro) {
                            return;
                        }
                        //AÇÃO {A12}

                        this.id++;
                        token = this.getProximoToken();
                        if (token.getLexema().equals("do")) {
                            this.bloco();
                            //AÇÃO {A13}
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
            //AÇÃO {A14}
            this.sentencas();
            if (this.erro) {
                return;
            }
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("until")) {
                this.expressaoLogica();
                //AÇÃO {A15}
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                        token.getLinha(), "Falta um until");
                this.erro = true;
            }
        } else if (token.getLexema().equals("while")) {
            //AÇÃO {A16}
            this.expressaoLogica();
            if (this.erro) {
                return;
            }
            //AÇÃO {A17}

            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("do")) {
                this.bloco();
                //AÇÃO {A18}
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
            //AÇÃO {A19}

            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals("then")) {
                this.bloco();
                if (this.erro) {
                    return;
                }
                //AÇÃO {A20}

                this.pfalsa();
                //AÇÃO {A21}
            } else {
                this.msgErro = String.format("Erro: \n(%03d) - %s",
                        token.getLinha(), "Falta um then");
                this.erro = true;
            }
        } else if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            this.id++;
            token = this.getProximoToken();
            if (token.getLexema().equals(":=")) {
                //é uma variável
                this.id--;
                token = this.getProximoToken();
                //AÇÃO {A49}
                this.id++;
                token = this.getProximoToken();
                this.expressao();
                //AÇÃO {A22}
            } else {
                //chamada_procedimento
                //AÇÃO {A50}
                this.argumentos();
                //AÇÃO {A23}
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
            //AÇÃO {A07}
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

    private void expWrite() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getLexema().equals("string")) {
            //AÇÃO {A59}
        } else {
            this.id--;
            this.varWrite();
        }
    }

    private void varWrite() {
        this.id++;
        Token token = this.getProximoToken();
        if (token.getClasse().equals("cId") && !token.getTipo().equals("Palavra Reservada")) {
            //AÇÃO {A09}
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
        if (token.getLexema().equals("+")) {
            this.termo();
            if (this.erro) {
                return;
            }
            //AÇÃO {A37}

            this.expressaoLinha();
        } else if (token.getLexema().equals("-")) {
            this.termo();
            if (this.erro) {
                return;
            }
            //AÇÃO {A38}

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
        // AÇÃO {A26}

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
            // AÇÃO {A26}

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
            //AÇÃO {A25}
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
        // AÇÃO {A27}

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
            // AÇÃO {A27}
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
            // AÇÃO {A28}
        } else if (token.getLexema().equals("true")) {
            // AÇÃO {A29}
        } else if (token.getLexema().equals("false")) {
            // AÇÃO {A30}
        } else {
            this.id--;
            this.relacional();
        }
    }

    private void relacional() {
        this.expressao();
        if (this.erro) {
            return;
        }

        this.id++;
        Token token = this.getProximoToken();

        if (token.getLexema().equals("=")) {
            //AÇÃO{A31}
        } else if (token.getLexema().equals(">")) {
            //AÇÃO {A32}
        } else if (token.getLexema().equals("<")) {
            //AÇÃO {A34}
        } else if (token.getLexema().equals(">=")) {
            //AÇÃO {A33}
        } else if (token.getLexema().equals("<=")) {
            //AÇÃO {A35}
        } else if (token.getLexema().equals("<>")) {
            //AÇÃO {A36}
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                    token.getLinha(), "Falta um operador lógico (=, >, <, >=, <=, <>)");
            this.erro = true;
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
        if (token.getLexema().equals("*")) {
            this.fator();
            if (this.erro) {
                return;
            }
            //AÇÃO {A39}
            this.termoLinha();
        } else if (token.getLexema().equals("/")) {
            this.fator();
            if (this.erro) {
                return;
            }
            //AÇÃO {A40}
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
        } else {
            //AÇÃO  {A41}
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
                //AÇÃO {A55}
                this.variavel();
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
            //AÇÃO {A42}
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
            //AÇÃO {A55} AQUI
        } else {
            this.msgErro = String.format("Erro: \n(%03d) - %s",
                    token.getLinha(), "Id inválido");
            this.erro = true;
        }
    }

    public String getMsgErro() {
        return msgErro;
    }

    public void setMsgErro(String msgErro) {
        this.msgErro = msgErro;
    }

}
