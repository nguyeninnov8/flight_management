package data_objects;

import business_object.CrewMember;
import java.util.ArrayList;
import java.util.List;

public class CrewMemberDao implements ICrewMemberDao{
    
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
    
}
