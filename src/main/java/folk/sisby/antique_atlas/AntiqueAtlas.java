package folk.sisby.antique_atlas;

import folk.sisby.antique_atlas.core.GlobalTileDataHandler;
import folk.sisby.antique_atlas.core.PlayerEventHandler;
import folk.sisby.antique_atlas.core.TileDataHandler;
import folk.sisby.antique_atlas.core.scanning.WorldScanner;
import folk.sisby.antique_atlas.marker.GlobalMarkersDataHandler;
import folk.sisby.antique_atlas.marker.MarkersDataHandler;
import folk.sisby.antique_atlas.network.AntiqueAtlasNetworking;
import folk.sisby.antique_atlas.structure.EndCity;
import folk.sisby.antique_atlas.structure.JigsawConfig;
import folk.sisby.antique_atlas.structure.NetherFortress;
import folk.sisby.antique_atlas.structure.Overworld;
import folk.sisby.antique_atlas.structure.Village;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AntiqueAtlas implements ModInitializer {
    public static final String ID = "antique_atlas";
    public static final String NAME = "Antique Atlas";

    public static Logger LOG = LogManager.getLogger(NAME);

    public static final WorldScanner worldScanner = new WorldScanner();
    public static final TileDataHandler tileData = new TileDataHandler();
    public static final MarkersDataHandler markersData = new MarkersDataHandler();

    public static final GlobalTileDataHandler globalTileData = new GlobalTileDataHandler();
    public static final GlobalMarkersDataHandler globalMarkersData = new GlobalMarkersDataHandler();

    public static AntiqueAtlasConfig CONFIG = AntiqueAtlasConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", "antique-atlas", AntiqueAtlasConfig.class);

    public static Identifier id(String path) {
        return path.contains(":") ? new Identifier(path) : new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {
        AntiqueAtlasNetworking.init();

        ClientPlayConnectionEvents.JOIN.register(tileData::onClientConnectedToServer);
        ClientPlayConnectionEvents.JOIN.register(markersData::onClientConnectedToServer);
        ClientPlayConnectionEvents.JOIN.register(globalMarkersData::onClientConnectedToServer);

        ServerPlayConnectionEvents.JOIN.register(globalMarkersData::onPlayerLogin);
        ServerPlayConnectionEvents.JOIN.register(globalTileData::onPlayerLogin);
        ServerPlayConnectionEvents.JOIN.register(PlayerEventHandler::onPlayerLogin);

        ServerWorldEvents.LOAD.register(globalMarkersData::onWorldLoad);
        ServerWorldEvents.LOAD.register(globalTileData::onWorldLoad);

        NetherFortress.registerPieces();
        EndCity.registerMarkers();
        Village.registerMarkers();
        Overworld.registerPieces();

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new JigsawConfig());
    }
}