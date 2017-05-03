package utilidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
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
        palabraTildes = palabraTildes.replace("Ã¢", "â");
        palabraTildes = palabraTildes.replace("Ãª", "ê");
        palabraTildes = palabraTildes.replace("Ã®", "î");
        palabraTildes = palabraTildes.replace("Ã´", "ô");
        palabraTildes = palabraTildes.replace("Ã»", "û");
        return palabraTildes;
    }

    public static Date validarFechas(String fecha) {
        if (fecha.length() < 8) {
            fecha = fecha.concat("-01");
        }

        SimpleDateFormat formato = null;
        Date resultado = null;

        if (fecha.length() == 8) {
            try {
                formato = new SimpleDateFormat("dd/MM/yy");
                resultado = formato.parse(fecha);
            } catch (Exception e) {
                System.out.println("Formato no valido");
            }

            if (resultado == null) {
                try {
                    formato = new SimpleDateFormat("dd-MM-yy");
                    resultado = formato.parse(fecha);
                } catch (Exception e) {
                    System.out.println("Formato no valido");
                }
            }

        } else {
            String[] componentesFecha = fecha.split(fecha.contains("/") ? "/" : "-");
            if (componentesFecha[2].length() == 4) {
                if (resultado == null) {
                    try {
                        formato = new SimpleDateFormat("dd/MM/yyyy");
                        resultado = formato.parse(fecha);
                    } catch (Exception e) {
                        System.out.println("Formato no valido");
                    }
                }

                if (resultado == null) {
                    try {
                        formato = new SimpleDateFormat("dd-MM-yyyy");
                        resultado = formato.parse(fecha);
                    } catch (Exception e) {
                        System.out.println("Formato no valido");
                    }
                }

            } else {
                if (resultado == null) {
                    try {
                        formato = new SimpleDateFormat("yyyy-MM-dd");
                        resultado = formato.parse(fecha);
                    } catch (Exception e) {
                        System.out.println("Formato no valido");
                    }
                }
            }
        }

        return resultado;
    }
}
