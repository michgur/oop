package gameobjects;

import java.awt.Color;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class NumericLifeCounter extends GameObject {
    private static final Color[] LIVES_COLORS = {
            Color.BLACK, // 0 lives
            Color.RED, // 1 life
            Color.YELLOW, // 2 lives
            Color.GREEN // 3+ lives
    };

    TextRenderable textRenderable = new TextRenderable("");

    Counter livesCounter;
    int displayedNumOfLives;

    public NumericLifeCounter(
            Counter livesCounter,
            Vector2 topLeftCorner,
            Vector2 dimensions,
            GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, Vector2.ZERO, null);

        this.livesCounter = livesCounter;
        GameObject counterGameObject = new GameObject(
                topLeftCorner,
                dimensions,
                textRenderable);
        gameObjectCollection.addGameObject(counterGameObject, Layer.UI);
        textRenderable.setColor(null);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (displayedNumOfLives != livesCounter.value()) {
            displayedNumOfLives = livesCounter.value();

            textRenderable.setString(String.valueOf(displayedNumOfLives));

            // index in color array - for more than 3 lives, use the last colors
            int colorIndex = Math.min(displayedNumOfLives, LIVES_COLORS.length - 1);
            textRenderable.setColor(LIVES_COLORS[colorIndex]);
        }
    }
}
