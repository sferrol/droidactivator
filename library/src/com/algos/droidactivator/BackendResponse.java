/*
 * This file is part of DroidActivator.
 * Copyright (C) 2012 algos.it
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.algos.droidactivator;

import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

/**
 * Object representing a response from the Backend.
 */
public class BackendResponse {

	private BackendRequest request;
	private HashMap<String, Object> responseMap = new HashMap<String, Object>();
	private int httpResultCode = 0;


	public BackendResponse(BackendRequest request) {
		super();
		this.request = request;
		init();
	}


	private void init() {

		HttpResponse response=null;
		
		// send the request and retrieve http response code
		response = this.request.execute();
		this.httpResultCode = response.getStatusLine().getStatusCode();

		// if the call succeeded, put all response headers in the map
		if (isHttpSuccess()) {
			
			Header[] headers = response.getAllHeaders();
			
			for (int i = 0; i < headers.length; i++) {
				Header h = headers[i];
				this.responseMap.put(h.getName(), h.getValue());
			}
			
		}

	}


//	private HttpURLConnection getConnection() {
//		return this.request.getConnection();
//	}


	/**
	 * @return true if the Http call succeeded (at protocol level)
	 */
	boolean isHttpSuccess() {
		return ((this.httpResultCode >= 200) && (this.httpResultCode < 300));
	}


	/**
	 * @return true if the backend call succeeded (at backend logical)
	 */
	boolean isResponseSuccess() {
		boolean success = false;
		if (isHttpSuccess()) {
			String string = getString("success");
			if (Lib.getBool(string)) {
				success = true;
			}
		}

		return success;
	}


	/**
	 * @return true if the app is activated
	 */
	boolean isActivated() {
		return getBool(DroidActivator.KEY_ACTIVATED);
	}


	
	/**
	 * @return the expiration date
	 */
	long getExpirationTime() {
		return getLong(DroidActivator.KEY_EXPIRATION);
	}



	/**
	 * @return the app level
	 */
	int getLevel() {
		return getInt(DroidActivator.KEY_LEVEL);
	}


	/**
	 * Retuns a int from the response map.
	 * 
	 * @param key the key to search
	 * @return the int
	 */
	int getInt(String key) {
		int num=0;
		String string = Lib.getString(this.responseMap.get(key));
		try {
			num=Integer.parseInt(string);
		}
		catch (Exception e) {
		}
		
		//return Lib.getInt(this.responseMap.get(key));
		return num;
	}


	/**
	 * Retuns a long from the response map.
	 * 
	 * @param key the key to search
	 * @return the long
	 */
	long getLong(String key) {
		long num=0;
		String string = Lib.getString(this.responseMap.get(key));
		try {
			num=Long.parseLong(string);
		}
		catch (Exception e) {
		}
		
		//return Lib.getInt(this.responseMap.get(key));
		return num;
	}


	/**
	 * Retuns a boolean from the response map.
	 * 
	 * @param key the key to search
	 * @return the boolean
	 */
	boolean getBool(String key) {
		return Lib.getBool(this.responseMap.get(key));
	}


	/**
	 * Retuns a string from the response map.
	 * 
	 * @param key the key to search
	 * @return the string
	 */
	String getString(String key) {
		return Lib.getString(this.responseMap.get(key));
	}
}
