package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class CollisionStrategy {
    private GameObjectCollection collection;

    public CollisionStrategy(GameObjectCollection collection) {
        this.collection = collection;
    }

    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        collection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        brickCounter.decrement();
    }
}
