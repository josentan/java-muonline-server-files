package net.sf.jmuserver.gs.muObjects;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javolution.util.FastMap;
import net.sf.jmuserver.gs.IdFactory;

/**
 * @author Miki
 * @since basicly class ofevery obiect in game
 */
public class MuObject {

    private MuMap _region;
    private short _ObiectId;
    protected short _myType; //00 player // 1 npc // 2 mob // 3 item // 4 ?
    protected short _x;// y
    protected short _y;// y
    private short _m;// map;
    private short _s; // status
    /**
     * list of knowns obiect
     */
    protected Map<Integer, MuObject> _knownObjects = new FastMap().setShared(true);
    /**
     * aet of players knowns
     */
    private Set _knownPlayer = new HashSet();

    public MuObject() {
    }

    /**
     * @param obiectId id obiektu
     * @param _x wsp x na mapie
     * @param _y wsp y na mapie
     * @param _m mapa
     */
    public MuObject(short obiectId, short _x, short _y, short _m) {
        super();
        _ObiectId = obiectId;
        this._x = _x;
        this._y = _y;
        this._m = _m;
    }

    /**
     * added new obiect to kowns
     * @param object added obiect
     */
    public void addKnownObject(MuObject object) {
        _knownObjects.put(object.getObjectId(), object);
        if (object instanceof MuPcInstance) {
            _knownPlayer.add(object);
        }
    }

    /**
     * added more thn one object to known obects
     * @param obj ector of object to add 
     */
    @SuppressWarnings("unchecked")
    public void addKnownObjects(Vector<MuObject> obj) {

        for (int i = 0; i < obj.size(); i++) {
            _knownObjects.put(obj.get(i).getObjectId(), obj.get(i));
            if (obj.get(i) instanceof MuPcInstance || obj.get(i) instanceof MuPcActorInstance) {
                _knownPlayer.add(obj.get(i));
            }
        }
    }

    /**
     * @param s change status on s
     */
    public void changeStatus(int s) {
        _s = (short) s;
    }

    /**
     * get actual map
     * @return return actual map
     */
    public MuMap getCurrentWorldRegion() {
        return _region;
    }

    /**
     * @return list of knowns players
     */
    public Map oldgetKnownObjects() {
        return _knownObjects;
    }

    /**
     * @return all knowns players
     */
    public Set getKnownPlayers() {
        return _knownPlayer;
    }

    /**
     * @return Bytecode of map
     */
    public int getM() {
        return _m;
    }

    public short getMyType() {
        return _myType;
    }

    /**
     * @return Obiect Id
     */
    public int getObjectId() {
        return _ObiectId;
    }

    /**
     * @return Status of object like where look for character and mobs
     */
    public short getStatus() {
        return _s;
    }

    /**
     * @return wsp x
     */
    public int getX() {
        return _x;
    }

    /**
     * @return wsp y
     */
    public int getY() {
        return _y;
    }

    /**
     * 
     * @return isvisibable on map ?
     */
    public boolean isVisible() {
        return true;
    }

    /**
     * remove all known objects
     */
    public void removeAllKnownObjects() {
        MuObject[] notifyList = _knownObjects.values().toArray(new MuObject[_knownObjects.size()]);
        // clear our own list
        _knownObjects.clear();

        for (int i = 0; i < notifyList.length; i++) {
            notifyList[i].removeKnownObject(this, RemKnow_NoRelasion);
        }
    }
    public static int RemKnow_NoRelasion = 0;//no send packages in pcinstance
    public static int RemKnow_ForgetID = 1;//send forget id package
    public static int RemKnow_DieId = 2;//send die package 

    /**
    * remove object from 'my' known list TO DO NO FORGET USE AFTER 
     * object.removeKnownObject(this,why) TO KEEP CLEAN KNOWNLISTS
     * @param object to removefrom knownlist
     * @param why remove object [swnd specifed packages in pcinstance]:
     * RemKnow_NoRelasion no send packages in pcinstance
     * RemKnow_ForgetID send forget id package in pcinstance
     * RemKnow_DieId send die package in pcinstanc
     */
    public void removeKnownObject(MuObject object, int why) {

        _knownObjects.remove(object.getObjectId());
        if (object instanceof MuPcInstance) {
            _knownPlayer.remove(object);
        }
    }

    /**
     * luking for  id in knownsobiet
     * @param Id to check inknown list
     * @return founded
     */
    public boolean searchID(int id) {  // to moveto MuObiectKnown LIst calss
        return _knownObjects.containsKey(id);
    }

    /**
     *method set actual region
     * @param Actual regio to set
     */
    public void setCurrentWorldRegion(MuMap region) {
        _region = region;
    }
    ;

    /**
     * ustawia mape = m
     * @param m
     */
    public void setM(byte m) {
        _m = m;
    }

    /**
     * ustawia id obiektu na id
     * @param id
     */
    public void setObiectId(short id) {
        _ObiectId = id;
    }

    /**
     * method to rand new id
     */
    public void RandObjectId() {
        _ObiectId = (short) IdFactory.getInstance().newId();
    }

    /**
     * @param s - status czyli gdzie obiekt patrzy itp
     */
    public void setStatus(int s) {
        _s = (short) s;
    }

    /**
     * ustawia x na _newx
     * @param _newx
     */
    public void setX(int _newx) {
        _x = (short) _newx;
    }

    /**
     * Set new Y pos
     * @param _newy
     */
    public void setY(int _newy) {
        _y = (short) _newy;
    }

    /**
     * update maps  after it changes
     */
    public void updateCurrentWorldRegion() {
        MuMap newRegion = MuWorld.getInstance().getMap(getM());
        if (!newRegion.equals(_region)) {
            if (_region != null) {
                _region.removeObject(this);
            }
            newRegion.addObject(this);
            _region = newRegion;
        }
    }

    /**
     * Fast set location inf on map
     * @param x x wsp
     * @param y wsp
     * @param f where look
     */
    public void SetPos(int x, int y, int f) {
        setX(x);
        setY(y);
        setStatus(f);
    }

    /**
     * method to get info somoene objet  see me so a'm can add it to my knowns
     * @param o
     */
    public void UseeMe(MuObject o) {
        int SeeId = o.getObjectId();
        if (!searchID(SeeId)) {
            addKnownObject(o);
        }
    // System.out.println("Gobiekt:" + SeeId + " toll me i will se it");

    }

    @Override
    public String toString() {
        return "[" + getM() + "][" + getObjectId() + "][" + getX() + "," + getY() + "][" + getClass().getSimpleName() + "]";
    }

//    /**
//     * basic method for spownobiect on map [ fistime]
//     */
//    public void ISpown() {
//        // System.out.println("Spown in Mu Obiect !");
//        MuWorld.getInstance().storeObject(this);
//        Vector v = getCurrentWorldRegion().getVisibleObjects(this);
//        for (Iterator it = v.iterator(); it.hasNext();) {
//            MuObject object = (MuObject) it.next();
//            object.addKnownObject(this); // update his to kowme
//            addKnownObject(object);//updateme to know his
//        }
//    }

    /**
     * 
     */
    public void ShowNyKnowList() {

        Collection oldlist = oldgetKnownObjects().values();
        System.out.println("KnowList of " + this);
        int i = 0;
        for (Object knowns : oldlist) {
            i++;
            System.out.println(i + ") " + knowns);
        }


    }
}