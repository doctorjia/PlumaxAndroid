package me.kyleyan.plumax.Game;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PlumaxMain {

    public static void main(String[] args) {

        PlumaxGame game = new PlumaxGame(3);
        Scanner input = new Scanner(System.in);
        game.play(input);

    }

}
