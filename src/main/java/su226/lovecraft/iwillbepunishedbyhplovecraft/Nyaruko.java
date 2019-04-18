package su226.lovecraft.iwillbepunishedbyhplovecraft;

import su226.lovecraft.LOVECraft;

/**
 * @since 2.2
 * @author su226
 * @see su226.lovecraft.iwillbepunishedbyhplovecraft.ICharacter
 * @see su226.lovecraft.iwillbepunishedbyhplovecraft.Su226
 */
public class Nyaruko implements ICharacter {
  public static final Nyaruko INSTANCE = new Nyaruko();

  /**
   * SENTENSES LIST IS WORK IN PROGRESS, DON'T USE IT :D!
   */
  private static final String[] sentences = new String[] {};

  private Nyaruko() {
  }

  @Override
  public void saySomething() {
    String chosenSentense = sentences[(int)(Math.random() * sentences.length)];
    LOVECraft.log.info(chosenSentense);
  }

  @Override
  public Su226 getCouple() {
    return Su226.INSTANCE;
  }
}