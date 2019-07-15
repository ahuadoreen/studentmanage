package org.demo.maven.controller;

import org.demo.maven.model.ResponseData;
import org.demo.maven.model.Subject;
import org.demo.maven.model.Teacher;
import org.demo.maven.respository.SubjectRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SubjectController {
    @Autowired
    private SubjectRespository subjectRespository;

    @RequestMapping(value = "/subject/addSubject", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addSubject(final String name){
        Subject subject = new Subject();
        subject.setName(name);
        subjectRespository.saveAndFlush(subject);
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }

    @RequestMapping(value = "/subject/subjects", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getSubjects(final String name, final Integer index, final Integer size)
    {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(index, size, sort);
        Subject subject = new Subject();
        subject.setName(name);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Subject> example = Example.of(subject,exampleMatcher);
        Page<Subject> page = subjectRespository.findAll(example, pageable);
        int pageCount = page.getTotalPages();
        long total = page.getTotalElements();
        List<Subject> subjectList = page.getContent();
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("pageCount", pageCount);
        responseData.putDataValue("total", total);
        responseData.putDataValue("subjects", subjectList);
        return responseData;
    }

    @RequestMapping(value = "/subject/editSubject", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData editTeacher(Long id, final String name)
    {
        Subject subject = subjectRespository.findOne(id);
        subject.setName(name);
        subjectRespository.saveAndFlush(subject);
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }

    @RequestMapping(value = "/subject/deleteSubject", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteSubject(Long id)
    {
        subjectRespository.delete(id);
        ResponseData responseData = ResponseData.ok();
        return responseData;
    }
}
