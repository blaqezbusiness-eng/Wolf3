package com.werewolf.game.util;

import com.werewolf.game.WerewolfPlugin;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldManager {
    private final WerewolfPlugin plugin;

    public WorldManager(WerewolfPlugin plugin) {
        this.plugin = plugin;
    }

    public File getWorldsFolder() {
        File folder = new File(this.plugin.getDataFolder(), "World");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    public File findWorldSourceFolder(String worldName) {
        File[] candidates = new File[]{
            new File(this.getWorldsFolder(), worldName),
            new File(this.plugin.getDataFolder(), "maps/" + worldName),
            new File(this.getWorldsFolder(), "maps/" + worldName)
        };
        for (File f : candidates) {
            if (f.exists() && f.isDirectory()) return f;
        }
        return null;
    }

    public boolean worldFolderExists(String worldName) {
        return this.findWorldSourceFolder(worldName) != null;
    }

    public World loadWorld(String worldName) {
        if (!this.worldFolderExists(worldName)) {
            return null;
        }
        World world = Bukkit.getWorld((String)worldName);
        if (world != null) {
            return world;
        }
        File sourceFolder = this.findWorldSourceFolder(worldName);
        File serverWorldContainer = Bukkit.getWorldContainer();
        File targetFolder = new File(serverWorldContainer, worldName);
        try {
            this.deleteRecursive(targetFolder.toPath());
            File migratedRoot = new File(serverWorldContainer, "world");
            File migratedDimension = new File(migratedRoot, "dimensions/minecraft/" + worldName);
            this.deleteRecursive(migratedDimension.toPath());
        }
        catch (IOException e) {
            this.plugin.getLogger().severe("Could not clean stale world '" + worldName + "': " + e.getMessage());
            return null;
        }
        try {
            this.copyFolder(sourceFolder.toPath(), targetFolder.toPath());
        }
        catch (IOException e) {
            this.plugin.getLogger().severe("Could not copy world '" + worldName + "': " + e.getMessage());
            return null;
        }
        WorldCreator creator = new WorldCreator(worldName);
        creator.environment(World.Environment.NORMAL);
        world = creator.createWorld();
        if (world != null) {
            this.plugin.getLogger().info("Loaded world '" + worldName + "' from " + sourceFolder.getPath());
        }
        return world;
    }

    public World getOrLoadWorld(String worldName) {
        return this.loadWorld(worldName);
    }

    private void copyFolder(final Path source, final Path target) throws IOException {
        Files.walkFileTree(source, (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path relative = source.relativize(dir);
                Path dest = target.resolve(relative);
                Files.createDirectories(dest, new FileAttribute[0]);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relative = source.relativize(file);
                Path dest = target.resolve(relative);
                Files.copy(file, dest, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void deleteRecursive(Path path) throws IOException {
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        Files.walkFileTree(path, (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
