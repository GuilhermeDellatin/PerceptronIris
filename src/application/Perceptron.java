package application;

import java.util.ArrayList;
import java.util.List;

public class Perceptron {

    private double[] w; //pesos sinápticos
    private double NET; //atributo responsável pelo somatório (rede)
    private double alfa; //taxa de redução da velocidade de aprendizado
    private double bias; //valor bias
    private int maxIte; //valor máximo de iterações durante o processo de aprendizado.

    private final int epocasMax = 30;//número max de epocas
    private int count = 0; //contagem de epocas durante o treinamento
    private int[][] matrizAprendizado = new int[5][5];

    public void setW(double[] w) {
        this.w = w;
    }

    public void setNET(double NET) {
        this.NET = NET;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public void setMaxIte(int maxIte) {
        this.maxIte = maxIte;
    }


    public List<Iris> executarRNA(double[][] base, double[] w_c1, double[] w_c2, double[] w_c3) {
        //resultado
        List<Iris> listaResultado = new ArrayList<Iris>();

        double y=0, d=0;

        //inicializa o bias
        for(int x = 0; x < base.length; x++){
            base[x][0]= this.bias;
            base[x][5] = d;
        }

        for(int x = 0; x < base.length; x++){
            d=base[x][4]; //classe do vinho que veio classificada

            //pega amostra
            double[] amostra = new double[6];
            for(int j = 0; j < 6; j++)
            {
                amostra[j] = base[x][j];
            }

            //testar a amostra nos neuronios
            double rna1 = executar(amostra, w_c1);
            double rna2 = executar(amostra, w_c2);
            double rna3 = executar(amostra, w_c3);

            int resultado = 0;
            if (rna1 > rna2 && rna1 > rna3) resultado = 1;
            else if (rna2 > rna1 && rna2 > rna3) resultado = 2;
            else resultado = 3;

            Iris iris = new Iris();
            iris.setA1(amostra[0]);
            iris.setA2(amostra[1]);
            iris.setA3(amostra[2]);
            iris.setA4(amostra[3]);
            iris.setClasseIris((int) amostra[4]);
            iris.setClassificacao(resultado);

            listaResultado.add(iris);
        }

        return listaResultado;
    }

    private double executar(double[] amostra, double[] w) {
        double y = 0, soma = 0;
        //calcula a saida da função de ativação e função soma
        soma = 0;
        for(int x = 0; x< w.length; x++) {
            soma = soma + (amostra[x] * w[x]);
        }
        //retorno da função soma
        y = soma;
        return y;
    }

}
