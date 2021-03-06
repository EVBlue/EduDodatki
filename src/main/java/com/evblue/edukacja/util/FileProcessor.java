package com.evblue.edukacja.util;

import com.evblue.edukacja.Main;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileProcessor {
	public static final String VARS_FILE = "config/EduDodatki/variables.cache";
	public static final String USERS_FILE = "config/EduDodatki/permitted.users";
	public static final String ADMIN_LOG_FILE = "config/EduDodatki/logs/admins.log";
	public static final String DEATH_LOG_FILE = "config/EduDodatki/logs/death/log";
	private static DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM");
	
	public static String getHash(File file) {
		String s = "miss";
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA1");
			InputStream is = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int read;
			while((read = is.read(buffer)) > 0)
				md.update(buffer, 0, read);
			byte[] md5 = md.digest();
			BigInteger bi = new BigInteger(1, md5);
			s = bi.toString(16);
			is.close();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}


    public static void writeVars() {
        BufferedWriter writer = null;
        File remote = new File(VARS_FILE);
        try {
            remote.getParentFile().mkdirs();
            writer = new BufferedWriter(new FileWriter(remote));
            for (String s : Main.dynStorage.vars_cache.keySet()) {
                writer.write(s + "$" + Main.dynStorage.vars_cache.get(s));
                writer.newLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readVars() {

        InputStream is = null;
        BufferedReader reader = null;
        File remote = new File(VARS_FILE);
        if (!remote.exists()) return;

        Main.logger.info("Reading variables cache...");

        try {
            is = new FileInputStream(remote);
            reader = new BufferedReader(new InputStreamReader(is));
            String s;
            while ((s = reader.readLine()) != null) {
                if (!s.isEmpty() && s.contains("$")) {
                    String[] splitted = s.split("\\$");
                    Main.dynStorage.vars_cache.put(splitted[0], splitted[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (reader != null) try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readLastLogLines() {
        Main.logger.info("Reading last log lines...");
        readLastLinesToList(ADMIN_LOG_FILE, 30, Main.dynStorage.admin_logs);
        readLastLinesToList(DEATH_LOG_FILE, 30, Main.dynStorage.last_deads);
    }

    public static void readLastLinesToList(String file, int lines, List<String> target) {
        RandomAccessFile f = null;
        long pos;
        int linesFound = 0;
        File remote = new File(file);
        if (!remote.exists()) return;
        try {
            f = new RandomAccessFile(remote, "r");
            pos = f.length();
            while (pos > 0) {
                f.seek(pos - 1);
                if ((char) f.readByte() == '\n') {
                    if (linesFound + 1 <= lines) {
                        linesFound++;
                    } else break;
                }
                pos--;
            }
            if (linesFound > 0) {
                f.seek(pos);
                String s;
                while ((s = f.readLine()) != null) {
                    target.add(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (f != null) try {
                f.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void appendToDeathLog(String str) {
        Date date = new Date();
        String s = "[" + dateFormat.format(date) + "]: " + str;
        Main.dynStorage.last_deads.add(s);
        if (Main.dynStorage.last_deads.size() > 50) Main.dynStorage.last_deads.remove(0);
        File remote = new File(DEATH_LOG_FILE);
        try {
            remote.getParentFile().mkdirs();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(remote, true)));
            out.println(s);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void appendToAdminLog(String str) {
        Date date = new Date();
        String s = "[" + dateFormat.format(date) + "]: " + str;
        Main.dynStorage.admin_logs.add(s);
        if (Main.dynStorage.admin_logs.size() > 50) Main.dynStorage.admin_logs.remove(0);
        File remote = new File(ADMIN_LOG_FILE);
        try {
            remote.getParentFile().mkdirs();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(remote, true)));
            out.println(s);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeUsers() {
        BufferedWriter writer = null;
        File remote = new File(USERS_FILE);
        try {
            remote.getParentFile().mkdirs();
            writer = new BufferedWriter(new FileWriter(remote));
            for (String s : Main.dynStorage.permissed_users) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readUsers() {

        InputStream is = null;
        BufferedReader reader = null;
        File remote = new File(USERS_FILE);
        if (!remote.exists()) return;

        Main.logger.info("Reading users...");

        try {
            is = new FileInputStream(remote);
            reader = new BufferedReader(new InputStreamReader(is));
            String s;
            while ((s = reader.readLine()) != null) {
                if (!s.isEmpty()) {
                    Main.dynStorage.permissed_users.add(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (reader != null) try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
