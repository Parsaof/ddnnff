//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon.api;

import com.pixelmonmod.pixelmon.storage.playerData.*;
import com.pixelmonmod.pixelmon.quests.*;
import com.pixelmonmod.pixelmon.quests.objectives.*;
import de.waterdu.aquaskills.skill.*;
import java.util.function.*;
import com.pixelmonmod.pixelmon.quests.quest.*;
import java.util.*;
import de.waterdu.aquaskills.file.*;
import com.pixelmonmod.pixelmon.quests.editor.*;
import com.pixelmonmod.pixelmon.quests.editor.args.*;

public class GainLevelObjective implements IObjective
{
    public static final String IDENTIFIER = "GAIN_SKILL_LEVEL";
    
    public int quantity(final Stage stage, final QuestData data, final QuestProgress progress, final Objective objective, final Arguments arguments) {
        return (int)arguments.value(1, progress);
    }
    
    public String identifier() {
        return "GAIN_SKILL_LEVEL";
    }
    
    public Arguments parse(final Quest quest, final Stage stage, final ArgsIn argsIn) {
        return Arguments.create(new Argument[] { Argument.from(argsIn.get(0), (Function)SkillMap::get), Argument.from(argsIn.get(1), (Function)Integer::parseInt) });
    }
    
    public boolean test(final Stage stage, final QuestData data, final QuestProgress progress, final Objective objective, final Arguments arguments, final Context context) {
        final Optional<Skill> skill = (Optional<Skill>)arguments.value(0, progress);
        final Skill in = (Skill)context.get(0);
        return !skill.isPresent() || skill.get().getName().equalsIgnoreCase(in.getName());
    }
    
    public QuestElement getStructure() {
        return new QuestElement(QuestElementType.OBJECTIVE, this.identifier(), new QuestElementArgument[] { new QuestElementArgument("Skill", false, false, ArgumentType.TEXT, new String[0]), new QuestElementArgument("Levels", false, false, ArgumentType.WHOLE_NUMBER, new String[0]) });
    }
}
