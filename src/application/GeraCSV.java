package application;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class GeraCSV {
    static public void geraCSV(List<Estatistica> listaEstatistica, String nomeArquivo) throws IOException {
        String[] cabecalho = {"VP","VN","FP","FN"};

        Writer writer = new FileWriter(nomeArquivo);
        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeNext(cabecalho);
        for(Estatistica estatistica : listaEstatistica) {
            String[] linha = {estatistica.getVP(),  estatistica.getVN(), estatistica.getFP(), estatistica.getFN()};
            csvWriter.writeNext(linha);
        }
        writer.flush();
        writer.close();
    }
}
