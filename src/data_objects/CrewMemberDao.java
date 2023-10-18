package data_objects;

import business_object.CrewMember;
import java.util.ArrayList;
import java.util.List;
import utils.HandlingFile;

public class CrewMemberDao implements ICrewMemberDao{
    private final String CREWMEMBER_FILEPATH = "src\\crewMember.dat";
    private List<CrewMember> crewMemberList;

    public CrewMemberDao() {
        crewMemberList = new ArrayList<>();
    }

    @Override
    public List<CrewMember> getAll() {
        return this.crewMemberList;
    }

    @Override
    public CrewMember getCrewMember(String id) {
       for(CrewMember member: crewMemberList){
            if(member.getId().equals(id)){
                return member;
            }
        }
        return null;
    }

    @Override
    public boolean addCrewMember(CrewMember member) {
        return crewMemberList.add(member);
    }

    @Override
    public boolean deleteCrewMember(CrewMember member) {
        return crewMemberList.remove(member);
    }

    @Override
    public List<CrewMember> getAvailableMembers() {
        List<CrewMember> availableMembers = new ArrayList<>();
        for(CrewMember member: crewMemberList){
            if(member.isIsAvailable()){
                availableMembers.add(member);
            }
        }
        return availableMembers;
    }

    @Override
    public CrewMember getAvailabeMember(String id) {
        for(CrewMember member: getAvailableMembers()){
            if(member.getId().equals(id)){
                return member;
            }
        }
        return null;
    }

    @Override
    public boolean saveToFile() {
        return new HandlingFile<CrewMember>().saveToFile(CREWMEMBER_FILEPATH, crewMemberList);
    }

    @Override
    public boolean loadFromFile() {
        return new HandlingFile<CrewMember>().loadFromFile(CREWMEMBER_FILEPATH, crewMemberList);
    }
    
}
