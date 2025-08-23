//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.file;

import de.waterdu.aquaapi.file.api.*;

@Lang
public class SkillMessages implements IConfiguration
{
    public String SILVER_TONGUE;
    public String I_WANNA_BE_THE_VERY_BEST;
    public String WORD_OF_RECALL1;
    public String WORD_OF_RECALL2;
    
    public String getSILVER_TONGUE() {
        return this.SILVER_TONGUE;
    }
    
    public String getI_WANNA_BE_THE_VERY_BEST() {
        return this.I_WANNA_BE_THE_VERY_BEST;
    }
    
    public String getWORD_OF_RECALL1() {
        return this.WORD_OF_RECALL1;
    }
    
    public String getWORD_OF_RECALL2() {
        return this.WORD_OF_RECALL2;
    }
    
    public void setSILVER_TONGUE(final String SILVER_TONGUE) {
        this.SILVER_TONGUE = SILVER_TONGUE;
    }
    
    public void setI_WANNA_BE_THE_VERY_BEST(final String I_WANNA_BE_THE_VERY_BEST) {
        this.I_WANNA_BE_THE_VERY_BEST = I_WANNA_BE_THE_VERY_BEST;
    }
    
    public void setWORD_OF_RECALL1(final String WORD_OF_RECALL1) {
        this.WORD_OF_RECALL1 = WORD_OF_RECALL1;
    }
    
    public void setWORD_OF_RECALL2(final String WORD_OF_RECALL2) {
        this.WORD_OF_RECALL2 = WORD_OF_RECALL2;
    }
    
    public SkillMessages() {
        this.SILVER_TONGUE = "&7Your silvered words gained you an extra $%1!";
        this.I_WANNA_BE_THE_VERY_BEST = "&7%1 held on to %2 XP!";
        this.WORD_OF_RECALL1 = "&7Set your recall location.";
        this.WORD_OF_RECALL2 = "&7You can only recall while you're in the same world!";
    }
}
