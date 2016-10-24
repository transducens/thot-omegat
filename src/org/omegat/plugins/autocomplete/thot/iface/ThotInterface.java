package org.omegat.plugins.autocomplete.thot.iface;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ThotInterface {

	public static String whereIsThotClient = "thot_client";
	public static String ipThot = "127.0.0.1";

	public static String ThotTranslate(String s) {
		ProcessBuilder pb = new ProcessBuilder(whereIsThotClient, "-i", ipThot, "-sc", s.toLowerCase());
		Process proc = null;
		BufferedReader in = null;
		String toRet = "";
		try {
			proc = pb.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
		in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		try {
			String resultLine = in.readLine();
			if (resultLine != null) {
				toRet = resultLine;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
		return toRet;
	}

	public static String ThotInteractive(String prefix) {
		ProcessBuilder pb = new ProcessBuilder(whereIsThotClient, "-i", ipThot, "-rp");
		Process proc = null;
		BufferedReader in = null;
		String toRet = null;
		try {
			proc = pb.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		pb = new ProcessBuilder(whereIsThotClient, "-i", ipThot, "-ap", prefix);
		try {
			proc = pb.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}		
		in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		try {
			String resultLine = in.readLine();
			if (resultLine != null) {
				toRet = resultLine;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return toRet;
	}
}
