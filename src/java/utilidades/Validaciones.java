package utilidades;

import java.util.List;

/**
 * <table>
 * <tr>
 * <td>Julio</td>
 * <td>Jonathan</td>
 * </tr>
 * </table>
 * @author RA302
 */
public class Validaciones {
    
    public boolean exists(String id, List<String> ids){
        return ids.contains(id);
    }
    
    
}
