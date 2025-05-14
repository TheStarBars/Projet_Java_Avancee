package Class;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Commande {
    private int id;
    private String status;
    private ArrayList<Plat> platArrayList;
    private int TableArrayList;
    private Timestamp dateHeureService;

    public Commande(int id, String status, ArrayList<Plat> platArrayList, int TableArrayList, Timestamp dateHeureService) {
        this.id = id;
        this.status = status;
        this.platArrayList = platArrayList;
        this.TableArrayList = TableArrayList;
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

    public void setPlatArrayList(ArrayList<Plat> platArrayList) {
        this.platArrayList = platArrayList;
    }

    public int getTableArrayList() {
        return TableArrayList;
    }

    public void setTableArrayList(int tableArrayList) {
        TableArrayList = tableArrayList;
    }

    public void setDateHeureService(Timestamp dateHeureService) {
        this.dateHeureService = dateHeureService;
    }
}
