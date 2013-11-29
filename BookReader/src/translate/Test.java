package translate;

/**
 * Created with IntelliJ IDEA.
 * User: Лиза
 * Date: 29.11.13
 * Time: 20:29
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args){
        String text = "IT was a special pleasure to see things eaten," +
                " to see things blackened and changed. With the brass nozzle in his " +
                "fists, with this great python spitting its venomous kerosene upon " +
                "the world, the blood pounded in his head, and his hands were the hands" +
                " of some amazing conductor playing all the symphonies of blazing and " +
                "burning to bring down the tatters and charcoal ruins of history. With" +
                " his symbolic helmet numbered 451 on his stolid head, and his eyes all " +
                "orange flame with the thought of what came next, he flicked the igniter" +
                " and the house jumped up in a gorging fire that burned the evening sky " +
                "red and yellow and black.He strode in a swarm of firefliers.He wanted" +
                " above all, like the old joke, to shove a marshmallow on a stick in the " +
                "furnace, while the flapping pigeon-winged books died on the porch and lawn" +
                " of the house. While the books went up in sparkling whirls and blew away" +
                " on a wind turned dark with burning.";
        try {
            System.out.println(new Request("eng", "ru", text).sendGet());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
