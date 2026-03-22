package Entities;

public class Upgrade {

    public UpgradeType type;
    public String name;
    public String description;

    public Upgrade(UpgradeType type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }
}
