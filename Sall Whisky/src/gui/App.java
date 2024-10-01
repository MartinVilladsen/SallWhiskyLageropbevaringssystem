package gui;

import controller.Controller;
import controller.Storage;
import javafx.application.Application;
import model.*;
import storage.ListStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {

        Storage storage = ListStorage.loadStorage();
        if (storage == null) {
            storage = new ListStorage();
            System.out.println("Empty ListStorage created");
        }
        Controller.setStorage(storage);


        // Only used the first time program is run for base data
        // initStorage();

        Application.launch(Gui.class);

        ListStorage.saveStorage(storage);
    }

    public static void initStorage() {
        CaskSupplier cs1 = Controller.createCaskSupplier("Minas", "Parisstreet 22", "Frankrig", "FR829829");
        Controller.createCaskSupplier("Loius Cask", "NormandyPiur 22", "Frankrig", "FR82827");
        Controller.createGrainSupplier("Heimdahl", "Hemsedal 22", "Danmark", "DK234242");
        Controller.createGrainSupplier("Hougaard", "Snællevej 88", "Danmark", "DK999282");

        Field f1 = Controller.createField("Mindemark", "En mark der udskifter afgrøder hvert 4. år");
        Field f2 = Controller.createField("Thou", "De bedste afgrøder i Jylland");

        GrainSupplier gs1 = Controller.createGrainSupplier("Jørgen", "Hjemmevej 2", "Danmark", "13578");

        Grain g1 = Controller.createGrain("Byg", gs1, "En unik byg", f1);
        Grain g2 = Controller.createGrain("Hvede", gs1, "En unik hvede", f1);

        Maltbatch m1 = Controller.createMaltbatch("MB2015", "En dejlig aldret maltbatch", g1);
        Maltbatch m2 = Controller.createMaltbatch("MB2018", "Godt batch", g1);
        List<Maltbatch> mList1 = new ArrayList<>();
        mList1.add(m1);

        Warehouse w1 = Controller.createWarehouse("Lager1");
        Warehouse w2 = Controller.createWarehouse("Lager2");
        Rack r1 = Controller.createRack(w1);
        Rack r2 = Controller.createRack(w2);
        Rack r3 = Controller.createRack(w2);

        Shelf sh1 = Controller.createShelf(r1);
        Shelf sh2 = Controller.createShelf(r2);
        Shelf sh3 = Controller.createShelf(r3);
        Shelf sh4 = Controller.createShelf(r1);
        Shelf sh5 = Controller.createShelf(r1);

        Controller.createPosition(sh1, 30);
        Controller.createPosition(sh1, 30);
        Controller.createPosition(sh1, 30);
        Controller.createPosition(sh1, 30);
        Controller.createPosition(sh2, 50);
        Controller.createPosition(sh1, 40);
        Position p2 = Controller.createPosition(sh3, 100);
        Position p1 = Controller.createPosition(sh5, 50);

        Cask mainCask = Controller.createCask("Frankrig", 50, "Bourbon", p1, cs1);

        Distillate testD = Controller.createDistillate("T882", 20, 50, 50, "Hans", mList1, "Destilleret 2 gange");
        Distillate testD2 = Controller.createDistillate("T883", 20, 50, 60, "Hans", Controller.getMaltbatches(), "Destilleret 2 gange");

        ArrayList<DistillateFill> distillateFills = new ArrayList<>();
        distillateFills.add(new DistillateFill(50, testD));

        Cask mainCask2 = Controller.createCask("Italien", 70, "Bourbon", p2, cs1);

        ArrayList<DistillateFill> distillateFills3 = new ArrayList<>();
        distillateFills3.add(new DistillateFill(60, testD2));

        Controller.createTapFromDistillate(LocalDate.of(2015, 10, 2), mainCask, distillateFills);
        Controller.createTapFromDistillate(LocalDate.of(2015, 10, 2), mainCask2, distillateFills3);


    }
}
