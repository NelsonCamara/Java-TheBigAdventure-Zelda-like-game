package com.TheBigAdventure.Main;

import java.awt.Color;
import java.io.IOException;

import com.TheBigAdventure.graphic.Graph;
import com.TheBigAdventure.mapBuiler.GameMap;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ScreenInfo;


public class Main {

  public static void main(String[] args) throws IOException {
    
    
    String mapFileName = "maps/fun.map"; 

    for (int i = 0; i < args.length; i++) {
        if (args[i].equals("--level") && i + 1 < args.length) {
            mapFileName = "maps/" + args[i + 1];
            break;
        }
    }

    final GameMap gameMap = Graph.initGameMap(mapFileName);
    if (gameMap == null) {
        return;
        }

    Application.run(Color.LIGHT_GRAY, context -> {
        ScreenInfo screenInfo = context.getScreenInfo();
        Graph.configureMapScale(gameMap, screenInfo);
        
        context.renderFrame(graphics -> Graph.drawInitialMap(gameMap, graphics, screenInfo));
        
        Graph.gameLoop(context, gameMap);
    });

  }

}
