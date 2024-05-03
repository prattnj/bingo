package model.bean;

public class Item {

    private final String description;
    private final boolean checked;
    private final long checkedAt;

    public Item(String description, boolean checked, long checkedAt) {
        this.description = description;
        this.checked = checked;
        this.checkedAt = checkedAt;
    }

    public String getDescription() {
        return description;
    }

    public boolean isChecked() {
        return checked;
    }

    public long getCheckedAt() {
        return checkedAt;
    }
}
