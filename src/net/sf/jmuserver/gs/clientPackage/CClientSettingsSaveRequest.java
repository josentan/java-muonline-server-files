package net.sf.jmuserver.gs.clientPackage;

import net.sf.jmuserver.gs.ClientThread;

public class CClientSettingsSaveRequest extends ClientBasePacket {


	public CClientSettingsSaveRequest(byte[] data, ClientThread _client) {
		super(data);
		_client.getClientSettings().fromByteArray(data, 2);
		_client.storeClientSettingsInDb();
	}

	@Override
	public String getType() {
		
		return null;
	}

}