/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.freememorymagament;

import java.util.ArrayList;
import java.util.Collections;

/**
 * MEDIANFIT - Algoritmo de Asignación basado en la Mediana
 *
 * Elige el hueco cuyo remanente esté más cercano a la MEDIANA de todos los
 * remanentes posibles. Logra un balance entre Best Fit (huecos muy pequeños)
 * y Worst Fit (huecos muy grandes).
 *
 * Lógica de decisión:
 *   1. Recorre la lista buscando huecos válidos (canContain).
 *   2. Calcula el remanente de cada hueco válido con getRemainder().
 *   3. Calcula la mediana de esos remanentes.
 *   4. Elige el hueco con menor |remanente - mediana|.
 *   5. En empate, gana el primero encontrado en la lista.
 */
public class MedianFitMemorySlotManager extends FreeMemorySlotManager {

    public MedianFitMemorySlotManager(int memSize) {
        super(memSize);
    }

    @Override
    public MemorySlot getSlot(int size) {

        // Paso 1: recopilar remanentes de huecos válidos (sin alterar list)
        ArrayList<Integer> remainders = new ArrayList<>();
        for (MemorySlot slot : list) {
            if (slot.canContain(size)) {
                remainders.add(slot.getRemainder(size));
            }
        }

        if (remainders.isEmpty()) {
            System.out.println("Error - Memory cannot allocate a slot big enough for the requested memory");
            return null;
        }

        // Paso 2: calcular la mediana sobre una copia ordenada
        ArrayList<Integer> sorted = new ArrayList<>(remainders);
        Collections.sort(sorted);

        double median;
        int n = sorted.size();
        if (n % 2 != 0) {
            median = sorted.get(n / 2);
        } else {
            median = (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2.0;
        }

        // Paso 3: recorrer list de nuevo y elegir el hueco más cercano a la mediana
        MemorySlot chosen = null;
        double minDistance = Double.MAX_VALUE;

        for (MemorySlot slot : list) {
            if (slot.canContain(size)) {
                // Fit perfecto: se usa getSize() igual que Best/WorstFit
                if (slot.getSize() == size) {
                    list.remove(slot);
                    return slot;
                }

                double distance = Math.abs(slot.getRemainder(size) - median);
                // Condición estricta < preserva el primero en empate
                if (distance < minDistance) {
                    minDistance = distance;
                    chosen = slot;
                }
            }
        }

        return chosen.assignMemory(size);
    }
}
