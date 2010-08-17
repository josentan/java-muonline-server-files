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

package com.google.code.openmu.gs.muObjects;

/**
 * 
 * @author Miki i Linka
 * @ToDO write all staff to using, auras
 */
public class MuAura {
	public static final byte NoAura = 0x00;

	/**
	 * 
	 * @return the modificator to HP
	 */
	public int getHpMod() {
		return 0;
	}

	/**
	 * 
	 * @return the Dmg modyficator
	 */
	public int getDmgMod() {
		return 0;
	}

	/**
	 * 
	 * @return modyficator to Defence
	 */
	public int getDefMod() {
		return 0;
	}

	/**
	 * 
	 * @return return the byte confersion of auras
	 */
	public byte toByte() {
		return 0x00;
	}

}