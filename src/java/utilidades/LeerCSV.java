/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RA302
 */
public class LeerCSV {

    public static final String SEPARATOR = ";";
    public static final String QUOTE = "\"";

    private static LeerCSV instance;

    public static LeerCSV getInstance() {
        if (instance == null) {
            instance = new LeerCSV();
        }

        return instance;
    }

    public List<List<String>> getData(String path) throws Exception {
        BufferedReader br = null;
        List<List<String>> resultado = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(path));
            String line = br.readLine();

            while (null != line) {
                String[] fields = line.split(SEPARATOR);
                List<String> fila = new ArrayList<>();

                for (String s : fields) {
                    fila.add(s == null ? "" : Validaciones.palabra(s));
                }

                line = br.readLine();
                resultado.add(fila);
            }

            //Se elimina la fila que corresponde a los nombres de la columna en cada archivo
            resultado.remove(0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                br.close();
            }
        }

        return resultado;
    }
}
