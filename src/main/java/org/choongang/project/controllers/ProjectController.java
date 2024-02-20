package org.choongang.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.BadRequestException;
import org.choongang.commons.rests.JSONData;
import org.choongang.member.MemberUtil;
import org.choongang.project.entities.Project;
import org.choongang.project.service.ProjectInfoService;
import org.choongang.project.service.SaveProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final SaveProjectService saveService;
    private final ProjectInfoService infoService;
    private final MemberUtil memberUtil;

    /**
     * 프로젝트 상세 보기에 데이터를 전달할 컨트롤러
     */
    @GetMapping
    public JSONData<Project> projectView(@RequestParam("projectSeq") Long projectSeq) {
        Project project = infoService.viewOne(projectSeq);

        JSONData<Project> item = new JSONData<>(project);
        return item;
    }

    /**
     * 새 프로젝트 생성
     */
    @PostMapping("/new")
    public ResponseEntity<JSONData<Object>> newProject(@RequestBody @Valid RequestProjectForm form, Errors errors) {

        saveService.newProject(form, errors);
        errorProcess(errors);

        HttpStatus status = HttpStatus.CREATED;
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(true);
        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }

    private void errorProcess(Errors errors) {
        if (errors.hasErrors()) {
            throw new BadRequestException(Utils.getMessages(errors));
        }
    }

    /**
     * 현재 로그인 중인 회원이 참여 중인 프로젝트 목록 반환
     */
    @GetMapping("/list")
    public ResponseEntity<JSONData<Object>> list() {
        Long memberSeq = memberUtil.getMember().getSeq();
        List<Project> projects =  infoService.getProjects(memberSeq);

        HttpStatus status = HttpStatus.OK;
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(true);
        data.setStatus(status);
        data.setData(projects);

        return ResponseEntity.status(status).body(data);
    }

    /**
     * 프로젝트 설정 수정
     */
    @GetMapping("/edit")
    public void editProject() {}

    /**
     * 프로젝트에 참여자 초대
     */
    @GetMapping("/invite")
    public void inviteMember() {}

    /**
     * 프로젝트 삭제
     */
    @GetMapping("delete")
    public void deleteProject() {}
}
