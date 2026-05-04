package com.final_project.faculty_service.services;

import com.final_project.faculty_service.DTO.RepositoryDTO;
import com.final_project.faculty_service.DTO.mapper.ProjectMapper;
import com.final_project.faculty_service.DTO.request.ProjectRequest;
import com.final_project.faculty_service.DTO.response.PageResponse;
import com.final_project.faculty_service.DTO.response.ProjectResponse;
import com.final_project.faculty_service.models.Group;
import com.final_project.faculty_service.models.Project;
import com.final_project.faculty_service.models.Student;
import com.final_project.faculty_service.models.Teacher;
import com.final_project.faculty_service.repository.GroupRepository;
import com.final_project.faculty_service.repository.ProjectRepository;
import com.final_project.faculty_service.repository.StudentRepository;
import com.final_project.faculty_service.repository.TeacherRepository;
import com.final_project.faculty_service.services.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {
    private  final ProjectRepository projectRepository;
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final ProjectMapper projectMapper;
    private StudentRepository studentRepository;
    private final VersionContolService versionContolService;
    public PageResponse<ProjectResponse> findAll(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findByIsDeletedIsFalse(pageable);
        List<ProjectResponse> projectResponses = projectPage
                .getContent()
                .stream()
                .map(projectMapper::toResponse)
                .toList();
        return PageResponse.<ProjectResponse>builder()
                .data(projectResponses)
                .page(projectPage.getNumber())
                .size(projectPage.getSize())
                .totalElements(projectPage.getTotalElements())
                .totalPages(projectPage.getTotalPages())
                .last(projectPage.isLast())
                .build();
    }

    public ProjectResponse update(String id , ProjectRequest request){
        projectRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        Project project = projectMapper.toEntity(request);

        Teacher teacher = teacherRepository.findByIdAndIsDeletedIsFalse(request.getTeacher())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        project.setTeacher(teacher);
        Group group = groupRepository.findByIdAndIsDeletedIsFalse(request.getGroup())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        RepositoryDTO rep = versionContolService.getRpoById(request.getProjectRepository());
        if(rep == null){
            throw new ResourceNotFoundException("Repository Doesn't exist");
        }
        project.setProjectRepository(rep);
        project.setGroup(group);
        project.setId(id);

        projectRepository.save(project);
        return projectMapper.toResponse(project);
    }

    // group by student id
    public ProjectResponse getProjectByStudentId(String projectId, String studentId){
        studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("The student not found"));
        Project project = projectRepository.findProjectByIdAndStudentInGroup(projectId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return projectMapper.toResponse(project);
    }
    public ProjectResponse getProjectByTeacherId(String projectId, String studentId){
         teacherRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("The student not found"));
        Project project = projectRepository.findProjectByIdAndTeacher(projectId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return projectMapper.toResponse(project);
    }
    public List<ProjectResponse> getProjectsByTeacherId(String teacherId){
        Teacher teacher = teacherRepository.findByIdAndIsDeletedIsFalse(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher Not Found"));
        List<Project> projects = projectRepository.findAllByTeacherId(teacherId);
        return projects.stream().map(projectMapper::toResponse).collect(Collectors.toList());
    }

    public List<ProjectResponse> getProjectsByStudentId(String student){
        Student student1 = studentRepository.findByIdAndIsDeletedIsFalse(student)
                .orElseThrow(() -> new ResourceNotFoundException("The student not exist"));
        List<Project> projects = projectRepository.findAllByStudentId(student1.getId());
        return projects.stream().map(projectMapper::toResponse).collect(Collectors.toList());
    }
    public ProjectResponse findProjectByteacherAndStudent(String teacherId, String student){
        Student student1 = studentRepository.findByIdAndIsDeletedIsFalse(student)
                .orElseThrow(() -> new ResourceNotFoundException("The student not exist"));
        Teacher teacher = teacherRepository.findByIdAndIsDeletedIsFalse(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher Not Found"));
        Project project = projectRepository.findByTeacherIdAndStudentId(student1.getId(), teacher.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return projectMapper.toResponse(project);
    }

    public ProjectResponse findById(String id){
        Project current = projectRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return projectMapper.toResponse(current);
    }

    public ProjectResponse create(ProjectRequest request){
        Group group = groupRepository.findByIdAndIsDeletedIsFalse(request.getGroup())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        Teacher teacher = teacherRepository.findByIdAndIsDeletedIsFalse(request.getTeacher())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        RepositoryDTO repo = versionContolService.getRpoById(request.getProjectRepository());
        if (repo == null){
            throw new ResourceNotFoundException("Repo doesn't exist");
        }

        Project project = projectMapper.toEntity(request);
        project.setProjectRepository(repo);
        project.setTeacher(teacher);
        project.setGroup(group);
        projectRepository.save(project);

        return projectMapper.toResponse(project);
    }

    public void delete(String id){
        Project curr = projectRepository.findByIdAndIsDeletedIsFalse(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        curr.setDeleted(true);
        projectRepository.save(curr);
    }



}
