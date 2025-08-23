//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.ui;

import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.battlepass.*;
import net.minecraft.entity.player.*;
import java.util.*;
import de.waterdu.aquaskills.battlepass.ui.*;
import de.waterdu.aquaapi.ui.api.*;

public class MainMenuPage implements IPage
{
    private final UIDef ui;
    private final Player p;
    private final Tristate tristate;
    
    public MainMenuPage(final Player p) {
        this.p = p;
        final ASBPSettings settings = Config.settingsASBP();
        if (settings.isOnlyASBPEnabled()) {
            this.ui = UI.getUI("mainASBP");
            this.tristate = Tristate.NONE;
        }
        else if (settings.isASBPEnabled()) {
            this.ui = UI.getUI("mainBoth");
            this.tristate = Tristate.ALL;
        }
        else {
            this.ui = UI.getUI("mainSkills");
            this.tristate = Tristate.SOME;
        }
    }
    
    public PageOptions getPageOptions(final EntityPlayerMP player) {
        return PageOptions.builder().setRows(this.ui.getRows()).setInventoryHidden(true).setTitleAlignment(PageOptions.TextAlignment.CENTER).build();
    }
    
    public void addButtons(final EntityPlayerMP player, final Set<Button> buttons) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: istore_3        /* i */
        //     2: iload_3         /* i */
        //     3: aload_0         /* this */
        //     4: getfield        de/waterdu/aquaskills/ui/MainMenuPage.ui:Lde/waterdu/aquaskills/file/UIDef;
        //     7: invokevirtual   de/waterdu/aquaskills/file/UIDef.getButtonCount:()I
        //    10: if_icmpge       51
        //    13: iload_3         /* i */
        //    14: istore          j
        //    16: aload_2         /* buttons */
        //    17: aload_0         /* this */
        //    18: getfield        de/waterdu/aquaskills/ui/MainMenuPage.ui:Lde/waterdu/aquaskills/file/UIDef;
        //    21: iload_3         /* i */
        //    22: invokevirtual   de/waterdu/aquaskills/file/UIDef.getButton:(I)Lde/waterdu/aquaapi/ui/api/Button$Builder;
        //    25: aload_0         /* this */
        //    26: iload           j
        //    28: invokedynamic   BootstrapMethod #0, accept:(Lde/waterdu/aquaskills/ui/MainMenuPage;I)Ljava/util/function/Consumer;
        //    33: invokevirtual   de/waterdu/aquaapi/ui/api/Button$Builder.setClickAction:(Ljava/util/function/Consumer;)Lde/waterdu/aquaapi/ui/api/Button$Builder;
        //    36: invokevirtual   de/waterdu/aquaapi/ui/api/Button$Builder.build:()Lde/waterdu/aquaapi/ui/api/Button;
        //    39: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //    44: pop            
        //    45: iinc            i, 1
        //    48: goto            2
        //    51: return         
        //    Signature:
        //  (Lnet/minecraft/entity/player/EntityPlayerMP;Ljava/util/Set<Lde/waterdu/aquaapi/ui/api/Button;>;)V
        //    StackMapTable: 00 02 FC 00 02 01 FA 00 30
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.Decompiler.decompile(Decompiler.java:70)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompile(Deobfuscator3000.java:538)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompileAndDeobfuscate(Deobfuscator3000.java:552)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.processMod(Deobfuscator3000.java:510)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.lambda$21(Deobfuscator3000.java:329)
        //     at java.base/java.lang.Thread.run(Thread.java:834)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public String getDisplayName(final EntityPlayerMP player) {
        return this.ui.getTitle();
    }
}
