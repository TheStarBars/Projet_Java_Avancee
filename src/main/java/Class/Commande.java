package Class;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Commande {
    private int id;
    private String status;
    private ArrayList<Plat> platArrayList;
    private int tableId;
    private Timestamp dateHeureService;

    public Commande(int id, String status, ArrayList<Plat> platArrayList, int TableId, Timestamp dateHeureService) {
        this.id = id;
        this.status = status;
        this.platArrayList = platArrayList;
        this.tableId = TableId;
        this.dateHeureService = dateHeureService;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Plat> getPlatArrayList() {
        return platArrayList;
    }


    public Timestamp getDateHeureService() {
        return dateHeureService;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTableId() {
        return tableId;
    }
}

