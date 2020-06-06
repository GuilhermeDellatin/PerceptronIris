package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    static public List<Iris> baseDados = new ArrayList<Iris>();
    static public List<Iris> resultado = new ArrayList<Iris>();

    //Bases individuais para neuronio classe 1,2 e 3
    static public double[][] baseTreina;
    static public double[][] baseValidacao;
    static public double[][] baseTeste;


    public static void main(String[] args) {
        //Lista resultado final
        List<Estatistica> listaResultadoFinal = new ArrayList<Estatistica>();

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

    static private void sorteiaBases(List<Iris> listaIris, boolean valida, int classe) {

        double percentualTreina = 0, percentualValida = 0, percentualTeste = 0;

        if(valida) {
            percentualTreina = 0.5;
            percentualValida = 0.2;
            percentualTeste = 0.3;
        }
        else{
            percentualTreina = 0.7;
            percentualValida = 0;
            percentualTeste = 0.3;
        }

        //conta numero de casos
        int numCaso1 = 0, numCaso2 = 0, numCaso3 = 0;
        int numBaseTreinaC1 = 0, numBaseTreinaC2 = 0, numBaseTreinaC3 = 0;
        int numBaseValidaC1 = 0, numBaseValidaC2 = 0, numBaseValidaC3 = 0;
        int numBaseTesteC1 = 0, numBaseTesteC2 = 0, numBaseTesteC3 = 0;

        for(Iris iris : listaIris) {
            if(iris.getClasseIris()==1)
                numCaso1++;
            else if (iris.getClasseIris()==2)
                numCaso2++;
            else if (iris.getClasseIris()==3)
                numCaso3++;
        }

        //define tamanho de cada tipo de base para cada classe
        numBaseTreinaC1 =(int) (numCaso1 * percentualTreina);
        numBaseTreinaC2 =(int) (numCaso2 * percentualTreina);
        numBaseTreinaC3 =(int) (numCaso3 * percentualTreina);

        numBaseValidaC1 = (int) (numCaso1 * percentualValida);
        numBaseValidaC2 = (int) (numCaso2 * percentualValida);
        numBaseValidaC3 = (int) (numCaso3 * percentualValida);

        numBaseTesteC1 = (numCaso1  - (numBaseTreinaC1 + numBaseValidaC1));
        numBaseTesteC2 = (numCaso2 - (numBaseTreinaC2 + numBaseValidaC2));
        numBaseTesteC3 = (numCaso3 - (numBaseTreinaC3 + numBaseValidaC3));

        //declara o tamanho de cada base
        int tamBaseTreina = 0, tamBaseValida = 0, tamBaseTeste = 0;
        if(numBaseValidaC1>0) {
            //se houver validação
            tamBaseTreina = numBaseTreinaC1 + numBaseTreinaC2 + numBaseTreinaC3;
            tamBaseValida = numBaseValidaC1 + numBaseValidaC2 + numBaseValidaC3;
            tamBaseTeste = listaIris.size()-(tamBaseTreina+tamBaseValida);
            baseTreina = new double[tamBaseTreina][6];
            baseValidacao = new double[tamBaseValida][6];
            baseTeste = new double[tamBaseTeste][6];
        }else{
            //se não houver validação
            tamBaseTreina = numBaseTreinaC1 + numBaseTreinaC2 + numBaseTreinaC3;
            tamBaseValida = 0;
            tamBaseTeste = listaIris.size() - (tamBaseTreina + tamBaseValida);
            baseTreina = new double[tamBaseTreina][6];
            baseTeste = new double[tamBaseTeste][6];
        }
        int sorteia = 0;
        int contaBase1Classe1 = 0, contaBase1Classe2 = 0, contaBase1Classe3 = 0;
        int contaBase2Classe1 = 0, contaBase2Classe2 = 0, contaBase2Classe3 = 0;
        int contaBase3Classe1 = 0, contaBase3Classe2 = 0, contaBase3Classe3 = 0;
        int linhaBase1 = 0, linhaBase2 = 0, linhaBase3 = 0;

        Random randBase = new Random();
        int contaelemento = -1;
        for (Iris separa : listaIris) {
            contaelemento++;
            System.out.println(contaelemento);


            //seleciona 0 ou 1 se for a classe de interesse
            int valorD = 0;
            if (separa.getClasseIris() == classe)
                valorD = 1;

            /*
              Sorteia qual base vai pertencer o elemento proporcional
              tamanho da base respeitando o tamanho de cada base especifica
            */
            boolean passa;
            do {

                passa = true;

                int numeroRandomico = randBase.nextInt(99);

                if (tamBaseValida > 0) {
                    if (numeroRandomico <= 69)
                        sorteia = 1;
                    else if (numeroRandomico > 69 && numeroRandomico <= 84)
                        sorteia = 2;
                    else if (numeroRandomico > 84 && numeroRandomico <= 99)
                        sorteia = 3;
                } else {
                    if (numeroRandomico <= 69)
                        sorteia = 1;
                    else if (numeroRandomico > 69 && numeroRandomico <= 99)
                        sorteia = 2;
                }

                int classeElemento = separa.getClasseIris();

                //validar meu sorteio
                if (sorteia==1 && linhaBase1 < tamBaseTreina &&
                        ((separa.getClasseIris()==1 && contaBase1Classe1 < numBaseTreinaC1)
                                || (separa.getClasseIris()==2 && contaBase1Classe2 < numBaseTreinaC2)
                                || (separa.getClasseIris()==3 && contaBase1Classe3 < numBaseTreinaC3)))
                    passa = false;
                else if (sorteia==2 && linhaBase2 < tamBaseTeste &&
                        ((separa.getClasseIris()==1 && contaBase2Classe1 < numBaseTesteC1)
                                || (separa.getClasseIris()==2 && contaBase2Classe2 < numBaseTesteC2)
                                || (separa.getClasseIris()==3 && contaBase2Classe3 < numBaseTesteC3)))
                    passa=false;
                else if (sorteia==3 && linhaBase3 < tamBaseValida &&
                        ((separa.getClasseIris()==1 && contaBase3Classe1 < numBaseValidaC1)
                                || (separa.getClasseIris()==2 && contaBase3Classe2 < numBaseValidaC2)
                                || (separa.getClasseIris()==3 && contaBase3Classe3 < numBaseValidaC3)))
                    passa=false;
            }while(passa);

            if (sorteia==1){

                //base treinamento
                baseTreina[linhaBase1][0] = separa.getA1();
                baseTreina[linhaBase1][1] = separa.getA2();
                baseTreina[linhaBase1][2] = separa.getA3();
                baseTreina[linhaBase1][3] = separa.getA4();
                baseTreina[linhaBase1][4] = separa.getClasseIris();
                baseTreina[linhaBase1][5] = valorD;
                linhaBase1++;
                //acumula tipo de classe inserida na base
                if (separa.getClasseIris() == 1)
                    contaBase1Classe1++;
                else if (separa.getClasseIris()==2)
                    contaBase1Classe2++;
                else if (separa.getClasseIris()==3)
                    contaBase1Classe3++;
            }

            else if (sorteia == 2){

                //base teste
                baseTeste[linhaBase2][0] = separa.getA1();
                baseTeste[linhaBase2][1] = separa.getA2();
                baseTeste[linhaBase2][2] = separa.getA3();
                baseTeste[linhaBase2][3] = separa.getA4();
                baseTeste[linhaBase2][4] = separa.getClasseIris();
                baseTeste[linhaBase2][5] = valorD;

                linhaBase2++;

                //acumula tipo de classe inserida na base
                if (separa.getClasseIris() == 1)
                    contaBase2Classe1++;
                else if (separa.getClasseIris()==2)
                    contaBase2Classe2++;
                else if (separa.getClasseIris()==3)
                    contaBase2Classe3++;

            }
            else if (sorteia == 3){
                //base validação
                baseValidacao[linhaBase3][0] = separa.getA1();
                baseValidacao[linhaBase3][1] = separa.getA2();
                baseValidacao[linhaBase3][2] = separa.getA3();
                baseValidacao[linhaBase3][3] = separa.getA4();
                baseValidacao[linhaBase3][4] = separa.getClasseIris();
                baseValidacao[linhaBase3][5] = valorD;

                linhaBase3++;

                //acumula tipo de classe inserida na base
                if (separa.getClasseIris() == 1)
                    contaBase3Classe1++;
                else if (separa.getClasseIris()==2)
                    contaBase3Classe2++;
                else if (separa.getClasseIris()==3)
                    contaBase3Classe3++;

            }
        }
    }

    static private void atualizaBases(int classe){
        int tamBaseTreina = baseTreina.length;
        int tamBaseTeste = baseTeste.length;
        int tamBaseValida = 0;

        if (!(baseValidacao==null)) {
            tamBaseValida=baseValidacao.length;
        }

        int valorD = 1;
        for (int x=0; x < tamBaseTreina; x++) {
            if(baseTreina[x][4] == classe) baseTreina[x][5] = valorD;
            else baseTreina[x][5] = 0;
        }

        for (int x=0; x<tamBaseTeste; x++) {
            if(baseTeste[x][4] == classe) baseTeste[x][5] = valorD;
            else baseTeste[x][5] = 0;
        }

        for (int x=0; x<tamBaseValida; x++) {
            if(baseValidacao[x][4] == classe) baseValidacao[x][5] = valorD;
            else baseValidacao[x][5] = 0;
        }
    }


}


