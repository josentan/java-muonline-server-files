import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author mikiones
 */
public class ServersRunner {

	// we need to deterene the working directory to .jmusrv at home directory

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("user.home"));
		// general dirctory structure
		// .jmuser/etc/etcfiles
		// /scripts
		// /log
		// /data
		final Properties GameSerer = new Properties();
		GameSerer.put("database.user", new String("postgresql"));
		GameSerer.put("databse.pass", new String("postgresql"));
		final ServersRunner s = new ServersRunner();
		s.createDirestories();
		s.copyData();
	}

	// GameServer gs = new GameServer();
	// FS fs = new FS();
	// gs.start();
	// fs.start();

	/**
	 * Create default directories structure in home directory for server usage
	 */
	public void createDirestories() {
		System.out.println("Create Server home directories structure...");
		final File HomeDir = new File(System.getProperty("user.home")
				+ "/.jmuserv/");
		final File etcDir = new File(HomeDir, "/etc/");
		final File dataDir = new File(HomeDir, "/data/");
		final File mapsDir = new File(dataDir, "/maps/");
		final File logDir = new File(HomeDir, "/logs/");
		final File scripts = new File(HomeDir, "/scripts/");
		if (HomeDir.mkdir()) {
			System.out.println(".jmuserv... OK");
		}
		if (etcDir.mkdir()) {
			System.out.println(".jmuserv/etc... OK");
		}
		if (dataDir.mkdir()) {
			System.out.println(".jmuserv/data... OK");
		}
		if (mapsDir.mkdir()) {
			System.out.println(".jmuserv/data/maps... OK");
		}
		if (logDir.mkdir()) {
			System.out.println(".jmuserv/logs... OK");
		}
		if (scripts.mkdir()) {
			System.out.println(".jmuserv/scripts... OK");
		}
	}

	/**
	 * Create default databases setting file and open editor to customise file
	 * ...
	 */
	public void createDefaultDatabaseConfFile() {
		final String defaultSett = "#Database settings:"
				+ "#to return to default just delate this file and serer will recreate it on next run\n\n"
				+ "#the url to databse \n"
				+ "database.Url=jdbc\\:postgresql\\:mu_online"
				+ "#user name \n\n" + "database.Login=postgresql \n"
				+ "#password  to databse\n" + "database.Password=password\n\n"
				+ "#driver default:org.postgresql.Driver\n"
				+ "database.Driver=org.postgresql.Driver\n";
		try {
			final File databaseConf = new File(System.getProperty("user.home")
					+ "/.jmuserv/etc/databse.ini");
			final FileWriter fw = new FileWriter(databaseConf);
			fw.write(defaultSett);
			fw.flush();
			fw.close();

			Desktop desktop = null;
			if (Desktop.isDesktopSupported()) {
				desktop = Desktop.getDesktop();
				try {
					desktop.edit(databaseConf);
				} catch (final IOException e) {
				}
			}
		} catch (final FileNotFoundException e) {
		} catch (final IOException e) {
		}
	}

	public static void copyFile(File in, File out) throws Exception {
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		try {
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (fis != null)
				fis.close();
			if (fos != null)
				fos.close();
		}
	}

	public void copyData() {
		File databaseTempl = new File("templates/database.templ");
		File databaseOut = new File(System.getProperty("user.home")
				+ "/.jmuserv/etc/database.ini");
		File GameserverTempl = new File("templates/GameServer.templ");
		File gameserverOut = new File(System.getProperty("user.home")
				+ "/.jmuserv/etc/GameServer.ini");
		File LoggerTempl = new File("templates/MuLog.templ");
		File LoggerOut = new File(System.getProperty("user.home")
				+ "/.jmuserv/etc/MuLog.ini");
		System.out.println("Coppy tempates settings to .jmuserv/etc/ diretory");
		try {
			copyFile(databaseTempl, databaseOut);
			copyFile(GameserverTempl, gameserverOut);
			copyFile(LoggerTempl, LoggerOut);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
