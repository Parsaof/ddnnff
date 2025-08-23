//r

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

public class GainExperienceObjective implements IObjective
{
    public static final String IDENTIFIER = "GAIN_SKILL_XP";
    
    public int quantity(final Stage stage, final QuestData data, final QuestProgress progress, final Objective objective, final Arguments arguments) {
        return 1;
    }
    
    public String identifier() {
        return "GAIN_SKILL_XP";
    }
    
    public Arguments parse(final Quest quest, final Stage stage, final ArgsIn argsIn) {
        return Arguments.create(new Argument[] { Argument.from(argsIn.get(0), (Function)SkillMap::get), Argument.from(argsIn.get(1), (Function)Double::parseDouble) });
    }
    
    public boolean test(final Stage stage, final QuestData data, final QuestProgress progress, final Objective objective, final Arguments arguments, final Context context) {
        final Optional<Skill> skill = (Optional<Skill>)arguments.value(0, progress);
        final Skill in = (Skill)context.get(0);
        if (!skill.isPresent() || skill.get().getName().equalsIgnoreCase(in.getName())) {
            final double xp = (double)context.get(1);
            final String key = "XP-" + stage.getStage() + "-" + objective.getID();
            final double target = (double)arguments.value(1, progress);
            final String s = progress.getData(key);
            double remaining = (s == null) ? target : Double.parseDouble(s);
            remaining -= xp;
            if (remaining < 0.0) {
                remaining = 0.0;
            }
            progress.setData(key, String.valueOf(remaining));
            return remaining > 0.0;
        }
        return false;
    }
    
    public QuestElement getStructure() {
        return new QuestElement(QuestElementType.OBJECTIVE, this.identifier(), new QuestElementArgument[] { new QuestElementArgument("Skill", false, false, ArgumentType.TEXT, new String[0]), new QuestElementArgument("Experience", false, false, ArgumentType.DECIMAL_NUMBER, new String[0]) });
    }
}
