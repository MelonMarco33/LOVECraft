package su226.lovecraft;

import net.minecraftforge.common.config.Config;

@Config(modid = LOVECraft.MODID)
public class LCConfig {
  @Config.Comment("The formula to calculate EXP of a entity, x means the max health of the entity.")
  public static String EXPFormula = "x";
  @Config.Comment("The formula to calculate needed EXP to upgrade, x means love.")
  public static String MaxEXPFormula = "2^x";
  @Config.Comment("The table to get needed EXP to upgrade, from 0 to which level you need, if exp exceed the level in the table, it will be calculated with the formula.")
  public static int[] MaxEXPTable = new int[0];
  @Config.Comment("The blacklist of entities.")
  public static String[] EntitiesBlacklist = new String[0];
  @Config.Comment("The whitelist of entities, if it is set, any entity that is not on the list will be ignored, and the blacklist will be ignored too, format RegisterName, example minecraft:pig.")
  public static String[] EntitiesWhitelist = new String[0];
  @Config.Comment("The EXP of entities, the EXP of any entity that is not on the list will be calculated with the formula, format RegisterName:EXP, example minecraft:pig:10.")
  public static String[] EntitiesEXP = new String[0];
  @Config.Comment("Will the EXP bar be shown? Client side only.")
  public static boolean showBar = true;
  @Config.Comment("Will the EXP message be sent to players?")
  public static boolean showMessage = true;
  @Config.Comment("Will the EXP sound be played?")
  public static boolean playSound = true;
}