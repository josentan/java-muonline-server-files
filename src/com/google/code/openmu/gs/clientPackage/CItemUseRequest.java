/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.code.openmu.gs.clientPackage;

import com.google.code.openmu.gs.ClientThread;

/**
 * 
 * @author Miki
 */
public class CItemUseRequest extends ClientBasePacket {

	private final int _slot; // sot w oknie
	private final int _wid; // id okna

	public CItemUseRequest(byte[] data, ClientThread _client) {
		super(data);
		_slot = data[1] & 0xff;
		_wid = data[2] & 0xff;
		System.out.println("Request use item fro window [" + _wid
				+ "] on slot[" + _slot + "]");

	}

	@Override
	public String getType() {
		return "";
	}
}
