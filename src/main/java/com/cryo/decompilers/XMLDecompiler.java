package com.cryo.decompilers;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiPredicate;

import static com.cryo.NewWorldWikiBuilder.*;

public class XMLDecompiler extends Decompiler {

	public XMLDecompiler(String progressBarName) {
		super(progressBarName);
	}

	@Override
	public BiPredicate<Path, BasicFileAttributes> getFilter() {
		return (path, attr) -> FilenameUtils.getExtension(path.getFileName().toString()).equals("xml");
	}

	@Override
	public void decompile(Path[] paths) {
		for(Path path : paths) {
			try {
				bar.step();
				File dir = new File(path.toString().replace(UNPACKED_BASE_PATH, DECOMPILED_BASE_PATH).replace(path.getFileName().toString(), ""));
				if (!dir.exists()) dir.mkdirs();

				File file = new File(path.toString().replace(UNPACKED_BASE_PATH, DECOMPILED_BASE_PATH));
				if (file.exists()) file.delete();

				file.createNewFile();

				FileInputStream in = new FileInputStream(path.toFile());
				FileOutputStream out = new FileOutputStream(file);

				out.write(in.readAllBytes());
				out.flush();
				out.close();
				in.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
