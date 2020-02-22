package de.cyne.advancedlobby.misc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class Balloon {

    private Player p;
    private Material material;
    private byte b;
    private FallingBlock fallingBlock;
    private Bat bat;

    public static HashMap<Player, FallingBlock> fallingBlocks = new HashMap<>();
    public static HashMap<Player, Bat> bats = new HashMap<>();

    public Balloon(Player p, Material material, byte b) {
        this.p = p;
        this.material = material;
        this.b = b;
    }

    @SuppressWarnings("deprecation")
    public void create() {
        Location location = p.getLocation();
        location.setYaw(location.getYaw() + 90.0F);
        location.setPitch(-45.0F);
        Vector direction = location.getDirection().normalize();
        location.add(direction.getX() * 1.5D, direction.getY() * 1.5D + 0.5D, direction.getZ() * 1.5D);

        this.bat = (Bat) p.getWorld().spawnEntity(location, EntityType.BAT);

        this.fallingBlock = p.getWorld().spawnFallingBlock(bat.getLocation(), material, b);
        this.fallingBlock.setDropItem(false);

        this.bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 255));
        this.bat.setCanPickupItems(false);
        this.bat.setLeashHolder(p);
        this.bat.setPassenger(fallingBlock);
        this.bat.setRemoveWhenFarAway(false);

        bats.put(p, bat);
        fallingBlocks.put(p, fallingBlock);
    }

    public void remove() {
        if (fallingBlocks.containsKey(p)) {
            fallingBlocks.get(p).remove();
            fallingBlocks.remove(p);
        }
        if (bats.containsKey(p)) {
            bats.get(p).remove();
            bats.remove(p);
        }
    }

    public FallingBlock getFallingBlock() {
        return this.fallingBlock;
    }

    public Bat getBat() {
        return this.bat;
    }
}
