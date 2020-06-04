package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static public List<Iris> baseDados = new ArrayList<Iris>();
    static public List<Iris> resultado = new ArrayList<Iris>();

    //Bases individuais para neuronio classe 1,2 e 3
    static public double[][] baseTreina;
    static public double[][] baseValidacao;
    static public double[][] baseTeste;


    public static void main(String[] args) {
        //Lista resultado final

        //Definir os padrões de execução da rede neural
        double bias = 1;
        double taxaAprendizado = 0.01;
        double[] pesoW = {0, 0, 0, 0};
        int numeroIteracoes = 20000;

        //boolean gerarValidacaoBase = false;
        //boolean normalizarBase = false;
        boolean gerarValidacaoBase = true;
        boolean normalizarBase = true;
    }

    static private List<Iris> readFile(String arquivo) {
        List<Iris> listaBase = new ArrayList<Iris>();

        try {
            BufferedReader base = new BufferedReader(new FileReader(arquivo));
            //Realiza a leitura linha a linha
            while (base.ready()) {
                String linhaBase = base.readLine();
                //Os valores na linha são definidos pelo separador ","
                String[] valueFields = linhaBase.split(",");
                Iris iris = new Iris();
                iris.setClasseIris(Integer.parseInt(valueFields[0]));
                iris.setA1(Double.parseDouble(valueFields[1]));
                iris.setA2(Double.parseDouble(valueFields[2]));
                iris.setA3(Double.parseDouble(valueFields[3]));
                iris.setA4(Double.parseDouble(valueFields[4]));



                listaBase.add(iris);

            }
            base.close();

        } catch (Exception e) {
            System.out.println("Erro" + e);
        }

        return listaBase;
    }

    private static List<Iris> normalizarBase(List<Iris> listaIris) {

        List<Iris> listaNormalizada = new ArrayList<Iris>();
        double[] a1 = new double[listaIris.size()];
        double[] a2 = new double[listaIris.size()];
        double[] a3 = new double[listaIris.size()];
        double[] a4 = new double[listaIris.size()];

        int contaElementos = 0;

        for(Iris iris : listaIris){
            a1[contaElementos] = iris.getA1();
            a2[contaElementos] = iris.getA2();
            a3[contaElementos] = iris.getA3();
            a4[contaElementos] = iris.getA4();
            contaElementos++;
        }

        //calcular o desvio padrão dos vetores e sua média
        double desvioPadraoA1 = calcularDesvioPadrao(a1);
        double mediaA1 = calcularMedia(a1);
        double desvioPadraoA2 = calcularDesvioPadrao(a2);
        double mediaA2 = calcularMedia(a2);
        double desvioPadraoA3 = calcularDesvioPadrao(a3);
        double mediaA3 = calcularMedia(a1);
        double desvioPadraoA4 = calcularDesvioPadrao(a4);
        double mediaA4 = calcularMedia(a4);

        for(Iris iris : listaIris){

            Iris irisNormalizado = new Iris();
            irisNormalizado.setA1((iris.getA1() - mediaA1) / desvioPadraoA1);
            irisNormalizado.setA2((iris.getA2() - mediaA2) / desvioPadraoA2);
            irisNormalizado.setA3((iris.getA3() - mediaA3) / desvioPadraoA3);
            irisNormalizado.setA4((iris.getA4() - mediaA4) / desvioPadraoA4);
            irisNormalizado.setClasseIris(iris.getClasseIris());

            listaNormalizada.add(irisNormalizado);

        }

        return listaNormalizada;
    }

    static private double calcularMedia(double[] listaValores){
        double resultadoMedia = 0;
        double somatorio = 0;
        int tamanhoVetor = listaValores.length;

        for(int i = 0; i < tamanhoVetor; i++){
            somatorio = somatorio + listaValores[i];
        }

        resultadoMedia = somatorio / tamanhoVetor;

        return resultadoMedia;
    }

    static private double calcularDesvioPadrao(double[] listaValores){
        double media = calcularMedia(listaValores);
        double somatorio = 0;
        int tamanhoVetor = listaValores.length;

        //achar o somatorio
        for (int i = 0; i < tamanhoVetor; i++) {
            somatorio = somatorio + Math.pow((listaValores[i] - media), 2);
        }

        //achar a variancia
        //Soma dos quadrados da diferença entre cada valor e sua média aritméetica, dividida pela quantidade de elementos no vetor
        double valorVariancia = somatorio / tamanhoVetor;

        //Desvio padrão é a raiz quadrada da variância
        double valorDesvioPadrao = Math.sqrt(valorVariancia);

        return valorDesvioPadrao;
    }

}


