package com.cryo.utils;

import com.cryo.ConnectionManager;
import com.cryo.DBConnection;
import com.cryo.NewWorldWikiBuilder;
import com.cryo.entities.Asset;
import com.cryo.entities.AssetData;
import com.cryo.entities.AssetFolder;
import org.apache.commons.codec.digest.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetUtils {

	public static String getImagePath(String name) {
		name = name+".png";

		DBConnection connection = NewWorldWikiBuilder.getConnection();

		Asset asset = connection.selectClass("assets", "filename=?", Asset.class, name);
		if(asset == null) return null;

		if(asset.getFolderId() == null || asset.getFolderId() == 0)
			return "https://new-world.wiki/"+name;

		AssetFolder folder = connection.selectClass("assetFolders", "id=?", AssetFolder.class, asset.getFolderId());
		if(folder == null) return null;

		return "https://new-world.wiki/"+folder.getName()+"/"+name;
	}

	public static boolean uploadImage(String name, String path, File file) {
		try {
			BufferedImage image = ImageIO.read(file);
			if(image == null) return false;
			return uploadImage(name, path, new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static boolean uploadImage(String name, String path, InputStream stream) {

		try {
			DBConnection connection = NewWorldWikiBuilder.getConnection();

			Integer folderId = null;

			if (path != null) {
				if (path.startsWith("/")) path = path.replaceFirst("/", "");
				if (path.endsWith("/")) path = path.substring(0, path.lastIndexOf("/"));
				if (path.contains("/")) return false;

				AssetFolder folder = connection.selectClass("assetFolders", "name=?", AssetFolder.class, path);
				if(folder == null) {
					System.out.println("Creating folder path: "+path);
					folder = new AssetFolder(-1, path, path, null);
					folderId = connection.insert("assetFolders", folder);
				} else
					folderId = folder.getId();
			}

			byte[] data = stream.readAllBytes();

			if (!name.endsWith("png")) name = name + ".png";

			Asset asset = new Asset(-1, name, DigestUtils.sha1Hex(name), ".png", "image", "image/png", data.length, null, Utils.getTimestamp(), Utils.getTimestamp(), folderId, 5);
			int id = connection.insert("assets", asset);

			AssetData assetData = new AssetData(id, data);
			connection.insert("assetData", assetData);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;

	}

}
