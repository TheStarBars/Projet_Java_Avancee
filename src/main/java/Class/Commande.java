package Class;

import java.util.ArrayList;

public class Commande {
    private String status;
    private ArrayList<Plat> platArrayList;
    private ArrayList<Table> TableArrayList;

    public Commande(String status, ArrayList<Plat> platArrayList, ArrayList<Table> TableArrayList) {
        this.status = status;
        this.platArrayList = platArrayList;
        this.TableArrayList = TableArrayList;
    }
}
