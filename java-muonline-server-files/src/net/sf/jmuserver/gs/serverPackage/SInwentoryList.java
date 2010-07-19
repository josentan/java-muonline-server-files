/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;
import java.util.Collection;
import net.sf.jmuserver.gs.muObiects.MuCharacterInventory;
import net.sf.jmuserver.gs.muObiects.MuStoreableItem;

/**
 *
 * @author Miki
 */
public class SInwentoryList extends ServerBasePacket {

    public final int ItemSize = 5;
    MuCharacterInventory _inw;

    public SInwentoryList(MuCharacterInventory inw) {
        super();
        _inw = inw;

    }

    private void makeHead(int ItemCout) {
        int size = 6 + (ItemCout * (ItemSize + 1));
        mC4Header(0xf3, 0x10, size);
        writeC(ItemCout);
    }

    private void makeSub(MuStoreableItem item) {
        int pos = item.getPosition();
        writeC(pos); //1
        writeB(item.toByteArray()); // 5
    //size 6
    }

    public byte[] getContent() throws IOException, Throwable {
//        byte t[] = {(byte) 0xc4, (byte) 0x00, (byte) 12, (byte) 0xf3, (byte) 0x10, (byte) 0x01, (byte) 0x0f, (byte) 0x20, (byte) 0x00, (byte) 0x12, (byte) 0x00, (byte) 0x00};
        Collection<MuStoreableItem> inw = _inw.getItems();
        makeHead(_inw.getCoutofItems());
        for (MuStoreableItem muStoreableItem : inw) {
            makeSub(muStoreableItem);
        }
        return getBytes();
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public boolean testMe() {
        return true;
    }
}
