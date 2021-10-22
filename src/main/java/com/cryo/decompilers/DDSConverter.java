package com.cryo.decompilers;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

import static com.cryo.NewWorldWikiBuilder.*;

public class DDSConverter extends Decompiler {

	public DDSConverter(String progressBarName) {
		super(progressBarName);
	}

	@Override
	public BiPredicate<Path, BasicFileAttributes> getFilter() {
		return (path, attr) -> FilenameUtils.getExtension(path.getFileName().toString()).equals("dds") || FilenameUtils.getExtension(path.getFileName().toString()).matches("\\d+");
	}

	@Override
	public void decompile(Path[] paths) {
		Map<String, byte[]> checkedFiles = new HashMap<>();
		for(Path path : paths) {
			File file = path.toFile();
			try {
				if(file.getName().matches(".*?\\.\\d+") || Arrays.stream(file.getParentFile().listFiles()).anyMatch(f -> f.getName().matches(".*?\\.\\d+"))) {
					String name = file.getPath().substring(0, file.getPath().indexOf(".dds"));
					if(!checkedFiles.containsKey(name))
						checkedFiles.put(name, new byte[0]);
					FileInputStream in = new FileInputStream(file);
					byte[] data = checkedFiles.get(name);
					data = add(data, in.readAllBytes());
					checkedFiles.put(name, data);
					if(!file.getName().matches(".*?\\.\\d+")) bar.step();
					continue;
				}
				bar.step();
				File decompiled = new File(DECOMPILED_BASE_PATH+file.getPath().replace(UNPACKED_BASE_PATH, "").replace("dds", "png"));
				File decompiledDir = new File(decompiled.getPath().replace(decompiled.getName(), ""));
				if(decompiled.exists()) continue;
				BufferedImage image = ImageIO.read(file);
				if(image == null) {
					System.err.println("Unable to read file: "+file.getPath());
					continue;
				}
				if(!decompiledDir.exists())
					decompiledDir.mkdirs();
				if(decompiled.exists()) decompiled.delete();
				decompiled.createNewFile();

				FileOutputStream stream = new FileOutputStream(decompiled);

				ImageIO.write(image, "PNG", stream);

				stream.flush();
				stream.close();

			} catch(Exception e) {
				System.err.println("Error decompiling file: "+file.getPath());
				e.printStackTrace();
			}
		}
		for(String path : checkedFiles.keySet()) {
			try {
				File decompiled = new File(DECOMPILED_BASE_PATH + path.replace(UNPACKED_BASE_PATH, "") + ".png");
				File decompiledDir = new File(decompiled.getPath().replace(decompiled.getName(), ""));
				if (!decompiledDir.exists()) decompiledDir.mkdirs();
				if (decompiled.exists()) decompiled.delete();
				decompiled.createNewFile();

				ByteArrayInputStream arrayStream = new ByteArrayInputStream(checkedFiles.get(path));

				BufferedImage image = ImageIO.read(arrayStream);
				if(image == null) {
					System.err.println("Unable to read file: "+path);
					continue;
				}

				FileOutputStream stream = new FileOutputStream(decompiled);

				ImageIO.write(image, "PNG", stream);

				stream.flush();
				stream.close();
				arrayStream.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static byte[] add(byte[] src, byte[] toAdd) {
		byte[] results = new byte[src.length + toAdd.length];
		System.arraycopy(src, 0, results, 0, src.length);
		System.arraycopy(toAdd, 0, results, src.length, toAdd.length);
		return results;
	}
}
