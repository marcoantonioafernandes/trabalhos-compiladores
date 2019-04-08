package Control;

import Model.Simbolo;
import Model.Token;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class AnalisadorLexico {

    private List<Token> tokens;
    private TreeSet<String> reservadas;
    private TreeSet<Simbolo> simbolos;

    public AnalisadorLexico() {
        this.preencheReservadas();
        this.tokens = new ArrayList<>();
        this.simbolos = new TreeSet<>();
    }

    public List<Token> getTokens(){
        return this.tokens;
    }
    public String tokensToString() {
        StringBuilder sb = new StringBuilder();
        for (Token token : this.tokens) {
            sb.append(token.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public void analisar(String[] f) {
        this.tokens = new ArrayList<>();
        this.simbolos = new TreeSet<>();
        int linha, coluna;
        int i, j = 0;

        for (i = 0; i < f.length; i++) {
            for (j = 0; j < f[i].length(); j++) {
                linha = i + 1;
                coluna = j + 1;

                // ignorando comentarios
                while (f[i].charAt(j) == '{') {
                    while ((f[i].charAt(j) != '}')) {
                        if (j == f[i].length() - 1) {
                            i++;
                            j = 0;
                        } else {
                            j++;
                        }
                    }
                    if (j == (f[i].length() - 1)) {
                        i++;
                        j = 0;
                    }
                }

                if (((f[i].charAt(j) > 64) && (f[i].charAt(j) < 91))
                        || ((f[i].charAt(j) > 96) && (f[i].charAt(j) < 123))) {
                    String s = "";
                    boolean fim = false;
                    s = s + f[i].charAt(j);
                    j++;
                    while ((j < (f[i].length())) && !fim) {
                        if (((f[i].charAt(j) > 64) && (f[i].charAt(j) < 91))
                                || ((f[i].charAt(j) > 96) && (f[i].charAt(j) < 123))
                                || ((f[i].charAt(j) > 47) && (f[i].charAt(j) < 58))) {
                            s = s + f[i].charAt(j);
                            j++;
                        } else {
                            fim = true;
                        }
                    }
                    if (this.reservadas.contains(s.toLowerCase())) {
                        tokens.add(new Token("Palavra Reservada", "cId", s, linha, coluna));
                    } else {
                        tokens.add(new Token("Identificador", "cId", s, linha, coluna));
                        if (!this.simbolos.contains(new Simbolo(s, "Variável"))) {
                            this.simbolos.add(new Simbolo(s, "Variável"));
                        }
                    }
                    j--;
                } else if (f[i].charAt(j) == '-' || f[i].charAt(j) == '+'
                        || (f[i].charAt(j) > 47 && f[i].charAt(j) < 58)) {

                    int estado = 1;
                    boolean inteiro = false;
                    boolean real = false;
                    boolean erro = false;
                    int operacao = 0;
                    StringBuilder sb = new StringBuilder();
                    while (estado != 8) {
                        switch (estado) {
                            case 1:
                                if (f[i].length() < (j + 1)) {
                                    erro = true;
                                    estado = 8;
                                    break;
                                }
                                if (f[i].charAt(j) == '+' || f[i].charAt(j) == '-') {
                                    sb.append(f[i].charAt(j));
                                    estado = 2;
                                    if (f[i].charAt(j) == '+') {
                                        operacao = 1;
                                    }else{
                                        operacao = -1;
                                    }
                                    j++;
                                }else if(f[i].charAt(j) > 47 && f[i].charAt(j) < 58){
                                    sb.append(f[i].charAt(j));
                                    estado = 3;
                                    j++;
                                } else{
                                    erro = true;
                                    estado = 8;
                                }
                            break;
                            
                            case 2:
                                if (f[i].length() < (j + 1)) {
                                    estado = 8;
                                    j--;
                                    break;
                                }
                                if(f[i].charAt(j) > 47 && f[i].charAt(j) < 58){
                                    sb.append(f[i].charAt(j));
                                    estado = 3;
                                    j++;
                                } else {
                                    estado = 8;
                                    j--;
                                }
                            break;
                            
                            case 3:
                                if (f[i].length() < (j + 1)) {
                                    inteiro = true;
                                    estado = 8;
                                    j--;
                                    break;
                                }
                                if (f[i].charAt(j) > 47 && f[i].charAt(j) < 58) {
                                    sb.append(f[i].charAt(j));
                                    estado = 3;
                                    j++;
                                } else if (f[i].charAt(j) == '.'){
                                    sb.append(f[i].charAt(j));
                                    estado = 4;
                                    j++;
                                } else if (f[i].charAt(j) == 'E' || f[i].charAt(j) == 'e'){
                                    sb.append(f[i].charAt(j));
                                    estado = 6;
                                    inteiro = true;
                                    j++;
                                }else {
                                    estado = 8;
                                    inteiro = true;
                                    j--;
                                }
                            break;
                                
                            case 4:
                                if (f[i].length() < (j + 1)) {
                                    erro = true;
                                    estado = 8;
                                    j--;
                                    break;
                                }
                                if (f[i].charAt(j) > 47 && f[i].charAt(j) < 58){
                                    sb.append(f[i].charAt(j));
                                    estado = 5;
                                    j++;
                                } else {
                                    erro = true;
                                    j--;
                                    estado = 8;
                                }
                            break;
                                
                            case 5:
                                if (f[i].length() < (j + 1)) {
                                    real = true;
                                    estado = 8;
                                    j--;
                                    break;
                                }
                                if (f[i].charAt(j) > 47 && f[i].charAt(j) < 58){
                                    sb.append(f[i].charAt(j));
                                    estado = 5;
                                    j++;
                                }else if (f[i].charAt(j) == 'E' || f[i].charAt(j) == 'e'){
                                    sb.append(f[i].charAt(j));
                                    estado = 6;
                                    real = true;
                                    j++;
                                }else {
                                    real = true;
                                    estado = 8;
                                    j--;
                                }
                            break;
                            
                            case 6:
                                if (f[i].length() < (j + 1)) {
                                    erro = true;
                                    estado = 8;
                                    j--;
                                    break;
                                }
                                if ((f[i].charAt(j) > 47 && f[i].charAt(j) < 58)
                                        || f[i].charAt(j) == '+' || f[i].charAt(j) == '-'){
                                    sb.append(f[i].charAt(j));
                                    estado = 7;
                                    j++;
                                }else {
                                    erro = true;
                                    estado = 8;
                                    j--;
                                }
                            break;
                            
                            case 7:
                                if (f[i].length() < (j + 1)) {
                                    estado = 8;
                                    j--;
                                    break;
                                }
                                if (f[i].charAt(j) > 47 && f[i].charAt(j) < 58){
                                    sb.append(f[i].charAt(j));
                                    estado = 7;
                                    j++;
                                }else {
                                    estado = 8;
                                    j--;
                                }
                            break;
                        }
                    }
                    
                    if(erro) {
                        tokens.add(new Token("Erro Léxico", "", sb.toString(), linha, coluna));
                    }else if(inteiro) {
                        tokens.add(new Token("Inteiro", "cInt", sb.toString(), linha, coluna));
                    }else if(real) {
                        tokens.add(new Token("Real","cReal", sb.toString(), linha, coluna));
                    }else if(operacao == 1){
                        tokens.add(new Token("Adição", "cAdd", "+", linha, coluna));
                    }else if (operacao == -1){
                        tokens.add(new Token("Subtração", "cSub", "-", linha, coluna));
                    }

                } else if (f[i].charAt(j) == '"') {
                    StringBuilder sb = new StringBuilder();
                    j++;
                    while (f[i].charAt(j) != '"') {
                        sb.append(f[i].charAt(j));
                        j++;
                    }
                    tokens.add(new Token("String", "cStr", sb.toString(), linha, coluna));

                } else if (f[i].charAt(j) == ':') {
                    if (f[i].length() != (j + 1)) {
                        if (f[i].charAt(j + 1) == '=') {
                            tokens.add(new Token("Atribuição", "cAtr", ":=", linha, coluna));
                            j++;
                        } else {
                            tokens.add(new Token("Dois Pontos", "cDPto", ":", linha, coluna));
                        }
                    } else {
                        tokens.add(new Token("Dois Pontos", "cDPto", ":", linha, coluna));
                    }

                } else if (f[i].charAt(j) == '(') {
                    tokens.add(new Token("Parêntese Esquerdo", "cLPar", "(", linha, coluna));
                } else if (f[i].charAt(j) == ')') {
                    tokens.add(new Token("Parêntese Direito", "cDPar", ")", linha, coluna));
                } else if (f[i].charAt(j) == '*') {
                    tokens.add(new Token("Multiplicação", "cMul","*", linha, coluna));
                } else if (f[i].charAt(j) == '/') {
                    tokens.add(new Token("Divisão", "cDiv", "/", linha, coluna));
                } else if (f[i].charAt(j) == '=') {
                    tokens.add(new Token("Igual", "cEQ", "=", linha, coluna));

                } else if (f[i].charAt(j) == '<') {
                    boolean aux = true;
                    if (f[i].length() != (j + 1)) {
                        if (f[i].charAt(j + 1) == '>') {
                            tokens.add(new Token("Diferente", "cNE","<>", linha, coluna));
                            j++;
                            aux = false;
                        } else if (f[i].charAt(j + 1) == '=') {
                            tokens.add(new Token("Menor ou Igual", "cLE", "<=", linha, coluna));
                            j++;
                            aux = false;
                        }
                    }
                    if (aux) {
                        tokens.add(new Token("Menor", "cLT", "<", linha, coluna));
                    }
                } else if (f[i].charAt(j) == '>') {
                    boolean aux = true;
                    if (f[i].length() != (j + 1)) {
                        if (f[i].charAt(j + 1) == '=') {
                            tokens.add(new Token("Maior ou Igual", "cGE", ">=", linha, coluna));
                            j++;
                            aux = false;
                        }
                    }
                    if (aux) {
                        tokens.add(new Token("Maior", "cGT", ">", linha, coluna));
                    }
                } else if (f[i].charAt(j) == ';') {
                    tokens.add(new Token("Ponto e Vírgula", "cPVir", ";", linha, coluna));
                } else if (f[i].charAt(j) == ',') {
                    tokens.add(new Token("Vírgula", "cVir", ",", linha, coluna));
                } else if (f[i].charAt(j) == '.') {
                    tokens.add(new Token("Ponto", "cPto", ".", linha, coluna));
                }
                //loop end
            }
        }
        tokens.add(new Token("Fim de Arquivo", "cEOF", "EOF", i + 1, j + 1));

    }

    private void preencheReservadas() {
        this.reservadas = new TreeSet<>();
        this.reservadas.add("integer");
        this.reservadas.add("real");
        this.reservadas.add("string");
        this.reservadas.add("boolean");
        this.reservadas.add("false");
        this.reservadas.add("true");
        this.reservadas.add("and");
        this.reservadas.add("array");
        this.reservadas.add("begin");
        this.reservadas.add("break");
        this.reservadas.add("case");
        this.reservadas.add("const");
        this.reservadas.add("div");
        this.reservadas.add("do");
        this.reservadas.add("downto");
        this.reservadas.add("else");
        this.reservadas.add("end");
        this.reservadas.add("file");
        this.reservadas.add("for");
        this.reservadas.add("function");
        this.reservadas.add("goto");
        this.reservadas.add("if");
        this.reservadas.add("in");
        this.reservadas.add("label");
        this.reservadas.add("mod");
        this.reservadas.add("nil");
        this.reservadas.add("not");
        this.reservadas.add("of");
        this.reservadas.add("or");
        this.reservadas.add("packed");
        this.reservadas.add("procedure");
        this.reservadas.add("program");
        this.reservadas.add("record");
        this.reservadas.add("repeat");
        this.reservadas.add("set");
        this.reservadas.add("then");
        this.reservadas.add("to");
        this.reservadas.add("type");
        this.reservadas.add("until");
        this.reservadas.add("var");
        this.reservadas.add("while");
        this.reservadas.add("with");
        this.reservadas.add("read");
        this.reservadas.add("write");
    }
}
