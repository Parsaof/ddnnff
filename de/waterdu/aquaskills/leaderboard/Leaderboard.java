//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.leaderboard;

import de.waterdu.aquaskills.file.*;
import java.util.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.player.*;

public class Leaderboard
{
    private static final Leaderboard instance;
    private final LinkedHashMap<UUID, ArrayList<Player>> leaderboard;
    
    private Leaderboard() {
        this.leaderboard = new LinkedHashMap<UUID, ArrayList<Player>>();
    }
    
    public static Leaderboard get() {
        return Leaderboard.instance;
    }
    
    public void init() {
        for (final Player player : Config.players()) {
            player.init();
        }
        this.refresh();
    }
    
    public void refresh() {
        final Iterator<IData> iterator;
        IData data;
        Skill skill;
        AquaConfig.whenNotBusy("aquaskills", (Class)Skill.class, skills -> {
            skills.iterator();
            while (iterator.hasNext()) {
                data = iterator.next();
                skill = (Skill)data;
                this.leaderboard.put(skill.getUUID(), new ArrayList<Player>(Config.players()));
                this.leaderboard.get(skill.getUUID()).sort(Comparator.comparingDouble(player -> player.getLeaderboardXP(skill)));
            }
            TransientData.gc();
        });
    }
    
    public ArrayList<Player> getLeaderboard(final Skill skill) {
        return this.leaderboard.get(skill.getUUID());
    }
    
    public Tuple<Player, Experience> getLeader(final Skill skill) {
        final ArrayList<Player> leaderboard = this.leaderboard.get(skill.getUUID());
        if (leaderboard.isEmpty()) {
            return null;
        }
        final Player player = leaderboard.get(0);
        return (Tuple<Player, Experience>)player.getXP(skill).map(xp -> new Tuple((Object)player, (Object)xp)).orElse(null);
    }
    
    public int getPos(final Skill skill, final Player player) {
        final ArrayList<Player> leaderboard = this.leaderboard.get(skill.getUUID());
        int i = 0;
        for (final Player p : leaderboard) {
            if (p.getUUID().equals(player.getUUID())) {
                return i;
            }
            ++i;
        }
        return i;
    }
    
    static {
        instance = new Leaderboard();
    }
}
