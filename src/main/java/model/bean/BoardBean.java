package model.bean;

public class BoardBean {

    private final String id;
    private final String name;
    private final String username;
    private final Item[][] items;
    private final long createdAt;
    private final boolean isPublic;

    public BoardBean(String id, String name, String username, Item[][] items, long createdAt, boolean isPublic) {
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

    public long getCreatedAt() {
        return createdAt;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
