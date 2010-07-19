/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;
import java.util.ArrayList;
import net.sf.jmuserver.gs.muObiects.MuItemOnGround;
import net.sf.jmuserver.gs.muObiects.MuObiect;

/**
 *
 * @author Miki i Linka
 */
public class SMeetItemOnGround extends ServerBasePacket {

    ArrayList<MuObiect> _item;

    public SMeetItemOnGround(ArrayList<MuObiect> _items) {
        _item = _items;
    }

    public byte[] getContent() throws IOException, Throwable {
        int size = (9 * _item.size()) + 5; //(size of block items+ id+wsp)* itemscout + itemcontbit + head
        mC2Header(0x20, size);
        writeC(_item.size()); // count of items
        for (int i = 0; i < _item.size(); i++) {
            System.out.println("buduje sub item");
            if (_item.get(i) instanceof MuItemOnGround) {
                makeSub((MuItemOnGround) _item.get(i));
            }
        }
        return getBytes();
    }

    private void makeSub(MuItemOnGround i) {
        writeC(0x00);
        writeC(i.getObjectId());
        writeC(i.getX()); // write x pos 1 
        writeC(i.getY()); // write y pos 1
        writeB(i.getItemHex().toByteArray());// 5 bytes of item


    }

    public String getType() {
        return "meet itemson ground";
    }

    public boolean testMe() {
        return true;
    }
}
