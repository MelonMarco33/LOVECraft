package su226.lovecraft.iwillbepunishedbyhplovecraft;

import su226.lovecraft.LOVECraft;

/**
 * @see su226.lovecraft.iwillbepunishedbyhplovecraft.Su226
 * @author su226
 * @since 2.2
 */
public class Nyaruko {
  /**
   * SENTENSES LIST ARE WORK IN PROGRESS, DON'T USE IT :D!
   */
  private static final String[] sentences = new String[] {};

  /**
   * <blockquote>
   * <p>This does what you think it does.</p>
   * <p>--GCC C++ headers</p>
   * </blockquote>
   */
  public static final void saySomething() {
    String chosenSentense = sentences[(int)(Math.random() * sentences.length)];
    LOVECraft.log.info(chosenSentense);
  }

  /**
   * Get the "couple" class of this class
   */
  public static final String getCouple() {
    return Su226.class.getSimpleName();
  }
}