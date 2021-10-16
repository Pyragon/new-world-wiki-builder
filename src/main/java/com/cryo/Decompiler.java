package com.cryo;

import com.cryo.io.InputStream;
import com.cryo.unluac.LuaDecompiler;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.function.Predicate;

import static com.cryo.Constants.*;

public class Decompiler {

	public static String UNPACKED_BASE_PATH = "D:\\workspace\\github\\NewWorldUnpacker\\unpacked_out\\";
	public static String PACKED_LUA_BASE_PATH = "D:\\workspace\\github\\NewWorldUnpacker\\compiled_lua\\";
	public static String DECOMPILED_BASE_PATH = "D:\\workspace\\github\\NewWorldUnpacker\\decompiled\\";

	public static Gson gson;

	public static void main(String[] args) {

		buildGson();

		File unpackedBase = new File(UNPACKED_BASE_PATH);
		if(!unpackedBase.exists()) return;
		File[] files = unpackedBase.listFiles();
		if(files == null) return;
//		decompileLua(Arrays.stream(files).filter(LUAC_FILTER).toArray(File[]::new));
//		decompileDatasheets(Arrays.stream(files).filter(DATASHEET_FILTER).toArray(File[]::new));
		decompileDDS(Arrays.stream(files).filter(DDS_FILTER).toArray(File[]::new));

	}

	public static Predicate<File> LUAC_FILTER = f -> f.isDirectory() || FilenameUtils.getExtension(f.getName()).equals("luac");
	public static Predicate<File> DATASHEET_FILTER = f -> f.isDirectory() || FilenameUtils.getExtension(f.getName()).equals("datasheet");
	public static Predicate<File> DDS_FILTER = f -> f.isDirectory() || FilenameUtils.getExtension(f.getName()).equals("dds");

	public record StringData(byte[] hash, int offset) {}

	public static String findImage(String name) {
		for(File file : new File(DECOMPILED_BASE_PATH).listFiles()) {
			
		}
	}

	public static void decompileDDS(File[] files) {
		for(File file : files) {
			if (!file.exists()) continue;
			if (file.isDirectory()) {
				File[] subFiles = file.listFiles();
				if (subFiles == null) continue;
				decompileDDS(Arrays.stream(subFiles).filter(DDS_FILTER).toArray(File[]::new));
				continue;
			}
			try {

				BufferedImage image = ImageIO.read(file);
				if(image == null) {
					System.err.println("Unable to read file: "+file.getPath());
					continue;
				}
				File decompiled = new File(DECOMPILED_BASE_PATH+file.getPath().replace(UNPACKED_BASE_PATH, "").replace("dds", "png"));
				File decompiledDir = new File(decompiled.getPath().replace(decompiled.getName(), ""));
				if(!decompiledDir.exists())
					decompiledDir.mkdirs();
				if(decompiled.exists()) decompiled.delete();
				decompiled.createNewFile();

				FileOutputStream stream = new FileOutputStream(decompiled);

				ImageIO.write(image, "PNG", stream);

				stream.flush();
				stream.close();

				System.out.println("Decompiled: "+file.getPath());

			} catch(Exception e) {
				System.err.println("Error decompiling file: "+file.getPath());
				e.printStackTrace();
			}
		}
	}

	public static void decompileDatasheets(File[] files) {
		for(File file : files) {
			if(!file.exists()) continue;
			if(file.isDirectory()) {
				File[] subFiles = file.listFiles();
				if(subFiles == null) continue;
				decompileDatasheets(Arrays.stream(subFiles).filter(DATASHEET_FILTER).toArray(File[]::new));
				continue;
			}
			try {
				FileInputStream stream = new FileInputStream(file);
				byte[] data = stream.readAllBytes();
				stream.close();

				InputStream in = new InputStream(data);

				int columnSize = in.readIntLE(AMOUNT_OF_COLUMNS_OFFSET);
				int rowSize = in.readIntLE(AMOUNT_OF_ROWS_OFFSET);

				int cellsOffset = HEADERS_OFFSET + columnSize * HEADER_SIZE;
				int rowByteSize = CELL_SIZE * columnSize;
				int stringsOffset = cellsOffset + rowSize * columnSize * CELL_SIZE;

				record HeaderData(int type, String rowName) { }

				List<HeaderData> headers = new ArrayList<>();

				for(int i = 0; i < columnSize; i++) {
					int headerOffset = HEADERS_OFFSET + i * HEADER_SIZE;
					StringData value = readStringValue(in, headerOffset);
					String rowName = readString(in, stringsOffset, value.offset);
					int type = in.readIntLE(headerOffset + 8);
					headers.add(new HeaderData(type, rowName));
				}

				List<Map<String, Object>> rows = new ArrayList<>();
				for(int i = 0; i < rowSize; i++) {
					Map<String, Object> cellData = new HashMap<>();
					for(int j = 0; j < columnSize; j++) {
						int cellOffset = cellsOffset + i * rowByteSize + j * CELL_SIZE;
						byte[] cellBytes = readCell(in, cellOffset);
						int type = headers.get(j).type;
						String rowName = headers.get(j).rowName;
						cellData.put(rowName, parseCellValue(in, cellBytes, type, stringsOffset));
					}
					rows.add(cellData);
				}

				String json = gson.toJson(rows);
				File decompiled = new File(DECOMPILED_BASE_PATH+file.getPath().replace(UNPACKED_BASE_PATH, "").replace("datasheet", "json"));
				File decompiledDir = new File(decompiled.getPath().replace(decompiled.getName(), ""));
				if(!decompiledDir.exists())
					decompiledDir.mkdirs();
				if(decompiled.exists()) decompiled.delete();
				decompiled.createNewFile();

				BufferedWriter writer = new BufferedWriter(new FileWriter(decompiled));
				System.out.println("Decompiled: "+file.getPath());
				writer.append(json);
				writer.flush();
				writer.close();

			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Object parseCellValue(InputStream dataIn, byte[] cellBytes, int type, int stringsOffset) {
		InputStream in = new InputStream(cellBytes);
		return switch(type) {
			case 1 -> readString(dataIn, stringsOffset, in.readIntLE());
			case 2 -> in.readFloatLE();
			case 3 -> in.readIntLE() != 0;
			default -> null;
		};
	}

	public static byte[] readCell(InputStream in, int offset) {
		in.offset = offset + 4;
		byte[] cellValue = new byte[4];
		in.readBytes(cellValue);

		return cellValue;
	}

	public static String readString(InputStream in, int stringsOffset, int offset) {
		offset = stringsOffset + offset;
		int lengthUntilNullTermination = 0;
		byte nextByte;
		do {
			nextByte = (byte) in.readByte(offset + lengthUntilNullTermination++);
		} while(nextByte != 0);
		in.offset = offset;
		byte[] stringData = new byte[lengthUntilNullTermination - 1];
		in.readBytes(stringData);

		return new String(stringData);
	}

	public static StringData readStringValue(InputStream in, int offset) {
		in.offset = offset;
		byte[] hash = new byte[4];
		in.readBytes(hash);

		int stringOffset = in.readIntLE();

		return new StringData(hash, stringOffset);
	}

	public static void decompileLua(File[] files) {
		for(File file : files) {
			if(file.isDirectory()) {
				File[] subFiles = file.listFiles();
				if(subFiles == null) continue;
				decompileLua(Arrays.stream(subFiles).filter(LUAC_FILTER).toArray(File[]::new));
				continue;
			}
			try {
				FileInputStream stream = new FileInputStream(file);
				byte[] data = stream.readAllBytes();
				stream.close();
				byte[] revised = new byte[data.length-2];
				System.arraycopy(data, 2, revised, 0, data.length-2);

				File compiled = new File(PACKED_LUA_BASE_PATH+file.getPath().replace(UNPACKED_BASE_PATH, ""));
				File decompiled = new File(DECOMPILED_BASE_PATH+file.getPath().replace(UNPACKED_BASE_PATH, "").replace("luac", "lua"));

				File compiledDir = new File(compiled.getPath().replace(compiled.getName(), ""));
				File decompiledDir = new File(decompiled.getPath().replace(decompiled.getName(), ""));
				if(!compiledDir.exists())
					compiledDir.mkdirs();
				if(!decompiledDir.exists())
					decompiledDir.mkdirs();
				if(decompiled.exists()) decompiled.delete();
				if(compiled.exists()) compiled.delete();
				compiled.createNewFile();
				decompiled.createNewFile();
				FileOutputStream out = new FileOutputStream(compiled);
				out.write(revised);
				out.flush();
				out.close();

				LuaDecompiler.decompile(compiled.getPath(), decompiled.getPath());
				System.out.println("Decompiled: "+file.getPath());
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void buildGson() {
		gson = new GsonBuilder()
				.serializeNulls()
				.setVersion(1.0)
				.disableHtmlEscaping()
				.setPrettyPrinting()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.create();
	}


}
