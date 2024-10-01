package storage;

import controller.Storage;
import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage, Serializable {
    private final List<Cask> casks = new ArrayList<>();
    private final List<CaskSupplier> caskSuppliers = new ArrayList<>();
    private final List<GrainSupplier> grainSuppliers = new ArrayList<>();
    private final List<Warehouse> warehouses = new ArrayList<>();
    private final List<Maltbatch> maltbatches = new ArrayList<>();
    private final List<Distillate> distillates = new ArrayList<>();
    private final List<Grain> grains = new ArrayList<>();
    private final List<Field> fields = new ArrayList<>();
    private final List<WhiskyBottle> whiskyBottles = new ArrayList<>();
    private final List<Whisky> whiskies = new ArrayList<>();
    private final List<WhiskyFill> whiskyFills = new ArrayList<>();
    private StorageCounter storageCounter = new StorageCounter();

    // ---------------------------------------------------------------------
    /** Suppliers */
    // ---------------------------------------------------------------------

    public List<CaskSupplier> getCaskSuppliers() {
        return new ArrayList<>(caskSuppliers);
    }
    public void storeCaskSupplier(CaskSupplier caskSupplier) {
        caskSuppliers.add(caskSupplier);
    }
    @Override
    public List<GrainSupplier> getGrainSuppliers() {
        return new ArrayList<>(grainSuppliers);
    }
    public void storeGrainSupplier(GrainSupplier grainSupplier) {
        grainSuppliers.add(grainSupplier);
    }

    // ---------------------------------------------------------------------
    /** Casks */
    // ---------------------------------------------------------------------

    public List<Cask> getCasks() {
        return new ArrayList<>(casks);
    }
    public void storeCask(Cask cask) {
        casks.add(cask);
    }
    public void deleteCask(Cask cask) {
        casks.remove(cask);
    }

    // ---------------------------------------------------------------------
    /** Warehouses */
    // ---------------------------------------------------------------------

    public List<Warehouse> getWarehouses() {
        return new ArrayList<>(warehouses);
    }
    public void storeWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }

    // ---------------------------------------------------------------------
    /** Storagecounter */
    // ---------------------------------------------------------------------

    public StorageCounter getStorageCounter() {
        return storageCounter;
    }

    // ---------------------------------------------------------------------
    /** Maltbatches */
    // ---------------------------------------------------------------------

    public List<Maltbatch> getMaltbatches() {
        return new ArrayList<>(maltbatches);
    }
    public void storeMaltbatch(Maltbatch maltBatch) {
        maltbatches.add(maltBatch);
    }
    public void deleteMaltbatch(Maltbatch maltBatch) {
        maltbatches.remove(maltBatch);
    }

    // ---------------------------------------------------------------------
    /** Distillates */
    // ---------------------------------------------------------------------

    @Override
    public List<Distillate> getDistillates() {
        return new ArrayList<>(distillates);
    }
    public void storeDistillate(Distillate distillate) {
        distillates.add(distillate);
    }

    // ---------------------------------------------------------------------
    /** Grains */
    // ---------------------------------------------------------------------

    @Override
    public List<Grain> getGrains() {
        return new ArrayList<>(grains);
    }
    @Override
    public void storeGrain(Grain grain) {
        grains.add(grain);
    }
    @Override
    public void deleteGrain(Grain grain) {
        grains.remove(grain);
    }

    // ---------------------------------------------------------------------
    /** Fields */
    // ---------------------------------------------------------------------

    @Override
    public List<Field> getFields() {
        return new ArrayList<>(fields);
    }
    @Override
    public void storeField(Field field) {
        fields.add(field);
    }
    @Override
    public void deleteField(Field field) {
        fields.remove(field);
    }

    // ---------------------------------------------------------------------
    /** WhiskyBottles */
    // ---------------------------------------------------------------------

    @Override
    public List<WhiskyBottle> getWhiskyBottles() {
        return new ArrayList<>(whiskyBottles);
    }
    @Override
    public void storeWhiskyBottle(WhiskyBottle whiskyBottle) {
        whiskyBottles.add(whiskyBottle);
    }

    @Override
    public List<WhiskyFill> getWhiskyFills() {
        return whiskyFills;
    }

    @Override
    public void storeWhiskyFill(WhiskyFill whiskyFill) {
        whiskyFills.add(whiskyFill);
    }

    @Override
    public void removeWhiskyFill(WhiskyFill whiskyFill) {
        whiskyFills.remove(whiskyFill);
    }

    // ---------------------------------------------------------------------
    /** Whisky */
    // ---------------------------------------------------------------------

    @Override
    public List<Whisky> getWhiskies() {
        return new ArrayList<>(whiskies);
    }
    @Override
    public void storeWhisky(Whisky whisky) {
        whiskies.add(whisky);
    }

    // ---------------------------------------------------------------------
    /** Serializable */
    // ---------------------------------------------------------------------

    public static ListStorage loadStorage() {
        String fileName = "Sall Whisky/src/storage.ser";
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objIn = new ObjectInputStream(fileIn)
        ) {
            Object obj = objIn.readObject();
            ListStorage storage = (ListStorage) obj;
            System.out.println("Storage loaded from file " + fileName);
            return storage;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error deserializing storage");
            System.out.println(ex);
            return null;
        }
    }
    public static void saveStorage(Storage storage) {
        String fileName = "Sall Whisky/src/storage.ser";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)
        ) {
            objOut.writeObject(storage);
            System.out.println("Storage saved in file " + fileName);
        } catch (IOException ex) {
            System.out.println("Error serializing storage");
            System.out.println(ex);
            throw new RuntimeException();
        }
    }
}
