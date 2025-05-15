package Class;

public class Employe {
    private Integer id;
    private String name;
    private String post;
    private Integer age;
    private Integer workhours;

    public Employe(Integer id, String name, String post, Integer workhours, Integer age) {
        this.id = id;
        this.name = name;
        this.post = post;
        this.workhours = workhours;
        this.age = age;

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPost() {
        return post;
    }

    public String getWorkedHour() {
        return workhours.toString();
    }

    public String getAge() {
        return age.toString();
    }
}

