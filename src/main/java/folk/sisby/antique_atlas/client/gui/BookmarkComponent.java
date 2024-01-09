package folk.sisby.antique_atlas.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import folk.sisby.antique_atlas.client.AntiqueAtlasTextures;
import folk.sisby.antique_atlas.client.gui.core.ToggleButtonComponent;
import folk.sisby.antique_atlas.client.texture.ITexture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.Collections;


/**
 * Bookmark-button in the journal. When a bookmark is selected, it will not
 * bulge on mouseover.
 */
public class BookmarkComponent extends ToggleButtonComponent {
    private static final int WIDTH = 21;
    private static final int HEIGHT = 18;

    private final int colorIndex;
    private ITexture iconTexture;
    private Text title;

    /**
     * @param colorIndex  0=red, 1=blue, 2=yellow, 3=green
     * @param iconTexture the path to the 16x16 texture to be drawn on top of the bookmark.
     * @param title       hovering text.
     */
    BookmarkComponent(int colorIndex, ITexture iconTexture, Text title) {
        this.colorIndex = colorIndex;
        setIconTexture(iconTexture);
        setTitle(title);
        setSize(WIDTH, HEIGHT);
    }

    void setIconTexture(ITexture iconTexture) {
        this.iconTexture = iconTexture;
    }

    public Text getTitle() {
        return title;
    }

    void setTitle(Text title) {
        this.title = title;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTick) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // Render background:
        int u = colorIndex * WIDTH;
        int v = isMouseOver || isSelected() ? 0 : HEIGHT;
        AntiqueAtlasTextures.BOOKMARKS.draw(context, getGuiX(), getGuiY(), u, v, WIDTH, HEIGHT);

        // Render the icon:
        iconTexture.draw(context, getGuiX() + (isMouseOver || isSelected() ? 3 : 2), getGuiY() + 1);

        if (isMouseOver) {
            drawTooltip(Collections.singletonList(title), MinecraftClient.getInstance().textRenderer);
        }
    }
}