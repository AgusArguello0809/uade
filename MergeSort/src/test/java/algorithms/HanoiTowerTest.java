package algorithms;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

class HanoiTowerTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testSecuenciaParaN1() {
        HanoiTower h = new HanoiTower();
        h.transfer("A", "C", "B", 1);

        String salida = outContent.toString().trim();
        assertEquals("Move A to C", salida);
    }

    @Test
    void testSecuenciaExactaParaN3() {
        HanoiTower h = new HanoiTower();
        h.transfer("A", "C", "B", 3);

        String salida = outContent.toString().trim();
        String[] lineas = salida.split("\\R+"); // divide por saltos de línea
        // Deben ser 2^3 - 1 = 7 movimientos
        assertEquals(7, lineas.length, "Cantidad de movimientos incorrecta");

        List<String> esperado = List.of(
                "Move A to B",
                "Move A to C",
                "Move B to C",
                "Move A to B",
                "Move C to A",
                "Move C to B",
                "Move A to B"
        );
        // ¡OJO! El orden correcto para tu implementación es (from=a, to=c, aux=b):
        // transfer(a,b,c,n-1), move(a,c), transfer(b,c,a,n-1)
        // Eso produce exactamente esta secuencia para n=3:
        List<String> esperadoTuOrden = List.of(
                "Move A to C",
                "Move A to B",
                "Move C to B",
                "Move A to C",
                "Move B to A",
                "Move B to C",
                "Move A to C"
        );

        // Verificá contra la secuencia que corresponde a TU orden
        assertIterableEquals(esperadoTuOrden, List.of(lineas));
    }

    @Test
    void testCantidadMovimientosParaVariosN() {
        HanoiTower h = new HanoiTower();

        for (int n : new int[]{1, 2, 3, 4}) {
            outContent.reset();
            h.transfer("A", "C", "B", n);
            long cant = outContent.toString().lines().count();
            long esperado = (1L << n) - 1; // 2^n - 1
            assertEquals(esperado, cant, "Cantidad de movimientos incorrecta para n=" + n);
        }
    }
}
