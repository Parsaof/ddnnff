//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.battlepass;

import java.util.*;
import de.waterdu.aquaapi.file.api.*;

public class ASBPRewards implements IConfiguration
{
    private ArrayList<ASBPReward> rewards;
    
    public ArrayList<ASBPReward> getInternal() {
        return this.rewards;
    }
    
    public static ArrayList<ASBPReward> getRewards() {
        return ((ASBPRewards)AquaConfig.get("aquaskills", (Class)ASBPRewards.class)).rewards;
    }
    
    public void setRewards(final ArrayList<ASBPReward> rewards) {
        this.rewards = rewards;
    }
    
    public ASBPRewards() {
        this.rewards = new ArrayList<ASBPReward>();
    }
}
