import enviroment.WorldMap;
import logic.concrete.AStar;
import logic.concrete.System;
import logic.config.CreationConfig;
import logic.interfaces.Pathfinder;
import rendering.concrete.CLIRender;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(15, 15);
        Pathfinder pathfinder = new AStar(worldMap);
        List<CreationConfig> instructions = List.of(
                CreationConfig.TREE,
                CreationConfig.GRASS,
                CreationConfig.WOLF,
                CreationConfig.ZEBRA,
                CreationConfig.ZEBRA,
                CreationConfig.ZEBRA,
                CreationConfig.WOLF,
                CreationConfig.ROCK,
                CreationConfig.ROCK,
                CreationConfig.GRASS,
                CreationConfig.GRASS,
                CreationConfig.GRASS,
                CreationConfig.GRASS
        );
        System system = new System(worldMap, new CLIRender(), pathfinder, 10);
        system.generateEntities(instructions);
        system.start();
    }
}
