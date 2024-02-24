package org.choongang.subtask.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.constants.BType;
import org.choongang.commons.constants.Status;
import org.choongang.commons.entities.BaseMember;
import org.choongang.member.entities.Member;
import org.choongang.project.entities.Project;
import org.hibernate.annotations.Fetch;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Subtask extends BaseMember {
    @Id @GeneratedValue
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pSeq")
    private Project project;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_subtask")
    private List<Member> member = new ArrayList<>();    // 참여자

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private BType bType = BType.TODOLIST;

    private Status status = Status.REQUEST ;

    private boolean isNotice = false;
}
