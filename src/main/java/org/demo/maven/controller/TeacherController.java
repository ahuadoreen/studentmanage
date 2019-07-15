package org.demo.maven.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.demo.maven.model.ResponseData;
import org.demo.maven.model.Subject;
import org.demo.maven.model.Teacher;
import org.demo.maven.respository.SubjectRespository;
import org.demo.maven.respository.TeacherRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TeacherController {
	@Autowired
	TeacherRespository teacherRespository;
    @Autowired
    private SubjectRespository subjectRespository;
	
	@RequestMapping(value = "/teacher/addTeacher", method = RequestMethod.POST)
	@ResponseBody
    public ResponseData addTeacher(final String name, final String[] subjectIds, final String age, Integer gender)
    {
        Set<Subject> subjects = new HashSet<>();
        for(int i=0;i<subjectIds.length;i++){
            Subject subject = subjectRespository.findOne(Long.valueOf(subjectIds[i]));
            subjects.add(subject);
        }
        // ���ݿ������һ���û����ò���ʱ����ˢ�»���,save()����������Ϻ�������Ȼ�ڻ�����δд�����ݿ�
		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setSubject(subjects);
		teacher.setAge(Integer.valueOf(age));
		teacher.setGender(gender);
        // ���ݿ������һ���û���������ˢ�»��沢д�����ݿ�
    	teacherRespository.saveAndFlush(teacher);
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }
	
	@RequestMapping(value = "/teacher/teachers", method = RequestMethod.GET)
	@ResponseBody
    public ResponseData getTeachers(final String name, final Integer index, final Integer size)
    {
        //��ѯteacher�������еļ�¼
//        List<Teacher> teacherList = teacherRespository.findAll();
        ///Sort.Direction�Ǹ�ö����ASC(����)��DESC(����)
        Sort sort = new Sort(Sort.Direction.ASC, "id");
///PageRequest�̳���AbstractPageRequest����ʵ����Pageable
///��ȡPageRequest���� index:ҳ�� ��0��ʼ  sizeÿҳ���� sort����ʽ "id"->properties ��˭Ϊ׼����
        Pageable pageable = new PageRequest(index, size, sort);
///Ҫƥ���ʵ������
        Teacher teacher = new Teacher();
        teacher.setName(name);
///����ƥ����� ƥ��"name"������� exact ��׼ƥ��
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Teacher> example = Example.of(teacher,exampleMatcher);
        Page<Teacher> page = teacherRespository.findAll(example, pageable);
///��ȡ��ҳ��
        int pageCount = page.getTotalPages();
///��ȡ��Ԫ�ظ���
        long total = page.getTotalElements();
///��ȡ�÷�ҳ���б�
        List<Teacher> teacherList = page.getContent();
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("pageCount", pageCount);
        responseData.putDataValue("total", total);
        responseData.putDataValue("teachers", teacherList);
        return responseData;
    }

    @RequestMapping(value = "/teacher/editTeacher", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData editTeacher(Long id, final String name, final String[] subjectIds, final String age, Integer gender)
    {
        Set<Subject> subjects = new HashSet<>();
        for(int i=0;i<subjectIds.length;i++){
            Subject subject = subjectRespository.findOne(Long.valueOf(subjectIds[i]));
            subjects.add(subject);
        }
        Teacher teacher = teacherRespository.findOne(id);
        teacher.setName(name);
        teacher.setSubject(subjects);
        teacher.setAge(Integer.valueOf(age));
        teacher.setGender(gender);
        teacherRespository.saveAndFlush(teacher);
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }

    @RequestMapping(value = "/teacher/deleteTeacher", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteTeacher(Long id)
    {
        teacherRespository.delete(id);
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }
}
