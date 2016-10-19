package com.google.appinventor.components.runtime.util;

import gnu.kawa.xml.ElementType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInstance {
    private String instanceId;
    private String leader;
    private Map<String, String> messageTimes;
    private List<String> players;

    public GameInstance(String instanceId) {
        this.players = new ArrayList(0);
        this.messageTimes = new HashMap();
        this.instanceId = instanceId;
        this.leader = ElementType.MATCH_ANY_LOCALNAME;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public String getLeader() {
        return this.leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public PlayerListDelta setPlayers(List<String> newPlayersList) {
        if (newPlayersList.equals(this.players)) {
            return PlayerListDelta.NO_CHANGE;
        }
        List<String> removed = this.players;
        List<String> added = new ArrayList(newPlayersList);
        this.players = new ArrayList(newPlayersList);
        added.removeAll(removed);
        removed.removeAll(newPlayersList);
        if (added.size() == 0 && removed.size() == 0) {
            return PlayerListDelta.NO_CHANGE;
        }
        return new PlayerListDelta(removed, added);
    }

    public List<String> getPlayers() {
        return this.players;
    }

    public String getMessageTime(String type) {
        if (this.messageTimes.containsKey(type)) {
            return (String) this.messageTimes.get(type);
        }
        return ElementType.MATCH_ANY_LOCALNAME;
    }

    public void putMessageTime(String type, String time) {
        this.messageTimes.put(type, time);
    }
}
