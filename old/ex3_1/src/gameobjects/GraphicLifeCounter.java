package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    private int displayedNumOfLives;
    private Counter livesCounter;

    private GameObjectCollection gameObjectsCollection;
    private GameObject[] hearts;

    // space between hearts
    private static final int HEART_PADDING = 6;

    public GraphicLifeCounter(
            Vector2 widgetTopLeftCorner, Vector2 heartDimensions,
            Counter livesCounter, Renderable heartRenderable,
            GameObjectCollection gameObjectsCollection, int numOfLives) {
        super(widgetTopLeftCorner, Vector2.ZERO, null);
        this.displayedNumOfLives = numOfLives;
        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;

        float offset = heartDimensions.x() + HEART_PADDING;
        hearts = new GameObject[numOfLives];
        for (int i = 0; i < numOfLives; i++) {
            hearts[i] = new GameObject(
                    widgetTopLeftCorner.add(new Vector2(i * offset, 0)),
                    heartDimensions,
                    heartRenderable);
            gameObjectsCollection.addGameObject(hearts[i], Layer.UI);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (displayedNumOfLives != livesCounter.value()) {
            // Remove `(numOfLives - livesCounter.value())` hearts from the end
            for (int i = displayedNumOfLives; i > livesCounter.value(); i--) {
                gameObjectsCollection.removeGameObject(hearts[i - 1], Layer.UI);
            }
            displayedNumOfLives = livesCounter.value();
        }
    }
}
