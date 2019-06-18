package com.qa.unsplash.test.API;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.util;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DownloadRandomPhotos {

	String folderPathToCreate = "/Users/alikozan/Documents/Picture/";
	File folder;

	@BeforeMethod
	public void setUp() throws IOException {
		System.out.println(util.getCurrentDate());
		System.out.println(folderPathToCreate + "/" +util.getCurrentDate() + "/" );

		folder = util.createFolder(folderPathToCreate, util.getCurrentDate());

	}

	@Test
	public void getRandomPhotoDemo() throws IOException {

		Response response = given().
				param("orientation", "landscape").
				param("count", "10").
				param("query","nature").
				queryParam("client_id", "1349ed4a4d9f4fd7512c64e270f7335e9669653eee17f34454075c160a28563a").
				when().
				get("https://api.unsplash.com/photos/random").
				then().statusCode(200).
				extract().response();

		// RAW TO STRING
		String responseString = response.asString();
		// System.out.println(responseString);

		// STRING TO JSON
		JsonPath responseJSON = new JsonPath(responseString);
		int imageArraySize = responseJSON.get("array.size()");
		System.out.println(imageArraySize);
		ArrayList<String> downloadLinks = responseJSON.get("urls.full");
		ArrayList<String> imageIDs = responseJSON.get("id");
		for (int j = 0; j < imageArraySize; j++) {
			util.saveImage(downloadLinks.get(j), folder.getAbsolutePath(), imageIDs.get(j)+".jpg");
			System.out.println("Image " + imageIDs.get(j) + " downloaded.");

		}

		

	}

	@AfterMethod
	public void tearUp() throws InterruptedException {

		Thread.sleep(10000);
		//util.deleteFolder(folder);
	}

}
