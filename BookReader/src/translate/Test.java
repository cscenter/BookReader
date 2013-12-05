package translate;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Лиза
 * Date: 29.11.13
 * Time: 20:29
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args){
        String enText = "IT was a special pleasure to see things eaten," +
                " to see things blackened and changed. With the brass nozzle in his " +
                "fists, with this great python spitting its venomous kerosene upon " +
                "the world, the blood pounded in his head, and his hands were the hands" +
                " of some amazing conductor playing all the symphonies of blazing and " +
                "burning to bring down the tatters and charcoal ruins of history. With" +
                " his symbolic helmet numbered 451 on his stolid head, and his eyes all " +
                "orange flame with the thought of what came next, he flicked the igniter" +
                " and the house jumped up in a gorging fire that burned the evening sky " +
                "red and yellow and black.He strode in a swarm of firefliers. He wanted" +
                " above all, like the old joke, to shove a marshmallow on a stick in the " +
                "furnace, while the flapping pigeon-winged books died on the porch and lawn" +
                " of the house. While the books went up in sparkling whirls and blew away" +
                " on a wind turned dark with burning.";
        String ruText = "﻿Жечь  было наслаждением. Какое-то особое наслаждение видеть, как  огонь\n" +
                "пожирает  вещи, как они чернеют и  меняются. Медный  наконечник  брандспойта\n" +
                "зажат в кулаках, громадный питон  изрыгает на  мир  ядовитую струю керосина,\n" +
                "кровь  стучит  в  висках,  а  руки  кажутся   руками  диковинного  дирижера,\n" +
                "исполняющего симфонию огня  и  разрушения,  превращая  в  пепел  изорванные,\n" +
                "обуглившиеся страницы истории.  Символический  шлем,  украшенный цифрой 451,\n" +
                "низко  надвинут на лоб, глаза сверкают  оранжевым пламенем  при мысли о том,\n" +
                "что должно сейчас произойти:  он  нажимает  воспламенитель  -  и огонь жадно\n" +
                "бросается на  дом,  окрашивая вечернее небо в багрово-желто-черные тона.  Он\n" +
                "шагает в рое огненно-красных светляков,  и больше всего  ему хочется сделать\n" +
                "сейчас то, чем он  так часто забавлялся в  детстве,- сунуть в огонь прутик с\n" +
                "леденцом, пока книги, как  голуби, шелестя крыльями-страницами,  умирают  на\n" +
                "крыльце и на лужайке перед домом, они взлетают в огненном вихре, и черный от\n" +
                "копоти ветер уносит их прочь.\n";
        String translate = null;
        try {
            translate = new Request( "eng","ru", enText.toLowerCase()).sendGet();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        HashMap<String, Integer> map = Search.stringParser(ruText);
     //   System.out.println(map);
        System.out.println(translate);
        System.out.println(Search.compare(translate, ruText.toLowerCase()));
    }
}
