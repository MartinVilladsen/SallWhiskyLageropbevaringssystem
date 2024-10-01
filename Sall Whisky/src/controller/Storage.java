package controller;

import model.*;

import java.util.List;

public interface Storage {


    // ---------------------------------------------------------------------
    /** Casks */
    // ---------------------------------------------------------------------

    List<Cask> getCasks();
    void storeCask(Cask cask);
    void deleteCask(Cask cask);

    // ---------------------------------------------------------------------
    /** Warehouses */
    // ---------------------------------------------------------------------

    List<Warehouse> getWarehouses();
    void storeWarehouse(Warehouse warehouse);

    // ---------------------------------------------------------------------
    /** Distillates */
    // ---------------------------------------------------------------------

    List<Distillate> getDistillates();
    void storeDistillate(Distillate distillate);

    // ---------------------------------------------------------------------
    /** StorageCounter */
    // ---------------------------------------------------------------------

    StorageCounter getStorageCounter();

    // ---------------------------------------------------------------------
    /** Maltbatches */
    // ---------------------------------------------------------------------

    List<Maltbatch> getMaltbatches();
    void storeMaltbatch(Maltbatch maltBatch);
    void deleteMaltbatch(Maltbatch maltBatch);

    // ---------------------------------------------------------------------
    /** Grains */
    // ---------------------------------------------------------------------

    List<Grain> getGrains();
    void storeGrain(Grain grain);
    void deleteGrain(Grain grain);

    // ---------------------------------------------------------------------
    /** Fields */
    // ---------------------------------------------------------------------

    List<Field> getFields();
    void storeField(Field field);
    void deleteField(Field field);

    // ---------------------------------------------------------------------
    /** Grainsuppliers */
    // ---------------------------------------------------------------------

    List<GrainSupplier> getGrainSuppliers();
    void storeGrainSupplier(GrainSupplier grainSupplier);

    // ---------------------------------------------------------------------
    /** Casksuppliers */
    // ---------------------------------------------------------------------

    List<CaskSupplier> getCaskSuppliers();
    void storeCaskSupplier(CaskSupplier caskSupplier);

    // ---------------------------------------------------------------------
    /** Whiskybottles */
    // ---------------------------------------------------------------------

    List<WhiskyBottle> getWhiskyBottles();
    void storeWhiskyBottle(WhiskyBottle whiskyBottle);


    /** WhiskyFill */
    // ---------------------------------------------------------------------
    List<WhiskyFill> getWhiskyFills();
    void storeWhiskyFill(WhiskyFill whiskyFill);
    void removeWhiskyFill(WhiskyFill whiskyFill);

    // ---------------------------------------------------------------------
    /** Whisky */
    // ---------------------------------------------------------------------

    List<Whisky> getWhiskies();
    void storeWhisky(Whisky whisky);
}
