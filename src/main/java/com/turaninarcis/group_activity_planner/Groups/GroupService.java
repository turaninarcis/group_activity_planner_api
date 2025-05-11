package com.turaninarcis.group_activity_planner.Groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.Chat.ChatMessage;
import com.turaninarcis.group_activity_planner.Exceptions.PermissionException;
import com.turaninarcis.group_activity_planner.Exceptions.ResourceNotFoundException;
import com.turaninarcis.group_activity_planner.Exceptions.UserAlreadyJoinedException;
import com.turaninarcis.group_activity_planner.FileStorage.FileStorageService;
import com.turaninarcis.group_activity_planner.Groups.Models.Group;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupCreateDTO;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupDetailsDTO;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupMember;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupMemberDetailsDTO;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupMemberUpdateDTO;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupRoleEnum;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupSummaryDTO;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupUpdateDTO;
import com.turaninarcis.group_activity_planner.Users.UserService;
import com.turaninarcis.group_activity_planner.Users.Models.User;


@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMembersRepository groupMembersRepository;

    @Autowired 
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;


    public void createGroup(GroupCreateDTO groupCreateDTO) {
        String inviteToken = UUID.randomUUID().toString();
        Group group = new Group(groupCreateDTO.name(), groupCreateDTO.description(),inviteToken);
        Group savedGroup = groupRepository.save(group);

        User user = userService.getLoggedUser();
        GroupMember creator = new GroupMember(user, savedGroup);
        creator.setRole(GroupRoleEnum.CREATOR);

        groupMembersRepository.save(creator);
    }
    public void joinGroup(String groupToken){
        Group group = groupRepository.findByInviteToken(groupToken);
        if(group == null)
            throw new ResourceNotFoundException(Group.class.getSimpleName());
            
        User user = userService.getLoggedUser();
        GroupMember member = groupMembersRepository.findByUserAndGroup(user, group);
        if(member != null){
            if(member.isDeleted()==true){
                member.setDeleted(false);
                groupMembersRepository.save(member);
                return;
            }else throw new UserAlreadyJoinedException();
        }

        GroupMember groupMember = new GroupMember(user, group);
        groupMembersRepository.save(groupMember);
    }

    public void updateGroup(GroupUpdateDTO groupUpdateDTO, String groupId){
        UUID id = UUID.fromString(groupId);
        Group group=groupRepository.findById(id).orElse(null);
        isLoggedUserGroupAdmin(group);

        if(groupUpdateDTO.description() != null) group.setDescription(groupUpdateDTO.description());
        if(groupUpdateDTO.name() != null) group.setName(groupUpdateDTO.name());

        groupRepository.save(group);
    }

    public String updateGroupInviteToken(String groupId){
        UUID id = UUID.fromString(groupId);
        Group group=groupRepository.findById(id).orElse(null);
        isLoggedUserGroupAdmin(group);

        group.setInviteToken(UUID.randomUUID().toString());

        groupRepository.save(group);

        return group.getInviteToken();
    }

    public void updateGroupMember(GroupMemberUpdateDTO groupMemberUpdateDTO, String groupId){
        if(groupMemberUpdateDTO.role() == GroupRoleEnum.CREATOR) throw new PermissionException("You can't assign a new creator!");
        Group group = getGroup(groupId);

        isLoggedUserGroupAdmin(group);

        GroupMember member = groupMembersRepository.findById(UUID.fromString(groupMemberUpdateDTO.memberId())).orElse(null);

        member.setRole(groupMemberUpdateDTO.role());

        groupMembersRepository.save(member);
    }
    private Group getGroup(String groupId){
        UUID id;
        try{
            id = UUID.fromString(groupId);
        }catch(RuntimeException e){
            throw new ResourceNotFoundException("Group");
        }

        Group group=groupRepository.findById(id).orElse(null);
        if(group == null) throw new ResourceNotFoundException(Group.class.getSimpleName());
        return group;
    }
    private GroupMember getGroupMember(User user, Group group){
        GroupMember member = groupMembersRepository.findByUserAndGroup(user, group);
        if(member == null)
            throw new PermissionException("The user is not part of the group!");
        return member;
    }

    public void leaveGroup(String groupId){
        Group group = getGroup(groupId);
        GroupMember member = isLoggedUserGroupMember(group);
        if(!member.isDeleted()){
            member.setDeleted(true);
            groupMembersRepository.save(member);
            if(groupMembersRepository.findGroupMembersDetails(group.getId()).size()==0)
                deleteGroup(group);
        }
            
    }
    public void kickMember(String groupId, String memberId){
        Group group = getGroup(groupId);
        GroupMember groupMember = groupMembersRepository.findById(UUID.fromString(memberId)).orElse(null);
        if(groupMember == null) throw new ResourceNotFoundException("Group member");
        isLoggedUserGroupAdmin(group);

        groupMember.setDeleted(true);
        groupMembersRepository.save(groupMember);
    }

    public GroupDetailsDTO getGroupDetails(String groupId){
        Group group = getGroup(groupId);

        isLoggedUserGroupMember(group);

        Set<GroupMemberDetailsDTO> groupMembersDetails = groupMembersRepository.findGroupMembersDetails(group.getId());
        GroupDetailsDTO detailsDTO = new GroupDetailsDTO(group.getId().toString(),group.getName(),group.getDescription(), group.getInviteToken(),group.getCreated(), group.getLastUpdate(), groupMembersDetails);
        return detailsDTO;
    }

    public void deleteGroup(Group group){
        for (ChatMessage message : group.getMessages()) {
            if(message.getImage()!=null)
                fileStorageService.deleteImages(message.getImage());
        }
         groupRepository.delete(group);
    }

    public void deleteGroupByMember(String groupId){
        Group group = getGroup(groupId);
        isLoggedUserGroupAdmin(group);
        deleteGroup(group);
    }

    private GroupMember isLoggedUserGroupAdmin(Group group){
        GroupMember member = isLoggedUserGroupMember(group);

        if(!member.getRole().isAdmin())
            throw new PermissionException();
        
        return member;
    }
    private GroupMember isLoggedUserGroupMember(Group group){
        User user = userService.getLoggedUser();
        GroupMember member = getGroupMember(user, group);

        if(member==null  || member.isDeleted()==true)
            throw new PermissionException("You are not a member of this group! ");

        return member;
    }


    public GroupMember getGroupMemberByUsernameAndGroupId(String username, String groupId){
        return groupMembersRepository.findByUserUsernameAndGroupId(username, UUID.fromString(groupId));
    }

    public Group getGroupById(String id){
        return groupRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public List<GroupSummaryDTO> getJoinedGroups(){
        User user = userService.getLoggedUser();
        List<GroupSummaryDTO> groupSummaryList = new ArrayList<GroupSummaryDTO>();
        List<Group> groups= groupRepository.findAllByUserId(user.getId());
        for (Group group : groups) {
            groupSummaryList.add(new GroupSummaryDTO(group.getId(), group.getName()));
        }
        return groupSummaryList;
    }
}
