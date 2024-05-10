package model.bean;

public record BoardBean(String id, String name, String username, Item[][] items, Long createdAt, Boolean isPublic) {
}
