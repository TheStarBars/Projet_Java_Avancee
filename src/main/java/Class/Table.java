package Class;

public class Table {
    private Integer id;
    private Integer size;
    private String status;

    public Table(Integer id, Integer size, String status) {
        this.id = id;
        this.size = size;
        this.status = status;
    }

    public Object getStatus() {
        return status;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public String getSize() {
        return String.valueOf(size);
    }
}
