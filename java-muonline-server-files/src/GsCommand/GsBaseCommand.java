/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GsCommand;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.serverPackage.SPublicMsg;

/**
 * base COmmanf clas 
 * @author Miki i Linka
 */
public abstract class GsBaseCommand {

    private String _command = "";

    /**
     * return the client theard for user who run command
     * @return
     */
    public GsBaseCommand() {
    }

    /**
     * the command run procedure
     * @return
     */
    abstract public boolean RunCommand();
    protected ClientThread _cli;

    public void ParseArgs(String[] args) {
    }

    public void SetClientTheard(ClientThread _cli) {
        this._cli = _cli;
    }

    /**
     * @return command string
     */
    abstract public String getCmdString();

    /**
     * 
     * @return Help to command
     */
    abstract public String getHelpToCommand();

    /**
     * 
     * @return shortdescrysion of command
     */
    abstract public String getShortDesc();

    /**
     * Send debug informacions to stdout and to client
     * @param s 
     */
   
    protected void SendDbgMsg(String s) {
        System.out.println(getCmdString() + " : " + s);
//        try {
//            _cli.getConnection().sendPacket(new SPublicMsg(getCmdString(), s));
//        } catch (IOException ex) {
//            Logger.getLogger(GsBaseCommand.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Throwable ex) {
//            Logger.getLogger(GsBaseCommand.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
