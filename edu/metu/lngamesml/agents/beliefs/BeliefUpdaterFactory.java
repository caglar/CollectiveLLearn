package edu.metu.lngamesml.agents.beliefs;

import edu.metu.lngamesml.agents.com.CategoricalComm;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 12/31/10
 * Time: 1:26 AM
 * To change this template use File | Settings | File Templates.
 */

public class BeliefUpdaterFactory {

    private static SpeakerBeliefUpdater SBUpdater = null;
    private static HearerBeliefUpdater HBUpdater = null;

    private BeliefUpdaterFactory(){}

    public static void init(){
        if (SBUpdater == null) {
            SBUpdater = new SpeakerBeliefUpdater();
        }
        if (HBUpdater == null) {
            HBUpdater = new HearerBeliefUpdater();
        }
    }

    public static void updateBeliefs(CategoricalComm sCatComm, CategoricalComm hCatComm, SigmoidFunctionTypes sigFunType, boolean isSuccess) {
            SBUpdater.updateBeliefs(sCatComm, hCatComm, sigFunType, isSuccess);
            HBUpdater.updateBeliefs(sCatComm, hCatComm, sigFunType, isSuccess);
            sCatComm.setConfidence(SBUpdater.getBelief());
            hCatComm.setConfidence(HBUpdater.getBelief());
    }

    public static double getSpeakersBelief(){
        double belief = SBUpdater.getBelief();
        return belief;
    }

    public static double getHearersBelief(){
        double belief = HBUpdater.getBelief();
        return belief;
    }
}
