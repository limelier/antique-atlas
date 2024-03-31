package folk.sisby.antique_atlas;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public record MarkerTexture(Identifier id, int offsetX, int offsetY, int textureWidth, int textureHeight, int mipLevels) {
    public static MarkerTexture ofId(Identifier id, int offsetX, int offsetY, int width, int height, int mipLevels) {
        return new MarkerTexture(new Identifier(id.getNamespace(), "textures/atlas/markers/%s.png".formatted(id.getPath())), offsetX, offsetY, width, height, mipLevels);
    }

    public static MarkerTexture centered(Identifier id, int width, int height, int mipLevels) {
        return ofId(id, -width / 2, -height / 2, width, height, mipLevels);
    }

    public static final MarkerTexture DEFAULT = centered(AntiqueAtlas.id("unknown"), 32, 32, 0);

    public Identifier keyId() {
        return new Identifier(id.getNamespace(), id.getPath().substring("textures/atlas/markers/".length(), id.getPath().length() - 4));
    }

    public String displayId() {
        return id.getNamespace().equals(AntiqueAtlas.ID) ? keyId().getPath() : keyId().toString();
    }

    public int fullTextureWidth() {
        int width = textureWidth;
        for (int i = 0; i < mipLevels; i++) {
            width += textureWidth >> (i + 1);
        }
        return width;
    }

    public void draw(DrawContext context, int markerX, int markerY) {
        context.drawTexture(id(), markerX + offsetX(), markerY + offsetY(), 0, 0, textureWidth(), textureHeight(), fullTextureWidth(), textureHeight());
    }
}