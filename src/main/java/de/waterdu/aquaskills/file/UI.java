//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.file;

import java.util.*;
import de.waterdu.aquaapi.file.api.*;

public class UI implements IConfiguration
{
    private HashMap<String, UIDef> ui;
    
    public UI() {
        (this.ui = new HashMap<String, UIDef>()).put("mainBoth", new UIDef(3, "AquaSkills", new ArrayList<UIDef.Data>(Arrays.asList(UIDef.Data.of(10, "totem_of_undying", "&6Skills and Abilities", new String[0]), UIDef.Data.of(12, "emerald", "&6Battle Pass", new String[0]), UIDef.Data.of(14, "book", "&bLeaderboards", new String[0]), UIDef.Data.of(16, "paper", "&bSettings", new String[0])))));
        this.ui.put("mainASBP", new UIDef(3, "Battle Pass", new ArrayList<UIDef.Data>(Arrays.asList(UIDef.Data.of(11, "emerald", "&6Battle Pass", new String[0]), UIDef.Data.of(13, "book", "&bLeaderboards", new String[0]), UIDef.Data.of(15, "paper", "&bSettings", new String[0])))));
        this.ui.put("mainSkills", new UIDef(3, "AquaSkills", new ArrayList<UIDef.Data>(Arrays.asList(UIDef.Data.of(11, "totem_of_undying", "&6Skills and Abilities", new String[0]), UIDef.Data.of(13, "book", "&bLeaderboards", new String[0]), UIDef.Data.of(15, "paper", "&bSettings", new String[0])))));
        this.ui.put("asbp", new UIDef(4, "Battle Pass", new ArrayList<UIDef.Data>(Arrays.asList(UIDef.Data.of(31, "pixelmon:trade_machine", "", new String[0]), UIDef.Data.of(29, "pixelmon:trade_holder_left", "&ePrevious Rewards", new String[0]), UIDef.Data.of(33, "pixelmon:trade_holder_right", "&eNext Rewards", new String[0])))));
        this.ui.put("leaderboard", new UIDef(3, "%1 Leaderboard", new ArrayList<UIDef.Data>(Arrays.asList(UIDef.Data.of(40, "air", "", new String[0]), UIDef.Data.of(38, "pixelmon:trade_holder_left", "&e<-", new String[0]), UIDef.Data.of(42, "pixelmon:trade_holder_right", "&e->", new String[0]), UIDef.Data.of(10, "air", "", new String[0]), UIDef.Data.of(11, "air", "", new String[0]), UIDef.Data.of(12, "air", "", new String[0]), UIDef.Data.of(13, "air", "", new String[0]), UIDef.Data.of(14, "air", "", new String[0]), UIDef.Data.of(15, "air", "", new String[0]), UIDef.Data.of(16, "air", "", new String[0])))));
        this.ui.put("bind", new UIDef(3, "Bind %1", new ArrayList<UIDef.Data>(Arrays.asList(UIDef.Data.of(11, "pixelmon:eject_button", "&cCancel Binding", new String[0]), UIDef.Data.of(15, "pixelmon:binding_band", "&6Bind Ability to Item", new String[0]), UIDef.Data.of(13, "barrier", "&8No Item Selected", new String[0])))));
    }
    
    public static UIDef getUI(final String key) {
        return ((UI)AquaConfig.get("aquaskills", (Class)UI.class)).ui.get(key);
    }
    
    public HashMap<String, UIDef> getUi() {
        return this.ui;
    }
    
    public void setUi(final HashMap<String, UIDef> ui) {
        this.ui = ui;
    }
}
