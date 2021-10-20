package com.cryo.decompilers;

import com.cryo.DecompilerAndBuilder;
import com.cryo.io.InputStream;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import static com.cryo.Constants.*;
import static com.cryo.DecompilerAndBuilder.*;

public class DatasheetDecompiler extends Decompiler {

	private record StringData(byte[] hash, int offset) {}

	public DatasheetDecompiler(String progressBarName) {
		super(progressBarName);
	}

	@Override
	public BiPredicate<Path, BasicFileAttributes> getFilter() {
		return (path, attr) -> FilenameUtils.getExtension(path.getFileName().toString()).equals("datasheet");
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

				String json = DecompilerAndBuilder.getGson().toJson(rows);
				File decompiled = new File(DECOMPILED_BASE_PATH+file.getPath().replace(UNPACKED_BASE_PATH, "").replace("datasheet", "json"));
				File decompiledDir = new File(decompiled.getPath().replace(decompiled.getName(), ""));
				if(!decompiledDir.exists())
					decompiledDir.mkdirs();
				if(decompiled.exists()) decompiled.delete();
				decompiled.createNewFile();

				BufferedWriter writer = new BufferedWriter(new FileWriter(decompiled));
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
}
