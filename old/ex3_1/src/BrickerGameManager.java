import java.awt.event.KeyEvent;

import brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.Brick;
import gameobjects.GraphicLifeCounter;
import gameobjects.NumericLifeCounter;
import gameobjects.Paddle;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;

public class BrickerGameManager extends GameManager {
    public static int BORDER_WIDTH = 10;
    public static int BALL_SPEED = 300;

    public static int BRICK_ROWS = 8;
    public static int BRICK_COLUMNS = 7;
    public static int BRICK_PADDING = 6; // space between bricks

    public static int LIFE_PER_GAME = 3;

    private GameObject ball, paddle;
    private Counter brickCounter = new Counter(0), lifeCounter;

    private Vector2 windowDimensions;
    private WindowController windowController;
    private UserInputListener inputListener;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void initializeGame(
            ImageReader imageReader,
            SoundReader soundReader,
            UserInputListener inputListener,
            WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.inputListener = inputListener;
        this.windowController = windowController;
        windowController.setTargetFramerate(60);

        lifeCounter = new Counter(LIFE_PER_GAME);

        Vector2 ballSize = new Vector2(20, 20),
                paddleSize = new Vector2(100, 15);

        // open assets
        Renderable ballRenderable = imageReader.readImage("./ex3/assets/ball.png", true),
                paddleRenderable = imageReader.readImage("./ex3/assets/paddle.png", true),
                backgroundRenderable = imageReader.readImage("./ex3/assets/DARK_BG2_small.jpeg", false),
                brickRenderable = imageReader.readImage("./ex3/assets/brick.png", false),
                heartRenderable = imageReader.readImage("./ex3/assets/heart.png", true);
        Sound ballSound = soundReader.readSound("./ex3/assets/blop_cut_silenced.wav");

        ball = new Ball(
                Vector2.ZERO,
                ballSize,
                ballRenderable,
                ballSound);
        resetBall();

        paddle = new Paddle(
                Vector2.ZERO,
                paddleSize,
                paddleRenderable,
                inputListener,
                windowDimensions,
                5);
        paddle.setCenter(new Vector2(
                windowDimensions.x() / 2,
                windowDimensions.y() - (paddleSize.y() / 2) - (BORDER_WIDTH * 2)));

        GameObject wallLeft = new GameObject(
                Vector2.ZERO,
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                null);
        GameObject wallRight = new GameObject(
                new Vector2(windowDimensions.x() - BORDER_WIDTH, 0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                null);
        GameObject wallTop = new GameObject(
                Vector2.ZERO,
                new Vector2(windowDimensions.x(), BORDER_WIDTH),
                null);
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowDimensions,
                backgroundRenderable);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        GameObject[] bricks = createBrickArray(brickRenderable);

        Vector2 heartDimensions = new Vector2(20, 20);
        Vector2 uiTopLeftCorner = new Vector2(BORDER_WIDTH, windowDimensions.y() - BORDER_WIDTH - heartDimensions.y());
        GameObject numericLifeCounter = new NumericLifeCounter(
                lifeCounter,
                uiTopLeftCorner,
                heartDimensions,
                gameObjects());
        GameObject graphicLifeCounter = new GraphicLifeCounter(
                uiTopLeftCorner.add(new Vector2(heartDimensions.x(), 0)),
                heartDimensions,
                lifeCounter,
                heartRenderable,
                gameObjects(),
                LIFE_PER_GAME);

        // add all objects
        gameObjects().addGameObject(ball);
        gameObjects().addGameObject(paddle);
        gameObjects().addGameObject(wallTop);
        gameObjects().addGameObject(wallLeft);
        gameObjects().addGameObject(wallRight);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        for (GameObject brick : bricks) {
            gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS); }
        gameObjects().addGameObject(numericLifeCounter, Layer.UI);
        gameObjects().addGameObject(graphicLifeCounter, Layer.UI);
}

    /**
     * Creates an array of BRICK_ROWS * BRICK_COLUMNS bricks, with BRICK_PADDING
     * space between each brick.
     * 
     * @param brickRenderable
     *            The renderable to use for the bricks.
     * @return An array of bricks.
     */
    private GameObject[] createBrickArray(Renderable brickRenderable) {
        Brick[] bricks = new Brick[BRICK_ROWS * BRICK_COLUMNS];

        int brickHeight = 15;
        // window size excluding borders
        int windowWidth = (int) (windowDimensions.x() - BORDER_WIDTH * 2);
        // size for all bricks, accounting for padding
        int allBricksWidth = windowWidth - (BRICK_PADDING * (BRICK_COLUMNS - 1));
        // size per brick
        int brickWidth = allBricksWidth / BRICK_COLUMNS;
        Vector2 brickSize = new Vector2(brickWidth, brickHeight);

        // x and y offsets for each brick
        int brickX = BORDER_WIDTH, brickY = BORDER_WIDTH;
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLUMNS; j++) {
                bricks[i * BRICK_COLUMNS + j] = new Brick(
                        new Vector2(brickX, brickY),
                        brickSize,
                        brickRenderable,
                        new CollisionStrategy(gameObjects()), brickCounter);

                // move to next brick
                brickX += brickWidth + BRICK_PADDING;
            }
            // reset x offset, move to next row
            brickX = BORDER_WIDTH;
            brickY += brickHeight + BRICK_PADDING;
        }
        return bricks;
    }

    /**
     * Checks for loss or win conditions.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (ball.getTopLeftCorner().y() > windowDimensions.y()) {
            // life lost
            lifeCounter.decrement();
            if (lifeCounter.value() == 0) {
                // no lives left
                gameOver("You Lost! Play Again?");
            } else {
                resetBall();
            }
        } else if (brickCounter.value() == 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            // win condition - all bricks destroyed or user pressed W
            gameOver("You Won! Play Again?");
        }
    }

    /**
     * Asks the user if they want to play again. If they do, resets the game. If
     * they don't, closes the window.
     * 
     * @param prompt
     *            The prompt to show the user.
     */
    private void gameOver(String prompt) {
        boolean playAgain = windowController.openYesNoDialog(prompt);
        if (playAgain) {
            windowController.resetGame();
        } else {
            windowController.closeWindow();
        }
    }

    /**
     * Places the ball at the center of the screen with a random velocity.
     */
    private void resetBall() {
        Vector2 ballVelocity = new Vector2(
                Math.random() > .5 ? 1 : -1,
                Math.random() > .5 ? 1 : -1).mult(BALL_SPEED);
        ball.setVelocity(ballVelocity);
                ball.setCenter(windowDimensions.mult(.5f));
    }

    public static void main(String[] args) {
        BrickerGameManager gameManager = new 
          BrickerGameManager("Bricker", new Vector2(800, 600));
        gameManager.run();
    }
}
