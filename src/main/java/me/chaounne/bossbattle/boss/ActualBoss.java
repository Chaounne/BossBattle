package me.chaounne.bossbattle.boss;

public class ActualBoss {

    private static Boss boss;

    public static Boss getBoss() {
        return boss;
    }

    public static void setBoss(Boss boss) {
        ActualBoss.boss = boss;
    }
}
