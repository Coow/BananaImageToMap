package com.ubempire.map;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

public class TorchPopulator extends BlockPopulator {
	public static final int ITERATIONS = 3;
	public static final int TORCH_CHANCE = 70;
	public static final int ATTEMPTS = 30;
	private static final BlockFace[] directions = new BlockFace[] {
			BlockFace.UP, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST,
			BlockFace.WEST };

	@Override
	public void populate(World world, Random random, Chunk source) {
		ChunkSnapshot snapshot = source.getChunkSnapshot();

		for (int i = 0; i < ITERATIONS; i++) {
			if (random.nextInt(100) < TORCH_CHANCE) {
				attemptloop: for (int j = 0; j < ATTEMPTS; j++) {
					int x = random.nextInt(16);
					int z = random.nextInt(16);
					int y = snapshot.getHighestBlockYAt(x, z);
					if (y < 19) {
						continue;
					}
					y = random.nextInt(y - 18) + 18;

					Block base = source.getBlock(x, y, z);
					for (BlockFace direction : directions) {
						if (direction.getModX() + x > 15 || direction.getModX() + x < 0)
							continue;
						if (direction.getModZ() + z > 15 || direction.getModZ() + z < 0)
							continue;
						if (base.getRelative(direction).getType() == Material.AIR) {
							base.getRelative(direction).setType(Material.TORCH);
							break attemptloop;
						}
					}
				}
			}
		}
	}
}
