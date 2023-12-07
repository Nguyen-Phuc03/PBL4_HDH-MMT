
package com.raven.model;

import java.util.ArrayList;
import java.util.List;

public class Model_Group {
    private int groupID;
    private String groupName;
    private List<Model_User_Account> members;

    public Model_Group(int groupID, String groupName) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.members = new ArrayList<>();
    }

    // Getter and setter methods for groupID and groupName

    public List<Model_User_Account> getMembers() {
        return members;
    }

    public void addMember(Model_User_Account member) {
        members.add(member);
    }

    public void removeMember(Model_User_Account member) {
        members.remove(member);
    }
}