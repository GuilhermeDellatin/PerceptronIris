package application;

public class Estatistica {

    private String VP; //Verdadeiro Positivo
    private String VN; //Verdadeiro Negativo
    private String FP; //Falso Positivo
    private String FN; //Falso Negativo

    public Estatistica(String VP, String VN, String FP, String FN) {
        this.VP = VP;
        this.VN = VN;
        this.FP = FP;
        this.FN = FN;
    }

    public String getVP() {
        return VP;
    }

    public void setVP(String VP) {
        this.VP = VP;
    }

    public String getVN() {
        return VN;
    }

    public void setVN(String VN) {
        this.VN = VN;
    }

    public String getFP() {
        return FP;
    }

    public void setFP(String FP) {
        this.FP = FP;
    }

    public String getFN() {
        return FN;
    }

    public void setFN(String FN) {
        this.FN = FN;
    }
}
