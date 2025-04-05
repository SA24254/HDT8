import java.io.BufferedReader;
import java.io.FileReader;
import java.util.PriorityQueue;

interface PriorityQueue<E extends Comparable<E>> {
    boolean add(E element);
    E remove();
    boolean isEmpty();
    int size();
}

class Paciente implements Comparable<Paciente> {
    private String nombre, sintoma;
    private char codigo;

    public Paciente(String nombre, String sintoma, char codigo) {
        this.nombre = nombre;
        this.sintoma = sintoma;
        this.codigo = codigo;
    }

    private int prioridadToInt() {
        return switch (codigo) {
            case 'A' -> 1; case 'B' -> 2; case 'C' -> 3; case 'D' -> 4; case 'E' -> 5; default -> 6;
        };
    }

    @Override
    public int compareTo(Paciente otro) {
        return Integer.compare(this.prioridadToInt(), otro.prioridadToInt());
    }

    @Override
    public String toString() {
        return nombre + ", " + sintoma + ", " + codigo;
    }
}

class VectorHeap<E extends Comparable<E>> implements PriorityQueue<E> {
    private ArrayList<E> data = new ArrayList<>();

    @Override
    public boolean add(E element) {
        data.add(element);
        upheap(data.size() - 1);
        return true;
    }

    @Override
    public E remove() {
        if (isEmpty()) return null;
        E min = data.get(0);
        E last = data.remove(data.size() - 1);
        if (!isEmpty()) {
            data.set(0, last);
            downheap(0);
        }
        return min;
    }

    private void upheap(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (data.get(index).compareTo(data.get(parent)) >= 0) break;
            swap(index, parent);
            index = parent;
        }
    }

    private void downheap(int index) {
        int left = 2 * index + 1;
        int smallest = index;
        if (left < data.size() && data.get(left).compareTo(data.get(smallest)) < 0) smallest = left;
        int right = left + 1;
        if (right < data.size() && data.get(right).compareTo(data.get(smallest)) < 0) smallest = right;
        if (smallest != index) {
            swap(index, smallest);
            downheap(smallest);
        }
    }

    private void swap(int i, int j) {
        E temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

    @Override
    public boolean isEmpty() { return data.isEmpty(); }

    @Override
    public int size() { return data.size(); }
}

public class EmergenciaHospital {
    public static void main(String[] args) {
        VectorHeap<Paciente> cola = new VectorHeap<>();
        cargarPacientes(cola, "pacientes.txt");
        System.out.println("Usando VectorHeap:");
        atenderPacientes(cola);

        PriorityQueue<Paciente> colaJCF = new PriorityQueue<>();
        cargarPacientesJCF(colaJCF, "pacientes.txt");
        System.out.println("\nUsando Java Collection Framework:");
        atenderPacientesJCF(colaJCF);
    }

    private static void cargarPacientes(VectorHeap<Paciente> cola, String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(", ");
                cola.add(new Paciente(partes[0], partes[1], partes[2].charAt(0)));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void atenderPacientes(VectorHeap<Paciente> cola) {
        while (!cola.isEmpty()) System.out.println(cola.remove());
    }

    private static void cargarPacientesJCF(PriorityQueue<Paciente> cola, String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(", ");
                cola.add(new Paciente(partes[0], partes[1], partes[2].charAt(0)));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void atenderPacientesJCF(PriorityQueue<Paciente> cola) {
        while (!cola.isEmpty()) System.out.println(cola.poll());
    }
}
