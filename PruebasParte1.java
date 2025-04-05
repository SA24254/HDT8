import org.junit.Test;
import static org.junit.Assert.*;

public class PruebasParte1 {
    @Test
    public void testVectorHeap() {
        VectorHeap<Paciente> heap = new VectorHeap<>();
        heap.add(new Paciente("Maria", "Apendicitis", 'A'));
        heap.add(new Paciente("Juan", "Fractura", 'C'));
        assertEquals("Maria", heap.remove().getNombre());
    }

    @Test
    public void testJCF() {
        PriorityQueue<Paciente> cola = new PriorityQueue<>();
        cola.add(new Paciente("Carmen", "Parto", 'B'));
        cola.add(new Paciente("Lorenzo", "Chikunguya", 'E'));
        assertEquals("Carmen", cola.poll().getNombre());
    }
}
