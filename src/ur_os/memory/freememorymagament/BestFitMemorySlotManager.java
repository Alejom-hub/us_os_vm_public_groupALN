/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.freememorymagament;

/**
 *
 * @author super
 */
public class BestFitMemorySlotManager extends FreeMemorySlotManager{
    
    public BestFitMemorySlotManager(int memSize){
        super(memSize);
    }
    
    @Override
    public MemorySlot getSlot(int size) {
        MemorySlot best = null;

        for (MemorySlot slot : list) {
            if (slot.canContain(size)) {
                if (slot.getSize() == size) {
                    list.remove(slot);
                    return slot;
                }
                if (best == null || slot.getRemainder(size) < best.getRemainder(size)) {
                    best = slot;
                }
            }
        }

        if (best == null) {
            System.out.println("Error - Memory cannot allocate a slot big enough for the requested memory");
            return null;
        }

        return best.assignMemory(size);
    }
    
}
