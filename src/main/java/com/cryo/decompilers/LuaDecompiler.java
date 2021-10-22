package com.cryo.decompilers;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiPredicate;

import static com.cryo.NewWorldWikiBuilder.*;

public class LuaDecompiler extends Decompiler {

	public LuaDecompiler(String progressBarName) {
		super(progressBarName);
	}

	@Override
	public BiPredicate<Path, BasicFileAttributes> getFilter() {
		return (path, attr) -> FilenameUtils.getExtension(path.getFileName().toString()).equals("luac");
	}

	@Override
	public void decompile(Path[] paths) {
		for(Path path : paths) {
			try {
				File file = path.toFile();
				bar.step();

				FileInputStream stream = new FileInputStream(file);
				byte[] data = stream.readAllBytes();
				stream.close();
				byte[] revised = new byte[data.length-2];
				System.arraycopy(data, 2, revised, 0, data.length-2);

				File decompiled = new File(DECOMPILED_BASE_PATH+file.getPath().replace(UNPACKED_BASE_PATH, "").replace("luac", "lua"));
				File decompiledDir = new File(decompiled.getPath().replace(decompiled.getName(), ""));
				if(!decompiledDir.exists())
					decompiledDir.mkdirs();
				if(decompiled.exists()) decompiled.delete();
				decompiled.createNewFile();

				com.cryo.unluac.LuaDecompiler.decompile(revised, decompiled.getPath());
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
