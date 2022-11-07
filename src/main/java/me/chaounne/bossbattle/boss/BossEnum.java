package me.chaounne.bossbattle.boss;

import me.chaounne.bossbattle.boss.classes.ZombieMaster;
import org.bukkit.Material;

public enum BossEnum {

    ZombieMaster(ZombieMaster.class, Material.SLIME_BLOCK);

    private Class<? extends Boss> bossClass;
    private Material material;

    BossEnum(Class<? extends Boss> bossClass, Material material) {
        this.bossClass = bossClass;
        this.material = material;
    }

    public static BossEnum getBossEnumByMaterial(Material material) {
        for (BossEnum bossEnum : BossEnum.values()) {
            if (bossEnum.getMaterial() == material) {
                return bossEnum;
            }
        }
        return null;
    }

    public Material getMaterial() {
        return this.material;
    }

    public Class<? extends Boss> getBossClass() {
        return this.bossClass;
    }

}