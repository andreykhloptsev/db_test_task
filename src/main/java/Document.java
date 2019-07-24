
/**
 * Created by DIO
 */
public class Document  {
    private int id;
    private User user;
    private String tshort;
    private String date;
    private Status status;
    private  Category category;
    private Document link_doc;

    public Document() {
    }

    public Document(int id, User user, String tshort, String date, Status status) {
        this.id = id;
        this.user = user;
        this.tshort = tshort;
        this.date = date;
        this.status = status;
        this.category=null;
        this.link_doc=null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTshort() {
        return tshort;
    }

    public void setTshort(String tshort) {
        this.tshort = tshort;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Document getLink_doc() {
        return link_doc;
    }

    public void setLink_doc(Document link_doc) {
        this.link_doc = link_doc;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", user=" + user +
                ", tshort='" + tshort + '\'' +
                ", date='" + date + '\'' +
                ", status=" + status +
                ", category=" + category +
                ", link_doc=" + link_doc +
                '}';
    }
}
