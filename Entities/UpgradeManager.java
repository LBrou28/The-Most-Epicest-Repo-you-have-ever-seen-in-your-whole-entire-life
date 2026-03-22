package Entities;

import java.util.*;

public class UpgradeManager {

    private static Random random = new Random();

    public static Upgrade getRandomUpgrade() {
        int r = random.nextInt(5);

        switch (r) {
            case 0: return new Upgrade(UpgradeType.SPEED, "Speed Up", "+1 Speed");
            case 1: return new Upgrade(UpgradeType.DAMAGE, "Damage Up", "+1 Damage");
            case 2: return new Upgrade(UpgradeType.FIRE_RATE, "Faster Shots", "-100ms cooldown");
            case 3: return new Upgrade(UpgradeType.RANGE, "Range Up", "+50 Range");
            default: return new Upgrade(UpgradeType.HEAL, "Heal", "+20 HP");
        }
    }
}