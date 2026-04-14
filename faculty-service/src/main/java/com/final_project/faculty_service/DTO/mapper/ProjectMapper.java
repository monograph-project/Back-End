package com.final_project.faculty_service.DTO.mapper;

import com.final_project.faculty_service.DTO.request.ProjectRequest;
import com.final_project.faculty_service.DTO.response.*;
import com.final_project.faculty_service.models.Group;
import com.final_project.faculty_service.models.Project;
import com.final_project.faculty_service.models.Teacher;
import com.final_project.faculty_service.services.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper implements BaseMapper<ProjectRequest, ProjectResponse, Project> {
    @Override
    public Project toEntity(ProjectRequest request) {
        Project project = new Project();

        project.setProjectName(request.getProjectName());
        project.setProjectRepository(request.getProjectRepository());

        Teacher teacher =  new Teacher();
        teacher.setId(request.getTeacher());
        project.setTeacher(teacher);

        Group group = new Group();
        group.setId(request.getGroup());
        project.setTeacher(teacher);

        return project;
    }

    @Override
    public ProjectResponse toResponse(Project entity) {
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setId(entity.getId());
        projectResponse.setProjectName(entity.getProjectName());
        projectResponse.setProjectRepository(entity.getProjectRepository());
        projectResponse.setGroup(
                new ProjectGroupResponse(
                        entity.getGroup().getId(),
                        entity.getGroup().getName(),
                        entity.getGroup()
                                .getGroupMembers()
                                .stream()
                                .map((member) -> {
                                   return  new GroupMemberResponse(
                                            member.getId(),
                                           member.getFirstName(),
                                           member.getLastName(),
                                           member.getCode(),
                                           member.getEmail(),
                                           member.getPhone(),
                                           member.getKankorId(),
                                           member.getProfilePicture()
                                    );
                                }).toList()
                )
        );
        projectResponse.setTeacher(
                new GroupTeacherResponse(
                        entity.getTeacher().getId(),
                        entity.getTeacher().getFirstName(),
                        entity.getTeacher().getLastName(),
                        entity.getTeacher().getFatherName(),
                        entity.getTeacher().getDateOfBirth(),
                        entity.getTeacher().getEmail(),
                        entity.getTeacher().getPhone(),
                        entity.getTeacher().getCode()
                )
        );

        return projectResponse;
    }
}
