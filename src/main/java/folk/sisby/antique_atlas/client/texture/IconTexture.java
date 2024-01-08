package folk.sisby.antique_atlas.client.texture;

import net.minecraft.util.Identifier;

/**
 * Represents an icon texture for the atlas gui, such as the arrows and center on player.
 */
public class IconTexture extends ATexture {
    public IconTexture(Identifier texture) {
        super(texture);
    }

    @Override
    public int width() {
        return 16;
    }

    @Override
    public int height() {
        return 16;
    }
}