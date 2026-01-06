

package Trabajodeenfoque;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

public class DAOTest {

    @Test
    public void testListarTabla() {
        DAO dao = new DAO();
        List<Object[]> resultados = dao.listarTabla();
        assertNotNull(resultados);
    }
}

