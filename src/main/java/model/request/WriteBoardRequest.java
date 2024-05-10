package model.request;

import model.bean.Item;

import java.util.Objects;

public final class WriteBoardRequest extends BaseRequest {

    private final String id;
    private final String name;
    private final String username;
    private final Item[][] items;
    private final Long createdAt;
    private final Boolean isPublic;

    public WriteBoardRequest(String id, String name, String username, Item[][] items, Long createdAt, Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.items = items;
        this.createdAt = createdAt;
        this.isPublic = isPublic;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String username() {
        return username;
    }

    public Item[][] items() {
        return items;
    }

    public Long createdAt() {
        return createdAt;
    }

    public Boolean isPublic() {
        return isPublic;
    }
}
