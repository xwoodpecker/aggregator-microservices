package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.repository.GroupMemberRepository;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupMember;
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