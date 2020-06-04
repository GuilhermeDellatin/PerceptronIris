package application;

public class Iris {
    /*
   1. sepal length in cm
   2. sepal width in cm
   3. petal length in cm
   4. petal width in cm
   5. class:
    -- Iris Setosa
    -- Iris Versicolour
    -- Iris Virginica
    */
    private double a1, a2, a3, a4;
    private int classeIris;
    private int classificacao;

    public Iris(){
    }

    public Iris(double a1, double a2, double a3, double a4, int classeIris, int classificacao) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.classeIris = classeIris;
        this.classificacao = classificacao;
    }

    public double getA1() {
        return a1;
    }

    public void setA1(double a1) {
        this.a1 = a1;
    }

    public double getA2() {
        return a2;
    }

    public void setA2(double a2) {
        this.a2 = a2;
    }

    public double getA3() {
        return a3;
    }

    public void setA3(double a3) {
        this.a3 = a3;
    }

    public double getA4() {
        return a4;
    }

    public void setA4(double a4) {
        this.a4 = a4;
    }

    public int getClasseIris() {
        return classeIris;
    }

    public void setClasseIris(int classeIris) {
        this.classeIris = classeIris;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

}
