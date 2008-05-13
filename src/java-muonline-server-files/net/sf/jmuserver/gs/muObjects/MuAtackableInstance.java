package net.sf.jmuserver.gs.muObjects;

import java.util.HashMap;

import net.sf.jmuserver.gs.templates.MuNpc;

public class MuAtackableInstance extends MuNpcInstance {

    private int _moveRadius;
    private boolean _active;
    private HashMap _aggroList = new HashMap();

    public MuAtackableInstance(MuNpc tem) {
        super(tem);
    //_live=new MuHeal(50,3);
    }

    public boolean isActive() {
        return _active;
    }

    public void setActive(boolean b) {
        _active = b;
    }

    /**
     * @hmm
     */
    @Override
    public void IDie() {
     super.IDie();   
     System.out.println("iday in atackable instance");
    }
}
