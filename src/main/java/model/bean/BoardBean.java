package model.bean;

public class BoardBean {

    private final String id;
    private final String name;
    private final String username;
    private final Item[][] items;
    private final Long createdAt;
    private final Boolean isPublic;

    public BoardBean(String id, String name, String username, Item[][] items, Long createdAt, Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.items = items;
        this.createdAt = createdAt;
        this.isPublic = isPublic;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public Item[][] getItems() {
        return items;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Boolean isPublic() {
        return isPublic;
    }
}
