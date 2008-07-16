package net.sf.jmuserver.gs.muObjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Logger;
import net.sf.jmuserver.gs.serverPackage.SGoneExp;
import net.sf.jmuserver.gs.serverPackage.SIdGoneDie;
import net.sf.jmuserver.gs.serverPackage.SNpcMiting;
import net.sf.jmuserver.gs.templates.MuNpc;
/**
 * Instance for Mobs 
 * @author Miki i Linka
 */
public class MuMonsterInstance extends MuAtackableInstance {

    private static Logger _log = Logger.getLogger(MuMonsterInstance.class.getName());
    private MuMobWalkArea _walkArea = null;
    private boolean _walkActive = false;

    public void setWalkArea(MuMobWalkArea w) {
        _walkArea = w;
    }

    private void onWalkTimer() {
        int nx = _walkArea.getRandX(getX());
        int ny = _walkArea.getRandY(getY());
        moveTo(nx, ny);
    }

    class RandomWalkingTask extends TimerTask {

        MuMonsterInstance _instance;

        public RandomWalkingTask(MuMonsterInstance c) {
            _instance = c;
        }

        public void run() {

            _instance.onWalkTimer();
            //System.out.println(_instance.getClass().getSimpleName() + ".RandomWalk.Run(): DOne");
        }
    }

    public MuMonsterInstance(MuNpc temp) {
        super(temp);
        setMaxHp(temp.getMaxHp());
        setCurentHp(temp.getMaxHp());
        _myType = 2;
      
    }
    private RandomWalkingTask _walkTask = null;
    private Timer _walkTimer = new Timer(true);

    @Override
    public void setTarget(MuObject t) {
        super.setTarget(t);
        System.out.println("Set target in mu monster");
    }

    @Override
    public void addKnownObject(MuObject object) {
        super.addKnownObject(object);
       // System.out.println("mmonster see new user");
        if (object instanceof MuPcInstance && !isActive()) {
            setActive(true);
        // startRandomWalking();
        //		if (isAggressive() && !isTargetScanActive())
        //	{
        //			startTargetScan();
        //		}
        }
    }

    public void removeKnownObject(MuObject object) {
        super.removeKnownObject(object);
        _log.finest("monster dont see obiects");
    }

    public void reduceCurrentHp(int i, MuCharacter c) {
        super.reduceCurrentHp(i, c);
        System.out.println("test");
        System.out.println("HP:(" + getCurentHp() + "/" + getMaxHp() + ").");
    }

    public void calculateReward() {
        int _who = getTargetID(); // get id
        long _exp = getExpReward(); // geting exp reward value
        //Item _item = getItemReward(); // geting item reward
        MuObject t=MuWorld.getInstance().findObject(_who);
        if(t instanceof MuPcInstance)
        {
            System.out.println("Reward for"+t);
            ((MuPcInstance)t).sendPacket(new SGoneExp(_who, (int)_exp));
        }
        
        System.out.println("calculate reward to :" + getTargetID() + " getting exp  :" + _exp);
        
    }

    @Override
    public void IDie() {

        super.IDie();
        calculateReward();
        System.out.println("Iday w MuMonster");
        broadcastPacket(new SIdGoneDie(getObjectId()));
        System.out.println("Starting Respown Task");
        startRespownTask();

    }

    public void startRandomWalking() {

        if (_walkTask == null) {
            _walkTask = new RandomWalkingTask(this);
            //System.out.println("start Random Walk");
            _walkTimer.scheduleAtFixedRate(_walkTask, 6000, 6000);
            _walkActive = true;
        }

    }

    public void stopRandomWalking() {
        if (_walkActive) {
            _walkTask.cancel();
            _walkTask = null;
            System.out.println("rex MP stopped");
            _walkActive = false;
        }
    }

    @Override
    /**
     * after added to map we get all characters near and send to them  its see as
     */
    public void ISpown() {
        super.ISpown();
       // System.out.println("Spown in MoMonsterInstance");
        Object[] players = getKnownPlayers().toArray();
        for (Object muPcInstance : players) {
           if(muPcInstance instanceof MuPcInstance)
               ((MuPcInstance)muPcInstance).UseeMe(this);
        }
       
    }

    @Override
    public void moveTo(int x, int y) {
       
          updateKnownsLists();
           super.moveTo(x, y);
    }

    @Override
    public void updateKnownsLists() {
    
    
     //new lists
        ArrayList<MuObject> Mob = new ArrayList<MuObject>();
        Mob.add(this);
        ArrayList<MuObject> _toForget = new ArrayList<MuObject>();

        Collection oldlist = oldgetKnownObjects().values();
        //secend look for new object and swich it to lists and add also to known list
        Vector visitable = getCurrentWorldRegion().getVisibleObjects(this);
        for (Iterator it = visitable.iterator(); it.hasNext();) {
            MuObject checked = (MuObject) it.next();
            if (checked.getObjectId() == getObjectId()) {
                continue; // if we are next
            }

            if (oldlist.contains(checked)) {
                continue; // allready kow him
            }
                  //if is player
            if (checked instanceof MuPcInstance) {
                
                System.out.println("player meet " + checked);
                ((MuPcInstance)checked).sendPacket(new SNpcMiting(Mob) );
                addKnownObject(checked);
       
        }
        }
        //check old list of known objects for obj that are no longer visible and remove them
        for ( Iterator<MuObject> it = oldlist.iterator(); it.hasNext();) {
            MuObject muObject = it.next();
            if (!visitable.contains(muObject)) {
                //_toForget.add(muObject);
                removeKnownObject(muObject);
            }
        }        
        //now wi have all knowns, and to forget objects
        //so send packages
        
        
    }
}
    

    

