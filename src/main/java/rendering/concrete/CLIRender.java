package rendering.concrete;

import enviroment.Cell;
import enviroment.WorldMap;
import rendering.Renderer;
import units.abstraction.Entity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CLIRender implements Renderer {
    @Override
    public void render(WorldMap worldMap) {
        printHorizontalBorder(worldMap.getWidth());

        for (int y = 0; y < worldMap.getHeight(); y++) {
            for (int x = 0; x < worldMap.getWidth(); x++) {
                Cell cell = worldMap.cellOf(x, y).orElseThrow();
                Set<Entity> entities = worldMap.getMap().get(cell);
                renderCell(entities);
            }
            System.out.println("|");
            printHorizontalBorder(worldMap.getWidth());
        }
    }

    private void renderCell(Set<Entity> entities) {
        // fixed 4 slots, pad empty with "  "
        List<String> slots = entities.stream()
                .map(Entity::getRepresentation)
                .collect(Collectors.toList());

        while (slots.size() < 4) slots.add("  ");

        System.out.print("|" + String.join("", slots.subList(0, 4)));
    }

    private void printHorizontalBorder(int width) {
        System.out.println(("+--------").repeat(width) + "+");
    }
}
