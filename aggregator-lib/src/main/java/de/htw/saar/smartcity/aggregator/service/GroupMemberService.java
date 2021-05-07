package de.htw.saar.smartcity.aggregator.service;

import de.htw.saar.smartcity.aggregator.entity.GroupMember;
import de.htw.saar.smartcity.aggregator.repository.GroupMemberRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class GroupMemberService {

    private GroupMemberRepository groupMemberRepository;

    public GroupMemberService(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }


    public Optional<GroupMember> findGroupMemberById(Long memberId) {
        return groupMemberRepository.findById(memberId);
    }

}