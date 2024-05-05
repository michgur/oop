package gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    public static int MOVE_SPEED = 300;

    private int minDistFromEdge;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener,
            Vector2 windowDimensions, int minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistFromEdge = minDistFromEdge;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        Vector2 velocity = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            velocity = velocity.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            velocity = velocity.add(Vector2.RIGHT);
        }
        setVelocity(velocity.mult(MOVE_SPEED));

        Vector2 topLeft = getTopLeftCorner();
        float rightBorder = windowDimensions.x() - minDistFromEdge - getDimensions().x();
        if (topLeft.x() < minDistFromEdge) {
            setTopLeftCorner(new Vector2(minDistFromEdge, topLeft.y()));
        } else if (topLeft.x() > rightBorder) {
            setTopLeftCorner(new Vector2(rightBorder, topLeft.y()));
        }
    }

}
