package su226.lovecraft.iwillbepunishedbyhplovecraft;

import su226.lovecraft.LOVECraft;

/**
 * <p>I will be punished by H.P.Lovecraft.</p>
 * <p><del>Hewlett-Packard</del></p>
 * <p><del>Harry Potter</del></p>
 * <p><del>Helicobacter Pylori</del></p>
 * <p><del>Health Point</del></p>
 * <p><del>Horse Power</del></p>
 * <p><del>High Performance</del></p>
 * <p><del>High Pressure. YES WE ARE GREGTECH PLAYERS AND WE FEAR NOTHING!!!</del></p>
 * <p>SO TELL ME WHY I SHOULD WRITE JAVADOC FOR THIS CLASS?</p>
 * @see su226.lovecraft.iwillbepunishedbyhplovecraft.Nyaruko
 * @author su226
 * @since 2.2
 */
public class Su226 {
  /**
   * Something to say :D.
   */
  private static String[] sentences = new String[] {
    "It's a beautiful day outside, brids are singing, flowers are blooming, on days like that, kids like you, SHOULD FIX THE BUGS IN THE HELL!",
    "I love Nyarlathotep... Wait a minute what did I say?",
    "What's up everybody welcome to my modding studio where safety is number one priority.",
    "My name is su226, I'm a modder, I'm a performace modder, I'm hired for people for fulfil their fantasies, theri DEEP♂DARK♂FANTASIES.",
    "VSCode is the best editor... Hold on vim, emacs, sublime text and atom users I have to explain.",
    "I am honored that I'm from the great Gregtech Intergalactical",
    "Get the f**k out of my room I'm playing Minecraft!",
    "If you have some entity that can generate electrity such as Misaka Mikoto, Kanna Kamui, Pikachu or whatever you will live like Mehdi Sadaghdar.",
    "Praise the Python!",
    "Let me have a cup of Java and calm down.",
    "I have a C++, I have some hair, oh! No hair left!"};

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
    return Nyaruko.class.getSimpleName();
  }
}