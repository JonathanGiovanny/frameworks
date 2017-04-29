package utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <table>
 * <tr>
 * <td>Julio</td>
 * <td>Jonathan</td>
 * </tr>
 * </table>
 *
 * @author RA302
 */
public class Validaciones {

    public static boolean exists(String id, List<String> ids) {
        return ids.contains(id);
    }

    public static List<List<String>> eliminarColumna(List<List<String>> matriz, int numColumna) {
        for (List<String> fila : matriz) {
            fila.remove(numColumna);
        }

        return matriz;
    }

    public static String palabra(String palabra) {
        String palabraTildes = palabra;
        palabraTildes = palabraTildes.replace("Ã±", "ñ");
        palabraTildes = palabraTildes.replace("Ã¡", "á");
        palabraTildes = palabraTildes.replace("Ã©", "é");
        palabraTildes = palabraTildes.replace("Ã­", "í");
        palabraTildes = palabraTildes.replace("Ã³", "ó");
        palabraTildes = palabraTildes.replace("Ãº", "ú");
        palabraTildes = palabraTildes.replace("Ã¤", "ä");
        palabraTildes = palabraTildes.replace("Ã«", "ë");
        palabraTildes = palabraTildes.replace("Ã¯", "ï");
        palabraTildes = palabraTildes.replace("Ã¶", "ö");
        palabraTildes = palabraTildes.replace("Ã¼", "ü");
        palabraTildes = palabraTildes.replace("ÃŸ", "ß");
        palabraTildes = palabraTildes.replace("Ãª", "ê");
        palabraTildes = palabraTildes.replace("Ã¢", "â");
        return palabraTildes;
    }

    public static Date validarFechas(String fecha) {
        if (fecha.length() <= 8) {
            fecha = fecha.concat("-01");
        }

        SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yy");
        Date resultado = null;

        try {
            resultado = formato.parse(fecha);
        } catch (Exception e) {
            System.out.println("Formato no valido");
        }

        if (resultado != null) {
            try {
                formato = new SimpleDateFormat("dd/mm/yyyy");
                resultado = formato.parse(fecha);
            } catch (Exception e) {
                System.out.println("Formato no valido");
            }
        }

        if (resultado != null) {
            try {
                formato = new SimpleDateFormat("dd-mm-yyyy");
                resultado = formato.parse(fecha);
            } catch (Exception e) {
                System.out.println("Formato no valido");
            }
        }

        if (resultado != null) {
            try {
                formato = new SimpleDateFormat("dd-mm-yy");
                resultado = formato.parse(fecha);
            } catch (Exception e) {
                System.out.println("Formato no valido");
            }
        }

        if (resultado != null) {
            try {
                formato = new SimpleDateFormat("yyyy-mm-dd");
                resultado = formato.parse(fecha);
            } catch (Exception e) {
                System.out.println("Formato no valido");
            }
        }
        
        return resultado;
    }
}
