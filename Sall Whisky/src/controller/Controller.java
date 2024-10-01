package controller;

import model.*;
import storage.ListStorage;

import java.time.LocalDate;
import java.util.*;

public abstract class Controller {

    private static List<Observer> observers = new ArrayList<>();
    private static Storage storage;

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    /**
     * Create and return all Casks that have more than 0 currentContentInLiters and was filled more than 3 years ago
     */
    public static List<Cask> getRipeCasks() {
        List<Cask> ripeCasks = new ArrayList<>();
        for (Cask cask : storage.getCasks()) {
            if (cask.getYoungestTapFromDistillate() != null) {
                if (cask.getYoungestTapFromDistillate().getFirstTimeOfFill().isBefore(LocalDate.now().minusYears(3)) && cask.getCurrentContentInLiters() > 0) {
                    ripeCasks.add(cask);
                }
            }
        }
        return ripeCasks;
    }

    public static List<Observer> getObservers() {
        return observers;
    }

    /** Get all casks with Distillatefills */
    public static List<Cask> getCasksWithDistillateOn() {
        ArrayList<Cask> casksWithDestillate = new ArrayList<>();
        for (Cask cask : storage.getCasks()) {
            if (cask.getCurrentFillOnCasks().size() > 0) {
                casksWithDestillate.add(cask);
            }
        }
        return casksWithDestillate;
    }

    /**
     * Gets a list of all casks with litersAvailable above litercapacity
     * @param litercapacity minimum amount of liters that the casks has to have available
     * @return casks with litersAvailable above litercapacity
     */
    public static List<Cask> getCasksWithXLitersAvailable(double litercapacity) {
        ArrayList<Cask> caskAvailableForTransference = new ArrayList<>();
        for (Cask cask : storage.getCasks()) {
            if (cask.getLitersAvailable() >= litercapacity) {
                caskAvailableForTransference.add(cask);
            }
        }
        return caskAvailableForTransference;
    }

    /**
     * Finds warehouses that has atleast 1 rack where there is space for the Cask we're trying to add
     * @param cask we're checking for space for
     * @return Warehouses where atleast 1 rack is available
     */
    public static List<Warehouse> getAvailableWarehouses(Cask cask) {
        List<Warehouse> warehouses = new ArrayList<>(storage.getWarehouses());

        for (int i = 0; i < warehouses.size(); i++) {
            if (warehouses.get(i).isFilled()) {
                warehouses.remove(warehouses.get(i));
                i--;

            } else if (getAvailableRacks(warehouses.get(i), cask).isEmpty()) {
                warehouses.remove(warehouses.get(i));
                i--;
            }

        }
        return warehouses;
    }

    /**
     * Finds racks in a specific warehouse which have space for the cask we're trying to add
     * @param warehouse from which we want non-full racks
     * @return Racks that are not full
     */
    public static List<Rack> getAvailableRacks(Warehouse warehouse, Cask cask) {
        List<Rack> racks = new ArrayList<>(warehouse.getRacks());
        for (int i = 0; i < racks.size(); i++) {
            if (racks.get(i).isFilled()) {
                racks.remove(racks.get(i));
                i--;
            } else if (getAvailableShelves(racks.get(i), cask).isEmpty()) {
                racks.remove(racks.get(i));
                i--;
            }
        }
        return racks;
    }

    /**
     * Finds shelves at a specific rack which are not full
     * @param rack from which we want to find non-full shelves
     * @return Shelves that are not full (isFilled = false)
     */
    public static List<Shelf> getAvailableShelves(Rack rack, Cask cask) {
        List<Shelf> shelves = new ArrayList<>(rack.getShelves());
        for (int i = 0; i < shelves.size(); i++) {
            if (shelves.get(i).isFilled()) {
                shelves.remove(shelves.get(i));
                i--;
            } else if (getAvailablePositions(shelves.get(i), cask).isEmpty()) {
                shelves.remove(shelves.get(i));
                i--;
            }
        }
        return shelves;
    }

    /** Returns all casks not fully filled */
    public static ArrayList<Cask> getAvailableCasks() {
        ArrayList<Cask> availableCasks = new ArrayList<>();
        for (Cask cask: Controller.getCasks()) {
            if (cask.getLitersAvailable() > 0) {
                availableCasks.add(cask);
            }
        }
        return availableCasks;
    }

    /**
     * Finds positions at a specific shelf that aren't full
     * @param shelf from which we want to find non-full positions
     * @return positions at which the cask can be added to
     */
    public static List<Position> getAvailablePositions(Shelf shelf, Cask cask) {
        List<Position> positions = new ArrayList<>(shelf.getPositions());
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).isFilled()) {
                positions.remove(positions.get(i));
                i--;
            } else {
                double currentLiters = 0;
                for (Cask cask1 : positions.get(i).getCasks()) {
                    currentLiters += cask1.getSizeInLiters();
                }
                if (currentLiters + cask.getSizeInLiters() > positions.get(i).getLiterCapacity()) {
                    positions.remove(positions.get(i));
                    i--;
                }
            }
        }
        return positions;
    }

    /** Return all distillates that still haven't been fully filled on a Cask */
    public static List<Distillate> getAvailableDistillates() {
        List<Distillate> distillates = new ArrayList<>();
        for (Distillate distillate: storage.getDistillates())  {
            if (distillate.getAmountInLiters() > 0) {
                distillates.add(distillate);
            }
        }
        return distillates;
    }
    public static void setStorage(Storage storage) {
        Controller.storage = storage;
    }
    public static Storage getStorage() {
        return storage;
    }
    public static List<Cask> getCasks() {
        return storage.getCasks();
    }
    public static List<Maltbatch> getMaltbatches() {
        return storage.getMaltbatches();
    }
    public static List<Grain> getGrains() {
        return storage.getGrains();
    }
    public static List<Field> getFields() {
        return storage.getFields();
    }
    public static List<Whisky> getWhiskies() {
        return new ArrayList<>(storage.getWhiskies());
    }

    public static List<WhiskyFill> getWhiskyFills() {
        return new ArrayList<>(storage.getWhiskyFills());
    }
    public static List<GrainSupplier> getGrainSuppliers() {
        return storage.getGrainSuppliers();
    }
    public static List<CaskSupplier> getCaskSuppliers() {
        return storage.getCaskSuppliers();
    }
    public static List<Distillate> getDistillates() {
        return storage.getDistillates();
    }

    // ---------------------------------------------------------------------
    /** Create methods */
    // ---------------------------------------------------------------------

    /**
     * Create, store and return a Cask
     * Throws an illegalArgumentException if sizeInLiters <= 0
     * add the cask to the position
     */
    public static Cask createCask(String countryOfOrigin, double sizeInLiters, String previousContent,
                                  Position position, CaskSupplier supplier) {
        int id = storage.getStorageCounter().getCaskCount();
        storage.getStorageCounter().incrementCaskCount();
        if (sizeInLiters <= 0) {
            throw new IllegalArgumentException();
        }
        Cask cask = new Cask(id, countryOfOrigin, sizeInLiters, previousContent, position, supplier);
        storage.storeCask(cask);
        position.addCask(cask);
        notifyObserver();
        ListStorage.saveStorage(storage);
        return cask;
    }

    /**
     * Create and return a Warehouse.
     * Increment warehouse counter.
     */
    public static Warehouse createWarehouse(String address) {
        int id = storage.getStorageCounter().getWarehouseCount();
        Warehouse warehouse = new Warehouse(id, address);
        storage.storeWarehouse(warehouse);
        storage.getStorageCounter().incrementWarehouseCount();
        addObserver(warehouse);
        notifyObserver();
        return warehouse;
    }

    /**
     * Create and return a rack
     * Add the connection to the warehouse
     * Increment rack counter
     * Pre: A warehouse is created for the rack
     */
    public static Rack createRack(Warehouse warehouse) {
        int id = storage.getStorageCounter().getRackCount();
        Rack rack = new Rack(id, warehouse);
        warehouse.addRack(rack);
        storage.getStorageCounter().incrementRackCount();
        addObserver(rack);
        notifyObserver();
        return rack;
    }

    /**
     * Create and return a shelf
     * Add the connection to the rack
     * Increment shelf counter
     * Pre: A rack is created for the shelf
     */
    public static Shelf createShelf(Rack rack) {
        int id = storage.getStorageCounter().getShelfCount();
        Shelf shelf = new Shelf(id, rack);
        rack.addShelf(shelf);
        storage.getStorageCounter().incrementShelfCount();
        addObserver(shelf);
        notifyObserver();
        return shelf;
    }

    /**
     * Create and return a position
     * Add the connection to the shelf
     * Increment position counter
     * Notify observers
     * Pre: A shelf is created for the shelf
     */
    public static Position createPosition(Shelf shelf, double literCapacity) {
        int id = storage.getStorageCounter().getPositionCount();
        Position position = new Position(id, literCapacity, shelf);
        shelf.addPosition(position);
        storage.getStorageCounter().incrementPositionCount();
        addObserver(position);
        notifyObserver();

        return position;
    }

    /**
     * Creates and returns a TapFromDistillate object
     * Creates a fillOnCask object and connects it to the TapFromDistillate and cask
     * Connection is added to cask
     * Connection is added to DistillateFill
     * If sum of distillateFills amount in liters > sizeInLiters (Cask) throw an illegalArgumentException
     * If timeOfFill is after LocalDate.now() throw an illegalArgumentException
     * If total amount of distillateFills getAmountOfDistillateInLiters is above the Distillates amount
     * throw IllegalArgumentException
     */
    public static TapFromDistillate createTapFromDistillate(LocalDate timeOfFill, Cask cask,
                                                            ArrayList<DistillateFill> distillateFills) throws IllegalArgumentException {
        TapFromDistillate tapFromDistillate = new TapFromDistillate(timeOfFill, cask);

        double sum = 0;
        for (DistillateFill distillateFill : distillateFills) {
            sum += distillateFill.getAmountOfDistillateInLiters();
            tapFromDistillate.addDistillateFill(distillateFill);
        }
        if (sum > cask.getLitersAvailable())
            throw new IllegalArgumentException("Fadets størrelse er mindre end din påfyldning");
        if (timeOfFill.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Dato er efter nuværende dato");
        }
        for (DistillateFill distillateFill: distillateFills) {
            Distillate distillate = distillateFill.getDistillate();
            if (distillate.getAmountInLiters() - distillateFill.getAmountOfDistillateInLiters() < 0) {
                throw new IllegalArgumentException("Du prøver at påfylde mere destillat end tilgængeligt");
            }
            distillate.setAmountInLiters(distillate.getAmountInLiters() - distillateFill.getAmountOfDistillateInLiters());
        }
        FillOnCask fillOnCask = new FillOnCask(timeOfFill, tapFromDistillate, cask);
        cask.addCurrentFillOnCask(fillOnCask);
        ListStorage.saveStorage(storage);
        return tapFromDistillate;
    }

    /** Creates a Transference by moving all currentFillOnCask from oldcask */
    public static void createTransference(Cask oldCask, Cask newCask) {
        List<FillOnCask> fillOnCaskFromOldCask = new ArrayList<>(oldCask.getCurrentFillOnCasks());

        for (int i = 0; i < fillOnCaskFromOldCask.size(); i++) {
            FillOnCask currentFillOnCask = fillOnCaskFromOldCask.get(i);
            newCask.addCurrentFillOnCask(new FillOnCask(LocalDate.now(), currentFillOnCask.getTapFromDistillate(), newCask));
            currentFillOnCask.getTapFromDistillate().addFillOnCask(currentFillOnCask);
        }
        oldCask.getCurrentFillOnCasks().removeAll(fillOnCaskFromOldCask);
        oldCask.getPreviousPutOnCasks().addAll(fillOnCaskFromOldCask);

        oldCask.setCurrentContentInLiters(0);
        newCask.setCurrentContentInLiters(newCask.getTotalLitersOfFills());
    }

    /**
     * Creates a Distillate and saves it to storage
     * @param newMakenr
     * Pre: 100 > alcoholPercentage > 0
     * Pre: amountInLiters > 0
     * @return the created Distillate
     */
    public static Distillate createDistillate(String newMakenr, double distillationTimeInHours,
                                              double alcoholPercentage, double amountInLiters, String employee,
                                              List<Maltbatch> maltbatches, String description) {

        Distillate distillate = new Distillate(newMakenr, distillationTimeInHours, alcoholPercentage, amountInLiters,
                employee, maltbatches, description);
        storage.storeDistillate(distillate);
        ListStorage.saveStorage(storage);
        return distillate;
    }

    /**
     * Create, store and return a maltbatch
     * Add the connection to the grain
     * Pre: A grain is created for the maltbatch
     */
    public static Maltbatch createMaltbatch (String name, String description, Grain grain) {
        Maltbatch maltbatch = new Maltbatch(name, description, grain);
        storage.storeMaltbatch(maltbatch);
        ListStorage.saveStorage(storage);
        return maltbatch;
    }

    /**
     * Create, store and return a grain
     * Add the connection to the grain supplier
     * Pre: A grain supplier and a field is created
     */
    public static Grain createGrain (String grainType, GrainSupplier grainSupplier, String cultivationDescription, Field field) {
        Grain grain = new Grain(grainType, grainSupplier, cultivationDescription, field);
        storage.storeGrain(grain);
        ListStorage.saveStorage(storage);
        return grain;
    }

    /** Create, store and return a field */
    public static Field createField (String name, String description) {
        Field field = new Field(name, description);
        storage.storeField(field);
        ListStorage.saveStorage(storage);
        return field;
    }

    /** Create, store and return a GrainSupplier */
    public static GrainSupplier createGrainSupplier (String name, String address, String country, String vatId) {
        GrainSupplier grainSupplier = new GrainSupplier(name, address, country, vatId);
        storage.storeGrainSupplier(grainSupplier);
        ListStorage.saveStorage (storage);
        return grainSupplier;
    }


    /** Create, store and return a CaskSupplier */
    public static CaskSupplier createCaskSupplier (String name, String address, String country, String vatId) {
        CaskSupplier caskSupplier = new CaskSupplier(name, address, country, vatId);
        storage.storeCaskSupplier(caskSupplier);
        ListStorage.saveStorage(storage);
        return caskSupplier;
    }

    /**
     * Create, store and return a Whisky
     * Pre: alcoholPercentage > 0 && alcoholPercentage < 100
     */
    public static Whisky createWhisky(String name, double waterInLiters, List<WhiskyFill> whiskyFills, String description) {
        Whisky whisky = new Whisky(name,waterInLiters, whiskyFills, description);
        storage.storeWhisky(whisky);
        for (WhiskyFill whiskyFill: whiskyFills) {
            storage.removeWhiskyFill(whiskyFill);
        }
        ListStorage.saveStorage(storage);
        return whisky;
    }

    /**
     * Create, store and return a WhiskyBottle
     * Increment WhiskyBottleCount
     * Pre: centiliterCapacity > 0
     */
    public static WhiskyBottle createWhiskyBottle(int centiliterCapacity, Whisky whisky) {
        WhiskyBottle whiskyBottle = new WhiskyBottle(storage.getStorageCounter().getWhiskyBottleCount(),centiliterCapacity, whisky);
        storage.storeWhiskyBottle(whiskyBottle);
        storage.getStorageCounter().incrementWhiskyBottleCount();
        return whiskyBottle;
    }

    /**
     * Create and return a WhiskyFill
     * Pre: amountOfDistilateFillInLiters > 0
     * Add connection to cask
     */
    public static WhiskyFill createWhiskyFill(double amountOfDistilateFillInLiters, List<TapFromDistillate> tapFromDistillates, double alcoholPercentage, Cask cask) throws InterruptedException {
        WhiskyFill whiskyFill = new WhiskyFill(amountOfDistilateFillInLiters, tapFromDistillates, LocalDate.now(), alcoholPercentage, cask);

        if (cask.getCurrentContentInLiters() - amountOfDistilateFillInLiters < 0) {
            throw new InterruptedException("Du prøver at påfylde flere liter end du har tilgængelig");
        } else if ((cask.getCurrentContentInLiters() - amountOfDistilateFillInLiters) == 0) {
                ArrayList<FillOnCask> currentFillOnCasks = new ArrayList<>(cask.getCurrentFillOnCasks());
                for (FillOnCask fillOnCask : currentFillOnCasks) {
                    cask.removeCurrentFillOnCask(fillOnCask);
                    cask.addPreviousFillOnCask(fillOnCask);
                }
            }
        cask.setCurrentContentInLiters(cask.getCurrentContentInLiters() - amountOfDistilateFillInLiters);
        storage.storeWhiskyFill(whiskyFill);
        return whiskyFill;
    }

    /**
     * create multiple WhiskyBottles for a specific Whisky object
     * Pre: numberOfBottles > 0
     * Pre: centiliterCapacity > 0
     */
    public static void createWhiskyBottlesForWhisky(int numberOfBottles, int centiliterCapacity, Whisky whisky) {
        for (int i = 0; i < numberOfBottles; i++) {
            createWhiskyBottle(centiliterCapacity, whisky);
        }
    }

    /** Calculates and returns the amount of bottles needed for a whisky creation */
    public static int amountOfBottles(Whisky whisky, int whiskyBottleCapacity) {
        return (int) (whisky.totalFluidsInWhisky() * 100) / whiskyBottleCapacity;
    }

    // ---------------------------------------------------------------------
    /** Generate methods */
    // ---------------------------------------------------------------------

    /**
     * Takes a list of WhiskyFills and finds occurrences of unique casks and maltbatches
     * @param whiskyFills that you want a whiskytype from
     * @return A String with the type of whisky based on unique occurences of casks and maltbatches
     * The Following returns are possible: "Single malt" (Only 1 unique maltbatch),
     * "Single Cask" (Multiple maltbatches in 1 cask), "Blended" (Multiple casks with different maltbatches)
     */
    public static String getWhiskyType(List<WhiskyFill> whiskyFills) {
        ArrayList<Cask> casks = new ArrayList<>();
        ArrayList<Maltbatch> maltbatches = new ArrayList<>();

        for (WhiskyFill whiskyFill: whiskyFills) {
            for (TapFromDistillate tapFromDistillate: whiskyFill.getTapFromDestillates()) {
                for (FillOnCask fillOnCask: tapFromDistillate.getFillOnCasks()) {
                    if (!casks.contains(fillOnCask.getCask())) {
                        casks.add(fillOnCask.getCask());
                    }
                }
                for (DistillateFill distillateFill: tapFromDistillate.getDistillateFills()) {
                    for (Maltbatch maltbatch: distillateFill.getDistillate().getMaltbatches()) {
                        if (!maltbatches.contains(maltbatch)) {
                            maltbatches.add(maltbatch);
                        }
                    }
                }
            }
        }
        if (maltbatches.size() == 1) {
            return "Single Malt";
        }
        if (casks.size() == 1) {
            return "Single Cask";
        }
        return "Blended";
     }

    /**
     * Return a String containing the entire Story and process of a Whisky
     */
    public static String generateStoryForWhisky(Whisky whisky) {
        Stack<String> infoStrings = new Stack<>();

        String whiskyInfo = "Navn: " + whisky.getName()
                + "\nTappet d. "
                + whisky.getWhiskyFills().get(0).getTimeOfFill() +
                "\nFortyndet med "
                + whisky.getWaterInLiters() +
                " liter vand \nBeskrivelse: "
                + whisky.getDescription()
                + "\nAlc %: " + whisky.calculateAlcoholPercentage();

        infoStrings.add(whiskyInfo);

        String caskInfos = "\n\nFade i denne whisky:\n";
        infoStrings.add(caskInfos);
        infoStrings.add(getCaskStoryForWhisky(whisky));

        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = infoStrings.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
        }
        return sb.toString();
    }

    /**
     * Helper method for generateStoryForWhisky
     * Returns a full story of a Cask based on a Whisky's whiskyfills (Since a whiskyfill always has only 1 cask and
     * the whiskyfill is connected to the TapFromDestillate (Which is the content we're interested in)
     * @param whisky we want a story from
     * @return String containing the story of all Casks on this Whisky
     */
    private static String getCaskStoryForWhisky(Whisky whisky) {
        StringBuilder sb = new StringBuilder();

        for (WhiskyFill whiskyFill: whisky.getWhiskyFills()) {
            Cask cask = whiskyFill.getCask();
            sb.append("------------------------------------------");
            sb.append("\n[FadID: " + cask.getCaskId() + " ]");
            sb.append("\nProcentdel i % (medregnet vand): " + whisky.caskShare().get(whiskyFill));
            sb.append("\nOprindelsesland: " + cask.getCountryOfOrigin());
            sb.append("\nTidligere indhold: " + cask.getPreviousContent());
            sb.append("\nFadtype: Egetræ");
            sb.append("\nLeverandør: " + cask.getSupplierName());
            sb.append("\n - Land: " + cask.getSupplier().getCountry());
            sb.append("\n\n [Destillater fra dette fad]");
            sb.append("\n------------------------------------------");
            sb.append(generateDistillateStory(whiskyFill));
            }

        return sb.toString();
    }

    /**
     * Helper method for getCasksStoryForWhisky()
     * Generates an info string based on a whiskyfill and classes connected to it
     * @param whiskyFill you want info about
     * @return A string with info about a Whiskyfills: Distillates, DistillateFill
     */
    private static String generateDistillateStory(WhiskyFill whiskyFill) {
        StringBuilder sb = new StringBuilder();
        for (TapFromDistillate tapFromDistillate: whiskyFill.getTapFromDestillates()) {
            for (DistillateFill distillateFill: tapFromDistillate.getDistillateFills()) {
                Distillate distillate = distillateFill.getDistillate();
                if (tapFromDistillate.getFillOnCasks().size() == 1) {
                    sb.append("\nDestillat: " + distillate.getNewMakeNr());
                    sb.append("\nProcentdel af dette fad: " + tapFromDistillate.distillateShare().get(distillateFill));
                    sb.append("\nPåfyldt: " + tapFromDistillate.getFirstTimeOfFill());
                } else {
                    sb.append("\nDestillat: " + distillate.getNewMakeNr());
                    sb.append("\n\n[Tidligere lagt på disse fade]");
                    for (int i = 0; i < tapFromDistillate.getFillOnCasks().size()-1; i++) {
                        FillOnCask currentFillOnCask = tapFromDistillate.getFillOnCasks().get(i);
                        FillOnCask nextFillOnCask = tapFromDistillate.getFillOnCasks().get(i+1);
                        sb.append("\nFadID: " + currentFillOnCask.getCask().getCaskId());
                        sb.append("\nPåfyldt: " + currentFillOnCask.getTimeFill());
                        sb.append("\nOmhældt: " + nextFillOnCask.getTimeFill());
                    }
                    FillOnCask lastFill = tapFromDistillate.getFillOnCasks().get(tapFromDistillate.getFillOnCasks().size()-1);
                    sb.append("\n\n[Lagt på dette fad]");
                    sb.append("\nPåfyldt: " + lastFill.getTimeFill());
                    sb.append("\nTil: " + LocalDate.now());
                }
                sb.append("\nMedarbejder: " + distillate.getEmployee());
                sb.append("\nDestilleringstid: " + distillate.getDistillationTimeInHours());
                sb.append("\nAlcoholprocent: " + distillate.getAlcoholPercentage());
                sb.append("\nBeskrivelse: " + distillate.getDescription());
                sb.append("\n\n     [Består af følgende maltbatches]");
                sb.append("\n       ------------------------------------------");
                for (Maltbatch maltbatch : distillate.getMaltbatches()) {
                    Grain grain = maltbatch.getGrain();
                    sb.append("\n       Maltbatch: " + maltbatch.getName());
                    sb.append("\n       Beskrivelse:" + maltbatch.getDescription());
                    sb.append("\n       [Korn Information]");
                    sb.append("\n       Korntype: " + grain.getGrainType());
                    sb.append("\n       Landmand: " + grain.getGrainSupplier().getName());
                    sb.append("\n       Mark: " + grain.getField());
                    sb.append("\n       Dyrkelsesmetode: " + grain.getCultivationDescription());
                }
                sb.append("\n-------------------------------------------------");
            }
        }
        return sb.toString();
    }

    /** Remove a grain */
    public static void removeGrain(Grain grain) {
        storage.deleteGrain(grain);
    }

    /** Remove a maltbatch */
    public static void removeMaltbatch(Maltbatch maltbatch) {
        storage.deleteMaltbatch(maltbatch);
    }

    /** Remove a field */
    public static void removeField(Field field) {
        storage.deleteField(field);
    }

    /** Remove a cask from Storage */
    public static void removeCask(Cask cask) {
        storage.deleteCask(cask);
    }

    /** Observer methods */
    public static void notifyObserver() {
        for (int i = observers.size()-1; i >= 0; i--) {
            observers.get(i).update();
        }
    }

    public static void addObserver(Observer observer) {
        observers.add(observer);
    }
    }
