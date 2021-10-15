package com.giornosmp.rainingblocks.game;

import com.giornosmp.rainingblocks.RainingBlocks;
import com.giornosmp.rainingblocks.gamestates.State;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RainManager extends BukkitRunnable {

    private final RainingBlocks main;
    private final Random random = new Random();

    public RainManager(RainingBlocks main) {
        super();

        this.main = main;
    }

    @Override
    public void run() {
        int height = main.getConfig().getInt("height");
        int radiusX = main.getConfig().getInt("radius");
        int radiusZ = main.getConfig().getInt("radius");
        List<String> blockList;
        Material block;
        

        switch (main.getblockStage()){
            case STAGE1:
                blockList = main.getConfig().getStringList("stages.stage1.blocks");
                block = Material.valueOf(blockList.get(random.nextInt(blockList.toArray().length)));
                break;
            case STAGE2:
                blockList = main.getConfig().getStringList("stages.stage2.blocks");
                block = Material.valueOf(blockList.get(random.nextInt(blockList.toArray().length)));
                break;
            case STAGE3:
                blockList = main.getConfig().getStringList("stages.stage3.blocks");
                block = Material.valueOf(blockList.get(random.nextInt(blockList.toArray().length)));
                break;
            default:
                throw new IllegalStateException("unexpected value: " + main.getblockStage());
        }
        int x = random.nextInt(radiusX);
        int z = random.nextInt(radiusZ);
        int yDD = 0;

        int addOrSub = random.nextInt(5);

        if (main.getGameState() == State.OFF){
            this.cancel();
        }
        else if (main.getGameState() == State.ON){
            for (Player humans : Bukkit.getOnlinePlayers()){
                if (humans.getWorld().getEnvironment().equals(World.Environment.NORMAL) || humans.getWorld().getEnvironment().equals(World.Environment.THE_END)){
                    Location playerLocation = humans.getLocation();

                    playerLocation.setY(playerLocation.getY() + height);

                    switch (addOrSub){
                        case 1:
                            playerLocation.setX(Math.round(playerLocation.getX()) + x);
                            playerLocation.setZ(Math.round(playerLocation.getZ()) + z);
                            this.summonFallingBlock(playerLocation, block);
                            break;
                        case 2:
                            playerLocation.setX(Math.round(playerLocation.getX() - x) + 0.5);
                            playerLocation.setZ(Math.round(playerLocation.getZ() - z) + 0.5);
                            this.summonFallingBlock(playerLocation, block);
                            break;
                        case 3:
                            playerLocation.setX(Math.round(playerLocation.getX() - x) + 0.5);
                            playerLocation.setZ(Math.round(playerLocation.getZ() + z) + 0.5);
                            this.summonFallingBlock(playerLocation, block);
                            break;
                        case 4:
                            playerLocation.setX(Math.round(playerLocation.getX() + x) + 0.5);
                            playerLocation.setZ(Math.round(playerLocation.getZ() - z) + 0.5);
                            this.summonFallingBlock(playerLocation, block);
                            break;
                    }
                }
                else if (humans.getWorld().getEnvironment().equals(World.Environment.NETHER)){
                    Location playerLocation = humans.getLocation();

                    while (playerLocation.getWorld().getBlockAt(playerLocation.add(0, yDD, 0)).getType().equals(Material.AIR)) {
                        yDD++;
                    }
                    playerLocation.setY(playerLocation.getY() + yDD);

                    switch (addOrSub){
                        case 1:
                            playerLocation.setX(Math.round(playerLocation.getX() + x) + 0.5);
                            playerLocation.setZ(Math.round(playerLocation.getZ() + z) + 0.5);
                            this.summonFallingBlock(playerLocation, block);
                            break;
                        case 2:
                            playerLocation.setX(Math.round(playerLocation.getX() - x) + 0.5);
                            playerLocation.setZ(Math.round(playerLocation.getZ() - z) + 0.5);
                            this.summonFallingBlock(playerLocation, block);
                            break;
                        case 3:
                            playerLocation.setX(Math.round(playerLocation.getX() - x) + 0.5);
                            playerLocation.setZ(Math.round(playerLocation.getZ() + z) + 0.5);
                            this.summonFallingBlock(playerLocation, block);
                            break;
                        case 4:
                            playerLocation.setX(Math.round(playerLocation.getX() + x) + 0.5);
                            playerLocation.setZ(Math.round(playerLocation.getZ() - z) + 0.5);
                            this.summonFallingBlock(playerLocation, block);
                            break;
                    }
                }
            }
        }
    }

    private void summonFallingBlock(Location location, Material block){
        location.getWorld().spawnFallingBlock(location, block.createBlockData()).setDropItem(false);
    }
}
