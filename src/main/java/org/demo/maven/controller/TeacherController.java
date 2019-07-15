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
        // 数据库中添加一个用户，该步暂时不会刷新缓存,save()方法处理完毕后，数据依然在缓冲区未写入数据库
		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setSubject(subjects);
		teacher.setAge(Integer.valueOf(age));
		teacher.setGender(gender);
        // 数据库中添加一个用户，并立即刷新缓存并写入数据库
    	teacherRespository.saveAndFlush(teacher);
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }
	
	@RequestMapping(value = "/teacher/teachers", method = RequestMethod.GET)
	@ResponseBody
    public ResponseData getTeachers(final String name, final Integer index, final Integer size)
    {
        //查询teacher表中所有的记录
//        List<Teacher> teacherList = teacherRespository.findAll();
        ///Sort.Direction是个枚举有ASC(升序)和DESC(降序)
        Sort sort = new Sort(Sort.Direction.ASC, "id");
///PageRequest继承于AbstractPageRequest并且实现了Pageable
///获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
        Pageable pageable = new PageRequest(index, size, sort);
///要匹配的实例对象
        Teacher teacher = new Teacher();
        teacher.setName(name);
///定义匹配规则 匹配"name"这个属性 exact 精准匹配
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Teacher> example = Example.of(teacher,exampleMatcher);
        Page<Teacher> page = teacherRespository.findAll(example, pageable);
///获取总页数
        int pageCount = page.getTotalPages();
///获取总元素个数
        long total = page.getTotalElements();
///获取该分页的列表
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
