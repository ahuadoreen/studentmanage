package org.demo.maven.controller;

import org.demo.maven.model.*;
import org.demo.maven.respository.ClassRepository;
import org.demo.maven.respository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private ClassRepository classRepository;

    @RequestMapping(value = "/student/addStudent", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addStudent(final String sno, final String name, final Long classId, final String age, Integer gender)
    {
        // 数据库中添加一个用户，该步暂时不会刷新缓存,save()方法处理完毕后，数据依然在缓冲区未写入数据库
        Student student = new Student();
        student.setSno(sno);
        student.setName(name);
        if(classId != null){
            Classes classes = classRepository.findOne(classId);
            student.setClasses(classes);
        }
        student.setAge(Integer.valueOf(age));
        student.setGender(gender);
        // 数据库中添加一个用户，并立即刷新缓存并写入数据库
        studentRepository.saveAndFlush(student);
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }

    @RequestMapping(value = "/student/students", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getStudents(final String name, final Integer index, final Integer size)
    {
        //查询teacher表中所有的记录
//        List<Teacher> teacherList = teacherRespository.findAll();
        ///Sort.Direction是个枚举有ASC(升序)和DESC(降序)
        Sort sort = new Sort(Sort.Direction.ASC, "id");
///PageRequest继承于AbstractPageRequest并且实现了Pageable
///获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
        Pageable pageable = new PageRequest(index, size, sort);
///要匹配的实例对象
        Student student = new Student();
        student.setName(name);
///定义匹配规则 匹配"name"这个属性 exact 精准匹配
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Student> example = Example.of(student,exampleMatcher);
        Page<Student> page = studentRepository.findAll(example, pageable);
///获取总页数
        int pageCount = page.getTotalPages();
///获取总元素个数
        long total = page.getTotalElements();
///获取该分页的列表
        List<Student> studentList = page.getContent();
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("pageCount", pageCount);
        responseData.putDataValue("total", total);
        responseData.putDataValue("students", studentList);
        return responseData;
    }

    @RequestMapping(value = "/student/editStudent", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData editStudent(Long id, final String sno, final String name, final Long classId, final String age, Integer gender)
    {
        Student student = studentRepository.findOne(id);
        student.setSno(sno);
        student.setName(name);
        if(classId != null){
            Classes classes = classRepository.findOne(classId);
            student.setClasses(classes);
        }
        student.setAge(Integer.valueOf(age));
        student.setGender(gender);
        studentRepository.saveAndFlush(student);
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }

    @RequestMapping(value = "/student/deleteStudent", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteStudent(Long id)
    {
        studentRepository.delete(id);
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }
}
