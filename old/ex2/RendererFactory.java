public class RendererFactory {
    /**
     * Create a renderer based on the given type.
     * 
     * @param type
     *            type of renderer to create
     * @param size
     *            size of board to render
     * @return renderer of given type
     */
    public Renderer buildRenderer(String type, int size) {
        switch (type) {
            case "console":
                return new ConsoleRenderer(size);
            case "none":
                return new VoidRenderer();
            default:
                throw new IllegalArgumentException("Unknown renderer type: " + type);
        }
    }
}
