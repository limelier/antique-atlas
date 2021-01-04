package hunternif.mc.impl.atlas.ext.watcher;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public interface IStructureWatcher {

    /**
     * In SSP, this is cleared when the user has left the world.
     *
     * @return a <b>modifiable</b> collection of locations that have already been previously visited.
     */
    @Nonnull
    Set<WatcherPos> getVisited();

    /**
     * @return if the dimension is valid for this watcher
     */
    boolean isDimensionValid(ServerWorld world);

    /**
     * Return null if a structure should not be attempted to be visited.
     *
     * @param world the world containing the structure
     * @return the NBT tag to read structure data from
     */
    @Nullable
    CompoundTag getStructureData(@Nonnull ServerWorld world);

    /**
     * Called when a world is loaded or a chunk is populated. The return value is used to log what has been visited and where.
     *
     * @param world the world containing the structure
     * @return the locations that have been visited along with a name for it.
     */
    @Nonnull
    Set<Pair<WatcherPos, String>> visitStructure(@Nonnull World world, @Nonnull CompoundTag structureTag);
}
