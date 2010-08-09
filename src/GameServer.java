import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import net.sf.jmuserver.gs.MuClientSession;
import net.sf.jmuserver.gs.CommandHandler;
import net.sf.jmuserver.gs.GameServerConfig;
import net.sf.jmuserver.gs.muObjects.MuWorld;
import net.sf.jmuserver.logger.MuLoggerMennager;

public class GameServer extends Thread {
	// Socket listener

	private ServerSocket _serverSocket;
	// Ip adresses
	private String _ip;
	// Port listener
	public static int _port;
	// GS Global Logger
	static Logger GSLogger = Logger.getLogger("GameServer");

	public static void main(String[] args) throws Exception {
		MuLoggerMennager.getInstance();
		GSLogger.info("WorkingDir: " + System.getProperty("user.dir"));
		final GameServer server = new GameServer();

		GSLogger.info("GameServer Listening on port " + _port);
		server.start();// runing GS
	}

	@Override
	public void run() {
		GSLogger.info("Init Regions:...");
		MuWorld.getInstance().initWorld();
		GSLogger.info("Init Gates.....");
		MuWorld.getInstance().InitGates();
		CommandHandler.getInstancec();
		while (true) {
			try {
				final Socket connection = _serverSocket.accept();
				//new MuClientSession(connection);
			} catch (final IOException e) {
				// not a real problem
			}
		}
	}

	public long getUsedMemoryMB() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
				.freeMemory()) / 1024 / 1024;
	}

	public GameServer() throws Exception {
		super("AppMain");
		GameServerConfig.getInstance();

		_ip = GameServerConfig.gs.getProperty("gs.ip");
		_port = Integer.parseInt((GameServerConfig.gs.getProperty("gs.port")));

		System.out.println("used mem:" + getUsedMemoryMB() + "MB");

		final String hostname = "*";

	

		if (!"*".equals(hostname)) {
			final InetAddress adr = InetAddress.getByName(hostname);
			_ip = adr.getHostAddress();
			_serverSocket = new ServerSocket(_port, 50, adr);
			
		} else {
			_serverSocket = new ServerSocket(_port);
			
		}

		
	}
}
